# Business Specification — US-008: Việc tiếp theo & ngày hạn

## 1. Document Information

| Field | Value |
|---|---|
| Story | `US-008` — Việc tiếp theo & ngày hạn |
| Feature / domain | `FEAT-008` / `D1 — CRM lõi làm tay` |
| Version | `1.1` |
| Status | `AWAITING_SPECIFICATION_APPROVAL` |
| Date | `2026-08-14` |
| Priority | Must (16) |
| Sources | `REQ-109`; `US-008`, `AC-019`, `AC-020`; DoR; architect handoff traceability |

## 2. Purpose

**[CONFIRMED — US-008, REQ-109]** Xác định cách Sales ghi nhận Việc tiếp theo và ngày hạn cho một cơ hội mở, để biết deal nào cần thực hiện việc gì hôm nay. Tài liệu làm rõ rằng việc thiếu một trong hai thông tin là trạng thái cần cảnh báo, không phải lỗi chặn lưu.

## 3. User Story

**[CONFIRMED — US-008]** As a Sales, I want mỗi cơ hội có Việc tiếp theo + ngày hạn, so that tôi luôn biết deal nào cần làm gì hôm nay.

## 4. Business Goal

**[CONFIRMED — US-008]** Sales có danh sách việc phải làm theo hạn khi cơ hội mở đã có đủ hai thông tin. **[INFERRED — REQ-109]** Cờ cảnh báo giúp Sales nhận ra cơ hội mở chưa đủ thông tin để lập kế hoạch mà vẫn bảo toàn thao tác lưu.

## 5. Scope

**[CONFIRMED — REQ-109, AC-019..020]**

- Ghi nhận Việc tiếp theo và ngày hạn cho cơ hội mở.
- Lưu cơ hội mở khi thiếu Việc tiếp theo hoặc ngày hạn.
- Hiển thị cờ cảnh báo cho cơ hội mở thiếu ít nhất một trong hai thông tin.
- Đưa cơ hội mở đã có đủ hai thông tin vào danh sách việc phải làm theo hạn.
- Loại cơ hội mở thiếu một hoặc cả hai thông tin khỏi danh sách việc phải làm cho đến khi được điền đủ.

## 6. Out of Scope

**[CONFIRMED — user-stories, REQ-104..112]**

- Tạo và quản lý dữ liệu lõi của cơ hội: US-003.
- Đổi giai đoạn bằng kéo-thả: US-004; kiểm tra dấu hiệu đủ điều kiện: US-005.
- Lọc cơ hội theo tình trạng quá hạn Việc tiếp theo: US-009.
- Hiển thị danh sách Việc tiếp theo quá hạn trên tổng quan: US-010.
- Tự điền Việc tiếp theo và ngày hạn bằng A-AI: US-025; các quy tắc không đè giá trị thủ công: US-026.
- Quy tắc cho cơ hội đã đóng, cách xác định “quá hạn”, nhắc việc/gửi liên hệ khách, hoặc thay đổi stage/giá trị cơ hội.

## 7. Actor / Permission

| Actor | Business permission | Evidence |
|---|---|---|
| Sales | Ghi, cập nhật và xem Việc tiếp theo/ngày hạn của cơ hội mở; xem danh sách việc phải làm. | **[CONFIRMED]** US-008, AC-019..020 |
| A-AI | Không thuộc phạm vi thao tác tự động của story này. | **[CONFIRMED]** US-025 là story riêng; BR-017 / architecture guardrail |
| Admin | Quyền thao tác cụ thể chưa được nguồn US-008 xác định. | **[OPEN QUESTION]** Q-008-01 |

## 8. Business Rules

| ID | Rule | Evidence |
|---|---|---|
| BR-US008-01 | Với cơ hội mở, Việc tiếp theo và ngày hạn là hai thông tin cần có để cơ hội được liệt kê trong danh sách việc phải làm theo hạn. | **[CONFIRMED]** REQ-109; AC-019 |
| BR-US008-02 | Với cơ hội mở, thiếu Việc tiếp theo hoặc ngày hạn không được chặn thao tác lưu. | **[CONFIRMED]** REQ-109; AC-020 |
| BR-US008-03 | Cơ hội mở thiếu một hoặc cả hai thông tin phải mang cờ cảnh báo và không xuất hiện trong danh sách việc phải làm cho đến khi điền đủ. | **[CONFIRMED]** REQ-109; AC-020 |
| BR-US008-04 | Story này không tự đặt giá trị, không thay đổi stage/giá trị cơ hội, không xóa dữ liệu và không liên hệ khách. | **[CONFIRMED]** BR-017; architecture / project rules |
| BR-US008-05 | CRM thủ công vẫn hoạt động khi AI bị tắt. | **[CONFIRMED]** REQ-113; project rules |

## 9. Business Data Dictionary

