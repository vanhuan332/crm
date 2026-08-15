# Business Specification — US-005: Chốt chặn Đủ điều kiện (2 dấu hiệu)

## 1. Document Information

| Field | Value |
|---|---|
| Story | `US-005` — Chốt chặn Đủ điều kiện (2 dấu hiệu) |
| Feature / domain | `FEAT-005` / `D1 — CRM lõi làm tay` / `EPIC-02` |
| Version | `1.2` |
| Status | `AWAITING_SPECIFICATION_APPROVAL` |
| Date | `2026-08-15` |
| Priority | Should (12) |
| Sources | `REQ-106`, `BR-005`, `BR-004`, `BR-017`; `US-005`, `AC-013..014`; `T-1`; DoR review; architect handoff |

## 2. Purpose

**[CONFIRMED — US-005, REQ-106]** Xác định hành vi nghiệp vụ khi Sales kéo một Cơ hội sang giai đoạn Đủ điều kiện: hệ thống hỏi hai dấu hiệu — nhu cầu và ngân sách, mỗi dấu hiệu gồm một câu nhận định và một nguồn. Sales có thể bỏ qua nhưng Cơ hội vẫn chuyển giai đoạn ngay và mang cờ cảnh báo cho tới khi được bổ sung đầy đủ.

## 3. User Story

**[CONFIRMED — US-005]** As a Sales, I want khi vào Đủ điều kiện được hỏi dấu hiệu nhu cầu & ngân sách, so that tôi chỉ theo đuổi khi kiểm được cả hai chiều.

## 4. Business Goal

**[CONFIRMED — BR-005]** Đủ điều kiện (Qualify) phải kiểm chứng đồng thời hai chiều: khách có nhu cầu thật và có khả năng chi trả; mỗi chiều cần một fact có nguồn, không chỉ là cảm nhận chủ quan của Sales. **[INFERRED — REQ-106]** Hỏi ngay tại thời điểm chuyển giai đoạn giúp Sales dừng lại kiểm chứng trước khi tiếp tục đầu tư công sức vào cơ hội, đồng thời không làm chậm thao tác kéo-thả của US-004.

## 5. Scope

- **[CONFIRMED — REQ-106, AC-013..014]** Khi Sales kéo Cơ hội sang giai đoạn Đủ điều kiện, hệ thống hỏi hai dấu hiệu: nhu cầu và ngân sách.
- **[CONFIRMED — AC-013]** Mỗi dấu hiệu gồm một câu nhận định và một nguồn.
- **[CONFIRMED — AC-014; REQ-106]** Sales có thể bỏ qua việc nhập hai dấu hiệu; thao tác kéo không bao giờ bị chặn vì thiếu dấu hiệu.
- **[CONFIRMED — AC-014]** Nếu bỏ qua hoặc chưa đủ, Cơ hội vẫn chuyển sang Đủ điều kiện ngay và mang cờ cảnh báo cho tới khi được bổ sung.
- **[CONFIRMED — AC-013]** Khi đã nhập đủ cả hai dấu hiệu có nguồn, Cơ hội ở Đủ điều kiện không mang cờ cảnh báo.
- **[CONFIRMED — BR-017]** A-AI không được tự đổi giai đoạn Cơ hội; chỉ Sales thực hiện thao tác kéo kích hoạt checkpoint này.

## 6. Out of Scope

- **[CONFIRMED — REQ-104..105, US-004]** Cơ chế kéo-thả đổi giai đoạn (tiến/lùi/nhảy cóc, tên và thứ tự 7 giai đoạn) thuộc US-004.
- **[CONFIRMED — REQ-103, US-003]** Tạo và quản lý dữ liệu Cơ hội (tên, giá trị dự kiến, tháng dự kiến chốt) thuộc US-003.
- **[CONFIRMED — BR-017]** Mọi hành vi tự động đổi giai đoạn hoặc tự tạo/tự duyệt dấu hiệu thay Sales không thuộc phạm vi story này; A-AI bị chặn tuyệt đối khỏi việc tự đổi giai đoạn.
- **[CONFIRMED — REQ-110, US-006]** Ghi lý do khi chuyển sang Thua thuộc US-006 — một checkpoint khác, cùng khuôn mẫu "bỏ qua → cờ cảnh báo" nhưng ở giai đoạn khác.
- Định dạng cụ thể của trường nguồn cho mỗi dấu hiệu — xem Open Question `Q-005-01`.
- Hành vi kích hoạt checkpoint khi tạo Cơ hội trực tiếp ở Đủ điều kiện (không qua thao tác kéo) — xem Open Question `Q-005-03`.

