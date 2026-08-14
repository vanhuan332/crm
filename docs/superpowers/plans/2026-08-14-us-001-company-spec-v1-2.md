# US-001 Company Specification v1.2 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Align the existing Company CRUD vertical slice with approved US-001 v1.2: exactly three required and three optional business fields, normalized active-name uniqueness, field-level errors, soft-delete retention, and matching Vue screens.

**Architecture:** Keep the existing `crm-core` vertical slice and dependency direction REST API → application service → `Company` domain → JPA/Flyway infrastructure. Add `size` through a new additive Flyway migration, stop exposing the legacy optional fields without rewriting applied migrations, and preserve the existing partial unique index and `AutomationPolicyGuard` deletion boundary. Update the Vue API types, Pinia state, shared form, list/detail views, and browser acceptance flow as one contract.

**Tech Stack:** Java 17, Spring Boot 3.3, Spring Data JPA, Flyway, PostgreSQL 16, JUnit 5, Testcontainers, Vue 3, TypeScript, Pinia, Vue Router, Axios, Vitest, Testing Library, Playwright.

**Spec:** `docs/04-specifications/US-001/US-001-ba-specification.md`

## Global Constraints

- Company business fields are exactly: required `name`, `industry`, `companyType`; optional `country`, `website`, `size`.
- `companyType` accepts exactly `TRADITIONAL`, `IT_SOLUTION`, `IT_PRODUCT`, `TECH_STARTUP`, `OTHER_ITO`.
- Active Company names are unique after trimming leading/trailing whitespace and comparing without case sensitivity.
- Create and update apply the same required-field, company-type, website, length, and unique-name rules.
- Delete is soft delete: active list/detail no longer expose the Company, while Observation and every other related row remain stored.
- Sales and Quản trị use the same Company CRUD application contract. Authentication and login remain the cross-cutting US-046 dependency; this slice must not add a Sales-only or Admin-only gate.
- Every automated delete path must still pass `AutomationPolicyGuard`; `AI_SYSTEM` cannot delete a Company.
- Keep modular-monolith dependency direction API → application → domain → infrastructure; controllers contain mapping only, not business rules.
- Do not edit already-applied `V1__initial_crm_schema.sql` or `V2__complete_company_crud.sql`; add schema only under `backend/src/main/resources/db/migration/`.
- Keep the nullable legacy database columns `phone`, `address`, and `description` for migration safety, but remove them from the active domain/API/UI contract.
- CRM Company CRUD must work with `AI_ENABLED=false`.
- Do not add Grafana, monitoring, telemetry, log shipping, prompt storage, or agent-log storage.

---

## File Structure

| Path | Responsibility |
|---|---|
| `backend/src/main/resources/db/migration/V3__add_company_size.sql` | Add the optional `company_size` column without rewriting or destructively dropping deployed schema. |
| `backend/src/main/java/vn/hblab/crm/domain/Company.java` | Enforce the six-field Company profile, trimming, URL validation, timestamps and soft-delete state. |
| `backend/src/main/java/vn/hblab/crm/crmcore/application/CompanyCommand.java` | Carry the six business fields plus optimistic `version` across the application boundary. |
| `backend/src/main/java/vn/hblab/crm/crmcore/application/CompanyService.java` | Orchestrate create/update/list/detail/delete and preserve the automation guard. |
| `backend/src/main/java/vn/hblab/crm/api/company/CompanyRequest.java` | Validate and map the six-field REST write contract. |
| `backend/src/main/java/vn/hblab/crm/api/company/CompanyResponse.java` | Expose the six-field REST read contract plus timestamps/version. |
| `backend/src/main/java/vn/hblab/crm/api/ApiExceptionHandler.java` | Map duplicate names and validation failures to field-level API errors. |
| `frontend/src/api/companies.ts` | Define the matching TypeScript Company/read/write payloads. |
| `frontend/src/stores/companies.ts` | Maintain active list/detail state and mutations; its generic state contract updates through the API types without new behavior. |
| `frontend/src/views/companies/CompanyForm.vue` | Render and validate the shared create/edit six-field form. |
| `frontend/src/views/companies/CompaniesListView.vue` | Render active Companies with the six approved business columns and page states. |
| `frontend/src/views/companies/CompanyDetailView.vue` | Render the six-field Company profile and edit/delete actions. |
| `frontend/src/views/companies/CompanyDeleteDialog.vue` | Explain and confirm soft deletion. |
| `frontend/src/styles.css` | Implement the approved light cards, thin borders, table/form grouping and purple primary actions. |
| `frontend/e2e/company-management.spec.ts` | Exercise create, duplicate rejection, edit, detail, soft delete and deleted-detail behavior. |

