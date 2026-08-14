package vn.hblab.crm.crmcore.infrastructure;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.hblab.crm.crmcore.application.CompanyRepository;
import vn.hblab.crm.domain.Company;

public interface CompanyJpaRepository extends JpaRepository<Company, Long>, CompanyRepository {
    Optional<Company> findByIdAndDeletedAtIsNull(long id);
    List<Company> findAllByDeletedAtIsNull();
}
