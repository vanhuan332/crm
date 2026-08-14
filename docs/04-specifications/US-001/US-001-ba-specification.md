# Business Specification — US-001: Quản lý Công ty (CRUD + chi tiết)

## 1. Document Information

| Field | Value |
|---|---|
| Story | `US-001` — Quản lý Công ty (CRUD + chi tiết) |
| Feature / domain | `FEAT-001` / `D1 — CRM lõi làm tay` / `EPIC-01` |
| Version | `1.1` |
| Status | `AWAITING_SPECIFICATION_APPROVAL` |
| Date | `2026-08-14` |
| Priority | Must (17) |
| Sources | `REQ-101`, `BR-001`; `US-001`, `AC-001..004`; `T-1`; DoR review; architect handoff |

## 2. Purpose

**[CONFIRMED — US-001, REQ-101]** Xác định hành vi nghiệp vụ để Sales tạo, xem chi tiết, sửa và xoá hồ sơ Công ty; khi tạo, Tên công ty, Ngành và Loại công ty là ba thông tin bắt buộc, còn các thông tin khác có thể để trống.

## 3. User Story

**[CONFIRMED — US-001]** As a Sales, I want tạo/sửa/xoá/xem chi tiết công ty, so that tôi có hồ sơ khách chuẩn để làm việc và chịu trách nhiệm.

## 4. Business Goal

**[CONFIRMED — US-001]** Sales có hồ sơ khách chuẩn để làm việc và chịu trách nhiệm. **[INFERRED — REQ-101]** Ba thông tin cốt lõi tạo mức thông tin tối thiểu nhất quán cho CRM làm tay.

## 5. Scope

- **[CONFIRMED — REQ-101, US-001]** Sales tạo Công ty, xem danh sách và xem chi tiết Công ty.
- **[CONFIRMED — REQ-101, US-001]** Sales sửa và xoá Công ty.
- **[CONFIRMED — REQ-101, AC-001..003]** Khi tạo, Tên công ty, Ngành và Loại công ty là bắt buộc; các ô khác được phép để trống.
- **[CONFIRMED — BR-001, AC-001]** Loại công ty phải là một trong năm loại được quy định.

## 6. Out of Scope

- **[CONFIRMED — REQ-102, US-002]** Quản lý Người liên hệ và đầu mối chính.
- **[CONFIRMED — REQ-103..112, user-stories]** Cơ hội, giai đoạn, Hoạt động, dòng thời gian, việc tiếp theo, tìm kiếm/lọc và tổng quan.
- **[CONFIRMED — REQ-201..605, function-decomposition]** Các chức năng AI, gợi ý, tự động hoá, theo dõi và quản trị AI.
- **[OPEN QUESTION — Q-001-03]** Hành vi xoá khi Công ty đã có dữ liệu thuộc các story phụ thuộc không được nguồn xác định trong US-001.

## 7. Actor / Permission

| Actor | Business permission | Evidence |
|---|---|---|
| Sales | Tạo, xem chi tiết, sửa và xoá Công ty. | **[CONFIRMED]** US-001; REQ-101 |
| A-AI | Không có hành vi tự động nào trong story này; không được tự xoá dữ liệu do người tạo. | **[CONFIRMED]** BR-017; architecture; project rules |
| Quản trị | Quyền thao tác Công ty cụ thể chưa được nguồn US-001 quy định. | **[OPEN QUESTION]** Q-001-05 |

## 8. Business Rules