### Task 1: Align the complete backend Company contract with the six-field profile

**Files:**
- Create: `backend/src/main/resources/db/migration/V3__add_company_size.sql`
- Modify: `backend/src/main/java/vn/hblab/crm/domain/Company.java`
- Modify: `backend/src/main/java/vn/hblab/crm/crmcore/application/CompanyCommand.java`
- Modify: `backend/src/main/java/vn/hblab/crm/crmcore/application/CompanyService.java`
- Modify: `backend/src/main/java/vn/hblab/crm/api/company/CompanyRequest.java`
- Modify: `backend/src/main/java/vn/hblab/crm/api/company/CompanyResponse.java`
- Modify: `backend/src/main/java/vn/hblab/crm/api/ApiExceptionHandler.java`
- Modify: `backend/src/test/java/vn/hblab/crm/domain/CompanyTest.java`
- Modify: `backend/src/test/java/vn/hblab/crm/crmcore/infrastructure/CompanyJpaRepositoryIT.java`
- Modify: `backend/src/test/java/vn/hblab/crm/crmcore/application/CompanyServiceTest.java`
- Modify: `backend/src/test/java/vn/hblab/crm/api/ApiExceptionHandlerTest.java`
- Modify: `backend/src/test/java/vn/hblab/crm/api/company/CompanyControllerIT.java`

**Interfaces:**
- Produces: `Company.create(String name, String industry, CompanyType companyType, String country, String website, String size)`.
- Produces: `Company.update(String name, String industry, CompanyType companyType, String country, String website, String size)`.
- Produces: `CompanyCommand(String name, String industry, CompanyType companyType, String country, String website, String size, long version)`.
- Produces: PostgreSQL column `companies.company_size VARCHAR(100)` mapped by `Company.getSize()`.
- Produces: REST write payload `{ name, industry, companyType, country, website, size, version }`.
- Produces: REST read payload `{ id, name, industry, companyType, country, website, size, createdAt, updatedAt, version }`.
- Produces: duplicate-name response `409 { "code": "COMPANY_NAME_CONFLICT", "fieldErrors": { "name": "Tên công ty đã tồn tại" } }`.

- [ ] **Step 1: Replace the domain test expectations with the approved profile**

```java
@Test
void keepsOnlyTheApprovedOptionalFieldsAndTrimsThem() {
    Company company = Company.create(" ABC ", " Technology ", CompanyType.TECH_STARTUP,
            " Vietnam ", "https://abc.test", " 100-499 ");

    assertThat(company.getName()).isEqualTo("ABC");
    assertThat(company.getIndustry()).isEqualTo("Technology");
    assertThat(company.getCountry()).isEqualTo("Vietnam");
    assertThat(company.getWebsite()).isEqualTo("https://abc.test");
    assertThat(company.getSize()).isEqualTo("100-499");
}

@Test
void updateRejectsEveryBlankRequiredField() {
    Company company = Company.create("ABC", "Technology", CompanyType.IT_SOLUTION,
            null, null, null);

    assertThatThrownBy(() -> company.update(" ", "Technology", CompanyType.IT_SOLUTION,
            null, null, null)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("name is required");
    assertThatThrownBy(() -> company.update("ABC", " ", CompanyType.IT_SOLUTION,
            null, null, null)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("industry is required");
    assertThatThrownBy(() -> company.update("ABC", "Technology", null,
            null, null, null)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("companyType is required");
}
```

- [ ] **Step 2: Run the focused domain test and observe the red state**

Run: `mvn -f backend/pom.xml -s .mvn/settings.xml -Dtest=CompanyTest test`

Expected: compilation FAIL because the six-argument factory/update methods and `getSize()` do not exist.

- [ ] **Step 3: Add the additive Flyway migration**

```sql
ALTER TABLE companies
  ADD COLUMN company_size VARCHAR(100);
```

Do not remove the nullable V2 legacy columns; application code stops using them, and a later data-retention decision may remove them safely.

- [ ] **Step 4: Replace the Company optional-field mapping and command signature**

