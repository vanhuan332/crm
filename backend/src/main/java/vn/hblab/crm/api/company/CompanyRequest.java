package vn.hblab.crm.api.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import vn.hblab.crm.crmcore.application.CompanyCommand;
import vn.hblab.crm.domain.CompanyType;

public record CompanyRequest(
        @NotBlank String name,
        @NotBlank String industry,
        @NotNull CompanyType companyType,
        String country,
        String website,
        String phone,
        String address,
        String description,
        @NotNull Long version) {

    CompanyCommand toCommand() {
        return new CompanyCommand(name, industry, companyType, country, website, phone, address, description, version);
    }
}