| ID | Rule | Evidence |
|---|---|---|
| BR-001 | Loại công ty chỉ thuộc một trong năm giá trị: Traditional, IT Solution, IT Product, Tech-based/Startup, ITO khác. | **[CONFIRMED]** requirement-analysis; PRD §2 |
| BR-US001-01 | Khi tạo, Tên công ty, Ngành và Loại công ty là ba thông tin bắt buộc. | **[CONFIRMED]** REQ-101; AC-001..002 |
| BR-US001-02 | Các ô khác được phép để trống khi tạo Công ty. | **[CONFIRMED]** REQ-101; AC-003 |
| BR-US001-03 | Thiếu một thông tin bắt buộc thì từ chối lưu và chỉ rõ thông tin thiếu. | **[CONFIRMED]** AC-002 |
| BR-US001-04 | Sales có thể sửa một trường rồi lưu, hoặc xoá Công ty; sau xoá Công ty bị gỡ khỏi danh sách. | **[CONFIRMED]** AC-004 |
| BR-US001-05 | A-AI không được tự xoá dữ liệu Công ty do người tạo. | **[CONFIRMED]** BR-017; project rules |

## 9. Business Data Dictionary

| Business data | Meaning | Applicability / rule | Evidence |
|---|---|---|---|
| Công ty | Một pháp nhân khách hàng tiềm năng hoặc đang giao dịch. | Đối tượng Sales quản lý trong story. | **[CONFIRMED]** PRD §2; US-001 |
| Tên công ty | Tên của Công ty. | Bắt buộc khi tạo. | **[CONFIRMED]** REQ-101; AC-001..002 |
| Ngành | Ngành của Công ty. | Bắt buộc khi tạo. | **[CONFIRMED]** REQ-101; AC-001..002 |
| Loại công ty | Phân loại Công ty theo năm loại ITO. | Bắt buộc khi tạo; thuộc BR-001. | **[CONFIRMED]** REQ-101; BR-001; AC-001..002 |
| Thông tin khác của Công ty | Thông tin ngoài ba thông tin bắt buộc. | Có thể để trống; nguồn chưa xác định danh mục. | **[CONFIRMED]** REQ-101; AC-003; **[OPEN QUESTION]** Q-001-01 |

## 10. Business Flow

**BF-001-01 — Tạo Công ty.** **[CONFIRMED — AC-001..003]** Sales nhập ba thông tin bắt buộc, chọn Loại công ty hợp lệ và lưu. Nếu đủ, Công ty được tạo và hiện ở danh sách; nếu thiếu, hệ thống từ chối lưu và chỉ rõ thông tin thiếu. Sales cũng có thể chỉ nhập ba thông tin bắt buộc.

**BF-001-02 — Xem Công ty.** **[CONFIRMED — US-001, REQ-101]** Sales xem danh sách Công ty và mở chi tiết một Công ty để xem hồ sơ.

**BF-001-03 — Sửa Công ty.** **[CONFIRMED — AC-004]** Sales thay đổi một trường của Công ty đang tồn tại rồi lưu; thay đổi được ghi nhận. Hành vi khi sửa làm thiếu thông tin bắt buộc chưa được nguồn quy định. **[OPEN QUESTION — Q-001-04]**

**BF-001-04 — Xoá Công ty.** **[CONFIRMED — AC-004]** Sales xoá một Công ty đang tồn tại; Công ty bị gỡ khỏi danh sách. Điều kiện xoá khi có dữ liệu liên quan là câu hỏi nghiệp vụ còn mở. **[OPEN QUESTION — Q-001-03]**

## 11. Acceptance Criteria

**AC-001 — Tạo công ty đủ trường bắt buộc**

```gherkin
Scenario: Tạo công ty đủ trường bắt buộc
  Given tôi ở màn hình tạo công ty
  When tôi nhập tên, ngành, loại công ty (1 trong 5 loại) và lưu
  Then công ty được tạo và hiện ở danh sách.
```

**AC-002 — Thiếu trường bắt buộc**

```gherkin
Scenario: Thiếu trường bắt buộc
  Given tôi đang tạo công ty
  When tôi bỏ trống tên hoặc ngành hoặc loại công ty và lưu
  Then hệ thống từ chối lưu và chỉ rõ trường còn thiếu.
```

**AC-003 — Ô tuỳ chọn bỏ trống**

