# US-001 Company Management Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Deliver Sales-facing Company CRUD with required-field validation, case-insensitive active-name uniqueness, soft delete, and Vue routes for list/create/detail/edit.

**Architecture:** Implement the Company vertical slice in `crmcore` as API → application → domain → infrastructure. The REST controller is DTO-only; transactional application services own CRUD orchestration and call `AutomationPolicyGuard` before soft deletion. Vue consumes the REST contract through a focused company API module and shared Company form component.

**Tech Stack:** Java 17, Spring Boot 3.3, Spring Data JPA, Flyway, PostgreSQL, JUnit 5/Testcontainers, Vue 3, TypeScript, Pinia, Vue Router, Axios, Vitest, Playwright.

**Spec:** `docs/05-delivery/specs/2026-08-13-us-001-company-management-design.md`

## Global Constraints

- Keep modular-monolith dependency direction API → application → domain → infrastructure; no business rule in a controller.
- Every automation command must pass `AutomationPolicyGuard`; AI must never delete human-created CRM data.
- Add schema only through `backend/src/main/resources/db/migration/`; keep JPA `ddl-auto: validate`.
- Do not add Grafana, telemetry, monitoring, log shipping, or prompt/agent-log storage.
- CRM Company CRUD must operate when `AI_ENABLED=false`.
- The mandatory values are name, industry, and one of `TRADITIONAL`, `IT_SOLUTION`, `IT_PRODUCT`, `TECH_STARTUP`, `OTHER_ITO`.
- Active names are unique after trim and case-folding; delete is soft delete.
- Keep configuration in environment variables and retain Docker production constraints.

---

## File structure

| Path | Responsibility |
|---|---|
| `backend/src/main/resources/db/migration/V2__complete_company_crud.sql` | Company fields, audit/version columns, soft-delete and active-name index |
| `backend/src/main/java/vn/hblab/crm/domain/Company.java` | Company invariants and state transitions |
| `backend/src/main/java/vn/hblab/crm/domain/CompanyType.java` | Five stable business enum codes |
| `backend/src/main/java/vn/hblab/crm/crmcore/application/CompanyService.java` | Transactional Company commands/queries |
| `backend/src/main/java/vn/hblab/crm/crmcore/infrastructure/CompanyJpaRepository.java` | Active-record persistence/query port |
| `backend/src/main/java/vn/hblab/crm/api/company/*` | REST DTOs, controller, error mapping |
| `frontend/src/api/companies.ts` | Typed Axios Company client |
| `frontend/src/views/companies/*` | List, detail, shared form, delete dialog routes |
| `frontend/src/stores/companies.ts` | Company list/detail/request state |
| `frontend/tests/*` and `frontend/e2e/*` | UI and browser acceptance coverage |

### Task 1: Complete Company persistence contract

**Files:**
- Create: `backend/src/main/resources/db/migration/V2__complete_company_crud.sql`
- Modify: `backend/pom.xml`
- Create: `backend/src/test/java/vn/hblab/crm/crmcore/infrastructure/CompanyJpaRepositoryIT.java`

**Interfaces:**
- Produces table columns `phone`, `address`, `description`, `created_at`, `updated_at`, `deleted_at`, `version` and the active-name unique index.
- Produces PostgreSQL integration-test support through `org.testcontainers:postgresql`.

- [ ] **Step 1: Write the failing persistence integration test**

```java
@Test
void activeNamesAreUniqueIgnoringCaseAndWhitespace() {
    repository.saveAndFlush(company(" ABC Tech "));
    assertThatThrownBy(() -> repository.saveAndFlush(company("abc tech")))
        .isInstanceOf(DataIntegrityViolationException.class);
}
```

- [ ] **Step 2: Run the test to verify it fails**

Run: `mvn -f backend/pom.xml -s .mvn/settings.xml -Dtest=CompanyJpaRepositoryIT test`

Expected: FAIL because the migration/index and test database setup do not yet exist.

- [ ] **Step 3: Add migration and test dependencies**

Create `V2__complete_company_crud.sql` with PostgreSQL-compatible DDL. Do not edit the already-applied V1 migration in a deployed environment.

