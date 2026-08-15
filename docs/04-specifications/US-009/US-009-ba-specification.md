# Business Specification — US-009: Tìm kiếm và Lọc

## 1. Document Information

| Field | Value |
|---|---|
| Story | `US-009` — Tìm kiếm và Lọc |
| Feature / domain | `FEAT-009` / `D1 — CRM lõi làm tay` / `EPIC-03` |
| Version | `1.2` |
| Status | `AWAITING_SPECIFICATION_APPROVAL` |
| Date | `2026-08-15` |
| Priority | Could (10, acceptance-mandatory T-1) |
| Sources | `REQ-111`; `US-009`, `AC-021..023`; `T-1`; DoR review; architect handoff |

## 2. Purpose

**[CONFIRMED — REQ-111]** Xác định hành vi nghiệp vụ để Sales tìm Công ty theo một phần Tên công ty, lọc danh sách Công ty theo Ngành, Loại công ty, Quốc gia và nhãn Đang theo dõi, và lọc danh sách Cơ hội theo Giai đoạn và tình trạng quá hạn Việc tiếp theo.

## 3. User Story

**[CONFIRMED — US-009]** As a Sales, I want tìm và lọc công ty/cơ hội, so that tôi tìm lại nhanh thứ đã nhập.

## 4. Business Goal

**[CONFIRMED — US-009]** Sales thu hẹp danh sách còn mục thoả điều kiện cần xem. **[INFERRED — REQ-111]** Tìm và lọc giúp Sales tìm lại nhanh một Công ty hoặc Cơ hội đã nhập trước đó mà không phải duyệt toàn bộ danh sách.

## 5. Scope

- **[CONFIRMED — AC-021]** Sales gõ một phần Tên công ty; danh sách Công ty chỉ còn Công ty khớp.
- **[CONFIRMED — AC-022]** Sales lọc danh sách Công ty theo Ngành, Loại công ty, Quốc gia hoặc nhãn Đang theo dõi.
- **[CONFIRMED — AC-023]** Sales lọc danh sách Cơ hội theo Giai đoạn hoặc tình trạng quá hạn Việc tiếp theo.
- **[INFERRED — REQ-111]** Tìm và lọc chỉ thu hẹp góc nhìn hiển thị; không tạo, sửa hay xoá dữ liệu Công ty hoặc Cơ hội.

## 6. Out of Scope

- **[CONFIRMED — US-001]** Tạo, sửa, xoá và xem chi tiết dữ liệu Công ty (thuộc US-001).
- **[CONFIRMED — US-003]** Tạo, sửa, xoá dữ liệu Cơ hội (thuộc US-003).
- **[CONFIRMED — US-008]** Đặt Việc tiếp theo và ngày hạn (thuộc US-008); US-009 chỉ dùng tình trạng quá hạn làm tiêu chí lọc.
- **[CONFIRMED — US-030]** Bật/tắt nhãn Đang theo dõi (thuộc US-030); US-009 chỉ dùng nhãn này làm tiêu chí lọc, không tạo hay đổi giá trị nhãn.
- **[OPEN QUESTION — Q-009-01]** Cách kết hợp nhiều tiêu chí lọc cùng lúc (AND/OR, thứ tự áp dụng), kể cả kết hợp từ khoá tìm kiếm với bộ lọc.
- **[OPEN QUESTION — Q-009-02]** Quyền của Quản trị đối với chức năng tìm kiếm và lọc.
- **[OPEN QUESTION — Q-009-03]** Nội dung và hành động cụ thể hiển thị khi không có kết quả thoả tiêu chí.

## 7. Actor / Permission

| Actor | Business permission | Evidence |
|---|---|---|
| Sales | Tìm Công ty theo một phần Tên công ty; lọc danh sách Công ty và danh sách Cơ hội theo các tiêu chí đã nêu. | **[CONFIRMED]** US-009; REQ-111 |
| A-AI | Không có hành vi tự động nào trong story này; tìm/lọc chỉ diễn ra khi Sales thao tác. | **[INFERRED]** requirement-analysis — quy ước mô hình hoá actor: khi phần mềm chỉ phản hồi thao tác của người (tìm kiếm, hiển thị) thì actor là Sales, không mô hình hoá "hệ thống" thành actor. |
| Quản trị | Quyền dùng chức năng tìm kiếm và lọc chưa được nêu trong nguồn. | **[OPEN QUESTION — Q-009-02]** |