```gherkin
Scenario: Ô tuỳ chọn bỏ trống
  Given tôi tạo công ty chỉ với 3 trường bắt buộc
  When tôi lưu
  Then công ty vẫn được tạo với các ô còn lại để trống.
```

**AC-004 — Sửa và xoá**

```gherkin
Scenario: Sửa và xoá
  Given một công ty đã tồn tại
  When tôi sửa một trường rồi lưu / hoặc xoá công ty
  Then thay đổi được ghi / công ty bị gỡ khỏi danh sách.
```

**[CONFIRMED — user-stories]** Bốn acceptance criteria trên được bảo toàn nguyên nghĩa từ nguồn.

## 12. Screen Specification

| Screen ID | Business area | Required information / behavior | Evidence |
|---|---|---|---|
| `SCR-US001-01` | Danh sách Công ty | Sales nhận biết Công ty đã tạo; có thể mở chi tiết, đi tới tạo/chỉnh sửa hoặc yêu cầu xoá Công ty. | **[CONFIRMED]** US-001; AC-001; AC-004 |
| `SCR-US001-02` | Chi tiết Công ty | Sales xem hồ sơ Công ty đã chọn và truy cập hành động chỉnh sửa hoặc xoá. | **[CONFIRMED]** US-001; REQ-101; AC-004 |
| `SCR-US001-03` | Tạo Công ty | Thu thập Tên công ty, Ngành và Loại công ty; cho phép để trống các thông tin bổ sung. | **[CONFIRMED]** AC-001..003 |
| `SCR-US001-04` | Chỉnh sửa Công ty | Hiển thị dữ liệu hiện tại để Sales sửa một trường và lưu thay đổi. | **[CONFIRMED]** AC-004 |
| `SCR-US001-05` | Trạng thái và phản hồi | Chỉ rõ trường bắt buộc còn thiếu; minh hoạ empty, loading, not-found, lỗi có thể thử lại, xác nhận xoá và phản hồi lưu thành công. | **[CONFIRMED]** AC-002; AC-004; **[ASSUMPTION]** A-001-01 |

## 13. Screen Design

> **UI-DESIGN UPDATE — 2026-08-14:** Hướng trình bày chi tiết được người dùng duyệt theo ảnh tham chiếu: nền sáng, card viền mảnh, bảng dữ liệu, biểu mẫu phân nhóm và hành động chính màu tím. Các asset là SVG Git-friendly, không quyết định framework, component library hoặc cách triển khai.

### 13.1 Tổng quan luồng

![US-001 screen flow](./assets/screen-flow.svg)

### 13.2 `SCR-US001-01` — Danh sách Công ty

![US-001 company list](./assets/company-list.svg)

### 13.3 `SCR-US001-02` — Chi tiết Công ty

![US-001 company detail](./assets/company-detail.svg)

### 13.4 `SCR-US001-03` — Tạo Công ty

![US-001 company create](./assets/company-create.svg)

### 13.5 `SCR-US001-04` — Chỉnh sửa Công ty

![US-001 company edit](./assets/company-edit.svg)

### 13.6 `SCR-US001-05` — Trạng thái và phản hồi

![US-001 company states](./assets/company-states.svg)

**[ASSUMPTION — A-001-01]** Quốc gia, điện thoại, website, địa chỉ và mô tả/ghi chú xuất hiện để minh hoạ bố cục theo ảnh tham chiếu. Chúng không tự đóng `Q-001-01` và chưa trở thành phạm vi dữ liệu bắt buộc của US-001.

## 14. Screen States

