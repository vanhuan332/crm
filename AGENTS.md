# Hướng dẫn cho contributor/agent

- Đọc `docs/01-product/prd.md`, `docs/02-analysis/architect-handoff.md`, `docs/03-architecture/system/architecture.md` và `docs/03-architecture/system/project-rules.md` trước khi thay đổi chức năng.
- Tuân thủ modular monolith: API → application → domain → infrastructure. Không đưa business rule vào controller.
- Mọi automation bắt buộc qua `AutomationPolicyGuard`; không thêm đường gọi tắt guardrail.
- Không thêm Grafana, monitoring, telemetry, log shipping hoặc lưu prompt/log của agent.
- Chỉ thêm schema qua `backend/src/main/resources/db/migration/`; giữ cấu hình ở biến môi trường.
- Frontend nằm trong `frontend/`, dùng Vue.js 3 + Pinia + Vue Router + Axios; route mới phải phản ánh một module/use case backend.
- Giữ Dockerfile multi-stage và Docker Compose deployable; image runtime không được chứa source build, Maven hoặc Node.
- Chạy `mvn -f backend/pom.xml -s .mvn/settings.xml test` trước khi bàn giao thay đổi backend.