## 7. Actor / Permission

| Actor | Business permission | Evidence |
|---|---|---|
| Sales | Kéo Cơ hội sang Đủ điều kiện; nhập hoặc bỏ qua dấu hiệu nhu cầu và ngân sách; bổ sung dấu hiệu còn thiếu sau đó. | **[CONFIRMED]** US-005; REQ-106; AC-013..014 |
| A-AI | Không được tự đổi giai đoạn Cơ hội (kể cả sang Đủ điều kiện) và không được tự tạo/tự duyệt dấu hiệu nhu cầu hoặc ngân sách thay Sales, kể cả khi gọi ngoài giao diện. | **[CONFIRMED]** BR-017; architect handoff AR-1 |

**[OPEN QUESTION — Q-005-04]** Không có nguồn nào trong `docs/02-analysis` xác nhận Quản trị có cùng quyền thao tác checkpoint này như Sales (khác với quyết định đã chốt riêng cho Công ty ở US-001); tạm để actor là Sales theo đúng US-005 gốc.

## 8. Business Rules

| ID | Rule | Evidence |
|---|---|---|
| BR-005 | Đủ điều kiện (Qualify) phải kiểm cả hai chiều nhu cầu và ngân sách; mỗi chiều cần một fact có nguồn. | **[CONFIRMED]** requirement-analysis |
| BR-004 | Đủ điều kiện thuộc nhóm giai đoạn Mở {Tiếp cận, Đủ điều kiện, Soạn đề xuất, Thương lượng, Tạm dừng}. | **[CONFIRMED]** requirement-analysis (bối cảnh giai đoạn) |
| BR-US005-01 | Khi Cơ hội được kéo sang Đủ điều kiện, hệ thống hỏi hai dấu hiệu: nhu cầu và ngân sách; mỗi dấu hiệu gồm một câu nhận định và một nguồn. | **[CONFIRMED]** REQ-106; AC-013 |
| BR-US005-02 | Cơ hội chỉ được coi là hoàn chỉnh — không mang cờ cảnh báo — khi cả hai dấu hiệu đều đã có câu nhận định và nguồn. | **[CONFIRMED]** BR-005; AC-013 |
| BR-US005-03 | Sales có thể bỏ qua việc nhập hai dấu hiệu; thao tác kéo/chuyển sang Đủ điều kiện không bao giờ bị chặn vì thiếu dấu hiệu. | **[CONFIRMED]** REQ-106; AC-014 |
| BR-US005-04 | Nếu bỏ qua hoặc còn thiếu một trong hai dấu hiệu, Cơ hội vẫn ở Đủ điều kiện nhưng mang cờ cảnh báo cho tới khi được bổ sung đầy đủ. | **[CONFIRMED]** AC-014 |
| BR-US005-05 | A-AI không được tự đổi giai đoạn Cơ hội sang Đủ điều kiện hoặc bất kỳ giai đoạn nào khác, kể cả khi gọi ngoài giao diện. | **[CONFIRMED]** BR-017; architect handoff AR-1 |

## 9. Business Data Dictionary

