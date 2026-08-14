package vn.hblab.crm.crmcore.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;
import vn.hblab.crm.application.AutomationPolicyGuard;
import vn.hblab.crm.application.AutomationPolicyViolation;
import vn.hblab.crm.domain.ActorType;
import vn.hblab.crm.domain.Company;
import vn.hblab.crm.domain.CompanyType;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository repository;

    private CompanyService service;

    @BeforeEach
    void setUp() {
        service = new CompanyService(repository, new AutomationPolicyGuard());
    }

    @Test
    void aiCannotSoftDeleteCompany() {
        assertThatThrownBy(() -> service.delete(1L, ActorType.AI_SYSTEM))
                .isInstanceOf(AutomationPolicyViolation.class);
    }

    @Test
    void humanSoftDeletesActiveCompany() {
        Company company = Company.create("ABC", "IT", CompanyType.IT_SOLUTION,
                null, null, null, null, null);
        given(repository.findByIdAndDeletedAtIsNull(1L)).willReturn(Optional.of(company));

        service.delete(1L, ActorType.HUMAN);

        assertThat(company.getDeletedAt()).isNotNull();
    }

    @Test
    void updateRejectsStaleVersionBeforeMutatingCompany() {
        Company company = Company.create("ABC", "IT", CompanyType.IT_SOLUTION,
                null, null, null, null, null);
        given(repository.findByIdAndDeletedAtIsNull(1L)).willReturn(Optional.of(company));

        assertThatThrownBy(() -> service.update(1L, command("Updated", company.getVersion() + 1)))
                .isInstanceOf(OptimisticLockingFailureException.class);

        assertThat(company.getName()).isEqualTo("ABC");
    }

    @Test
    void updateChangesActiveCompanyWhenVersionMatches() {
        Company company = Company.create("ABC", "IT", CompanyType.IT_SOLUTION,
                null, null, null, null, null);
        given(repository.findByIdAndDeletedAtIsNull(1L)).willReturn(Optional.of(company));

        Company updated = service.update(1L, command("Updated", company.getVersion()));

        assertThat(updated.getName()).isEqualTo("Updated");
    }

    private CompanyCommand command(String name, long version) {
        return new CompanyCommand(name, "IT", CompanyType.IT_SOLUTION, null,
                "https://example.test", null, null, null, version);
    }
}