```java
@Column(name = "company_size", length = 100)
private String size;

public Company(String name, String industry, CompanyType companyType) {
    this(name, industry, companyType, null, null, null);
}

private Company(String name, String industry, CompanyType companyType,
                String country, String website, String size) {
    apply(name, industry, companyType, country, website, size);
    Instant now = Instant.now();
    createdAt = now;
    updatedAt = now;
}

public static Company create(String name, String industry, CompanyType companyType,
                             String country, String website, String size) {
    return new Company(name, industry, companyType, country, website, size);
}

public void update(String name, String industry, CompanyType companyType,
                   String country, String website, String size) {
    apply(name, industry, companyType, country, website, size);
    updatedAt = Instant.now();
}

private void apply(String name, String industry, CompanyType companyType,
                   String country, String website, String size) {
    this.name = required(name, "name", 255);
    this.industry = required(industry, "industry", 255);
    if (companyType == null) throw new IllegalArgumentException("companyType is required");
    this.companyType = companyType;
    this.country = optional(country, "country", 100);
    this.websiteUrl = website(website);
    this.size = optional(size, "size", 100);
}

public String getSize() { return size; }
```

The final optional-field declarations/getters in `Company` are exactly:

```java
@Column(length = 100) private String country;
@Column(name = "website_url", length = 2048) private String websiteUrl;
@Column(name = "company_size", length = 100) private String size;

public String getCountry() { return country; }
public String getWebsite() { return websiteUrl; }
public String getSize() { return size; }
```

Remove the `phone`, `address`, and `description` fields, constructor parameters, assignments and getters from active domain code.

Replace the command record with:

```java
public record CompanyCommand(String name, String industry, CompanyType companyType,
                             String country, String website, String size,
                             long version) { }
```

- [ ] **Step 5: Extend persistence coverage for `company_size` and active-name reuse**

```java
@Test
void persistsSizeAndAllowsNameReuseAfterSoftDelete() {
    Company deleted = Company.create("ABC Tech", "Technology", CompanyType.IT_SOLUTION,
            "Vietnam", "https://abc.test", "100-499");
    repository.saveAndFlush(deleted);
    deleted.softDelete();
    repository.saveAndFlush(deleted);

    Company replacement = repository.saveAndFlush(Company.create(" abc tech ", "Technology",
            CompanyType.IT_SOLUTION, null, null, "500-999"));

    assertThat(replacement.getSize()).isEqualTo("500-999");
}
```

- [ ] **Step 6: Update application tests to compile against the approved command**

```java
private CompanyCommand command(String name, long version) {
    return new CompanyCommand(name, "IT", CompanyType.IT_SOLUTION,
            "Vietnam", "https://example.test", "100-499", version);
}

@Test
void updateKeepsApprovedOptionalFields() {
    Company company = Company.create("ABC", "IT", CompanyType.IT_SOLUTION,
            null, null, null);
    given(repository.findByIdAndDeletedAtIsNull(1L)).willReturn(Optional.of(company));

    Company updated = service.update(1L, command("Updated", company.getVersion()));

    assertThat(updated.getCountry()).isEqualTo("Vietnam");
    assertThat(updated.getWebsite()).isEqualTo("https://example.test");
    assertThat(updated.getSize()).isEqualTo("100-499");
}
```

- [ ] **Step 7: Add failing API tests for size, field-level duplicate errors and related-data retention**

```java
@Test
void returnsOnlyTheApprovedCompanyProfile() throws Exception {
    mockMvc.perform(post("/api/companies").contentType(APPLICATION_JSON)
                    .content(companyPayload("ABC")))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.size").value("100-499"))
            .andExpect(jsonPath("$.phone").doesNotExist())
            .andExpect(jsonPath("$.address").doesNotExist())
            .andExpect(jsonPath("$.description").doesNotExist());
}

@Test
void duplicateNameIsAttachedToTheNameField() throws Exception {
    createCompany("ABC");

    mockMvc.perform(post("/api/companies").contentType(APPLICATION_JSON)
                    .content(companyPayload(" abc ")))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.code").value("COMPANY_NAME_CONFLICT"))
            .andExpect(jsonPath("$.fieldErrors.name").value("Tên công ty đã tồn tại"));
}

@Test
void updateAlsoRejectsADuplicateNormalizedName() throws Exception {
    createCompany("ABC");
    long secondId = createCompany("XYZ");

    mockMvc.perform(put("/api/companies/{id}", secondId).contentType(APPLICATION_JSON)
                    .content(companyPayload(" abc ")))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.fieldErrors.name").value("Tên công ty đã tồn tại"));
}

@Test
void softDeleteKeepsRelatedObservations() throws Exception {
    long id = createCompany("ABC");
    jdbcTemplate.update("""
            INSERT INTO observations(company_id, source_url, raw_content,
              normalized_content_hash, read_at, readable)
            VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, TRUE)
            """, id, "https://abc.example", "source", "a".repeat(64));

    mockMvc.perform(delete("/api/companies/{id}", id)).andExpect(status().isNoContent());

    assertThat(jdbcTemplate.queryForObject(
            "SELECT count(*) FROM observations WHERE company_id = ?", Integer.class, id)).isEqualTo(1);
    mockMvc.perform(get("/api/companies/{id}", id)).andExpect(status().isNotFound());
}
```

