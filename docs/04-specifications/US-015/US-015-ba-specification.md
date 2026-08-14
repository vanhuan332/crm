# Business Specification — US-015: Vùng đọc và mức chắc chắn

## 1. Document Information
| Field | Value |
|---|---|
| Story | `US-015` |
| Version | `1.0` |
| Status | `AWAITING_SPECIFICATION_APPROVAL` |
| Sources | `REQ-204`, `REQ-209`, `BR-007`, `FEAT-015`, `AC-032..033`, DoR |
## 2. Purpose
**[CONFIRMED — REQ-204/209]** Xác định vùng đọc tách biệt và nhận biết trực quan ba mức chắc chắn.
## 3. User Story
**[CONFIRMED — US-015]** As a Sales, I want xem bản lưu/phát hiện ở khu riêng với mức chắc chắn nhìn-là-biết, so that tôi đánh giá độ tin cậy.
## 4. Business Goal
**[CONFIRMED — US-015]** Sales không nhầm vùng đọc với hồ sơ/timeline và phân biệt được mức tin cậy trước khi đọc chữ.
## 5. Scope
- **[CONFIRMED — AC-032]** Vùng riêng cho bản lưu/phát hiện, tách hồ sơ và timeline.
- **[CONFIRMED — AC-033]** Chắc/Có thể/Đoán phân biệt bằng ký hiệu hoặc màu.
## 6. Out of Scope
- **[CONFIRMED — US-011/013/016]** Tạo bản lưu, rút phát hiện, nhảy provenance.
- **[CONFIRMED — REQ-206]** Thay đổi dữ liệu Sales từ vùng đọc.
## 7. Actor / Permission
| Actor | Permission | Evidence |
|---|---|---|
| Sales | Xem vùng đọc và mức chắc chắn. | **[CONFIRMED]** US-015 |
## 8. Business Rules
| ID | Rule | Evidence |
|---|---|---|
| BR-US015-01 | Vùng đọc tách khỏi hồ sơ và dòng thời gian. | **[CONFIRMED]** REQ-204; AC-032 |
| BR-US015-02 | Ba mức là Chắc, Có thể, Đoán. | **[CONFIRMED]** BR-007 |
| BR-US015-03 | Ba mức không chỉ phân biệt bằng chữ. | **[CONFIRMED]** REQ-209; AC-033 |
| BR-US015-04 | Vùng đọc không tự đổi dữ liệu CRM. | **[CONFIRMED]** REQ-206; BR-017 |
## 9. Business Data Dictionary
| Data | Meaning | Evidence |
|---|---|---|
| Bản lưu | Nội dung nguồn nguyên văn. | **[CONFIRMED]** REQ-201 |
| Phát hiện | Nhận định rút từ bản lưu. | **[CONFIRMED]** REQ-202 |
| Chắc/Có thể/Đoán | Ba mức chắc chắn. | **[CONFIRMED]** BR-007 |
## 10. Business Flow
1. **[CONFIRMED — AC-032]** Sales mở công ty và thấy vùng đọc riêng.
2. **[CONFIRMED — AC-033]** Sales phân biệt ba mức bằng ký hiệu/màu.
## 11. Acceptance Criteria
### AC-032 — Vùng đọc tách biệt
```gherkin
Given tôi mở màn hình một công ty
Then bản lưu và phát hiện ở khu riêng, tách hồ sơ và dòng thời gian.
```
### AC-033 — Ba mức trực quan
```gherkin
Given phát hiện có mức Chắc/Có thể/Đoán
Then ba mức phân biệt bằng ký hiệu hoặc màu, không chỉ bằng nhãn chữ.
```
## 12. Screen Specification
| Area | Behavior | Evidence |
|---|---|---|
| Màn hình công ty | Có vùng đọc riêng. | **[CONFIRMED]** AC-032 |
| Phát hiện | Có dấu hiệu trực quan theo mức. | **[CONFIRMED]** AC-033 |
## 13. Screen Design

> **UI-DESIGN UPDATE — 2026-08-14:** Wireframe BA dưới đây được tạo từ các US/AC hiện hành và thay thế trạng thái “chưa có asset” được ghi nhận trước bước UI Design.

![US-015 screen flow](./assets/screen-flow.svg)
Không có asset đã phê duyệt. **[ASSUMPTION — A-015-01]** UI design chọn ký hiệu/màu cụ thể.
## 14. Screen States
| State | Outcome | Evidence |
|---|---|---|
| Có dữ liệu vùng đọc | Tách hồ sơ/timeline. | **[CONFIRMED]** AC-032 |
| Ba mức phát hiện | Nhìn thấy khác biệt. | **[CONFIRMED]** AC-033 |
## 15. Validation
| Condition | Response | Evidence |
|---|---|---|
| Hiển thị dữ liệu | Không lẫn vào hồ sơ/timeline. | **[CONFIRMED]** AC-032 |
| Chọn màu/ký hiệu cụ thể | Chưa có chuẩn nguồn. | **[OPEN QUESTION]** Q-015-01 |
## 16. Dependencies
| Direction | Item | Evidence |
|---|---|---|
| Upstream | US-011 bản lưu; US-013 phát hiện. | **[CONFIRMED]** REQ-201..202 |
| Related | US-016 xem provenance. | **[CONFIRMED]** function decomposition |
## 17. Business-level NFR Expectations
- **[CONFIRMED — REQ-209]** Không chỉ dựa vào nhãn chữ để phân biệt mức.
- **[CONFIRMED — REQ-113]** CRM thủ công không phụ thuộc AI.
## 18. Test Scenarios
| ID | Scenario | AC | Expected result |
|---|---|---|---|
| TC-015-01 | Sales mở công ty có dữ liệu nguồn. | AC-032 | Dữ liệu ở vùng đọc riêng. |
| TC-015-02 | Sales xem ba mức phát hiện. | AC-033 | Mức phân biệt không cần đọc chữ. |
## 19. Traceability
| Chain | Evidence |
|---|---|
| `REQ-204 → EPIC-05 → FEAT-015 → US-015 → AC-032 → TC-015-01` | **[CONFIRMED]** architect handoff |
| `REQ-209 → EPIC-05 → FEAT-015 → US-015 → AC-033 → TC-015-02` | **[CONFIRMED]** architect handoff |
## 20. Assumptions
| ID | Assumption | Status |
|---|---|---|
| A-015-01 | Không tạo asset trước ui-design. | **[ASSUMPTION]** |
## 21. Open Questions
| ID | Question | Owner |
|---|---|---|
| Q-015-01 | Chuẩn ký hiệu/màu accessibility là gì? | PO/UX |
## 22. Definition of Ready
| Item | Status | Evidence |
|---|---|---|
| Actor/value, AC, dependency, traceability | READY | REQ-204/209 → FEAT-015 → US-015 → AC-032..033 → TC |
| DoR nguồn | READY | **[CONFIRMED]** dor-review |
## 23. Technical Handoff
- **[CONFIRMED — REQ-204/209]** Bảo toàn vùng đọc tách biệt và ba mức trực quan.
- **[CONFIRMED — REQ-206]** Không biến vùng đọc thành đường sửa dữ liệu CRM.
## 24. Change Log
| Version | Date | Change | Author/Approver |
|---|---|---|---|
| 1.0 | 2026-08-14 | Tạo specification 24 mục. | Codex / awaiting human specification approval |
