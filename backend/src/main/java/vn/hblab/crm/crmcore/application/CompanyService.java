package vn.hblab.crm.crmcore.application;

import java.util.List;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hblab.crm.application.AutomationPolicyGuard;
import vn.hblab.crm.domain.ActorType;
import vn.hblab.crm.domain.Company;

@Service
@Transactional
public class CompanyService {
    private final CompanyRepository repository;
    private final AutomationPolicyGuard guard;

    public CompanyService(CompanyRepository repository, AutomationPolicyGuard guard) {
        this.repository = repository;
        this.guard = guard;
    }
    public Company create(CompanyCommand command) {
        return repository.save(Company.create(command.name(), command.industry(), command.companyType(),
                command.country(), command.website(), command.phone(), command.address(), command.description()));
    }
    @Transactional(readOnly = true)
    public List<Company> list() { return repository.findAllByDeletedAtIsNull(); }
    @Transactional(readOnly = true)
    public Company get(long id) { return activeCompany(id); }
    public Company update(long id, CompanyCommand command) {
        Company company = activeCompany(id);
        if (command.version() != company.getVersion()) {
            throw new OptimisticLockingFailureException("Company " + id + " has been modified");
        }
        company.update(command.name(), command.industry(), command.companyType(), command.country(),
                command.website(), command.phone(), command.address(), command.description());
        return company;
    }
    public void delete(long id, ActorType actor) {
        guard.assertCanDelete(actor);
        activeCompany(id).softDelete();
    }
    private Company activeCompany(long id) {
        return repository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new CompanyNotFoundException(id));
    }
}