| Business data | Meaning | Applicability / rule | Evidence |
|---|---|---|---|
| Cơ hội | Thương vụ Sales đang theo dõi qua các giai đoạn phễu bán hàng. | Đối tượng mang giai đoạn Đủ điều kiện và cờ cảnh báo của story. | **[CONFIRMED]** US-003; BR-004; US-005 |
| Giai đoạn Đủ điều kiện | Một trong bảy giai đoạn cố định của phễu bán hàng, thuộc nhóm Mở. | Kích hoạt hộp thoại hai dấu hiệu khi Cơ hội được kéo vào. | **[CONFIRMED]** BR-004; REQ-106 |
| Dấu hiệu nhu cầu | Một câu nhận định cho biết khách có nhu cầu thật, kèm một nguồn. | Một trong hai dấu hiệu cần có để Cơ hội không mang cờ cảnh báo. | **[CONFIRMED]** REQ-106; BR-005; AC-013 |
| Dấu hiệu ngân sách | Một câu nhận định cho biết khách có khả năng chi trả, kèm một nguồn. | Một trong hai dấu hiệu cần có để Cơ hội không mang cờ cảnh báo. | **[CONFIRMED]** REQ-106; BR-005; AC-013 |
| Nguồn (của dấu hiệu) | Căn cứ đi kèm câu nhận định của mỗi dấu hiệu. | Bắt buộc đi cùng câu nhận định để dấu hiệu được tính là đã cung cấp. | **[CONFIRMED — yêu cầu tồn tại]** BR-005; **[OPEN QUESTION — Q-005-01]** định dạng cụ thể |
| Cờ cảnh báo thiếu qualification | Đánh dấu Cơ hội đang ở Đủ điều kiện nhưng chưa đủ hai dấu hiệu có nguồn. | Hiển thị cho tới khi Sales bổ sung đầy đủ; không chặn bất kỳ thao tác nào khác. | **[CONFIRMED]** AC-014; BR-US005-04 |

## 10. Business Flow

**BF-005-01 — Kéo Cơ hội vào Đủ điều kiện và nhập đủ hai dấu hiệu.** **[CONFIRMED — AC-013]** Sales kéo Cơ hội sang giai đoạn Đủ điều kiện (cơ chế kéo thuộc US-004). Hệ thống hiển thị hộp thoại hỏi dấu hiệu nhu cầu và dấu hiệu ngân sách. Sales nhập câu nhận định và nguồn cho cả hai dấu hiệu rồi lưu. Cơ hội chuyển sang Đủ điều kiện và không mang cờ cảnh báo.

**BF-005-02 — Kéo vào Đủ điều kiện và bỏ qua.** **[CONFIRMED — AC-014]** Sales kéo Cơ hội sang Đủ điều kiện; hộp thoại hiện ra nhưng Sales bỏ qua cả hai ô. Cơ hội vẫn chuyển sang Đủ điều kiện ngay lập tức, thao tác kéo không bị chặn, và Cơ hội mang cờ cảnh báo thiếu qualification.

**BF-005-03 — Bổ sung dấu hiệu sau khi đã bỏ qua.** **[CONFIRMED — BR-US005-04]** Từ trạng thái mang cờ cảnh báo, Sales bổ sung câu nhận định và nguồn cho dấu hiệu còn thiếu. Khi cả hai dấu hiệu đã đủ, cờ cảnh báo được gỡ và Cơ hội chuyển sang trạng thái hoàn chỉnh.

## 11. Acceptance Criteria

**AC-013 — Nhập đủ hai dấu hiệu**

```gherkin
Scenario: Nhập đủ hai dấu hiệu
  Given tôi kéo cơ hội sang Đủ điều kiện
  When màn hình hỏi và tôi nhập cả dấu hiệu nhu cầu và dấu hiệu ngân sách (mỗi ô một câu + nguồn)
  Then cơ hội ở Đủ điều kiện không mang cờ cảnh báo.
```

**AC-014 — Bỏ qua vẫn kéo được**

```gherkin
Scenario: Bỏ qua vẫn kéo được
  Given tôi kéo cơ hội sang Đủ điều kiện
  When tôi bỏ qua hai ô
  Then cơ hội vẫn sang Đủ điều kiện và mang cờ cảnh báo cho tới khi bổ sung; thao tác kéo không bị chặn.
```

**[CONFIRMED — user-stories]** Hai acceptance criteria trên được bảo toàn nguyên nghĩa từ nguồn `docs/02-analysis/user-stories.md`. Không có acceptance criteria bổ sung nào cho US-005 được xác nhận ngoài AC-013 và AC-014.

## 12. Screen Specification

| Screen ID | Business area | Required information / behavior | Evidence |
|---|---|---|---|
| `SCR-US005-01` | Hộp thoại Đủ điều kiện | Xuất hiện khi Sales kéo Cơ hội sang Đủ điều kiện; thu thập câu nhận định + nguồn cho dấu hiệu nhu cầu và dấu hiệu ngân sách; cho phép Bỏ qua hoặc Lưu & chuyển giai đoạn. | **[CONFIRMED]** AC-013..014; REQ-106; BR-US005-01 |
| `SCR-US005-02` | Cờ cảnh báo thiếu qualification | Hiển thị trên đại diện của Cơ hội đang ở Đủ điều kiện nhưng chưa đủ hai dấu hiệu có nguồn; cho phép mở lại để bổ sung ngay. | **[CONFIRMED]** AC-014; BR-US005-04 |
| `SCR-US005-03` | Trạng thái Qualification | Phân biệt trạng thái hoàn chỉnh/không cảnh báo và chưa hoàn chỉnh/có cảnh báo; cả hai trạng thái đều không chặn chuyển giai đoạn. | **[CONFIRMED]** AC-013..014 |