## 8. Business Rules

| ID | Rule | Evidence |
|---|---|---|
| BR-US009-01 | Khi Sales gõ một phần Tên công ty, danh sách Công ty chỉ còn các Công ty có Tên khớp với phần đã gõ. | **[CONFIRMED]** AC-021 |
| BR-US009-02 | Danh sách Công ty có thể được lọc theo Ngành, Loại công ty, Quốc gia và nhãn Đang theo dõi; sau khi lọc, danh sách chỉ còn Công ty thoả tiêu chí đã chọn. | **[CONFIRMED]** AC-022 |
| BR-US009-03 | Danh sách Cơ hội có thể được lọc theo Giai đoạn và tình trạng quá hạn Việc tiếp theo; sau khi lọc, danh sách chỉ còn Cơ hội thoả tiêu chí đã chọn. | **[CONFIRMED]** AC-023 |
| BR-US009-04 | Tìm kiếm và lọc chỉ thay đổi danh sách hiển thị; không thay đổi dữ liệu Công ty hay Cơ hội. | **[INFERRED]** REQ-111 — mô tả hành vi thu hẹp danh sách, không nêu hành vi ghi/sửa dữ liệu. |
| BR-US009-05 | Loại công ty dùng làm tiêu chí lọc chỉ thuộc một trong năm giá trị đã quy định ở BR-001. | **[CONFIRMED]** BR-001; AC-022 |
| BR-US009-06 | Giai đoạn dùng làm tiêu chí lọc Cơ hội chỉ thuộc bảy giai đoạn cố định theo thứ tự đã quy định (Tiếp cận→Đủ điều kiện→Soạn đề xuất→Thương lượng→Thắng→Thua→Tạm dừng). | **[CONFIRMED]** REQ-104; US-004; AC-023 |
| BR-US009-07 | Cách kết hợp khi Sales chọn nhiều tiêu chí lọc cùng lúc, hoặc kết hợp từ khoá tìm kiếm với bộ lọc, chưa được quy định trong nguồn. | **[OPEN QUESTION — Q-009-01]** |

## 9. Business Data Dictionary

| Business data | Meaning | Applicability / rule | Evidence |
|---|---|---|---|
| Tên công ty | Dữ liệu dùng làm từ khoá tìm kiếm Công ty; là trường Tên công ty đã định nghĩa ở US-001. | Sales gõ một phần Tên; danh sách chỉ còn Công ty có Tên khớp với phần đã gõ. | **[CONFIRMED]** AC-021; US-001 |
| Ngành | Tiêu chí lọc Công ty; là trường Ngành đã định nghĩa ở US-001. | Một trong các tiêu chí lọc Công ty ở AC-022. | **[CONFIRMED]** AC-022; US-001 |
| Loại công ty | Tiêu chí lọc Công ty; là trường Loại công ty theo BR-001 (5 giá trị) đã định nghĩa ở US-001. | Một trong các tiêu chí lọc Công ty; giá trị lọc giới hạn trong BR-001. | **[CONFIRMED]** AC-022; BR-001; US-001 |
| Quốc gia | Tiêu chí lọc Công ty; là trường Quốc gia (tuỳ chọn) đã định nghĩa ở US-001. | Một trong các tiêu chí lọc Công ty ở AC-022. | **[CONFIRMED]** AC-022; US-001 |
| Đang theo dõi | Nhãn đánh dấu Công ty cần theo dõi sát, định nghĩa và bật/tắt ở US-030; US-009 dùng nhãn này làm tiêu chí lọc. | Một trong các tiêu chí lọc Công ty ở AC-022; US-009 không tạo hay đổi giá trị nhãn. | **[CONFIRMED]** AC-022; REQ-501; US-030 |
| Giai đoạn | Tiêu chí lọc Cơ hội; là một trong bảy giai đoạn cố định (Tiếp cận→Đủ điều kiện→Soạn đề xuất→Thương lượng→Thắng→Thua→Tạm dừng) định nghĩa ở US-004. | Một trong các tiêu chí lọc Cơ hội ở AC-023. | **[CONFIRMED]** AC-023; REQ-104; US-004 |
| Quá hạn Việc tiếp theo | Tiêu chí lọc Cơ hội cho biết Việc tiếp theo của Cơ hội đã tới hoặc qua ngày hạn mà chưa hoàn tất. | Một trong các tiêu chí lọc Cơ hội ở AC-023; kế thừa khái niệm Việc tiếp theo + ngày hạn của US-008. | **[INFERRED — REQ-109; REQ-112; US-008]** Cơ sở suy luận: PRD dùng cùng khái niệm "Next step quá hạn" ở màn hình tổng quan (REQ-112); US-009 không định nghĩa lại khái niệm này. |

