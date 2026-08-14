package vn.hblab.crm.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ApiExceptionHandlerTest {
    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    void mapsActiveCompanyNameConstraintToConflict() {
        ResponseEntity<ApiExceptionHandler.ApiError> response = handle(
                new DataIntegrityViolationException("duplicate", duplicateKey("uq_companies_active_normalized_name")));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().code()).isEqualTo("COMPANY_NAME_CONFLICT");
    }

    @Test
    void mapsUnrelatedIntegrityViolationToGenericError() {
        ResponseEntity<ApiExceptionHandler.ApiError> response = handle(
                new DataIntegrityViolationException("constraint", duplicateKey("unrelated_constraint")));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().code()).isEqualTo("INTEGRITY_VIOLATION");
    }

    private Throwable duplicateKey(String constraint) {
        return new org.postgresql.util.PSQLException(
                "ERROR: duplicate key value violates unique constraint \"" + constraint + "\"",
                org.postgresql.util.PSQLState.UNIQUE_VIOLATION);
    }

    @SuppressWarnings("unchecked")
    private ResponseEntity<ApiExceptionHandler.ApiError> handle(DataIntegrityViolationException exception) {
        try {
            Method method = ApiExceptionHandler.class.getDeclaredMethod("dataIntegrityViolation",
                    DataIntegrityViolationException.class);
            return (ResponseEntity<ApiExceptionHandler.ApiError>) method.invoke(handler, exception);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e) {
            throw new AssertionError(e.getCause());
        }
    }
}