| Business data | Meaning | Applicability / rule | Evidence |
|---|---|---|---|
| Cơ hội | Thương vụ bán hàng thuộc một công ty; đối tượng sở hữu Việc tiếp theo và ngày hạn. | Chỉ xử lý cơ hội mở trong AC của story này. | **[CONFIRMED]** US-003; AC-019..020 |
| Việc tiếp theo | Mô tả việc Sales cần làm tiếp cho cơ hội. | Có thể để trống khi lưu; phải có để vào danh sách việc phải làm. | **[CONFIRMED]** REQ-109 |
| Ngày hạn | Hạn của Việc tiếp theo. | Có thể để trống khi lưu; phải có để vào danh sách việc phải làm theo hạn. | **[CONFIRMED]** REQ-109; AC-019 |
| Cờ cảnh báo thiếu thông tin | Dấu hiệu rằng cơ hội mở thiếu Việc tiếp theo hoặc ngày hạn. | Hiển thị khi thiếu ít nhất một ô; không phải trạng thái chặn lưu. | **[CONFIRMED]** REQ-109; AC-020 |
| Danh sách việc phải làm | Danh sách các cơ hội mở đã đủ Việc tiếp theo và ngày hạn, được sắp/xem theo hạn. | Không chứa cơ hội còn thiếu một trong hai ô. | **[CONFIRMED]** AC-019..020 |

## 10. Business Flow

### BF-008-01 — Lưu đủ hai thông tin

1. **[CONFIRMED — AC-019]** Sales mở một cơ hội mở.
2. **[CONFIRMED — AC-019]** Sales điền Việc tiếp theo và ngày hạn.
3. **[CONFIRMED — AC-019]** Hệ thống lưu thông tin.
4. **[CONFIRMED — AC-019]** Cơ hội xuất hiện trong danh sách việc phải làm theo hạn.

### BF-008-02 — Lưu khi thiếu thông tin

1. **[CONFIRMED — AC-020]** Sales mở một cơ hội mở.
2. **[CONFIRMED — AC-020]** Sales để trống Việc tiếp theo hoặc ngày hạn rồi lưu.
3. **[CONFIRMED — AC-020]** Hệ thống vẫn lưu, hiển thị cờ cảnh báo và không chặn thao tác.
4. **[CONFIRMED — AC-020]** Cơ hội không vào danh sách việc phải làm cho tới khi có đủ cả hai thông tin.

## 11. Acceptance Criteria

### AC-019 — Đủ hai ô

```gherkin
Scenario: Đủ hai ô
  Given một cơ hội mở
  When tôi điền Việc tiếp theo và ngày hạn
  Then cơ hội xuất hiện trong danh sách việc phải làm theo hạn.
```

### AC-020 — Thiếu một ô vẫn lưu, mang cờ

```gherkin
Scenario: Thiếu một ô vẫn lưu, mang cờ
  Given một cơ hội mở
  When tôi để trống Việc tiếp theo hoặc ngày hạn và lưu
  Then cơ hội vẫn lưu, mang cờ cảnh báo và KHÔNG xuất hiện trong danh sách việc phải làm tới khi điền đủ; thao tác lưu không bị chặn.
```

**[CONFIRMED — user-stories.md]** Hai criterion trên được bảo toàn nguyên nghĩa từ nguồn.

## 12. Screen Specification

| Screen ID | Business area | Required information / behavior | Evidence |
|---|---|---|---|
| `SCR-US008-01` | Việc tiếp theo của Cơ hội mở | Cho phép ghi Việc tiếp theo và ngày hạn; thiếu một ô không cản trở lưu. | **[CONFIRMED]** REQ-109; AC-019..020 |
| `SCR-US008-02` | Danh sách việc phải làm | Chỉ hiển thị Cơ hội mở có đủ hai ô theo hạn. | **[CONFIRMED]** AC-019..020 |
| `SCR-US008-03` | Trạng thái đủ/thiếu | Phân biệt đủ thông tin và thiếu một ô đã lưu nhưng có cờ, không có trong danh sách. | **[CONFIRMED]** AC-019..020 |

## 13. Screen Design

> **UI-DESIGN UPDATE — 2026-08-14:** Wireframe BA dưới đây được tạo từ các US/AC hiện hành và thay thế trạng thái “chưa có asset” được ghi nhận trước bước UI Design.

![US-008 screen flow](./assets/screen-flow.svg)

### `SCR-US008-01` — Việc tiếp theo của Cơ hội
![US-008 next step editor](./assets/next-step-editor.svg)

### `SCR-US008-02` — Danh sách Việc phải làm
![US-008 task list](./assets/task-list.svg)

### `SCR-US008-03` — Trạng thái đủ/thiếu
![US-008 next step states](./assets/next-step-states.svg)