## 10. Business Flow

**BF-009-01 — Tìm Công ty theo tên.** **[CONFIRMED — AC-021]** Sales gõ một phần Tên công ty vào ô tìm kiếm trên màn hình danh sách Công ty. Hệ thống thu hẹp danh sách, chỉ còn hiển thị các Công ty có Tên khớp với phần đã gõ.

**BF-009-02 — Lọc danh sách Công ty.** **[CONFIRMED — AC-022]** Sales chọn một hoặc nhiều tiêu chí trong Ngành, Loại công ty, Quốc gia, nhãn Đang theo dõi trên màn hình danh sách Công ty. Hệ thống thu hẹp danh sách, chỉ còn Công ty thoả các tiêu chí đã chọn. **[OPEN QUESTION — Q-009-01]** Cách kết hợp khi Sales chọn nhiều tiêu chí cùng lúc chưa được nêu trong nguồn.

**BF-009-03 — Lọc danh sách Cơ hội.** **[CONFIRMED — AC-023]** Sales chọn Giai đoạn hoặc tình trạng quá hạn Việc tiếp theo trên màn hình danh sách Cơ hội. Hệ thống thu hẹp danh sách, chỉ còn Cơ hội thoả tiêu chí đã chọn.

**BF-009-04 — Không có kết quả thoả tiêu chí.** **[ASSUMPTION — A-009-02]** Khi từ khoá tìm kiếm hoặc bộ lọc không khớp Công ty hoặc Cơ hội nào, hệ thống hiển thị trạng thái không có kết quả và cho phép Sales xoá tiêu chí để quay lại danh sách đầy đủ. **[OPEN QUESTION — Q-009-03]** Nội dung và hành động cụ thể của trạng thái này chưa được nêu trong nguồn.

## 11. Acceptance Criteria

**AC-021 — Tìm công ty theo tên**

```gherkin
Scenario: Tìm công ty theo tên
  Given có nhiều công ty
  When tôi gõ một phần tên
  Then danh sách chỉ còn công ty khớp.
```

**AC-022 — Lọc công ty**

```gherkin
Scenario: Lọc công ty
  Given danh sách công ty
  When tôi lọc theo ngành / loại công ty / quốc gia / nhãn Đang theo dõi
  Then chỉ còn công ty thoả bộ lọc.
```

**AC-023 — Lọc cơ hội**

```gherkin
Scenario: Lọc cơ hội
  Given danh sách cơ hội
  When tôi lọc theo giai đoạn / tình trạng quá hạn Việc tiếp theo
  Then chỉ còn cơ hội thoả bộ lọc.
```

**[CONFIRMED — user-stories]** Ba acceptance criteria trên được bảo toàn nguyên nghĩa từ nguồn (`docs/02-analysis/user-stories.md`, US-009).

## 12. Screen Specification

