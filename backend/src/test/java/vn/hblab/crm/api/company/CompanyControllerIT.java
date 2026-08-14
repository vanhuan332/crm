package vn.hblab.crm.api.company;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import vn.hblab.crm.crmcore.infrastructure.CompanyJpaRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class CompanyControllerIT {

    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyJpaRepository repository;

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @BeforeEach
    void clearCompanies() {
        repository.deleteAll();
    }

    @Test
    void createsCompanyAndReturnsRestPayload() throws Exception {
        mockMvc.perform(post("/api/companies").contentType(APPLICATION_JSON).content(companyPayload("ABC")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("ABC"))
                .andExpect(jsonPath("$.industry").value("Software"))
                .andExpect(jsonPath("$.companyType").value("IT_SOLUTION"))
                .andExpect(jsonPath("$.website").value("https://abc.example"))
                .andExpect(jsonPath("$.version").value(0));
    }

    @Test
    void rejectsMissingOrInvalidRequiredFields() throws Exception {
        mockMvc.perform(post("/api/companies").contentType(APPLICATION_JSON)
                        .content("{\"name\":\" \",\"industry\":\"Software\",\"companyType\":\"IT_SOLUTION\",\"version\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors.name").exists());

        mockMvc.perform(post("/api/companies").contentType(APPLICATION_JSON)
                        .content("{\"name\":\"ABC\",\"industry\":\" \",\"companyType\":\"IT_SOLUTION\",\"version\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors.industry").exists());

        mockMvc.perform(post("/api/companies").contentType(APPLICATION_JSON)
                        .content("{\"name\":\"ABC\",\"companyType\":\"IT_SOLUTION\",\"version\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors.industry").exists());

        mockMvc.perform(post("/api/companies").contentType(APPLICATION_JSON)
                        .content("{\"name\":\"ABC\",\"industry\":\"Software\",\"version\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors.companyType").exists());

        mockMvc.perform(post("/api/companies").contentType(APPLICATION_JSON)
                        .content("{\"name\":\"ABC\",\"industry\":\"Software\",\"companyType\":\"IT_SOLUTION\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors.version").exists());

        mockMvc.perform(post("/api/companies").contentType(APPLICATION_JSON)
                        .content("{\"name\":\"ABC\",\"industry\":\"Software\",\"companyType\":\"UNKNOWN\",\"version\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors.companyType").exists());
    }

    @Test
    void listsGetsUpdatesAndSoftDeletesCompanies() throws Exception {
        long id = createCompany("ABC");

        mockMvc.perform(get("/api/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(id));

        mockMvc.perform(get("/api/companies/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ABC"));

        mockMvc.perform(put("/api/companies/{id}", id).contentType(APPLICATION_JSON)
                        .content(companyPayload("Renamed")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Renamed"));

        mockMvc.perform(delete("/api/companies/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/companies/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("COMPANY_NOT_FOUND"));
        mockMvc.perform(get("/api/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void mapsDuplicateNormalizedNameToConflict() throws Exception {
        createCompany("ABC");

        mockMvc.perform(post("/api/companies").contentType(APPLICATION_JSON)
                        .content(companyPayload(" abc ")))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("COMPANY_NAME_CONFLICT"));
    }

    @Test
    void mapsStaleVersionToConflict() throws Exception {
        long id = createCompany("ABC");

        mockMvc.perform(put("/api/companies/{id}", id).contentType(APPLICATION_JSON)
                        .content(companyPayload("Updated", 1)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("COMPANY_MODIFIED"));
    }

    private long createCompany(String name) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/companies").contentType(APPLICATION_JSON)
                        .content(companyPayload(name)))
                .andExpect(status().isCreated())
                .andReturn();
        return JsonPath.read(result.getResponse().getContentAsString(), "$.id");
    }

    private String companyPayload(String name) {
        return companyPayload(name, 0);
    }

    private String companyPayload(String name, long version) {
        return """
                {"name":"%s","industry":"Software","companyType":"IT_SOLUTION","country":"Vietnam","website":"https://abc.example","phone":"0123456789","address":"Hanoi","description":"Description","version":%d}
                """.formatted(name, version);
    }
}