**[ASSUMPTION — A-008-01]** Visual language kế thừa mẫu đã duyệt cho US-001. Asset không tự quyết định trường hợp thiếu cả hai, validation nội dung/ngày, Cơ hội đóng hoặc múi giờ đang mở tại Q-008-02..05.

## 14. Screen States

| State | Visible business outcome | Screen | Evidence |
|---|---|---|---|
| Đủ thông tin | Cơ hội mở có đủ hai ô và có mặt trong danh sách việc phải làm. | `SCR-US008-01`, `SCR-US008-02`, `SCR-US008-03` | **[CONFIRMED]** AC-019 |
| Thiếu Việc tiếp theo | Vẫn lưu, có cờ, không có trong danh sách. | `SCR-US008-03` | **[CONFIRMED]** AC-020 |
| Thiếu ngày hạn | Vẫn lưu, có cờ, không có trong danh sách. | `SCR-US008-03` | **[CONFIRMED]** AC-020 |
| Thiếu cả hai | Không được asset khẳng định là confirmed; chờ PO. | `SCR-US008-03` | **[ASSUMPTION]** A-008-02; **[OPEN QUESTION]** Q-008-02 |

## 15. Validation

| Condition | Expected business response | Evidence |
|---|---|---|
| Cơ hội mở có đủ Việc tiếp theo và ngày hạn | Lưu và đủ điều kiện xuất hiện trong danh sách việc phải làm theo hạn. | **[CONFIRMED]** AC-019 |
| Cơ hội mở thiếu Việc tiếp theo hoặc ngày hạn | Vẫn lưu; hiển thị cờ; không xuất hiện trong danh sách việc phải làm; không chặn lưu. | **[CONFIRMED]** AC-020 |
| Giá trị/ngữ nghĩa cụ thể hợp lệ của ngày hạn hoặc nội dung Việc tiếp theo | Chưa được nêu. | **[OPEN QUESTION]** Q-008-03 |
| Cơ hội đã đóng | Không có quy tắc trong story này. | **[OPEN QUESTION]** Q-008-04 |

## 16. Dependencies

| Direction | Item | Dependency | Evidence |
|---|---|---|---|
| Upstream | US-003 | Cung cấp cơ hội thuộc công ty, bao gồm trạng thái mở/đóng theo BR-004. | **[CONFIRMED]** US-008 dependency; US-003; architect handoff |
| Downstream | US-009 | Dùng tình trạng quá hạn Việc tiếp theo để lọc cơ hội. | **[CONFIRMED]** US-009 AC-023 |
| Downstream | US-010 | Hiển thị danh sách Việc tiếp theo quá hạn trên tổng quan. | **[CONFIRMED]** US-010 AC-024 |
| Downstream | US-025 | Tự điền Việc tiếp theo/ngày hạn cho cơ hội mở là use case độc lập, phụ thuộc US-008. | **[CONFIRMED]** US-025 dependency |

## 17. Business-level NFR Expectations

- **[CONFIRMED — REQ-113]** Hành vi CRM thủ công của story không phụ thuộc vào AI và tiếp tục hoạt động khi AI bị tắt.
- **[CONFIRMED — REQ-704; architecture]** Dữ liệu CRM cần bền qua khởi động lại trong triển khai sản phẩm; đây là kỳ vọng cấp hệ thống, không bổ sung quy tắc riêng cho US-008.
- **[OPEN QUESTION — Q-008-05]** Nguồn chưa quy định thời gian phản hồi, phạm vi múi giờ, hay quy tắc hiển thị thứ tự khi nhiều ngày hạn giống nhau.

## 18. Test Scenarios

Chưa có tài liệu `test-scenarios.md` riêng được cung cấp cho US-008. Các tình huống nghiệp vụ cần được truy chiếu bởi bộ nghiệm thu **T-1** theo architect handoff:

| Business scenario | AC | Expected result |
|---|---|---|
| Điền đủ Việc tiếp theo và ngày hạn cho cơ hội mở | AC-019 | Cơ hội nằm trong danh sách việc phải làm theo hạn. |
| Lưu cơ hội mở khi thiếu Việc tiếp theo | AC-020 | Lưu thành công, có cờ, không vào danh sách việc phải làm. |
| Lưu cơ hội mở khi thiếu ngày hạn | AC-020 | Lưu thành công, có cờ, không vào danh sách việc phải làm. |

## 19. Traceability

| Chain | Evidence |
|---|---|
| `D1 → EPIC-01 → FEAT-008 → US-008 → AC-019..020 → T-1` | **[CONFIRMED]** user-stories; architect handoff traceability matrix |
| `REQ-109 → US-008 → BR-US008-01..03 → AC-019..020` | **[CONFIRMED]** requirement-analysis; user-stories |
| `US-003 → US-008` | **[CONFIRMED]** US-008 dependency in user-stories |
| `REQ-113 → BR-US008-05` | **[CONFIRMED]** requirement-analysis; project rules |
| `BR-017 → BR-US008-04` | **[CONFIRMED]** requirement-analysis; architecture / project rules |