| Screen ID | Business area | Required information / behavior | Evidence |
|---|---|---|---|
| `SCR-US009-01` | Danh sách Công ty (Tìm và lọc) | Sales gõ một phần Tên công ty và/hoặc chọn tiêu chí Ngành, Loại công ty, Quốc gia, nhãn Đang theo dõi; danh sách chỉ còn Công ty thoả điều kiện. | **[CONFIRMED]** AC-021; AC-022 |
| `SCR-US009-02` | Danh sách Cơ hội (Lọc) | Sales chọn tiêu chí Giai đoạn hoặc tình trạng quá hạn Việc tiếp theo; danh sách chỉ còn Cơ hội thoả điều kiện. | **[CONFIRMED]** AC-023 |
| `SCR-US009-03` | Trạng thái Tìm/Lọc | Minh hoạ có kết quả, không có kết quả, đang tải và lỗi có thể thử lại; không tự quyết cách kết hợp nhiều tiêu chí hay quyền Quản trị. | **[ASSUMPTION]** A-009-01; **[OPEN QUESTION]** Q-009-01, Q-009-02, Q-009-03 |

## 13. Screen Design

> **UI-DESIGN UPDATE — 2026-08-15:** Wireframe BA dưới đây kế thừa đúng ngôn ngữ hình ảnh đã được người dùng duyệt ngày 2026-08-14 cho US-001 (nền sáng, card viền mảnh, bảng dữ liệu, thanh nhấn mục 5px màu tím, nút chính tím `#5236f5`, nút phụ viền `#bfcee0`, khối trạng thái rỗng/lỗi dùng icon tròn + tiêu đề + mô tả + nút hành động). Các asset là SVG Git-friendly, không quyết định framework, component library hay cách triển khai.

### 13.1 Tổng quan luồng

![US-009 screen flow](./assets/screen-flow.svg)

### 13.2 `SCR-US009-01` — Tìm và lọc Công ty

![US-009 company search filter](./assets/company-search-filter.svg)

### 13.3 `SCR-US009-02` — Lọc Cơ hội

![US-009 opportunity filter](./assets/opportunity-filter.svg)

### 13.4 `SCR-US009-03` — Trạng thái Tìm/Lọc

![US-009 search filter states](./assets/search-filter-states.svg)

**[ASSUMPTION — A-009-01]** Visual language kế thừa mẫu đã duyệt cho US-001; cách biểu đạt empty/loading/error không tự quyết logic kết hợp filter hoặc quyền Quản trị.

## 14. Screen States

| State | Visible business outcome | Screen / asset | Evidence |
|---|---|---|---|
| Có từ khoá tìm kiếm | Danh sách Công ty chỉ còn Công ty có Tên khớp phần đã gõ. | `SCR-US009-01` | **[CONFIRMED]** AC-021 |
| Có bộ lọc Công ty | Danh sách Công ty chỉ còn Công ty thoả tiêu chí Ngành/Loại/Quốc gia/Đang theo dõi đã chọn. | `SCR-US009-01` | **[CONFIRMED]** AC-022 |
| Có bộ lọc Cơ hội | Danh sách Cơ hội chỉ còn Cơ hội thoả tiêu chí Giai đoạn/quá hạn đã chọn. | `SCR-US009-02` | **[CONFIRMED]** AC-023 |
| Nhiều tiêu chí được chọn cùng lúc | Cách kết hợp kết quả chưa được xác nhận. | `SCR-US009-01`, `SCR-US009-03` | **[OPEN QUESTION]** Q-009-01 |
| Không có kết quả | Hiển thị trạng thái không có kết quả và cho phép xoá tiêu chí; nội dung/hành động chính xác chưa chốt. | `SCR-US009-03` | **[ASSUMPTION]** A-009-02; **[OPEN QUESTION]** Q-009-03 |
| Đang tải kết quả | Khung kết quả hiển thị dạng skeleton; ô tìm kiếm và tiêu chí lọc tạm khoá. | `SCR-US009-03` | **[ASSUMPTION]** A-009-01 |
| Lỗi có thể thử lại | Thông báo không thể tải kết quả, giữ nguyên từ khoá/tiêu chí đang chọn và cho thử lại. | `SCR-US009-03` | **[ASSUMPTION]** A-009-01 |
| Vai trò Quản trị dùng tìm/lọc | Chưa được xác nhận. | — | **[OPEN QUESTION]** Q-009-02 |

## 15. Validation