## 13. Screen Design

> **UI-DESIGN UPDATE — 2026-08-15:** Wireframe BA dưới đây chuẩn hoá theo đúng ngôn ngữ hình ảnh đã được người dùng duyệt cho US-001 ngày 2026-08-14: nền `#f7f9fc`, card bo góc 14px viền `#d9e2ef`, thanh nhấn mục 5px `#695cff`, hành động chính màu tím `#5236f5`, hộp thoại có lớp phủ tối + card giữa màn hình, khối trạng thái dùng icon tròn + tiêu đề + mô tả + nút hành động. Asset là SVG Git-friendly, không quyết định framework, component library hay cách triển khai.

### 13.1 Tổng quan luồng

![US-005 screen flow](./assets/screen-flow.svg)

### 13.2 `SCR-US005-01` — Hộp thoại Đủ điều kiện

![US-005 qualification dialog](./assets/qualification-dialog.svg)

### 13.3 `SCR-US005-02` — Cờ cảnh báo thiếu dấu hiệu

![US-005 qualification warning](./assets/qualification-warning.svg)

### 13.4 `SCR-US005-03` — Trạng thái Qualification

![US-005 qualification states](./assets/qualification-states.svg)

Các SVG chỉ minh hoạ hai dấu hiệu đã chốt: dấu hiệu nhu cầu và dấu hiệu ngân sách, mỗi dấu hiệu gồm câu nhận định và nguồn. Định dạng cụ thể của trường nguồn không được áp đặt trong wireframe do `Q-005-01` còn mở.

## 14. Screen States

| State | Visible business outcome | Screen / asset | Evidence |
|---|---|---|---|
| Kéo vào Đủ điều kiện, nhập đủ hai dấu hiệu | Cơ hội chuyển Đủ điều kiện, không mang cờ cảnh báo. | `SCR-US005-01` → `SCR-US005-03` | **[CONFIRMED]** AC-013 |
| Kéo vào Đủ điều kiện, bỏ qua cả hai ô | Cơ hội vẫn chuyển ngay sang Đủ điều kiện, thao tác kéo không bị chặn, mang cờ cảnh báo. | `SCR-US005-01` → `SCR-US005-02` | **[CONFIRMED]** AC-014 |
| Thiếu một trong hai dấu hiệu (chỉ nhập nhu cầu hoặc chỉ nhập ngân sách) | **[INFERRED — BR-005]** Cơ hội tiếp tục mang cờ cảnh báo cho tới khi cả hai dấu hiệu đều đủ; hành vi từ chối lưu một phần cụ thể chưa được xác nhận. | `SCR-US005-02` | **[OPEN QUESTION — Q-005-02]** |
| Bổ sung dấu hiệu còn thiếu sau khi đã bỏ qua | Cờ cảnh báo được gỡ; Cơ hội chuyển sang trạng thái hoàn chỉnh. | `SCR-US005-02` → `SCR-US005-03` | **[CONFIRMED]** BR-US005-04 |
| Đang lưu dấu hiệu | **[ASSUMPTION]** Khoá tạm hai nút hành động và hiển thị chỉ báo đang xử lý trong hộp thoại; không có nguồn riêng cho US-005. | `SCR-US005-01` | **[ASSUMPTION — A-005-04]** |
| Lỗi lưu có thể thử lại | **[ASSUMPTION]** Giữ nguyên nội dung đã nhập và cho phép thử lưu lại; mẫu chung nhất quán với các story CRM khác. | `SCR-US005-01` | **[ASSUMPTION — A-005-04]** |

## 15. Validation

