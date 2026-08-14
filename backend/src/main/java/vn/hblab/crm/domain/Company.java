package vn.hblab.crm.domain;

import jakarta.persistence.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

@Entity
@Table(name = "companies")
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 255) private String name;
    @Column(nullable = false, length = 255) private String industry;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 40) private CompanyType companyType;
    @Column(length = 100) private String country;
    @Column(name = "website_url", length = 2048) private String websiteUrl;
    @Column(length = 30) private String phone;
    @Column(length = 500) private String address;
    @Column(length = 2000) private String description;
    @Column(nullable = false) private boolean watching = false;
    @Column(nullable = false, updatable = false) private Instant createdAt;
    @Column(nullable = false) private Instant updatedAt;
    private Instant deletedAt;
    @Version private long version;

    protected Company() { }

    public Company(String name, String industry, CompanyType companyType) {
        this(name, industry, companyType, null, null, null, null, null);
    }

    private Company(String name, String industry, CompanyType companyType, String country,
                    String website, String phone, String address, String description) {
        apply(name, industry, companyType, country, website, phone, address, description);
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    public static Company create(String name, String industry, CompanyType companyType, String country,
                                 String website, String phone, String address, String description) {
        return new Company(name, industry, companyType, country, website, phone, address, description);
    }

    public void update(String name, String industry, CompanyType companyType, String country,
                       String website, String phone, String address, String description) {
        apply(name, industry, companyType, country, website, phone, address, description);
        updatedAt = Instant.now();
    }

    public void softDelete() {
        deletedAt = Instant.now();
        updatedAt = deletedAt;
    }

    @PrePersist
    private void initializeTimestamps() {
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
    }

    private void apply(String name, String industry, CompanyType companyType, String country,
                       String website, String phone, String address, String description) {
        this.name = required(name, "name", 255);
        this.industry = required(industry, "industry", 255);
        if (companyType == null) throw new IllegalArgumentException("companyType is required");
        this.companyType = companyType;
        this.country = optional(country, "country", 100);
        this.websiteUrl = website(website);
        this.phone = optional(phone, "phone", 30);
        this.address = optional(address, "address", 500);
        this.description = optional(description, "description", 2000);
    }

    private static String required(String value, String field, int maximumLength) {
        String trimmed = optional(value, field, maximumLength);
        if (trimmed == null || trimmed.isEmpty()) throw new IllegalArgumentException(field + " is required");
        return trimmed;
    }

    private static String optional(String value, String field, int maximumLength) {
        if (value == null) return null;
        String trimmed = value.trim();
        if (trimmed.length() > maximumLength) {
            throw new IllegalArgumentException(field + " exceeds " + maximumLength + " characters");
        }
        return trimmed;
    }

    private static String website(String value) {
        String trimmed = optional(value, "website", 2048);
        if (trimmed == null || trimmed.isEmpty()) return trimmed;
        try {
            URI uri = new URI(trimmed);
            if (!("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()))
                    || uri.getHost() == null) {
                throw new IllegalArgumentException("website must be an http or https URL");
            }
        } catch (URISyntaxException exception) {
            throw new IllegalArgumentException("website must be a valid URL", exception);
        }
        return trimmed;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getIndustry() { return industry; }
    public CompanyType getCompanyType() { return companyType; }
    public String getCountry() { return country; }
    public String getWebsite() { return websiteUrl; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getDescription() { return description; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Instant getDeletedAt() { return deletedAt; }
    public long getVersion() { return version; }
}
