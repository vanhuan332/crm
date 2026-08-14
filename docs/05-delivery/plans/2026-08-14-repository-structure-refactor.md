# Repository Structure Refactor Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Organize backend, frontend and documentation into a clear repository layout while preserving builds, deployment and traceability.

**Architecture:** Spring Boot becomes a deployable in `backend/`; Vue remains in `frontend/`; documentation is organized by lifecycle under numbered directories. The root remains the orchestration boundary for Docker Compose and environment configuration.

**Tech Stack:** Java 17, Maven, Spring Boot 3, Vue 3, npm, Docker Compose, PowerShell, Git.

**Spec:** `docs/05-delivery/specs/2026-08-14-repository-structure-refactor-design.md`

## Global Constraints

- Do not change Java packages, APIs, Flyway schema, business rules or modular-monolith boundaries.
- Keep AutomationPolicyGuard as the mandatory automation guard; do not introduce bypasses.
- Keep Dockerfiles multi-stage; runtime images contain no source, Maven or Node.
- Do not add monitoring, telemetry, Grafana, log shipping or agent/prompt logs.
- Preserve unrelated uncommitted user work.
- Backend verification command: `mvn -f backend/pom.xml -s .mvn/settings.xml test`.

---

## File structure

| Path | Responsibility |
|---|---|
| `backend/{pom.xml,Dockerfile,src/**}` | Spring Boot Maven deployable |
| `frontend/**` | Vue SPA deployable, unchanged location |
| `docs/01-product/**` | PRD and immutable source materials |
| `docs/02-analysis/**` | Requirement analysis and architect handoff |
| `docs/03-architecture/**` | Architecture, deployment, module map and ADRs |
| `docs/04-specifications/**` | Business specifications |
| `docs/05-delivery/{specs,plans}/**` | Approved designs and implementation plans |
| Root `docker-compose.yml`, `.env.example`, `README.md`, `AGENTS.md` | Orchestration and contributor entry points |

### Task 1: Move source and documentation into the target layout

**Files:**
- Move: `pom.xml` → `backend/pom.xml`; `Dockerfile` → `backend/Dockerfile`; `src/**` → `backend/src/**`.
- Move: `docs/prd.md` → `docs/01-product/prd.md`; hackathon source folder → `docs/01-product/source/`.
- Move: `docs/analysis/**` → `docs/02-analysis/**`; `docs/system/**` → `docs/03-architecture/**`; `docs/specification/**` → `docs/04-specifications/**`; legacy `docs/superpowers/{specs,plans}/**` → `docs/05-delivery/{specs,plans}/**`.

**Interfaces:**
- Consumes: existing Maven-standard source layout and versioned project documents.
- Produces: new paths consumed by Docker Compose, contributor instructions and links.

- [ ] **Step 1: Record pre-move inventory**

Run:

```powershell
git status --short
rg --files src docs pom.xml Dockerfile
```

Expected: capture every tracked source/document path and separately note unrelated dirty frontend files.

- [ ] **Step 2: Create destination parents**

Run:

```powershell
New-Item -ItemType Directory -Force -Path backend, docs/01-product, docs/02-analysis, docs/03-architecture, docs/04-specifications, docs/05-delivery/specs, docs/05-delivery/plans
```

Expected: only empty parent directories are created.

- [ ] **Step 3: Perform Git-aware moves**

Run:

```powershell
git mv pom.xml backend/pom.xml
git mv Dockerfile backend/Dockerfile
git mv src backend/src
git mv docs/prd.md docs/01-product/prd.md
git mv 'docs/2. Public_TÀI LIỆU AI HACKATHON - CHỦ ĐỀ 01' docs/01-product/source
git mv docs/analysis docs/02-analysis
git mv docs/system docs/03-architecture
git mv docs/specification docs/04-specifications
git mv docs/superpowers/specs/* docs/05-delivery/specs/
git mv docs/superpowers/plans/* docs/05-delivery/plans/
```

Expected: Java package declarations and Flyway contents are byte-for-byte unchanged.

- [ ] **Step 4: Assert mandatory moved paths**

Run:

```powershell
Test-Path backend/pom.xml
Test-Path backend/src/main/java/vn/hblab/crm/CrmApplication.java
Test-Path backend/src/main/resources/db/migration/V1__initial_crm_schema.sql
Test-Path docs/01-product/prd.md
Test-Path docs/03-architecture/architecture.md
```

Expected: each command returns `True`.

- [ ] **Step 5: Commit only intended moves**

```powershell
git add -A backend docs
git commit -m "refactor: organize backend and documentation"
```

Expected: no `frontend/package*.json`, `frontend/e2e/**` or other pre-existing work is staged.

### Task 2: Rewire build, deploy, instructions and links

**Files:**
- Modify: `backend/Dockerfile`, `docker-compose.yml`, `README.md`, `AGENTS.md`, and Markdown files under `docs/**`.

