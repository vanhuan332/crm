# Business Specification — US-010: Màn hình tổng quan

## 1. Document Information
| Field | Value |
|---|---|
| Story | `US-010` |
| Version | `1.1` |
| Status | `AWAITING_SPECIFICATION_APPROVAL` |
| Sources | `REQ-112`, `FEAT-010`, `AC-024`, DoR |
## 2. Purpose
**[CONFIRMED — REQ-112]** Xác định thông tin tổng quan cho Sales nắm nhanh tình hình.
## 3. User Story
**[CONFIRMED — US-010]** As a Sales, I want một màn hình tổng quan, so that tôi nắm nhanh tình hình.
## 4. Business Goal
**[CONFIRMED — REQ-112]** Xem số công ty theo ngành, cơ hội/tổng giá trị theo giai đoạn, Việc tiếp theo quá hạn trong một nơi.
## 5. Scope
- **[CONFIRMED — AC-024]** Số công ty theo ngành.
- **[CONFIRMED — AC-024]** Số cơ hội và tổng giá trị theo từng giai đoạn.
- **[CONFIRMED — AC-024]** Danh sách Việc tiếp theo quá hạn.
## 6. Out of Scope
- **[CONFIRMED — US-001/003/008]** Quản lý dữ liệu nguồn.
- **[OPEN QUESTION — Q-010-01]** Định nghĩa quá hạn, thứ tự và empty state chưa nêu.
## 7. Actor / Permission
| Actor | Permission | Evidence |
|---|---|---|
| Sales | Mở/xem tổng quan. | **[CONFIRMED]** US-010 |
| Quản trị | Số liệu Quản trị không thuộc story. | **[CONFIRMED]** FEAT-035 |
## 8. Business Rules
| ID | Rule | Evidence |
|---|---|---|
| BR-US010-01 | Tổng quan có số công ty theo ngành. | **[CONFIRMED]** AC-024 |
| BR-US010-02 | Tổng quan có số/tổng giá trị cơ hội theo giai đoạn. | **[CONFIRMED]** AC-024 |
| BR-US010-03 | Tổng quan có danh sách Việc tiếp theo quá hạn. | **[CONFIRMED]** AC-024 |
| BR-US010-04 | Tổng quan không tự đổi dữ liệu. | **[INFERRED]** REQ-112 |
## 9. Business Data Dictionary
| Data | Meaning | Evidence |
|---|---|---|
| Số công ty theo ngành | Số lượng công ty nhóm theo ngành. | **[CONFIRMED]** AC-024 |
| Số/tổng giá trị cơ hội | Dữ liệu cơ hội nhóm theo giai đoạn. | **[CONFIRMED]** AC-024 |
| Việc tiếp theo quá hạn | Danh sách Next step đã quá hạn. | **[CONFIRMED]** REQ-112 |
## 10. Business Flow
1. **[CONFIRMED — AC-024]** Có công ty/cơ hội.
2. **[CONFIRMED — AC-024]** Sales mở tổng quan và thấy đủ ba nhóm thông tin.
## 11. Acceptance Criteria
### AC-024 — Hiển thị tổng quan
```gherkin
Given có dữ liệu công ty và cơ hội
When tôi mở màn hình tổng quan
Then thấy số công ty theo ngành, số cơ hội/tổng giá trị theo giai đoạn và Việc tiếp theo quá hạn.
```
## 12. Screen Specification
| Screen ID | Area | Behavior | Evidence |
|---|---|---|---|
| `SCR-US010-01` | Tổng quan Công ty | Hiển thị tổng số và số Công ty theo ngành. | **[CONFIRMED]** AC-024 |
| `SCR-US010-02` | Tổng quan Cơ hội | Hiển thị số lượng và tổng giá trị theo từng giai đoạn. | **[CONFIRMED]** AC-024 |
| `SCR-US010-03` | Việc tiếp theo quá hạn | Hiển thị danh sách việc quá hạn, không tự đặt múi giờ/thứ tự khi đồng hạn. | **[CONFIRMED]** AC-024; **[OPEN QUESTION]** Q-010-01 |
## 13. Screen Design

