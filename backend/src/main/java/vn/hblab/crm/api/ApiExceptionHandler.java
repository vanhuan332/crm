package vn.hblab.crm.api;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.hblab.crm.crmcore.application.CompanyNotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> validation(MethodArgumentNotValidException exception) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            fieldErrors.putIfAbsent(error.getField(), error.getDefaultMessage());
        }
        return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", fieldErrors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ApiError> unreadableRequest() {
        return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", Map.of("companyType", "Invalid company type"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ApiError> domainValidation(IllegalArgumentException exception) {
        String message = exception.getMessage();
        String field = message == null || message.isBlank() ? "request" : message.split(" ", 2)[0];
        return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", Map.of(field, message));
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    ResponseEntity<ApiError> companyNotFound() {
        return error(HttpStatus.NOT_FOUND, "COMPANY_NOT_FOUND", Map.of());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<ApiError> dataIntegrityViolation(DataIntegrityViolationException exception) {
        if (hasConstraint(exception, "uq_companies_active_normalized_name")) {
            return error(HttpStatus.CONFLICT, "COMPANY_NAME_CONFLICT", Map.of());
        }
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "INTEGRITY_VIOLATION", Map.of());
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    ResponseEntity<ApiError> companyModified() {
        return error(HttpStatus.CONFLICT, "COMPANY_MODIFIED", Map.of());
    }

    private ResponseEntity<ApiError> error(HttpStatus status, String code, Map<String, String> fieldErrors) {
        return ResponseEntity.status(status).body(new ApiError(code, fieldErrors));
    }

    private boolean hasConstraint(Throwable throwable, String expectedConstraint) {
        Throwable current = throwable;
        while (current != null) {
            if (current.getMessage() != null
                    && current.getMessage().contains("constraint \"" + expectedConstraint + "\"")) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }

    record ApiError(String code, Map<String, String> fieldErrors) { }
}
