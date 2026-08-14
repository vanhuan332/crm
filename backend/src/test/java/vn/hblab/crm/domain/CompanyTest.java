package vn.hblab.crm.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class CompanyTest {

    @Test
    void rejectsBlankRequiredFieldsAndInvalidWebsite() {
        assertThatThrownBy(() -> Company.create(" ", "IT", CompanyType.IT_SOLUTION,
                null, null, null, null, null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Company.create("ABC", "IT", CompanyType.IT_SOLUTION,
                null, "ftp://abc.test", null, null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void trimsTextAndUpdatesTimestampWhenChanged() {
        Company company = Company.create(" ABC ", " Technology ", CompanyType.TECH_STARTUP,
                " Vietnam ", "https://abc.test", " 123 ", " Hanoi ", " Description ");
        Instant createdAt = company.getCreatedAt();

        company.update(" XYZ ", " Software ", CompanyType.IT_PRODUCT,
                " Japan ", "https://xyz.test", " 456 ", " Tokyo ", " Updated ");

        assertThat(company.getName()).isEqualTo("XYZ");
        assertThat(company.getIndustry()).isEqualTo("Software");
        assertThat(company.getCompanyType()).isEqualTo(CompanyType.IT_PRODUCT);
        assertThat(company.getCountry()).isEqualTo("Japan");
        assertThat(company.getWebsite()).isEqualTo("https://xyz.test");
        assertThat(company.getPhone()).isEqualTo("456");
        assertThat(company.getAddress()).isEqualTo("Tokyo");
        assertThat(company.getDescription()).isEqualTo("Updated");
        assertThat(company.getUpdatedAt()).isAfterOrEqualTo(createdAt);
    }

    @Test
    void softDeleteMarksDeletionTime() {
        Company company = Company.create("ABC", "IT", CompanyType.IT_SOLUTION,
                null, null, null, null, null);

        company.softDelete();

        assertThat(company.getDeletedAt()).isNotNull();
    }
}