| Condition | Expected business response | Evidence |
|---|---|---|
| Tiêu chí tìm kiếm hoặc lọc được chọn | Danh sách phản ánh đúng tiêu chí đã chọn. | **[CONFIRMED]** AC-021..023 |
| Loại công ty dùng lọc | Chỉ hiển thị lựa chọn thuộc năm giá trị theo BR-001. | **[CONFIRMED]** BR-001; BR-US009-05 |
| Giai đoạn dùng lọc | Chỉ hiển thị lựa chọn thuộc bảy giai đoạn cố định theo REQ-104. | **[CONFIRMED]** REQ-104; BR-US009-06 |
| Từ khoá tìm kiếm để trống | **[INFERRED — quy ước "một phần tên": chuỗi rỗng khớp mọi Tên]** Danh sách hiển thị đầy đủ, không áp bộ lọc tên. | **[INFERRED]** AC-021 |
| Nhiều tiêu chí được chọn cùng lúc | Cách kết hợp (AND/OR) chưa được quy định. | **[OPEN QUESTION]** Q-009-01 |
| Không có mục nào thoả tiêu chí | Nội dung/hành động cụ thể chưa được quy định. | **[OPEN QUESTION]** Q-009-03 |

Không có ràng buộc bắt buộc/tuỳ chọn nào áp dụng cho việc tìm kiếm và lọc vì đây là thao tác chỉ đọc, không ghi dữ liệu (BR-US009-04); do đó không có trạng thái "lưu thất bại" hay "lưu thành công" cho story này. **[INFERRED — BR-US009-04]**

## 16. Dependencies

| Direction | Item | Dependency | Evidence |
|---|---|---|---|
| Upstream | US-001 / FEAT-001 | Danh sách và các trường Công ty (Tên, Ngành, Loại công ty, Quốc gia) là nguồn dữ liệu để tìm và lọc. | **[CONFIRMED]** user-stories (Dep: US-001); AC-021..022 |
| Upstream | US-003 / FEAT-003 | Danh sách Cơ hội là nguồn dữ liệu để lọc. | **[CONFIRMED]** user-stories (Dep: US-003); AC-023 |
| Related | US-004 / FEAT-004 | Giai đoạn dùng lọc Cơ hội là bảy giai đoạn cố định định nghĩa ở US-004. | **[CONFIRMED]** REQ-104; AC-023 |
| Related | US-008 / FEAT-008 | Tình trạng quá hạn Việc tiếp theo dùng lọc Cơ hội kế thừa khái niệm Việc tiếp theo + ngày hạn của US-008. | **[CONFIRMED]** REQ-109; AC-023 |
| Related | US-030 / FEAT-030 | Nhãn Đang theo dõi dùng làm tiêu chí lọc Công ty được định nghĩa và bật/tắt ở US-030. | **[CONFIRMED]** REQ-501; AC-022 |
| Acceptance | T-1 | CRM lõi làm tay, gồm tìm kiếm và lọc, hoạt động khi toàn bộ AI tắt; US-009 acceptance-mandatory cho T-1. | **[CONFIRMED]** backlog-prioritization; architect handoff; REQ-113 |

## 17. Business-level NFR Expectations

- **[CONFIRMED — REQ-111]** Kết quả tìm kiếm và lọc phải phản ánh đúng tiêu chí Sales đã chọn.
- **[CONFIRMED — REQ-113; architect handoff]** Tìm kiếm và lọc thuộc CRM làm tay (Nhóm 1); phải hoạt động khi toàn bộ AI tắt, là một phần nội dung nghiệm thu T-1.
- **[CONFIRMED — backlog-prioritization]** US-009 được xếp MoSCoW Could (điểm 10) nhưng là "acceptance-mandatory" cho T-1 — nghĩa là vẫn bắt buộc hoàn thành để đáp ứng nghiệm thu dù không thuộc nhóm ưu tiên Must/Should.
- **[INFERRED]** Nguồn không nêu ngưỡng thời gian phản hồi hay SLA riêng cho tìm kiếm và lọc; US-009 áp dụng kỳ vọng chất lượng chung của hệ thống, không thêm ràng buộc hiệu năng riêng.

## 18. Test Scenarios

Chưa có `test-scenarios.md` riêng cho US-009. Các tình huống dưới đây là truy vết nghiệp vụ, không phải kiểm thử thực thi; chúng đóng góp vào bộ nghiệm thu **T-1**. **[CONFIRMED — architect handoff; backlog-prioritization]**

