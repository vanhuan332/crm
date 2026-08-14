package vn.hblab.crm.crmcore.application;

import java.util.List;
import java.util.Optional;
import vn.hblab.crm.domain.Company;

public interface CompanyRepository {
    Company save(Company company);

    Optional<Company> findByIdAndDeletedAtIsNull(long id);

    List<Company> findAllByDeletedAtIsNull();
}