> **UI-DESIGN UPDATE — 2026-08-14:** Wireframe BA dưới đây được tạo từ các US/AC hiện hành và thay thế trạng thái “chưa có asset” được ghi nhận trước bước UI Design.

![US-010 screen flow](./assets/screen-flow.svg)

### `SCR-US010-01` — Số Công ty theo ngành
![US-010 company overview](./assets/company-overview.svg)

### `SCR-US010-02` — Cơ hội theo giai đoạn
![US-010 opportunity overview](./assets/opportunity-overview.svg)

### `SCR-US010-03` — Việc tiếp theo quá hạn
![US-010 overdue next steps](./assets/overdue-next-steps.svg)

**[ASSUMPTION — A-010-01]** Visual language kế thừa mẫu đã duyệt cho US-001; dashboard chỉ đọc và không tự quyết định định nghĩa quá hạn hoặc empty state.
## 14. Screen States
| State | Outcome | Screen | Evidence |
|---|---|---|---|
| Có dữ liệu | Có đủ ba nhóm tổng quan. | `SCR-US010-01..03` | **[CONFIRMED]** AC-024 |
| Không có dữ liệu/quá hạn | Chưa khẳng định cách biểu đạt trong asset chi tiết. | — | **[OPEN QUESTION]** Q-010-02 |
## 15. Validation
| Condition | Response | Evidence |
|---|---|---|
| Có dữ liệu nguồn | Hiển thị đủ AC-024. | **[CONFIRMED]** AC-024 |
| Xác định quá hạn | Không tự đặt múi giờ/thời điểm. | **[OPEN QUESTION]** Q-010-01 |
## 16. Dependencies
| Direction | Item | Evidence |
|---|---|---|
| Upstream | US-001, US-003, US-008. | **[CONFIRMED]** user-stories |
| Related | US-009 dùng tình trạng quá hạn để lọc. | **[CONFIRMED]** FEAT-009 |
## 17. Business-level NFR Expectations
- **[CONFIRMED — REQ-112]** Phản ánh ba nhóm số liệu bắt buộc.
- **[CONFIRMED — REQ-113]** Không phụ thuộc AI.
## 18. Test Scenarios
| ID | Scenario | AC | Expected result |
|---|---|---|---|
| TC-010-01 | Sales mở tổng quan với dữ liệu nhiều ngành/giai đoạn. | AC-024 | Thấy đủ ba nhóm thông tin. |
## 19. Traceability
| Chain | Evidence |
|---|---|
| `REQ-112 → EPIC-03 → FEAT-010 → US-010 → AC-024 → TC-010-01` | **[CONFIRMED]** architect handoff |
## 20. Assumptions
| ID | Assumption | Status |
|---|---|---|
| A-010-01 | Visual language dùng mẫu đã duyệt cho US-001; dashboard chỉ đọc và không bổ sung quy tắc dữ liệu. | **[ASSUMPTION]** Không đóng Q-010-01..02. |
## 21. Open Questions
| ID | Question | Owner |
|---|---|---|
| Q-010-01 | Quá hạn dùng thời điểm/múi giờ nào? | PO |
| Q-010-02 | Empty state trình bày thế nào? | PO/UX |
## 22. Definition of Ready
| Item | Status | Evidence |
|---|---|---|
| Actor/value, AC, dependency, traceability | READY | REQ-112 → FEAT-010 → US-010 → AC-024 → TC-010-01 |
| DoR nguồn | READY | **[CONFIRMED]** dor-review |
## 23. Technical Handoff
- **[CONFIRMED — AC-024]** Bảo toàn ba nhóm tổng quan.
- **[CONFIRMED — REQ-113]** Không tạo phụ thuộc AI; chuyển Q-010-01..02 cho PO.
## 24. Change Log
| Version | Date | Change | Author/Approver |
|---|---|---|---|
| 1.1 | 2026-08-14 | Bổ sung ba SVG chi tiết cho Công ty theo ngành, Cơ hội theo giai đoạn và Việc tiếp theo quá hạn; giữ mở định nghĩa quá hạn/empty state. | Codex — UI pattern approved; specification approval unchanged |
| 1.0 | 2026-08-14 | Tạo specification 24 mục. | Codex / awaiting human specification approval |