Add `@Autowired JdbcTemplate jdbcTemplate;` to `CompanyControllerIT`. Replace its payload helper with:

```java
private String companyPayload(String name, long version) {
    return """
            {"name":"%s","industry":"Software","companyType":"IT_SOLUTION",
             "country":"Vietnam","website":"https://abc.example",
             "size":"100-499","version":%d}
            """.formatted(name, version);
}
```

Because the retention test intentionally leaves an Observation row, clear dependents before Companies in the existing setup:

```java
@BeforeEach
void clearCompanies() {
    jdbcTemplate.update("DELETE FROM observations");
    repository.deleteAll();
}
```

- [ ] **Step 8: Run the full backend red check after all contract tests are present**

Run: `mvn -f backend/pom.xml -s .mvn/settings.xml -Dtest=CompanyServiceTest,ApiExceptionHandlerTest,CompanyControllerIT test`

Expected: compilation or assertion FAIL because service/DTO mappings still use legacy optional fields, `size` is absent, and duplicate errors do not contain `fieldErrors.name`. This is the single red phase for the backend deliverable; do not commit the partially migrated contract.

- [ ] **Step 9: Update service and REST DTO mappings**

Use the Task 1 signatures in `CompanyService`:

```java
public Company create(CompanyCommand command) {
    return repository.save(Company.create(command.name(), command.industry(), command.companyType(),
            command.country(), command.website(), command.size()));
}

public Company update(long id, CompanyCommand command) {
    Company company = activeCompany(id);
    if (command.version() != company.getVersion()) {
        throw new OptimisticLockingFailureException("Company " + id + " has been modified");
    }
    company.update(command.name(), command.industry(), command.companyType(),
            command.country(), command.website(), command.size());
    return company;
}
```

Replace the request and response records with:

```java
public record CompanyRequest(
        @NotBlank String name,
        @NotBlank String industry,
        @NotNull CompanyType companyType,
        String country,
        String website,
        String size,
        @NotNull Long version) {
    CompanyCommand toCommand() {
        return new CompanyCommand(name, industry, companyType, country, website, size, version);
    }
}
```

```java
public record CompanyResponse(Long id, String name, String industry, CompanyType companyType,
                              String country, String website, String size,
                              Instant createdAt, Instant updatedAt, long version) {
    static CompanyResponse from(Company company) {
        return new CompanyResponse(company.getId(), company.getName(), company.getIndustry(),
                company.getCompanyType(), company.getCountry(), company.getWebsite(), company.getSize(),
                company.getCreatedAt(), company.getUpdatedAt(), company.getVersion());
    }
}
```

- [ ] **Step 10: Attach the duplicate conflict to `name`**

```java
if (hasConstraint(exception, "uq_companies_active_normalized_name")) {
    return error(HttpStatus.CONFLICT, "COMPANY_NAME_CONFLICT",
            Map.of("name", "Tên công ty đã tồn tại"));
}
```

Extend `ApiExceptionHandlerTest` with:

```java
assertThat(response.getBody().fieldErrors())
        .containsEntry("name", "Tên công ty đã tồn tại");
```

- [ ] **Step 11: Run the complete backend gate**

Run: `mvn -f backend/pom.xml -s .mvn/settings.xml test`

Expected: PASS with Docker available; Company domain, application, handler, PostgreSQL uniqueness, V3 schema, REST contract and retention tests all pass. If Docker is unavailable, record the exact Testcontainers environment error and still run `mvn -f backend/pom.xml -s .mvn/settings.xml -DskipTests package` to distinguish environment failure from compilation failure.

- [ ] **Step 12: Commit the complete backend slice**

```bash
git add backend/src/main/resources/db/migration/V3__add_company_size.sql backend/src/main/java/vn/hblab/crm/domain/Company.java backend/src/main/java/vn/hblab/crm/crmcore/application/CompanyCommand.java backend/src/main/java/vn/hblab/crm/crmcore/application/CompanyService.java backend/src/main/java/vn/hblab/crm/api/company/CompanyRequest.java backend/src/main/java/vn/hblab/crm/api/company/CompanyResponse.java backend/src/main/java/vn/hblab/crm/api/ApiExceptionHandler.java backend/src/test/java/vn/hblab/crm/domain/CompanyTest.java backend/src/test/java/vn/hblab/crm/crmcore/infrastructure/CompanyJpaRepositoryIT.java backend/src/test/java/vn/hblab/crm/crmcore/application/CompanyServiceTest.java backend/src/test/java/vn/hblab/crm/api/ApiExceptionHandlerTest.java backend/src/test/java/vn/hblab/crm/api/company/CompanyControllerIT.java
git commit -m "feat: align company backend with specification v1.2"
```

