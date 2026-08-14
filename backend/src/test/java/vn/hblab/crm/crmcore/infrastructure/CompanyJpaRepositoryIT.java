package vn.hblab.crm.crmcore.infrastructure;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import vn.hblab.crm.domain.Company;
import vn.hblab.crm.domain.CompanyType;

@SpringBootTest
@Testcontainers
class CompanyJpaRepositoryIT {

    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private CompanyJpaRepository repository;

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Test
    void activeNamesAreUniqueIgnoringCaseAndWhitespace() {
        repository.saveAndFlush(company(" ABC Tech "));

        assertThatThrownBy(() -> repository.saveAndFlush(company("abc tech")))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    private Company company(String name) {
        return new Company(name, "Technology", CompanyType.IT_SOLUTION);
    }
}