| State | Visible business outcome | Screen / asset | Evidence |
|---|---|---|---|
| Tạo với đủ ba thông tin bắt buộc | Công ty được tạo và hiện trong danh sách. | `SCR-US001-03` → `SCR-US001-01` | **[CONFIRMED]** AC-001 |
| Tạo thiếu Tên công ty | Không lưu và chỉ rõ Tên công ty thiếu. | `SCR-US001-05` | **[CONFIRMED]** AC-002 |
| Tạo thiếu Ngành | Không lưu và chỉ rõ Ngành thiếu. | `SCR-US001-05`; cùng mẫu validation cạnh trường | **[CONFIRMED]** AC-002 |
| Tạo thiếu Loại công ty | Không lưu và chỉ rõ Loại công ty thiếu. | `SCR-US001-05` | **[CONFIRMED]** AC-002 |
| Tạo chỉ với ba thông tin bắt buộc | Công ty được tạo; thông tin khác để trống. | `SCR-US001-03` | **[CONFIRMED]** AC-003 |
| Sửa Công ty | Hiển thị phản hồi thành công và dữ liệu mới được ghi. | `SCR-US001-04`, `SCR-US001-05` | **[CONFIRMED]** AC-004 |
| Yêu cầu xoá Công ty | Hiển thị bước xác nhận an toàn; sau khi xác nhận thành công, Công ty bị gỡ khỏi danh sách. | `SCR-US001-05` → `SCR-US001-01` | **[CONFIRMED]** AC-004; **[ASSUMPTION]** A-001-01 |
| Danh sách trống | Hiển thị empty state và hành động Tạo công ty. | `SCR-US001-05` | **[ASSUMPTION]** A-001-01 |
| Không tìm thấy / lỗi có thể phục hồi | Cho phép về danh sách hoặc thử tải lại. | `SCR-US001-05` | **[ASSUMPTION]** A-001-01 |

## 15. Validation

| Condition | Expected business response | Evidence |
|---|---|---|
| Thiếu Tên công ty, Ngành hoặc Loại công ty khi tạo | Từ chối lưu và chỉ rõ thông tin thiếu. | **[CONFIRMED]** AC-002 |
| Loại công ty ngoài năm loại BR-001 | Không đáp ứng điều kiện Loại công ty trong AC-001; cách hướng dẫn Sales chưa được nguồn xác định. | **[INFERRED]** BR-001; AC-001; **[OPEN QUESTION]** Q-001-02 |
| Chỉ có ba thông tin bắt buộc | Cho phép tạo; các ô khác để trống. | **[CONFIRMED]** AC-003 |
| Sửa làm thiếu thông tin bắt buộc | Hành vi chưa được nguồn quy định. | **[OPEN QUESTION]** Q-001-04 |

## 16. Dependencies

| Direction | Item | Dependency | Evidence |
|---|---|---|---|
| Downstream | US-002 / FEAT-002 | Quản lý Người liên hệ diễn ra dưới một Công ty. | **[CONFIRMED]** REQ-102; function-decomposition |
| Downstream | US-003 / FEAT-003 | Quản lý Cơ hội diễn ra thuộc một Công ty. | **[CONFIRMED]** REQ-103; function-decomposition |
| Cross-cutting | US-040 / FEAT-040 | Ràng buộc chặn A-AI tự xoá dữ liệu do người tạo áp dụng cho Công ty. | **[CONFIRMED]** BR-017; architect handoff |
| Acceptance | T-1 | CRM lõi, gồm tạo Công ty, hoạt động khi toàn bộ AI tắt. | **[CONFIRMED]** PRD §6; REQ-113; architect handoff |

## 17. Business-level NFR Expectations

- **[CONFIRMED — REQ-113; PRD §6]** CRM làm tay của story hoạt động khi toàn bộ AI tắt; T-1 bao gồm tạo Công ty trong điều kiện này.
- **[CONFIRMED — REQ-704; architecture]** Dữ liệu CRM được kỳ vọng bền qua khởi động lại trong triển khai sản phẩm; đây là kỳ vọng cấp hệ thống, không thêm quy tắc dữ liệu cho US-001.
- **[OPEN QUESTION — Q-001-06]** Nguồn chưa đặt kỳ vọng cụ thể về thời gian phản hồi hoặc khả năng truy cập cho màn hình Công ty.

## 18. Test Scenarios