### Task 2: Align Vue Company screens and state with the approved wireframes

**Files:**
- Modify: `frontend/src/api/companies.ts`
- Modify: `frontend/src/views/companies/CompanyForm.vue`
- Modify: `frontend/src/views/companies/CompanyFormView.vue`
- Modify: `frontend/src/views/companies/CompaniesListView.vue`
- Modify: `frontend/src/views/companies/CompanyDetailView.vue`
- Modify: `frontend/src/views/companies/CompanyDeleteDialog.vue`
- Modify: `frontend/src/styles.css`
- Modify: `frontend/src/views/companies/__tests__/CompanyForm.spec.ts`
- Modify: `frontend/src/views/companies/__tests__/CompanyDeleteDialog.spec.ts`
- Create: `frontend/src/views/companies/__tests__/CompaniesListView.spec.ts`

**Interfaces:**
- Consumes: Task 1 REST fields and error payloads.
- Produces: `Company` with optional `country`, `website`, `size`; no `phone`, `address`, or `description`.
- Produces: `CompanyInput = Omit<Company, 'id' | 'createdAt' | 'updatedAt'>` and existing list/get/create/update/delete functions.
- Produces: accessible create/edit form labels `Tên công ty`, `Ngành`, `Loại công ty`, `Quốc gia`, `Website`, `Quy mô`.

- [ ] **Step 1: Rewrite form tests for the six-field contract**

```ts
it('emits only the approved trimmed company fields', async () => {
  const { emitted } = render(CompanyForm)

  await fireEvent.update(screen.getByLabelText(/^Tên công ty/), ' ABC Technology ')
  await fireEvent.update(screen.getByLabelText(/^Ngành/), ' Software ')
  await fireEvent.update(screen.getByLabelText(/^Loại công ty/), 'IT_SOLUTION')
  await fireEvent.update(screen.getByLabelText('Quốc gia'), ' Vietnam ')
  await fireEvent.update(screen.getByLabelText('Website'), ' https://abc.example ')
  await fireEvent.update(screen.getByLabelText('Quy mô'), ' 100-499 ')
  await fireEvent.click(screen.getByRole('button', { name: 'Lưu' }))

  expect(emitted().submit?.[0]).toEqual([{
    name: 'ABC Technology', industry: 'Software', companyType: 'IT_SOLUTION',
    country: 'Vietnam', website: 'https://abc.example', size: '100-499', version: 0
  }])
  expect(screen.queryByLabelText('Điện thoại')).toBeNull()
  expect(screen.queryByLabelText('Địa chỉ')).toBeNull()
  expect(screen.queryByLabelText('Mô tả')).toBeNull()
})

it('shows a duplicate-name server error without clearing entered fields', async () => {
  render(CompanyForm, {
    props: {
      initialValues: { name: 'HBLAB', industry: 'Software' },
      fieldErrors: { name: 'Tên công ty đã tồn tại' }
    }
  })

  expect(screen.getByText('Tên công ty đã tồn tại')).toBeTruthy()
  expect((screen.getByLabelText(/^Tên công ty/) as HTMLInputElement).value).toBe('HBLAB')
  expect((screen.getByLabelText(/^Ngành/) as HTMLInputElement).value).toBe('Software')
})

it('maps the remaining server field errors to their controls', () => {
  render(CompanyForm, {
    props: {
      fieldErrors: {
        companyType: 'Loại công ty không hợp lệ', country: 'Quốc gia không hợp lệ',
        website: 'Website không hợp lệ', size: 'Quy mô không hợp lệ',
        version: 'Dữ liệu đã thay đổi'
      }
    }
  })

  expect(screen.getByLabelText(/^Loại công ty/).getAttribute('aria-describedby')).toBe('company-type-error')
  expect(screen.getByLabelText('Quốc gia').getAttribute('aria-describedby')).toBe('company-country-error')
  expect(screen.getByLabelText('Website').getAttribute('aria-describedby')).toBe('company-website-error')
  expect(screen.getByLabelText('Quy mô').getAttribute('aria-describedby')).toBe('company-size-error')
  expect(screen.getByText('Dữ liệu đã thay đổi')).toBeTruthy()
})
```