```sql
ALTER TABLE companies
  ADD COLUMN phone VARCHAR(30),
  ADD COLUMN address VARCHAR(500),
  ADD COLUMN description VARCHAR(2000),
  ADD COLUMN created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  ADD COLUMN updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  ADD COLUMN deleted_at TIMESTAMPTZ,
  ADD COLUMN version BIGINT NOT NULL DEFAULT 0;

CREATE UNIQUE INDEX uq_companies_active_normalized_name
  ON companies (lower(btrim(name))) WHERE deleted_at IS NULL;
```

Add test-scoped `org.testcontainers:junit-jupiter` and `org.testcontainers:postgresql`; configure the integration test with `@Testcontainers`, `@SpringBootTest`, and a static `PostgreSQLContainer` exposed via `@DynamicPropertySource`.

- [ ] **Step 4: Run the persistence test to verify it passes**

Run: `mvn -f backend/pom.xml -s .mvn/settings.xml -Dtest=CompanyJpaRepositoryIT test`

Expected: PASS; a second normalized active name is rejected by PostgreSQL.

- [ ] **Step 5: Commit**

```bash
git add backend/pom.xml backend/src/main/resources/db/migration/V2__complete_company_crud.sql backend/src/test/java/vn/hblab/crm/crmcore/infrastructure/CompanyJpaRepositoryIT.java
git commit -m "feat: complete company persistence schema"
```

### Task 2: Build domain and application use cases

**Files:**
- Modify: `backend/src/main/java/vn/hblab/crm/domain/Company.java`
- Modify: `backend/src/main/java/vn/hblab/crm/domain/CompanyType.java`
- Create: `backend/src/main/java/vn/hblab/crm/crmcore/application/CompanyService.java`
- Create: `backend/src/main/java/vn/hblab/crm/crmcore/application/CompanyCommand.java`
- Create: `backend/src/main/java/vn/hblab/crm/crmcore/application/CompanyNotFoundException.java`
- Create: `backend/src/main/java/vn/hblab/crm/crmcore/infrastructure/CompanyJpaRepository.java`
- Create: `backend/src/test/java/vn/hblab/crm/domain/CompanyTest.java`
- Create: `backend/src/test/java/vn/hblab/crm/crmcore/application/CompanyServiceTest.java`

**Interfaces:**
- Consumes `AutomationPolicyGuard.assertCanDelete(ActorType)` and `ActorType`.
- Produces `CompanyService.create(CompanyCommand)`, `list()`, `get(long)`, `update(long, CompanyCommand)`, and `delete(long, ActorType)`.

- [ ] **Step 1: Write failing domain/application tests**

```java
@Test
void rejectsBlankRequiredFieldsAndInvalidWebsite() {
    assertThatThrownBy(() -> Company.create(" ", "IT", CompanyType.IT_SOLUTION, null, null, null, null, null))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> Company.create("ABC", "IT", CompanyType.IT_SOLUTION, null, "ftp://abc.test", null, null, null))
        .isInstanceOf(IllegalArgumentException.class);
}

@Test
void aiCannotSoftDeleteCompany() {
    assertThatThrownBy(() -> service.delete(1L, ActorType.AI_SYSTEM))
        .isInstanceOf(AutomationPolicyViolation.class);
}
```

- [ ] **Step 2: Run focused tests to verify they fail**

Run: `mvn -f backend/pom.xml -s .mvn/settings.xml -Dtest=CompanyTest,CompanyServiceTest test`

Expected: FAIL because Company factories, service, repository and deletion guard call are absent.

- [ ] **Step 3: Implement minimal domain and use cases**

Define an immutable command record and explicit service methods.

```java
public record CompanyCommand(String name, String industry, CompanyType companyType,
        String country, String website, String phone, String address,
        String description, long version) { }

@Transactional
public void delete(long id, ActorType actor) {
    guard.assertCanDelete(actor);
    repository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(() -> new CompanyNotFoundException(id))
        .softDelete();
}
```

`Company` must trim non-null text, validate lengths and `http`/`https` URLs, map `website` to the existing `website_url` database column if it remains named that way, update `updatedAt`, use `@Version`, and set `deletedAt` instead of calling repository delete. `update` must compare `CompanyCommand.version()` with the loaded entity version and throw a conflict exception before mutation when they differ. Rename enum `TECH_BASED_STARTUP` to `TECH_STARTUP` and ensure V2 migrates any existing stored enum value before validation.

