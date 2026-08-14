package vn.hblab.crm.api.company;

import java.time.Instant;
import vn.hblab.crm.domain.Company;
import vn.hblab.crm.domain.CompanyType;

public record CompanyResponse(Long id, String name, String industry, CompanyType companyType,
                              String country, String website, String phone, String address,
                              String description, Instant createdAt, Instant updatedAt, long version) {
    static CompanyResponse from(Company company) {
        return new CompanyResponse(company.getId(), company.getName(), company.getIndustry(), company.getCompanyType(),
                company.getCountry(), company.getWebsite(), company.getPhone(), company.getAddress(),
                company.getDescription(), company.getCreatedAt(), company.getUpdatedAt(), company.getVersion());
    }
}