- [ ] **Step 2: Add a failing list-view contract test**

```ts
vi.mock('../../../stores/companies', () => ({
  useCompaniesStore: () => ({
    companies: [{
      id: 1, name: 'ABC', industry: 'Software', companyType: 'IT_SOLUTION',
      country: 'Vietnam', website: 'https://abc.example', size: '100-499',
      createdAt: '2026-08-14T00:00:00Z', updatedAt: '2026-08-14T00:00:00Z', version: 0
    }],
    listStatus: 'idle', listError: '', loadCompanies: vi.fn()
  })
}))

it('shows all six approved business columns', () => {
  render(CompaniesListView, { global: { stubs: ['RouterLink'] } })

  for (const heading of ['Tên công ty', 'Ngành', 'Loại', 'Quốc gia', 'Quy mô', 'Website']) {
    expect(screen.getByText(heading)).toBeTruthy()
  }
  expect(screen.getByText('100-499')).toBeTruthy()
  expect(screen.getByText('https://abc.example')).toBeTruthy()
})
```

- [ ] **Step 3: Run focused Vue tests and observe the red state**

Run from `frontend/`: `npm run test:unit -- --run src/views/companies/__tests__/CompanyForm.spec.ts src/views/companies/__tests__/CompaniesListView.spec.ts src/views/companies/__tests__/CompanyDeleteDialog.spec.ts`

Expected: FAIL because the TypeScript contract/form still contain legacy fields, `size` is absent, and the list is not a six-column table.

- [ ] **Step 4: Update the typed API and shared form**

Use this Company contract:

```ts
export interface Company {
  id: number
  name: string
  industry: string
  companyType: CompanyType
  country: string | null
  website: string | null
  size: string | null
  createdAt: string
  updatedAt: string
  version: number
}
```

Use a local form state that can represent an unselected required Company type, then initialize create mode with no selection:

```ts
type CompanyFormState = Omit<CompanyInput, 'companyType'> & { companyType: CompanyType | '' }

const form = ref<CompanyFormState>({
  name: '', industry: '', companyType: '',
  country: null, website: null, size: null, version: 0
})
```

Validate the select before emitting and use the successful check to narrow the value:

```ts
if (!form.value.name.trim()) clientErrors.value.name = 'Tên công ty là bắt buộc'
if (!form.value.industry.trim()) clientErrors.value.industry = 'Ngành là bắt buộc'
if (!form.value.companyType) clientErrors.value.companyType = 'Loại công ty là bắt buộc'
if (Object.keys(clientErrors.value).length) {
  nextTick(() => firstInvalidInput(clientErrors.value)?.focus())
  return
}

emit('submit', {
  ...form.value,
  companyType: form.value.companyType as CompanyType,
  name: form.value.name.trim(), industry: form.value.industry.trim(),
  country: optional(form.value.country), website: optional(form.value.website),
  size: optional(form.value.size)
})
```

The select begins with a disabled prompt:

```vue
<select id="company-type" ref="companyTypeInput" v-model="form.companyType">
  <option value="" disabled>Chọn một trong 5 loại công ty</option>
  <option v-for="type in companyTypes" :key="type.value" :value="type.value">{{ type.label }}</option>
</select>
```

Keep the optional trim helper, first-error focus and server `fieldErrors` mapping. Replace the three legacy controls with:

```vue
<p><label for="company-size">Quy mô</label><br>
  <input id="company-size" ref="sizeInput" v-model="form.size"
    :aria-invalid="Boolean(errorFor('size'))"
    :aria-describedby="errorFor('size') ? 'company-size-error' : undefined">
  <span v-if="errorFor('size')" id="company-size-error" role="alert">{{ errorFor('size') }}</span>
</p>
```

- [ ] **Step 5: Implement the list, detail and delete copy from the approved SVGs**

Use a semantic table in `CompaniesListView.vue`; apply `company-page` to the section and keep the table inside the responsive wrapper:

```vue
<div v-else class="company-table-wrap">
  <table class="company-table">
    <thead><tr><th>Tên công ty</th><th>Ngành</th><th>Loại</th><th>Quốc gia</th><th>Quy mô</th><th>Website</th><th>Thao tác</th></tr></thead>
    <tbody><tr v-for="company in companies.companies" :key="company.id">
      <td><RouterLink :to="`/companies/${company.id}`">{{ company.name }}</RouterLink></td>
      <td>{{ company.industry }}</td><td>{{ company.companyType }}</td>
      <td>{{ company.country || '—' }}</td><td>{{ company.size || '—' }}</td>
      <td><a v-if="company.website" :href="company.website" target="_blank" rel="noreferrer">{{ company.website }}</a><span v-else>—</span></td>
      <td><RouterLink :to="`/companies/${company.id}`">Xem</RouterLink> <RouterLink :to="`/companies/${company.id}/edit`">Sửa</RouterLink></td>
    </tr></tbody>
  </table>
</div>
```