- [ ] **Step 4: Run focused tests to verify they pass**

Run: `mvn -f backend/pom.xml -s .mvn/settings.xml -Dtest=CompanyTest,CompanyServiceTest test`

Expected: PASS; human deletion soft-deletes and AI deletion is rejected before persistence.

- [ ] **Step 5: Commit**

```bash
git add backend/src/main/java/vn/hblab/crm/domain backend/src/main/java/vn/hblab/crm/crmcore backend/src/test/java/vn/hblab/crm/domain backend/src/test/java/vn/hblab/crm/crmcore
git commit -m "feat: add company domain use cases"
```

### Task 3: Expose and test the REST contract

**Files:**
- Create: `backend/src/main/java/vn/hblab/crm/api/company/CompanyController.java`
- Create: `backend/src/main/java/vn/hblab/crm/api/company/CompanyRequest.java`
- Create: `backend/src/main/java/vn/hblab/crm/api/company/CompanyResponse.java`
- Create: `backend/src/main/java/vn/hblab/crm/api/ApiExceptionHandler.java`
- Create: `backend/src/test/java/vn/hblab/crm/api/company/CompanyControllerIT.java`

**Interfaces:**
- Consumes all `CompanyService` methods from Task 2.
- Produces REST endpoints `/api/companies` and payload `{ id, name, industry, companyType, country, website, phone, address, description, createdAt, updatedAt, version }`.

- [ ] **Step 1: Write failing MockMvc integration tests**

```java
mockMvc.perform(post("/api/companies").contentType(APPLICATION_JSON)
    .content("""{"name":"ABC","industry":"Software","companyType":"IT_SOLUTION","version":0}"""))
  .andExpect(status().isCreated())
  .andExpect(jsonPath("$.name").value("ABC"));

mockMvc.perform(post("/api/companies").contentType(APPLICATION_JSON)
    .content("""{"name":" ","industry":"Software","companyType":"IT_SOLUTION","version":0}"""))
  .andExpect(status().isBadRequest())
  .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
```

- [ ] **Step 2: Run controller tests to verify they fail**

Run: `mvn -f backend/pom.xml -s .mvn/settings.xml -Dtest=CompanyControllerIT test`

Expected: FAIL because no Company controller or exception mapping exists.

- [ ] **Step 3: Implement DTO mapping, controller and error mapping**

```java
@RestController
@RequestMapping("/api/companies")
class CompanyController {
    @PostMapping
    ResponseEntity<CompanyResponse> create(@Valid @RequestBody CompanyRequest request) { /* map and return 201 */ }
    @GetMapping List<CompanyResponse> list() { /* map active companies */ }
    @GetMapping("/{id}") CompanyResponse get(@PathVariable long id) { /* map */ }
    @PutMapping("/{id}") CompanyResponse update(@PathVariable long id, @Valid @RequestBody CompanyRequest request) { /* map */ }
    @DeleteMapping("/{id}") @ResponseStatus(NO_CONTENT) void delete(@PathVariable long id) { /* HUMAN actor */ }
}
```

Map Bean Validation/domain validation to `400` field errors, `CompanyNotFoundException` to `404`, database active-name conflict to `409 COMPANY_NAME_CONFLICT`, and `ObjectOptimisticLockingFailureException` to `409 COMPANY_MODIFIED`. Do not leak database exceptions.

- [ ] **Step 4: Run API tests to verify they pass**

Run: `mvn -f backend/pom.xml -s .mvn/settings.xml -Dtest=CompanyControllerIT test`

Expected: PASS for create/list/get/update/delete, missing/invalid required fields, invalid enum, duplicate normalized name, soft-delete 404 and version conflict.

- [ ] **Step 5: Commit**

```bash
git add backend/src/main/java/vn/hblab/crm/api backend/src/test/java/vn/hblab/crm/api
git commit -m "feat: expose company CRUD API"
```

### Task 4: Add typed client, state and read routes

**Files:**
- Create: `frontend/src/api/companies.ts`
- Create: `frontend/src/stores/companies.ts`
- Create: `frontend/src/views/companies/CompaniesListView.vue`
- Create: `frontend/src/views/companies/CompanyDetailView.vue`
- Modify: `frontend/src/router.ts`
- Modify: `frontend/src/App.vue`
- Create: `frontend/src/views/companies/__tests__/CompaniesListView.spec.ts`