| ID | Business scenario | AC / BR | Expected business result | Acceptance trace |
|---|---|---|---|---|
| TC-009-01 | Sales gõ một phần Tên công ty khi có nhiều Công ty. | AC-021; BR-US009-01 | Danh sách chỉ còn Công ty có Tên khớp. | T-1 |
| TC-009-02 | Sales lọc danh sách Công ty theo Ngành. | AC-022; BR-US009-02 | Danh sách chỉ còn Công ty thoả Ngành đã chọn. | T-1 |
| TC-009-03 | Sales lọc danh sách Công ty theo Loại công ty (một trong 5 loại BR-001). | AC-022; BR-US009-02; BR-001 | Danh sách chỉ còn Công ty thoả Loại công ty đã chọn. | T-1 |
| TC-009-04 | Sales lọc danh sách Công ty theo Quốc gia. | AC-022; BR-US009-02 | Danh sách chỉ còn Công ty thoả Quốc gia đã chọn. | T-1 |
| TC-009-05 | Sales lọc danh sách Công ty theo nhãn Đang theo dõi. | AC-022; BR-US009-02; REQ-501 | Danh sách chỉ còn Công ty đang mang nhãn Đang theo dõi. | T-1 |
| TC-009-06 | Sales lọc danh sách Cơ hội theo Giai đoạn. | AC-023; BR-US009-03; REQ-104 | Danh sách chỉ còn Cơ hội thuộc Giai đoạn đã chọn. | T-1 |
| TC-009-07 | Sales lọc danh sách Cơ hội theo tình trạng quá hạn Việc tiếp theo. | AC-023; BR-US009-03; REQ-109 | Danh sách chỉ còn Cơ hội có Việc tiếp theo quá hạn. | T-1 |
| TC-009-08 | Sales gõ từ khoá hoặc chọn tiêu chí không khớp Công ty/Cơ hội nào. | AC-021..023; BR-US009-01..03 | Danh sách rỗng; nội dung/hành động cụ thể chờ Q-009-03. | T-1 |

## 19. Traceability

| Chain | Evidence |
|---|---|
| `D1 → EPIC-03 → FEAT-009 → US-009 → AC-021..023 → T-1` | **[CONFIRMED]** function-decomposition; user-stories; architect handoff |
| `REQ-111 → FEAT-009 → US-009 → AC-021..023` | **[CONFIRMED]** requirement-analysis; user-stories |
| `BR-001 → US-001 → AC-022 → TC-009-03` | **[CONFIRMED]** requirement-analysis; user-stories |
| `REQ-104 → US-004 → AC-023 → TC-009-06` | **[CONFIRMED]** requirement-analysis; user-stories |
| `REQ-109 → US-008 → AC-023 → TC-009-07` | **[CONFIRMED]** requirement-analysis; user-stories |
| `REQ-501 → US-030 → AC-022 → TC-009-05` | **[CONFIRMED]** requirement-analysis; user-stories |
| `REQ-113 → T-1 → US-009` | **[CONFIRMED]** requirement-analysis; architect handoff |
| `backlog-prioritization → Could (10, acceptance-mandatory T-1) → US-009` | **[CONFIRMED]** backlog-prioritization |
| `dor-review → US-009 READY` | **[CONFIRMED]** dor-review |

## 20. Assumptions

| ID | Assumption | Rationale / status |
|---|---|---|
| A-009-01 | Bố cục và visual language tiếp tục theo hướng đã duyệt ngày 2026-08-14 cho US-001: nền sáng, card viền mảnh, bảng dữ liệu, nút chính màu tím. | **[ASSUMPTION]** Không quyết định framework hoặc component library; không đóng Q-009-01/Q-009-02/Q-009-03. |
| A-009-02 | Khi không có Công ty/Cơ hội nào thoả tiêu chí, hệ thống hiển thị trạng thái không có kết quả và cho phép Sales xoá tiêu chí. | **[ASSUMPTION]** Nội dung/hành động cụ thể chờ quyết định ở Q-009-03. |

## 21. Open Questions