Limit `CompanyDetailView.vue` to `industry`, `companyType`, `country`, `website`, and `size` under the Company name. Change the confirmation explanation to:

```vue
<p>Công ty sẽ bị gỡ khỏi danh sách. Dữ liệu liên quan được giữ nguyên để bảo toàn lịch sử.</p>
```

Extend `CompanyDeleteDialog.spec.ts` with:

```ts
expect(screen.getByText('Dữ liệu liên quan được giữ nguyên để bảo toàn lịch sử.')).toBeTruthy()
```

Keep successful create/update navigation to `/companies/{id}`, successful delete navigation to `/companies`, not-found handling, retry behavior, pending-button disabling and typed field-error mapping.

- [ ] **Step 6: Add focused Company styling without changing unrelated routes**

Append scoped class rules used by the Company templates:

```css
.company-page { background: #fff; border: 1px solid #d9e2ef; border-radius: 14px; padding: 28px; }
.company-card { border: 1px solid #d9e2ef; border-radius: 14px; padding: 28px; margin-block: 18px; }
.company-form-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 18px 24px; }
.company-form-grid .full { grid-column: 1 / -1; }
.company-table { width: 100%; border-collapse: collapse; }
.company-table th, .company-table td { border-bottom: 1px solid #e6ebf2; padding: 14px 10px; text-align: left; vertical-align: top; }
.company-primary { background: #5236f5; border: 1px solid #5236f5; border-radius: 7px; color: #fff; padding: .7rem 1rem; }
@media (max-width: 760px) { .company-form-grid { grid-template-columns: 1fr; } .company-table-wrap { overflow-x: auto; } }
```

- [ ] **Step 7: Run frontend unit, type and production-build gates**

Run from `frontend/`: `npm run test:unit -- --run`

Expected: PASS for all Vitest suites.

Run from `frontend/`: `npm run typecheck`

Expected: PASS with no legacy optional-field references in active TypeScript/Vue code.

Run from `frontend/`: `npm run build`

Expected: PASS and produce the Vite production bundle.

- [ ] **Step 8: Commit the Vue alignment**

```bash
git add frontend/src/api/companies.ts frontend/src/views/companies/CompanyForm.vue frontend/src/views/companies/CompanyFormView.vue frontend/src/views/companies/CompaniesListView.vue frontend/src/views/companies/CompanyDetailView.vue frontend/src/views/companies/CompanyDeleteDialog.vue frontend/src/styles.css frontend/src/views/companies/__tests__/CompanyForm.spec.ts frontend/src/views/companies/__tests__/CompanyDeleteDialog.spec.ts frontend/src/views/companies/__tests__/CompaniesListView.spec.ts
git commit -m "feat: align company screens with specification v1.2"
```

### Task 3: Extend browser acceptance and complete delivery verification

**Files:**
- Modify: `frontend/e2e/company-management.spec.ts`
- Modify: `README.md`

**Interfaces:**
- Consumes: complete backend and Vue contracts from Tasks 1–2.
- Produces: browser coverage for AC-001..004 and AC-US001-05..07; TC-010 remains compatible with both roles and becomes two authenticated runs when US-046 supplies login fixtures.
- Produces: documented commands for running US-001 with `AI_ENABLED=false`.

- [ ] **Step 1: Extend the Playwright scenario before changing the UI**