**Interfaces:**
- Consumes `GET /api/companies` and `GET /api/companies/{id}` from Task 3.
- Produces TypeScript interface `Company`, `listCompanies(): Promise<Company[]>`, `getCompany(id: number): Promise<Company>`, and routes `/companies`, `/companies/:id`.

- [ ] **Step 1: Write failing Vue test**

```ts
it('renders a loaded company and links to its detail page', async () => {
  mockListCompanies.mockResolvedValue([company])
  mount(CompaniesListView, { global: { plugins: [router] } })
  await flushPromises()
  expect(screen.getByText('ABC Technology')).toBeTruthy()
  expect(screen.getByRole('link', { name: 'Xem' }).getAttribute('href')).toBe('/companies/1')
})
```

- [ ] **Step 2: Run the frontend test to verify it fails**

Run: `npm run test:unit -- --run frontend/src/views/companies/__tests__/CompaniesListView.spec.ts`

Expected: FAIL because Vitest script, Company client/store and list view are absent.

- [ ] **Step 3: Implement the read client and routes**

Install `vitest`, `@vue/test-utils`, `jsdom`, and `@testing-library/vue` as dev dependencies; add `test:unit` to `frontend/package.json`. Keep Axios use in one client module.

```ts
export interface Company { id: number; name: string; industry: string; companyType: CompanyType; country: string | null; website: string | null; phone: string | null; address: string | null; description: string | null; createdAt: string; updatedAt: string; version: number }
export const listCompanies = () => http.get<Company[]>('/companies').then(r => r.data)
export const getCompany = (id: number) => http.get<Company>(`/companies/${id}`).then(r => r.data)
```

Replace the placeholder `/companies` route with `CompaniesListView`; add `/companies/:id` with a numeric route prop. Implement loading, empty, retry and not-found states without rendering unrelated company data.

- [ ] **Step 4: Run test and typecheck to verify they pass**

Run: `npm run test:unit -- --run frontend/src/views/companies/__tests__/CompaniesListView.spec.ts; npm run typecheck`

Expected: PASS; typecheck has no errors.

- [ ] **Step 5: Commit**

```bash
git add frontend/package.json frontend/package-lock.json frontend/src/api frontend/src/stores frontend/src/views/companies frontend/src/router.ts frontend/src/App.vue
git commit -m "feat: add company list and detail views"
```

### Task 5: Implement create/edit and confirmed deletion UX

**Files:**
- Modify: `frontend/src/api/companies.ts`
- Modify: `frontend/src/stores/companies.ts`
- Create: `frontend/src/views/companies/CompanyForm.vue`
- Create: `frontend/src/views/companies/CompanyFormView.vue`
- Create: `frontend/src/views/companies/CompanyDeleteDialog.vue`
- Modify: `frontend/src/views/companies/CompanyDetailView.vue`
- Modify: `frontend/src/views/companies/CompaniesListView.vue`
- Modify: `frontend/src/router.ts`
- Create: `frontend/src/views/companies/__tests__/CompanyForm.spec.ts`
- Create: `frontend/src/views/companies/__tests__/CompanyDeleteDialog.spec.ts`

**Interfaces:**
- Consumes `POST`, `PUT`, `DELETE` Company endpoints and `Company.version` from Task 3.
- Produces routes `/companies/new` and `/companies/:id/edit`; successful create/edit routes to `/companies/:id`.

- [ ] **Step 1: Write failing form and dialog tests**

```ts
it('does not submit blank name and keeps entered industry', async () => {
  await user.type(screen.getByLabelText('Ngành'), 'Software')
  await user.click(screen.getByRole('button', { name: 'Lưu' }))
  expect(screen.getByText('Tên công ty là bắt buộc')).toBeTruthy()
  expect((screen.getByLabelText('Ngành') as HTMLInputElement).value).toBe('Software')
})

it('calls delete only after explicit confirmation', async () => {
  await user.click(screen.getByRole('button', { name: 'Xóa công ty' }))
  expect(mockDeleteCompany).toHaveBeenCalledWith(1)
})
```

- [ ] **Step 2: Run the tests to verify they fail**

