# Kiến trúc hệ thống — AI-Native CRM

## Quyết định

- **Hai deployable đơn giản:** Vue.js 3 SPA (Pinia, Vue Router, Axios) gọi REST API JSON tới Spring Boot 3 modular monolith. Backend dùng Java 17, JPA/Hibernate, PostgreSQL và Flyway.
- **Không xây monitoring/logging:** không Grafana, log-agent, dashboard observability, trace pipeline hay prompt log. `Audit` nghiệp vụ vẫn được giữ vì là dữ liệu chức năng của CRM.
- **Cấu hình:** chỉ qua biến môi trường (`DATABASE_*`, `AI_ENABLED`, `SCAN_INTERVAL_SECONDS`); chu kỳ mặc định 60 giây.

## Lớp và hướng phụ thuộc

```text
Vue.js 3 SPA (presentation) -- HTTP/JSON --> Spring Boot REST API
                                              -> application (use cases, transactions, policy enforcement)
                                                -> domain (entities, business invariants)
                                                  -> infrastructure (JPA, scheduler, source/LLM adapters)
```

Backend gồm đúng các service trong sơ đồ: `crm-core`, `observation`, `proposal`, `autonomy`, `follow-up-loop`, `admin-safety`; phần dùng chung là `common-infrastructure` (auth 2 vai, RBAC, validation, exception handling, auditing). Chúng dùng chung một database PostgreSQL nhưng không gọi trực tiếp repository của nhau; giao tiếp qua application service/domain event.

Frontend có các route: Dashboard/Tổng quan, Công ty & Contact, Cơ hội & Pipeline 7 giai đoạn, Hoạt động & Timeline, AI Insight, Gợi ý chờ duyệt, Admin & Cấu hình.

## AI-native data model

`Company 1—N Observation 1—N Claim 1—N Proposal`.

- `Observation`: nội dung nguồn bất biến, URL, thời điểm đọc và trạng thái đọc.
- `Claim`: phát hiện có loại tin, mức chắc chắn, câu trích và offset bắt buộc. Claim thừa kế Company qua Observation, không nối trực tiếp Opportunity/Contact/Activity.
- `Proposal`: gợi ý chờ Sales quyết. Chỉ decision `APPROVED` hoặc `EDITED_AND_APPROVED` mới tạo thay đổi CRM.
- `NextStepAutomation`: snapshot trước/sau, Claim kích hoạt, hạn hoàn tác 7 ngày.

## Guardrail bắt buộc tại application service

Mọi command nhận `ActorType` (`HUMAN`, `AI_SYSTEM`). `AutomationPolicyGuard` chặn trước khi persistence:

| AI_SYSTEM bị cấm | Cách đảm bảo |
|---|---|
| đổi stage hoặc giá trị opportunity | `assertCanChangeOpportunity` ném lỗi |
| xóa dữ liệu do người tạo | `assertCanDelete` ném lỗi |
| gửi email/push/liên hệ khách | không có outbound customer adapter; guard chặn command |

AI chỉ được tạo Observation/Claim/Proposal, tự đặt Next Step theo chính sách, và thêm timeline do hệ thống ở scan loop. Kill switch được kiểm tra tại lúc bắt đầu từng use case AI và trước mỗi company trong vòng quét; tắt không xóa dữ liệu đã sinh.

## Provenance và phát hiện nội dung mới

Claim lưu `quote_start`/`quote_end` trên nội dung Observation. UI dùng các offset này để cuộn và highlight đúng đoạn nguồn. Scan chuẩn hóa nội dung (newline/whitespace), tạo SHA-256; hash trùng Observation gần nhất thì không phát hiện mới. Unique key `(company_id, normalized_content_hash)` ngăn vòng quét trùng lặp.

## Tích hợp hạ tầng

- Scheduler là adapter kích hoạt `follow-up-loop` theo chu kỳ env; không chứa nghiệp vụ.
- Email/push chỉ có in-app notification trong base. Outbound liên hệ khách bị guardrail chặn.
- File storage là port tùy chọn cho file đính kèm/bản lưu; PostgreSQL vẫn là nguồn dữ liệu chính.
- Backup do PostgreSQL/triển khai vận hành xử lý, không đặt logic backup vào domain.

## Không nằm trong scope

Không có Grafana, GitLab integration, telemetry, centralized log, log dashboard, hay observation/agent-log service. Audit nghiệp vụ, notification trong app và lịch sử quyết định được giữ để đáp ứng user stories.