```ts
test('user completes the US-001 v1.2 company flow', async ({ page }) => {
  const companyName = `ABC Technology ${Date.now()}`
  const companyUrlPattern = /\/companies\/(\d+)$/

  await page.goto('/companies/new')
  await page.getByLabel('Tên công ty').fill(companyName)
  await page.getByLabel('Ngành').fill('Software')
  await page.getByLabel('Loại công ty').selectOption('IT_SOLUTION')
  await page.getByLabel('Quốc gia').fill('Vietnam')
  await page.getByLabel('Website').fill('https://abc.example')
  await page.getByLabel('Quy mô').fill('100-499')
  await page.getByRole('button', { name: 'Lưu' }).click()
  await expect(page).toHaveURL(companyUrlPattern)
  const detailUrl = page.url()
  await expect(page.getByText('100-499', { exact: true })).toBeVisible()

  await page.goto('/companies/new')
  await page.getByLabel('Tên công ty').fill(` ${companyName.toUpperCase()} `)
  await page.getByLabel('Ngành').fill('Consulting')
  await page.getByRole('button', { name: 'Lưu' }).click()
  await expect(page.getByText('Tên công ty đã tồn tại')).toBeVisible()
  await expect(page.getByLabel('Ngành')).toHaveValue('Consulting')

  await page.goto(detailUrl)
  await page.getByRole('button', { name: 'Xóa công ty' }).click()
  await expect(page.getByRole('dialog')).toContainText('Dữ liệu liên quan được giữ nguyên')
  await page.getByRole('dialog').getByRole('button', { name: 'Xóa công ty' }).click()
  await expect(page).toHaveURL('/companies')
  await expect(page.getByText(companyName, { exact: true })).toHaveCount(0)

  await page.goto(detailUrl)
  await expect(page.getByRole('heading', { name: 'Không tìm thấy công ty' })).toBeVisible()
})
```

- [ ] **Step 2: Run the focused browser test and observe the red state**

Run from `frontend/` with the application stack running: `npm run test:e2e -- --grep "US-001 v1.2 company flow"`

Expected before Tasks 1–2: FAIL because `Quy mô`, field-level duplicate copy, retention confirmation copy, or the six-field detail is missing.

- [ ] **Step 3: Document the approved profile and AI-disabled acceptance command**

Add this concise section to `README.md`:

```markdown
### US-001 — Hồ sơ Công ty

Hồ sơ gồm Tên công ty, Ngành, Loại công ty và ba trường tùy chọn Quốc gia, Website, Quy mô. Tên active là duy nhất sau trim và không phân biệt hoa/thường. Xóa là xóa mềm; dữ liệu liên quan được giữ nguyên.

Để nghiệm thu CRM thủ công khi AI tắt, đặt `AI_ENABLED=false`, khởi động lại backend/stack rồi chạy tại `frontend/`:

`npm run test:e2e -- --grep "US-001 v1.2 company flow"`
```

- [ ] **Step 4: Run all delivery gates with fresh evidence**

Run: `mvn -f backend/pom.xml -s .mvn/settings.xml test`

Expected: PASS with PostgreSQL Testcontainers available.

Run from `frontend/`: `npm run test:unit -- --run`

Expected: PASS.

Run from `frontend/`: `npm run typecheck`

Expected: PASS.

Run from `frontend/`: `npm run build`

Expected: PASS.

Run from `frontend/` against a stack started with `AI_ENABLED=false`: `npm run test:e2e -- --grep "US-001 v1.2 company flow"`

Expected: PASS, proving Company CRUD does not depend on AI.

- [ ] **Step 5: Scan the active contract for removed fields and placeholders**

Run: `rg -n "phone|address|description|Điện thoại|Địa chỉ|Mô tả" backend/src/main/java/vn/hblab/crm/domain/Company.java backend/src/main/java/vn/hblab/crm/crmcore/application/CompanyCommand.java backend/src/main/java/vn/hblab/crm/api/company frontend/src/api/companies.ts frontend/src/views/companies frontend/e2e/company-management.spec.ts`

Expected: no matches. Legacy nullable columns may remain only in the historical V2 migration.

- [ ] **Step 6: Commit acceptance coverage and documentation**

```bash
git add frontend/e2e/company-management.spec.ts README.md
git commit -m "test: cover company specification v1.2"
```

## Plan Self-Review

- **Spec coverage:** Task 1 covers the six-field data dictionary, required-field invariants, optional `size`, create/update parity, invalid input, field-level duplicate errors, uniqueness persistence, active list/detail exclusion, soft delete and related-row retention. Task 2 covers the approved screens, six visible fields, validation preservation, loading/empty/not-found/retry states and soft-delete copy. Task 3 covers the complete browser flow and operation with AI disabled.
- **Role boundary:** US-001 adds no role-specific denial, so both Sales and Quản trị use the same contract. Actual login fixtures and a duplicated authenticated TC-010 run are owned by US-046; this plan preserves that integration point without inventing temporary header-based authentication.
- **Migration safety:** V3 is additive. V1/V2 remain immutable, and their nullable legacy columns are deliberately left untouched while disappearing from the active domain/API/UI contract.
- **Type consistency:** `size` is used in `Company`, `CompanyCommand`, request/response DTOs, TypeScript `Company`, `CompanyInput`, form controls, list/detail views and E2E. The database column is consistently `company_size`.
- **Placeholder scan:** The plan contains no incomplete implementation steps; every test, signature, migration, command and expected result is explicit.