Chưa có `test-scenarios.md` riêng cho US-001. Các tình huống dưới đây là truy vết nghiệp vụ, không phải kiểm thử thực thi; chúng đóng góp vào bộ nghiệm thu **T-1**. **[CONFIRMED — architect handoff; PRD §6]**

| ID | Business scenario | AC / BR | Expected business result | Acceptance trace |
|---|---|---|---|---|
| TC-001 | Sales tạo Công ty với ba thông tin bắt buộc, gồm Loại công ty thuộc BR-001. | AC-001; BR-001 | Công ty được tạo và hiện ở danh sách. | T-1 |
| TC-002 | Sales lần lượt bỏ trống Tên công ty, Ngành hoặc Loại công ty khi tạo. | AC-002; BR-US001-01..03 | Mỗi trường hợp bị từ chối lưu và nêu đúng thông tin thiếu. | T-1 |
| TC-003 | Sales chỉ cung cấp ba thông tin bắt buộc khi tạo. | AC-003; BR-US001-02 | Công ty được tạo; thông tin khác để trống. | T-1 |
| TC-004 | Sales mở chi tiết một Công ty đã tồn tại. | US-001 | Sales xem được hồ sơ Công ty đã chọn. | T-1 |
| TC-005 | Sales sửa một trường của Công ty đang tồn tại rồi lưu. | AC-004 | Thay đổi được ghi nhận. | T-1 |
| TC-006 | Sales xoá một Công ty đang tồn tại. | AC-004 | Công ty bị gỡ khỏi danh sách. | T-1 |

## 19. Traceability

| Chain | Evidence |
|---|---|
| `D1 → EPIC-01 → FEAT-001 → US-001 → AC-001..004 → T-1` | **[CONFIRMED]** function-decomposition; user-stories; architect handoff |
| `REQ-101 → FEAT-001 → US-001 → AC-001..004` | **[CONFIRMED]** requirement-analysis; user-stories |
| `BR-001 → US-001 → AC-001 → TC-001` | **[CONFIRMED]** requirement-analysis; user-stories |
| `AC-002 → TC-002`; `AC-003 → TC-003`; `AC-004 → TC-005..006` | **[CONFIRMED]** user-stories |
| `REQ-113 → T-1 → US-001` | **[CONFIRMED]** requirement-analysis; PRD §6; architect handoff |
| `BR-017 → US-040 → BR-US001-05` | **[CONFIRMED]** requirement-analysis; architecture; project rules |

## 20. Assumptions

| ID | Assumption | Rationale / status |
|---|---|---|
| A-001-01 | Bố cục và visual language được người dùng duyệt theo ảnh tham chiếu ngày 2026-08-14; các trạng thái phụ và trường tùy chọn chỉ minh hoạ trải nghiệm, không bổ sung quy tắc nghiệp vụ ngoài AC-001..004. | **[ASSUMPTION]** Giữ nguyên các câu hỏi mở Q-001-01..06 và phạm vi REQ-101. |
| A-001-02 | Không suy ra danh mục thông tin tùy chọn ngoài các hành vi nêu tại US-001 và AC-001..004. | **[ASSUMPTION]** Giữ phạm vi REQ-101; phụ thuộc Q-001-01. |

## 21. Open Questions

| ID | Question | Owner / impact |
|---|---|---|
| Q-001-01 | Những thông tin tùy chọn nào của Công ty cần được thu thập, hiển thị và duy trì trong MVP? | PO; xác định phạm vi dữ liệu ngoài ba thông tin bắt buộc. |
| Q-001-02 | Có cần chính sách nghiệp vụ riêng khi Sales chọn Loại công ty ngoài năm loại BR-001, ngoài việc không chấp nhận giá trị đó không? | PO; làm rõ hướng dẫn cho Sales. |
| Q-001-03 | Khi Công ty đã có Người liên hệ, Cơ hội, Hoạt động, Bản lưu hoặc dữ liệu liên quan, Sales được xoá theo chính sách nghiệp vụ nào? | PO; ảnh hưởng các story phụ thuộc và bảo toàn dữ liệu. |
| Q-001-04 | Khi sửa Công ty, có áp dụng cùng quy tắc bắt buộc cho Tên công ty, Ngành và Loại công ty như khi tạo không? | PO; hoàn thiện hành vi sửa trong AC-004. |
| Q-001-05 | Quản trị có quyền thao tác Công ty như Sales không? | PO; làm rõ quyền nghiệp vụ. |
| Q-001-06 | Có kỳ vọng nghiệp vụ cụ thể về thời gian phản hồi hoặc khả năng truy cập cho màn hình Công ty không? | PO; xác định kỳ vọng chất lượng nếu cần. |

