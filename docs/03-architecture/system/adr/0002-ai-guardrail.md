# ADR-0002: Guardrail AI ở application service

## Status

Accepted.

## Decision

Mỗi mutation use case nhận `ActorType`. `AutomationPolicyGuard` được gọi trong application service trước repository. `AI_SYSTEM` bị chặn thay stage/giá trị opportunity, xóa dữ liệu người tạo và mọi outbound customer contact.

## Consequences

Quy tắc đúng cả API, scheduler, CLI/test harness hoặc tool call. Test guardrail không yêu cầu UI. AI có thể đọc nguồn và tạo các thực thể được cấp quyền; kill switch kiểm tra ở mọi entry point AI.