| Condition | Expected business response | Evidence |
|---|---|---|
| Cả hai dấu hiệu có câu nhận định và nguồn | Không mang cờ cảnh báo; Cơ hội ở Đủ điều kiện bình thường. | **[CONFIRMED]** AC-013; BR-US005-02 |
| Bỏ qua cả hai dấu hiệu | Cơ hội vẫn chuyển sang Đủ điều kiện; mang cờ cảnh báo; thao tác kéo không bị chặn. | **[CONFIRMED]** AC-014; BR-US005-03 |
| Chỉ cung cấp một trong hai dấu hiệu, hoặc một dấu hiệu chỉ có câu nhận định mà thiếu nguồn (hoặc ngược lại) | **[INFERRED — BR-005]** Cơ hội tiếp tục mang cờ cảnh báo cho tới khi đủ cả câu và nguồn ở cả hai dấu hiệu; hành vi từ chối/chấp nhận lưu một phần chưa được xác nhận. | **[OPEN QUESTION — Q-005-02]** |
| Định dạng của trường nguồn (văn bản tự do, đường dẫn, tham chiếu bản lưu...) | Chưa xác định. | **[OPEN QUESTION — Q-005-01]** |
| Tạo Cơ hội trực tiếp ở giai đoạn Đủ điều kiện (không qua thao tác kéo) | Chưa xác định checkpoint có được kích hoạt theo cùng quy tắc hay không. | **[OPEN QUESTION — Q-005-03]** |

## 16. Dependencies

| Direction | Item | Dependency | Evidence |
|---|---|---|---|
| Upstream | US-004 / FEAT-004 | Thao tác kéo Cơ hội sang Đủ điều kiện (cơ chế kéo-thả của US-004) là điều kiện kích hoạt checkpoint này. | **[CONFIRMED]** user-stories US-005 "Dep: US-004"; REQ-104..105 |
| Upstream | US-003 / FEAT-003 | Cơ hội phải tồn tại và mang giai đoạn trước khi có thể được kéo sang Đủ điều kiện. | **[INFERRED]** BR-003; BR-004; function-decomposition |
| Cross-cutting | US-040 / FEAT-040 | Ràng buộc chặn A-AI tự đổi giai đoạn Cơ hội ở tầng service áp dụng cho checkpoint Đủ điều kiện. | **[CONFIRMED]** BR-017; architect handoff AR-1 |
| Acceptance | T-1 | CRM lõi, gồm hành vi hỏi 2 dấu hiệu và cờ cảnh báo khi vào Đủ điều kiện, hoạt động khi toàn bộ AI tắt. | **[CONFIRMED]** Coverage nghiệm thu (T-1→106); architect handoff |

## 17. Business-level NFR Expectations

- **[CONFIRMED — REQ-113]** CRM làm tay của story hoạt động khi toàn bộ AI tắt; T-1 bao gồm hành vi hỏi hai dấu hiệu và cờ cảnh báo trong điều kiện này.
- **[CONFIRMED — REQ-704; architecture]** Dữ liệu CRM được kỳ vọng bền qua khởi động lại ở triển khai sản phẩm; đây là kỳ vọng cấp hệ thống, không thêm quy tắc dữ liệu riêng cho US-005.
- **[INFERRED — không có nguồn đặt SLA riêng cho US-005]** US-005 không đặt SLA riêng; áp dụng kỳ vọng chất lượng chung của hệ thống.

## 18. Test Scenarios

Chưa có `test-scenarios.md` riêng cho US-005. Các tình huống dưới đây là truy vết nghiệp vụ, không phải kiểm thử thực thi; chúng đóng góp vào bộ nghiệm thu **T-1**. **[CONFIRMED — architect handoff; Coverage nghiệm thu]**

| ID | Business scenario | AC / BR | Expected business result | Acceptance trace |
|---|---|---|---|---|
| TC-005-01 | Sales kéo Cơ hội sang Đủ điều kiện và nhập đủ câu nhận định + nguồn cho cả hai dấu hiệu. | AC-013; BR-US005-01..02 | Cơ hội ở Đủ điều kiện không mang cờ cảnh báo. | T-1 |
| TC-005-02 | Sales kéo Cơ hội sang Đủ điều kiện và bỏ qua cả hai ô dấu hiệu. | AC-014; BR-US005-03..04 | Cơ hội vẫn chuyển sang Đủ điều kiện ngay, thao tác kéo không bị chặn, mang cờ cảnh báo. | T-1 |
| TC-005-03 | Sales bổ sung câu nhận định + nguồn cho dấu hiệu còn thiếu sau khi đã bỏ qua trước đó. | BR-US005-04 | Cờ cảnh báo được gỡ khi cả hai dấu hiệu đã đủ. | T-1 |
| TC-005-04 | Sales nhập đầy đủ một dấu hiệu (câu + nguồn) nhưng bỏ trống dấu hiệu còn lại. | BR-US005-02; BR-005 | **[INFERRED / OPEN QUESTION — Q-005-02]** Cơ hội chuyển Đủ điều kiện, tiếp tục mang cờ cảnh báo cho tới khi dấu hiệu còn lại được bổ sung đầy đủ; hành vi từ chối lưu một phần (nếu có) chưa được xác nhận. | T-1 (một phần) |