| ID | Question | Owner |
|---|---|---|
| Q-009-01 | Nhiều tiêu chí lọc kết hợp thế nào (AND/OR), kể cả kết hợp từ khoá tìm kiếm với bộ lọc? | PO |
| Q-009-02 | Quản trị có quyền dùng chức năng tìm kiếm và lọc Công ty/Cơ hội hay không? | PO |
| Q-009-03 | Khi không có kết quả thoả tiêu chí, hệ thống hiển thị nội dung/hành động cụ thể gì? | PO/UX |

## 22. Definition of Ready

| Check | Status | Evidence / note |
|---|---|---|
| Actor và giá trị nghiệp vụ rõ ràng | Ready | **[CONFIRMED]** US-009; DoR review |
| Phạm vi và AC nguồn truy vết được | Ready | **[CONFIRMED]** REQ-111; AC-021..023; DoR review |
| Business rules cơ bản rõ ràng (BR-US009-01..03 rút từ AC) | Ready | **[CONFIRMED]** AC-021..023 |
| Phụ thuộc và T-1 đã nhận diện | Ready | **[CONFIRMED]** architect handoff; DoR review |
| Câu hỏi nghiệp vụ được PO chấp nhận làm mở | Ready (với 3 câu hỏi mở) | Q-009-01..03 chưa được con người quyết định tính đến 2026-08-15; `dor-review.md` vẫn ghi nhận US-009 là READY ở mức khởi động, không yêu cầu đóng hết câu hỏi mở trước khi bắt đầu. |
| Đánh giá DoR của nguồn | READY | **[CONFIRMED]** `docs/02-analysis/dor-review.md` |

**[CONFIRMED — human-approval rule]** Tài liệu dừng tại `AWAITING_SPECIFICATION_APPROVAL`; chỉ con người có thể đặt `SPECIFICATION_APPROVED`.

## 23. Technical Handoff

| Type | Constraint, touchpoint, risk or decision for Tech Lead | Evidence |
|---|---|---|
| Constraint | Tìm kiếm và lọc không được thay đổi dữ liệu Công ty hay Cơ hội (BR-US009-04); chỉ tác động góc nhìn hiển thị. | **[CONFIRMED]** REQ-111 |
| Constraint | Tìm kiếm và lọc thuộc CRM làm tay (Nhóm 1); phải hoạt động khi toàn bộ AI tắt, theo T-1. | **[CONFIRMED]** REQ-113; architect handoff |
| Touchpoint | Tiêu chí lọc Công ty (Ngành, Loại công ty, Quốc gia) và nhãn Đang theo dõi phụ thuộc định nghĩa trường của US-001 và US-030. | **[CONFIRMED]** user-stories; AC-022 |
| Touchpoint | Tiêu chí lọc Cơ hội (Giai đoạn, quá hạn Việc tiếp theo) phụ thuộc định nghĩa của US-004 và US-008. | **[CONFIRMED]** user-stories; AC-023 |
| Question | [Q-009-01] Cách kết hợp nhiều tiêu chí lọc cùng lúc chưa được PO chốt — cần quyết định nghiệp vụ trước khi thiết kế logic kết hợp. | **[OPEN QUESTION]** |
| Question | [Q-009-02] Quyền của Quản trị với chức năng tìm/lọc chưa được PO chốt. | **[OPEN QUESTION]** |
| Question | [Q-009-03] Nội dung/hành vi chính xác của trạng thái không có kết quả chưa được PO chốt. | **[OPEN QUESTION]** |

## 24. Change Log

| Version | Date | Change | Author/Approver |
|---|---|---|---|
| 1.2 | 2026-08-15 | Viết lại toàn diện theo chuẩn 24 mục US-001 v1.2, đối chiếu docs/02-analysis, chuẩn hoá SVG theo ngôn ngữ hình ảnh đã duyệt. | Codex — comprehensive refinement pass; specification approval unchanged |
| 1.1 | 2026-08-14 | Bổ sung ba SVG chi tiết cho tìm/lọc Công ty, lọc Cơ hội và trạng thái kết quả; giữ mở logic kết hợp bộ lọc. | Codex — UI pattern approved; specification approval unchanged |
| 1.0 | 2026-08-14 | Tạo specification 24 mục. | Codex / awaiting human specification approval |