## 22. Definition of Ready

| Check | Status | Evidence / note |
|---|---|---|
| Actor và giá trị nghiệp vụ rõ ràng | Ready | **[CONFIRMED]** US-001; DoR review |
| Phạm vi và AC nguồn truy vết được | Ready | **[CONFIRMED]** REQ-101; AC-001..004; DoR review |
| BR-001 và ba thông tin bắt buộc rõ ràng | Ready | **[CONFIRMED]** requirement-analysis; AC-001..003 |
| Phụ thuộc và T-1 đã nhận diện | Ready | **[CONFIRMED]** architect handoff; DoR review |
| Câu hỏi nghiệp vụ được quyết định hoặc được PO chấp nhận làm mở | Awaiting decision | **[OPEN QUESTION]** Q-001-01..006 |
| Đánh giá DoR của nguồn | READY | **[CONFIRMED]** `docs/02-analysis/dor-review.md` |

**[CONFIRMED — human-approval rule]** Tài liệu dừng tại `AWAITING_SPECIFICATION_APPROVAL`; chỉ con người có thể đặt `SPECIFICATION_APPROVED`.

## 23. Technical Handoff

| Type | Constraint, touchpoint, risk or decision for Tech Lead | Evidence |
|---|---|---|
| Constraint | `AutomationPolicyGuard` phải ràng buộc việc xoá do tác nhân tự động; A-AI không được tự xoá dữ liệu do người tạo, kể cả ngoài giao diện. | **[CONFIRMED]** project rules; architecture; BR-017 |
| Touchpoint | Công ty là ngữ cảnh nghiệp vụ cho Người liên hệ và Cơ hội ở các story downstream. | **[CONFIRMED]** REQ-102..103; function-decomposition |
| Acceptance constraint | Hành vi CRM làm tay của US-001 vẫn dùng được khi AI tắt, theo T-1. | **[CONFIRMED]** REQ-113; PRD §6 |
| Risk / decision | Chính sách xoá khi có dữ liệu liên quan chưa được PO quyết định; không suy ra cách xử lý trước khi Q-001-03 được chấp thuận. | **[OPEN QUESTION]** Q-001-03 |
| Decision | Danh mục thông tin tùy chọn và hành vi sửa ba thông tin bắt buộc cần PO xác nhận trước khi mở rộng ngoài AC nguồn. | **[OPEN QUESTION]** Q-001-01; Q-001-04 |

## 24. Change Log

| Version | Date | Change | Author/Approver |
|---|---|---|---|
| 1.1 | 2026-08-14 | Bổ sung năm màn hình SVG chi tiết `SCR-US001-01..05`, liên kết trực tiếp trong specification và truy vết trạng thái tới AC-001..004; giữ các trường tùy chọn ở mức minh hoạ do Q-001-01 còn mở. | Codex — UI direction approved by user; specification approval unchanged |
| 1.0 | 2026-08-14 | Chuẩn hoá theo template 24 mục; bảo toàn US-001 / FEAT-001 / REQ-101 / BR-001 / AC-001..004 / T-1; loại nội dung kỹ thuật và chuyển quyết định chưa có nguồn thành câu hỏi nghiệp vụ. | Codex — awaiting human specification approval |
