package vn.hblab.crm.api.company;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vn.hblab.crm.crmcore.application.CompanyService;
import vn.hblab.crm.domain.ActorType;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> create(@Valid @RequestBody CompanyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CompanyResponse.from(companyService.create(request.toCommand())));
    }

    @GetMapping
    public List<CompanyResponse> list() {
        return companyService.list().stream().map(CompanyResponse::from).toList();
    }

    @GetMapping("/{id}")
    public CompanyResponse get(@PathVariable long id) {
        return CompanyResponse.from(companyService.get(id));
    }

    @PutMapping("/{id}")
    public CompanyResponse update(@PathVariable long id, @Valid @RequestBody CompanyRequest request) {
        return CompanyResponse.from(companyService.update(id, request.toCommand()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        companyService.delete(id, ActorType.HUMAN);
    }
}