**Interfaces:**
- Consumes: moved backend and documentation paths.
- Produces: reproducible root commands, valid Markdown links and Docker build context.

- [ ] **Step 1: Preserve backend Dockerfile instructions relative to the new build context**

`backend/Dockerfile` must continue to use:

```dockerfile
COPY pom.xml ./
RUN mvn -B -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -B -DskipTests package
```

Expected: with context `./backend`, runtime still copies only `/workspace/target/ai-native-crm-*.jar`.

- [ ] **Step 2: Update Compose backend context**

Replace `app.build` with:

```yaml
    build:
      context: ./backend
      dockerfile: Dockerfile
```

Expected: `web.build.context` remains `./frontend`; service names and environment variables do not change.

- [ ] **Step 3: Update operational and document paths**

Apply these replacements to live references:

```text
docs/prd.md -> docs/01-product/prd.md
docs/analysis/ -> docs/02-analysis/
docs/system/ -> docs/03-architecture/
docs/specification/ -> docs/04-specifications/
docs/superpowers/specs|plans/ -> docs/05-delivery/specs|plans/
src/main/resources/db/migration/ -> backend/src/main/resources/db/migration/
mvn -s .mvn/settings.xml -> mvn -f backend/pom.xml -s .mvn/settings.xml
```

Expected: source document body remains unmodified; only path references change.

- [ ] **Step 4: Detect stale live paths**

Run:

```powershell
rg -n 'docs/(analysis|system|specification|superpowers)|docs/prd\.md|(^|[^a-zA-Z])src/main|mvn -s \.mvn/settings\.xml' README.md AGENTS.md docs
```

Expected: no matches except explicitly historical material that declares a pre-refactor state.

- [ ] **Step 5: Validate Compose resolution**

Run:

```powershell
docker compose --env-file .env.example config
```

Expected: exit 0 and rendered app build context ends in `backend`.

- [ ] **Step 6: Commit links and configuration only**

```powershell
git add backend/Dockerfile docker-compose.yml README.md AGENTS.md docs
git commit -m "chore: update paths for repository layout"
```

Expected: user-owned frontend changes remain unstaged.

### Task 3: Ignore and remove reproducible local artefacts, then verify builds

**Files:**
- Modify: `.gitignore`.
- Remove: `target/`, `.codegraph/`, `frontend/.task5-verify-dist/`, `frontend/test-results/`, `frontend/verification-dist/`.

**Interfaces:**
- Consumes: finalized backend layout and frontend scripts.
- Produces: ignored generated output and verified build commands.

- [ ] **Step 1: Add exact ignore rules**

Append, retaining existing rules:

```gitignore
backend/target/
frontend/verification-dist/
frontend/test-results/
frontend/.task*-verify-dist/
.codegraph/
```

Expected: existing `frontend/node_modules/` and `frontend/dist/` rules remain.

- [ ] **Step 2: Verify deletion targets**

Run:

```powershell
Get-Item -Force target, .codegraph, frontend/.task5-verify-dist, frontend/test-results, frontend/verification-dist | Select-Object FullName,Attributes
```

Expected: every target is a generated directory named in the approved spec.

- [ ] **Step 3: Remove only verified generated artefacts**

Run:

```powershell
Remove-Item -Recurse -Force -LiteralPath target
Remove-Item -Recurse -Force -LiteralPath .codegraph
Remove-Item -Recurse -Force -LiteralPath frontend/.task5-verify-dist
Remove-Item -Recurse -Force -LiteralPath frontend/test-results
Remove-Item -Recurse -Force -LiteralPath frontend/verification-dist
```

Expected: `backend/`, `frontend/src/`, `frontend/e2e/` and `docs/` stay intact.

- [ ] **Step 4: Run required checks**

Run:

```powershell
mvn -f backend/pom.xml -s .mvn/settings.xml test
Push-Location frontend
npm run typecheck
npm run build
Pop-Location
```

Expected: all commands exit 0.

- [ ] **Step 5: Verify outputs are ignored**

Run:

```powershell
git check-ignore -v backend/target frontend/dist frontend/test-results frontend/verification-dist .codegraph
git status --short
```

Expected: ignore rules are reported and status contains no generated artefacts.

- [ ] **Step 6: Commit only ignore rules**

```powershell
git add .gitignore
git commit -m "chore: ignore generated verification artefacts"
```

Expected: untracked user test files are never added.

## Plan self-review

- Spec coverage: Task 1 implements every target move; Task 2 updates every build, deployment and documentation dependency; Task 3 cleans only approved artefacts and executes all required verification.
- Placeholder scan: no TBD/TODO or implicit test steps.
- Consistency: every backend command uses `backend/pom.xml`; all docs destinations follow the approved numbered structure.
