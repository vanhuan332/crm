package vn.hblab.crm.crmcore.application;

import vn.hblab.crm.domain.CompanyType;

public record CompanyCommand(String name, String industry, CompanyType companyType,
                             String country, String website, String phone, String address,
                             String description, long version) { }