## 19. Traceability

| Chain | Evidence |
|---|---|
| `D1 → EPIC-02 → FEAT-005 → US-005 → AC-013..014 → T-1` | **[CONFIRMED]** function-decomposition; user-stories; architect handoff |
| `REQ-106 → FEAT-005 → US-005 → AC-013..014` | **[CONFIRMED]** requirement-analysis; user-stories |
| `BR-005 → US-005 → AC-013 → TC-005-01` | **[CONFIRMED]** requirement-analysis; user-stories |
| `AC-014 → TC-005-02..03` | **[CONFIRMED]** user-stories |
| `REQ-113 → T-1 → US-005` | **[CONFIRMED]** requirement-analysis; Coverage nghiệm thu |
| `BR-017 → US-040 → BR-US005-05` | **[CONFIRMED]** requirement-analysis; architecture; architect handoff AR-1 |
| `US-004 → US-005` (Dep) | **[CONFIRMED]** user-stories header "Dep: US-004" |

## 20. Assumptions

| ID | Assumption | Rationale / status |
|---|---|---|
| A-005-01 | Bố cục và visual language kế thừa hướng đã duyệt ngày 2026-08-14 cho US-001: nền sáng, card viền mảnh, hộp thoại giữa lớp phủ tối, hành động chính màu tím. | **[ASSUMPTION]** Không quyết định framework hoặc component library. |
| A-005-02 | Cờ cảnh báo thiếu qualification được thể hiện trên đại diện của Cơ hội (thẻ trên bảng giai đoạn và/hoặc chi tiết Cơ hội); vị trí hiển thị chính xác chưa được nguồn xác nhận riêng cho US-005. | **[ASSUMPTION]** Suy luận từ AC-014 ("cơ hội... mang cờ cảnh báo") không chỉ rõ màn hình; nhất quán với US-004 (bảng giai đoạn) và US-003 (chi tiết Cơ hội). |
| A-005-03 | Một dấu hiệu chỉ được tính là "đã cung cấp" khi có cả câu nhận định và nguồn; thiếu một trong hai phần vẫn giữ cờ cảnh báo và không chặn lưu/kéo. | **[ASSUMPTION]** Suy luận từ BR-005 ("mỗi chiều cần fact có nguồn") kết hợp REQ-106 ("không chặn"); chưa có xác nhận riêng cho hành vi lưu một phần — xem `Q-005-02`. |
| A-005-04 | Trạng thái đang lưu và lỗi có thể thử lại theo mẫu chung của hệ thống (giữ dữ liệu đã nhập, không chặn thao tác kéo). | **[ASSUMPTION]** Không có quyết định riêng cho US-005; suy luận nhất quán với mẫu trạng thái đã áp dụng ở US-001. |

## 21. Open Questions

- **[Q-005-01]** Định dạng bắt buộc của trường nguồn cho mỗi dấu hiệu là gì (văn bản tự do, đường dẫn, tham chiếu bản lưu/Discovery...)? **[OPEN QUESTION — PO]**
- **[Q-005-02]** Khi Sales chỉ nhập một phần của một dấu hiệu (chỉ câu nhận định, hoặc chỉ nguồn), hệ thống có từ chối lưu phần đó tại chỗ hay chấp nhận và giữ cờ cảnh báo cho tới khi đủ? **[OPEN QUESTION — PO]**
- **[Q-005-03]** Khi Sales tạo Cơ hội mới trực tiếp ở giai đoạn Đủ điều kiện (không qua thao tác kéo từ giai đoạn khác), hộp thoại hai dấu hiệu có được kích hoạt theo cùng quy tắc hay không? **[OPEN QUESTION — PO]**
- **[Q-005-04]** Quản trị có được thao tác checkpoint này (nhập/bỏ qua/bổ sung dấu hiệu) như Sales hay không? **[OPEN QUESTION — PO]**

