# Definition of Ready — Review Result (AI Native CRM)

> Trạng thái: **PO REVIEW**. READY là khuyến nghị; chấp nhận cuối là cổng của con người.
> Phạm vi: 36 story in-scope. Đã loại 9 story deferred theo quyết định PO.
> Quyết định PO đã áp: (1) bỏ 9 feature phụ trợ — GIỮ FEAT-002 vì T-1 bắt buộc "người liên hệ"; (2) không tách story; (3) chạy DoR.

## Story bị đưa ra ngoài phạm vi (Won't-now, không DoR)
US-006, US-012, US-014, US-017, US-023, US-024, US-034, US-035, US-036
(FEAT-006/012/014/017/023/024/034/035/036)
> Rủi ro đã ghi nhận: bỏ FEAT-035 → tỉ lệ hoàn tác không có "màn hình Quản trị" như REQ-408 gợi ý; nếu BTC thử tay phần Quản trị là điểm hở.

## Checklist DoR (10 mục)
Actor · Business value · Description · Acceptance Criteria · Business rules · Dependencies · Ambiguity resolved · Small enough · Testable · Source traceability.

## Kết quả
| US | Feature | Priority | DoR |
|----|---------|----------|-----|
| US-001 | FEAT-001 | Must (17) | READY |
| US-002 | FEAT-002 | Should (12) — giữ vì T-1 | READY |
| US-003 | FEAT-003 | Must (17) | READY |
| US-004 | FEAT-004 | Should (14) | READY |
| US-005 | FEAT-005 | Should (12) | READY |
| US-007 | FEAT-007 | Should (14) | READY |
| US-008 | FEAT-008 | Must (16) | READY |
| US-009 | FEAT-009 | Could (10, T-1) | READY |
| US-010 | FEAT-010 | Could (10, T-1) | READY |
| US-011 | FEAT-011 | Must (16) | READY |
| US-013 | FEAT-013 | Must (19) | READY |
| US-015 | FEAT-015 | Should (12) | READY |
| US-016 | FEAT-016 | Should (13) | READY |
| US-018 | FEAT-018 | Should (15) | READY |
| US-019 | FEAT-019 | Should (12) | READY |
| US-020 | FEAT-020 | Should (13) | READY |
| US-021 | FEAT-021 | Should (14) | READY |
| US-022 | FEAT-022 | Should (12) | READY |
| US-025 | FEAT-025 | Must (17) | READY |
| US-026 | FEAT-026 | Should (14) | READY |
| US-027 | FEAT-027 | Could (11, T-6) | READY |
| US-028 | FEAT-028 | Must (16) | READY |
| US-029 | FEAT-029 | Should (13) | READY |
| US-030 | FEAT-030 | Should (14) | READY |
| US-031 | FEAT-031 | Must (17) | READY |
| US-032 | FEAT-032 | Should (12) | READY |
| US-033 | FEAT-033 | Should (12, T-8) | READY |
| US-037 | FEAT-037 | Should (15) | READY |
| US-038 | FEAT-038 | Could (11, T-9) | READY |
| US-039 | FEAT-039 | Could (9, T-9) | READY |
| US-040 | FEAT-040 | Must (17) | READY |
| US-041 | FEAT-041 | Must (17) | → Test-harness (phi tính năng) |
| US-042 | FEAT-042 | Must (18) | → Ops/Delivery (phi tính năng) |
| US-043 | FEAT-043 | Must (16) | → Ops/Delivery (phi tính năng) |
| US-044 | FEAT-044 | Must (17) | → NFR/Ops (phi tính năng) |
| US-046 | FEAT-046 | Must | READY (đăng nhập & phân vai — tách từ US-044) |
| US-045 | FEAT-045 | — | **Reclassified → Ops/Submission task** (không phải dev story) |

## US-045 — Tái phân loại thành Ops/Submission task (quyết định PO)
Theo QNA #2/#3: log là **log AI Agent (Claude Code)**, tự chảy về Grafana của công ty sau khi mỗi user config ở `tokens.hblab.ai:8443`. Đội **không xây tính năng logging** và **không tự lưu prompt log** — công ty tự thu. Vì vậy US-045 không còn là dev story; chuyển thành checklist vận hành/nộp bài:
- [ ] Mã nguồn trên GitLab HBLAB (AC-086 — bắt buộc §7.1).
- [ ] Mỗi thành viên đăng nhập & config `tokens.hblab.ai:8443` → log Claude Code tự lên Grafana (§7.2).
- [ ] Hoàn thành setup tokens **trước giờ bắt đầu sự kiện thứ 7** (QNA: chỉ chấm log trong thời gian sự kiện).

Resolved:
- [Q-12] ĐÓNG — không cần tự lưu prompt log; công ty tự thu log AI Agent qua Grafana.
- [Q-13] ĐÓNG — mỗi user tự config tokens theo hướng dẫn; mốc: trước giờ sự kiện.

## Caveat (không chặn READY)
- US-044: story lớn (build+env+persistence+auth); AC-082..085 đã tách nhánh để dev kiểm từng phần.
- US-031: hành vi READY; thuật toán diff (Q-05) là việc Architect/Dev.

## Kết luận
- **Feature backlog (tính năng hệ thống): 32 story READY** (thêm US-046 đăng nhập & phân vai; bỏ US-041/044 khỏi feature backlog).
- **0 story NOT READY.**
- **Phi tính năng — Ops/Delivery/NFR/Test-harness (ngoài feature backlog):** US-041 (test-harness), US-042, US-043, US-044 (NFR/Ops), US-045. Bắt buộc theo §6/§7 nhưng không phải tính năng người dùng.
- Actor đã làm sạch: chỉ còn **A-Sales, A-Admin** (login) + **A-AI** (agent nội bộ, không login). Đã loại: Ban giám khảo (login nhờ vai Sales/Admin), Dịch vụ ngoài/LLM (dependency), Grafana/GitLab (hạ tầng), Người sở hữu (gộp vào Sales).

## ✅ PO APPROVED — Backlog đã khoá
- Người duyệt: PO (huantv1@hblab.vn) · Ngày: 2026-08-13.
- Phạm vi duyệt: **32 story tính năng READY** khoá cho phát triển (gồm US-046 đăng nhập & phân vai).
- Phi tính năng (Ops/Delivery/NFR/Test-harness, ngoài feature backlog nhưng bắt buộc §6/§7): US-041, US-042, US-043, US-044, US-045.
- Deferred (Won't-now): 9 feature.
- Bước tiếp: chuyển sang System Design (Architect), dùng traceability REQ→EPIC→FEAT→US→AC làm đầu vào.