## 20. Assumptions

| ID | Assumption | Rationale / status |
|---|---|---|
| A-008-01 | Visual language dùng mẫu đã duyệt cho US-001; cờ cảnh báo phải dễ nhận biết nhưng không trở thành validation chặn lưu. | **[ASSUMPTION]** Không thay đổi AC. |
| A-008-02 | Khi cả hai ô trống, hành vi giống trường hợp thiếu một ô: vẫn lưu, có cờ, không vào danh sách việc phải làm. | **[ASSUMPTION]** Cần PO phê duyệt tại Q-008-02. |

## 21. Open Questions

| ID | Question | Owner / impact |
|---|---|---|
| Q-008-01 | Admin có được thao tác Việc tiếp theo/ngày hạn như Sales không? | PO; làm rõ phân quyền nghiệp vụ. |
| Q-008-02 | Khi cả hai ô đều trống, có áp dụng chính xác cùng kết quả AC-020 không? | PO; xác nhận assumption A-008-02. |
| Q-008-03 | Có quy định định dạng, giới hạn hoặc ý nghĩa hợp lệ cho ngày hạn và nội dung Việc tiếp theo không? | PO; không tự đặt validation ngoài nguồn. |
| Q-008-04 | Cơ hội đã đóng có được giữ/sửa/hiển thị Việc tiếp theo và ngày hạn không? | PO; ngoài phạm vi AC hiện tại. |
| Q-008-05 | “Theo hạn” dùng múi giờ nào và thứ tự khi đồng hạn như thế nào? | PO; ảnh hưởng cách trình bày danh sách. |

## 22. Definition of Ready

| DoR item | Status | Evidence / note |
|---|---|---|
| Actor, business value và mô tả rõ | READY | Sales và mục tiêu nêu rõ trong US-008. |
| Acceptance criteria có thể quan sát | READY | AC-019..020. |
| Dependency xác định | READY | Upstream US-003. |
| Traceability rõ | READY | REQ-109 → FEAT-008 → US-008 → AC-019..020 → T-1. |
| Priority / backlog được phê duyệt | READY | Must (16); dor-review xác nhận US-008 READY. |
| Ambiguities không thay đổi AC được ghi nhận | READY WITH QUESTIONS | Q-008-01..005; không chặn trạng thái DoR READY đã được PO review. |

**[CONFIRMED — dor-review]** US-008 được đánh dấu `READY`; specification này dừng tại cổng phê duyệt đặc tả của con người.

## 23. Technical Handoff

### Approved constraints

- **[CONFIRMED — AC-020]** Lưu thiếu một ô vẫn thành công, có cờ và không vào danh sách việc phải làm cho tới khi đủ hai ô.
- **[CONFIRMED — REQ-113]** Không tạo phụ thuộc vào AI cho luồng CRM thủ công.
- **[CONFIRMED — BR-017; architecture]** Không để bất kỳ tự động hóa nào thay đổi stage/giá trị cơ hội, xóa dữ liệu người tạo hay liên hệ khách; guardrail áp dụng ngoài UI.

### Touchpoints and risks

- **[CONFIRMED]** Phụ thuộc dữ liệu cơ hội từ US-003; US-009, US-010 và US-025 sử dụng kết quả của story.
- **[INFERRED — AC-019..020]** Nếu tiêu chí đưa vào danh sách việc phải làm không nhất quán với cờ cảnh báo, Sales có thể bỏ sót việc cần hoàn thiện.
- **[INFERRED — REQ-109]** Nếu xử lý “đủ hai ô” không nhất quán sau cập nhật, cơ hội có thể bị hiển thị sai trong danh sách việc phải làm.

### Decisions required from Tech Lead

- Không có quyết định kỹ thuật mới được đề xuất trong specification này. Tech Lead cần bảo đảm các ràng buộc đã phê duyệt được tôn trọng và chuyển các câu hỏi Q-008-01..005 cho PO thay vì tự suy diễn quy tắc nghiệp vụ.

## 24. Change Log

| Version | Date | Change | Author/Approver |
|---|---|---|---|
| 1.1 | 2026-08-14 | Bổ sung ba SVG chi tiết cho editor, danh sách việc phải làm và trạng thái đủ/thiếu; bảo toàn lưu non-blocking và các open question. | Codex — UI pattern approved; specification approval unchanged |
| 1.0 | 2026-08-14 | Tạo business specification 24 mục cho US-008 từ nguồn đã phê duyệt; giữ nguyên REQ-109, AC-019..020 và dependency US-003. | Codex / awaiting human specification approval |