## 22. Definition of Ready

| Check | Status | Evidence / note |
|---|---|---|
| Actor và giá trị nghiệp vụ rõ ràng | Ready | **[CONFIRMED]** US-005; DoR review |
| Phạm vi và AC nguồn truy vết được | Ready | **[CONFIRMED]** REQ-106; AC-013..014; DoR review |
| BR-005 và hai dấu hiệu có nguồn rõ ràng | Ready | **[CONFIRMED]** requirement-analysis; AC-013..014 |
| Phụ thuộc US-004 và T-1 đã nhận diện | Ready | **[CONFIRMED]** user-stories; architect handoff; DoR review |
| Câu hỏi nghiệp vụ còn mở được PO chấp nhận làm mở | Ready (không chặn) | Bốn câu hỏi `Q-005-01..04` (định dạng nguồn, lưu một phần, tạo trực tiếp ở Đủ điều kiện, quyền Quản trị) chưa ảnh hưởng hành vi cốt lõi "hỏi 2 dấu hiệu, không chặn, mang cờ cảnh báo". |
| Đánh giá DoR của nguồn | READY | **[CONFIRMED]** `docs/02-analysis/dor-review.md` |

**[CONFIRMED — human-approval rule]** Tài liệu dừng tại `AWAITING_SPECIFICATION_APPROVAL`; chỉ con người có thể đặt `SPECIFICATION_APPROVED`.

## 23. Technical Handoff

| Type | Constraint, touchpoint, risk or decision for Tech Lead | Evidence |
|---|---|---|
| Constraint | `AutomationPolicyGuard` (hoặc cơ chế tương đương) phải chặn A-AI tự đổi giai đoạn Cơ hội, kể cả sang Đủ điều kiện, kể cả khi gọi ngoài giao diện. | **[CONFIRMED]** BR-017; architect handoff AR-1 |
| Touchpoint | Checkpoint này gắn với thao tác kéo của US-004 (Bảng 7 giai đoạn); cần điểm neo để nhận biết Cơ hội vừa được kéo vào Đủ điều kiện và kích hoạt hộp thoại. | **[CONFIRMED]** user-stories "Dep: US-004"; AC-013..014 |
| Acceptance constraint | Hành vi hỏi hai dấu hiệu và cờ cảnh báo của US-005 vẫn phải dùng được khi AI tắt, theo T-1. | **[CONFIRMED]** REQ-113; Coverage nghiệm thu |
| Question | Định dạng trường nguồn (`Q-005-01`), hành vi khi nhập một phần dấu hiệu (`Q-005-02`), việc kích hoạt checkpoint khi tạo Cơ hội trực tiếp ở Đủ điều kiện (`Q-005-03`), và quyền Quản trị đối với checkpoint (`Q-005-04`) cần PO quyết định trước khi Tech Lead thiết kế luồng chi tiết. | **[OPEN QUESTION]** Q-005-01..04 |
| Decision (pending PO) | Cờ cảnh báo không chặn bất kỳ thao tác nào (kéo, lưu, chuyển giai đoạn); đây chỉ là chỉ báo trực quan cần hiển thị nhất quán trên các điểm chạm liên quan tới Cơ hội. | **[CONFIRMED]** REQ-106; BR-US005-03..04 |

## 24. Change Log

| Version | Date | Change | Author/Approver |
|---|---|---|---|
| 1.2 | 2026-08-15 | Viết lại toàn diện theo chuẩn 24 mục US-001 v1.2, đối chiếu docs/02-analysis, chuẩn hoá SVG theo ngôn ngữ hình ảnh đã duyệt. | Codex — comprehensive refinement pass; specification approval unchanged |
| 1.1 | 2026-08-14 | Bổ sung ba màn hình SVG cho hộp thoại nhập dấu hiệu, cảnh báo thiếu và trạng thái hoàn chỉnh/chưa hoàn chỉnh; định dạng nguồn còn để mở. | Codex — UI pattern approved; specification approval unchanged |
| 1.0 | 2026-08-14 | Khởi tạo đặc tả US-005. | Codex — awaiting human specification approval |