Run: `npm run test:unit -- --run frontend/src/views/companies/__tests__/CompanyForm.spec.ts frontend/src/views/companies/__tests__/CompanyDeleteDialog.spec.ts`

Expected: FAIL because the shared form and delete dialog do not exist.

- [ ] **Step 3: Implement form, API writes and deletion**

```ts
export type CompanyInput = Omit<Company, 'id' | 'createdAt' | 'updatedAt'>
export const createCompany = (input: CompanyInput) => http.post<Company>('/companies', input).then(r => r.data)
export const updateCompany = (id: number, input: CompanyInput) => http.put<Company>(`/companies/${id}`, input).then(r => r.data)
export const deleteCompany = (id: number) => http.delete(`/companies/${id}`)
```

Use one accessible form with labels, required indicators, select options matching the five enum values, first-error focus, disabled submit during request, and server `fieldErrors` mapping. `Hủy` routes back without an API call. The dialog must trap focus, show the company name, and only call delete after the explicit confirm button; on success route to `/companies`.

- [ ] **Step 4: Run frontend tests, typecheck and build**

Run: `npm run test:unit -- --run; npm run typecheck; npm run build`

Expected: PASS; the production Vue build succeeds.

- [ ] **Step 5: Commit**

```bash
git add frontend/src/api/companies.ts frontend/src/stores/companies.ts frontend/src/views/companies frontend/src/router.ts
git commit -m "feat: add company create edit and delete UX"
```

### Task 6: Add browser acceptance coverage and run delivery gates

**Files:**
- Modify: `frontend/package.json`
- Modify: `frontend/package-lock.json`
- Create: `frontend/playwright.config.ts`
- Create: `frontend/e2e/company-management.spec.ts`
- Modify: `README.md`

**Interfaces:**
- Consumes the complete Company REST/UI contract from Tasks 1–5.
- Produces `npm run test:e2e` and operator instructions to run the T-1 Company flow against the production-like stack.

- [ ] **Step 1: Write the failing Playwright acceptance test**

```ts
test('Sales completes create, detail, update and soft delete', async ({ page }) => {
  await page.goto('/companies/new')
  await page.getByLabel('Tên công ty').fill('ABC Technology')
  await page.getByLabel('Ngành').fill('Software')
  await page.getByLabel('Loại công ty').selectOption('IT_SOLUTION')
  await page.getByRole('button', { name: 'Lưu' }).click()
  await expect(page).toHaveURL(/\/companies\/\d+$/)
  await page.getByRole('button', { name: 'Xóa' }).click()
  await page.getByRole('button', { name: 'Xóa công ty' }).click()
  await expect(page).toHaveURL('/companies')
})
```

- [ ] **Step 2: Run it to verify it fails before Playwright setup**

Run: `npm run test:e2e -- --grep "create, detail, update and soft delete"`

Expected: FAIL because the Playwright script/configuration and full UI flow are absent until Tasks 1–5 are complete.

- [ ] **Step 3: Configure Playwright and document execution**

Install `@playwright/test`, add `test:e2e`, configure `baseURL` from `E2E_BASE_URL` defaulting to `http://localhost`, and keep credentials/configuration in environment variables. Document: start the production-like Docker Compose stack, run the idempotent seed/reset command, then run the backend and frontend gates below. Do not add telemetry, trace upload, video upload, or external reporting.

- [ ] **Step 4: Run complete verification**

Run: `mvn -f backend/pom.xml -s .mvn/settings.xml test; npm run test:unit -- --run; npm run typecheck; npm run build; npm run test:e2e`

Expected: every command exits 0; the Company CRUD flow passes with AI disabled as well as the default configuration.

- [ ] **Step 5: Commit**

```bash
git add frontend/package.json frontend/package-lock.json frontend/playwright.config.ts frontend/e2e/company-management.spec.ts README.md
git commit -m "test: cover company management acceptance flow"
```

## Plan self-review

Spec coverage: Tasks 1–3 cover persistence, validation, API, uniqueness, soft delete and guardrail; Tasks 4–5 cover list/detail/create/edit/delete states and accessibility; Task 6 covers the E2E route through T-1 and delivery gates. AI remains outside the feature path.

Type consistency: `CompanyCommand`, `CompanyService`, `Company`, `CompanyInput`, REST fields and optimistic `version` are named consistently across all tasks.
