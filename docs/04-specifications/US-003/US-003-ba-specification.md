# Business Specification — US-003: Quản lý Cơ hội

## 1. Document Information

| Field | Value |
|---|---|
| Story | `US-003` — Quản lý Cơ hội |
| Feature / domain | `FEAT-003` / `D1 — CRM lõi làm tay` / `EPIC-01 — Quản lý thực thể CRM` |
| Version | `1.2` |
| Status | `AWAITING_SPECIFICATION_APPROVAL` |
| Date | `2026-08-15` |
| Priority | Must (17) |
| Sources | `REQ-103`; `BR-003`, `BR-004`; `US-003`, `AC-008`, `AC-009`; `T-1`; `dor-review.md`; `architect-handoff.md` |

## 2. Purpose

**[CONFIRMED — US-003, REQ-103]** Xác định hành vi nghiệp vụ để Sales tạo và quản lý Cơ hội thuộc một Công ty, phục vụ theo dõi từng thương vụ. **[CONFIRMED — AC-008, AC-009]** Trong phạm vi nguồn hiện có, "quản lý" được xác nhận là tạo Cơ hội gắn đúng Công ty và cho phép nhiều Cơ hội cùng tồn tại dưới một Công ty; hành vi sửa/xóa Cơ hội chưa có tiêu chí chấp nhận riêng (xem Q-003-01).

## 3. User Story

**[CONFIRMED — US-003]** As a Sales, I want tạo và quản lý cơ hội thuộc một công ty, so that tôi theo dõi từng thương vụ.

## 4. Business Goal

**[CONFIRMED — US-003]** Sales theo dõi từng thương vụ trong đúng ngữ cảnh Công ty mà thương vụ đó thuộc về, để không nhầm lẫn thương vụ giữa các khách hàng. **[INFERRED — BR-003, AC-009]** Cho phép nhiều Cơ hội cùng thuộc một Công ty giúp Sales theo dõi song song các thương vụ riêng biệt của cùng một khách hàng.

## 5. Scope

- **[CONFIRMED — REQ-103, US-003, AC-008]** Sales tạo Cơ hội tại ngữ cảnh một Công ty đang thao tác.
- **[CONFIRMED — AC-008]** Khi tạo, Sales ghi nhận Tên cơ hội, Giá trị dự kiến, Tháng dự kiến chốt và Giai đoạn.
- **[CONFIRMED — AC-008; BR-US003-01]** Cơ hội vừa tạo được gắn vào đúng Công ty đang thao tác.
- **[CONFIRMED — AC-009; BR-003]** Một Công ty có thể có nhiều Cơ hội cùng tồn tại; tạo Cơ hội thứ hai không thay thế hay gỡ Cơ hội đã có.
- **[CONFIRMED — BR-004]** Giai đoạn ghi nhận khi tạo phải thuộc tập bảy giá trị đã phân loại mở/đóng của BR-004.

## 6. Out of Scope

- **[CONFIRMED — user-stories.md, REQ-104/105]** Đổi giai đoạn bằng kéo-thả, thứ tự hoặc tên bảy giai đoạn: thuộc `US-004`.
- **[CONFIRMED — REQ-106, user-stories.md]** Dấu hiệu nhu cầu/ngân sách khi vào Đủ điều kiện: thuộc `US-005`.
- **[CONFIRMED — REQ-109, user-stories.md]** Việc tiếp theo và ngày hạn của Cơ hội: thuộc `US-008`.
- **[CONFIRMED — REQ-107/108/111/112, user-stories.md]** Hoạt động, dòng thời gian, tìm kiếm/lọc và màn hình tổng quan: thuộc `US-007`, `US-009`, `US-010`.
- **[CONFIRMED — BR-017, US-040]** Mọi giới hạn automation đối với Cơ hội (không tự đổi giai đoạn, không tự Thắng/Thua, không tự sửa tiền) là ràng buộc xuyên suốt của `US-040`, không được thiết lập lại trong US-003.
- **[OPEN QUESTION — Q-003-01]** Hành vi sửa hoặc xóa Cơ hội: nguồn US-003 không có tiêu chí chấp nhận riêng cho hai thao tác này, nên chưa được đưa vào phạm vi đã xác nhận (không phải quyết định "không làm", mà là "chưa được nguồn xác nhận").

## 7. Actor / Permission

| Actor | Business permission | Evidence |
|---|---|---|
| Sales | Tạo Cơ hội tại một Công ty; ghi nhận Tên, Giá trị dự kiến, Tháng dự kiến chốt, Giai đoạn theo AC-008; có nhiều Cơ hội cùng tồn tại dưới một Công ty theo AC-009. | **[CONFIRMED]** US-003; AC-008, AC-009 |
| Quản trị (Admin) | Quyền thao tác Cơ hội cụ thể của Admin chưa được nguồn US-003 xác định. | **[OPEN QUESTION — Q-003-02]** |
| A-AI | Không phải actor thực hiện hành vi trong phạm vi story này; nếu automation ở use case khác chạm Cơ hội, không được tự đổi giai đoạn hoặc giá trị. | **[CONFIRMED]** US-003 nêu Sales là actor duy nhất; BR-017; REQ-113 |

## 8. Business Rules

| ID | Rule | Evidence |
|---|---|---|
| BR-003 | Một Công ty có thể có nhiều Cơ hội. | **[CONFIRMED]** requirement-analysis.md; AC-009 |
| BR-004 | Cơ hội mở là một trong: Tiếp cận, Đủ điều kiện, Soạn đề xuất, Thương lượng, Tạm dừng; Cơ hội đóng là Thắng hoặc Thua. | **[CONFIRMED]** requirement-analysis.md |
| BR-US003-01 | Cơ hội tạo theo AC-008 phải được gắn vào đúng Công ty mà Sales đang thao tác. | **[CONFIRMED]** AC-008 |
| BR-US003-02 | Một Công ty có thể giữ đồng thời Cơ hội đã có và Cơ hội mới tạo; tạo mới không thay thế hay gỡ Cơ hội cũ. | **[CONFIRMED]** AC-009; BR-003 |
| BR-US003-03 | US-003 không bổ sung quy tắc đổi giai đoạn; Giai đoạn ghi nhận khi tạo chỉ cần thuộc đúng phân loại mở/đóng của BR-004. | **[CONFIRMED]** BR-004; function-decomposition.md (FEAT-003 tách khỏi FEAT-004) |
| BR-US003-04 | Luồng CRM thủ công của Sales trong story này không phụ thuộc AI; vẫn hoạt động khi toàn bộ AI tắt. | **[CONFIRMED]** REQ-113; T-1 |
| BR-US003-05 | Nếu automation ở use case khác chạm tới Cơ hội, automation không được tự đổi giai đoạn hoặc tự sửa giá trị Cơ hội, kể cả khi gọi ngoài giao diện. | **[CONFIRMED]** BR-017; US-040; architect-handoff.md (AR-1) |

## 9. Business Data Dictionary

| Business data | Meaning | Applicability / rule | Evidence |
|---|---|---|---|
| Công ty | Khách hàng doanh nghiệp là ngữ cảnh sở hữu Cơ hội. | Một Công ty có thể có nhiều Cơ hội (BR-003). | **[CONFIRMED]** BR-003; AC-008, AC-009 |
| Cơ hội | Một thương vụ đang được Sales theo dõi, gắn với đúng một Công ty. | Được tạo dưới ngữ cảnh Công ty theo AC-008. | **[CONFIRMED]** PRD §2; US-003 |
| Tên Cơ hội | Tên nhận biết thương vụ. | Sales nhập khi tạo theo AC-008; điều kiện bắt buộc/định dạng chưa xác định. | **[CONFIRMED]** AC-008; **[OPEN QUESTION]** Q-003-03 |
| Giá trị dự kiến | Giá trị tiền dự kiến của thương vụ. | Sales nhập khi tạo theo AC-008; đơn vị tiền tệ chưa xác định. | **[CONFIRMED]** REQ-103; AC-008; **[OPEN QUESTION]** Q-003-05 |
| Tháng dự kiến chốt | Tháng dự kiến hoàn tất thương vụ. | Sales nhập khi tạo theo AC-008; quy ước hiển thị/múi giờ chưa xác định. | **[CONFIRMED]** REQ-103; AC-008; **[OPEN QUESTION]** Q-003-05 |
| Giai đoạn | Vị trí nghiệp vụ hiện tại của Cơ hội. | Phải thuộc bảy giá trị của BR-004 khi tạo. | **[CONFIRMED]** REQ-103; BR-004 |
| Phân loại mở / đóng | Phân loại nghiệp vụ của giai đoạn. | Mở: Tiếp cận, Đủ điều kiện, Soạn đề xuất, Thương lượng, Tạm dừng. Đóng: Thắng, Thua. | **[CONFIRMED]** BR-004 |

## 10. Business Flow

**BF-003-01 — Tạo Cơ hội tại một Công ty.** **[CONFIRMED — AC-008]** Sales đang ở màn hình một Công ty và tạo Cơ hội mới, nhập Tên cơ hội, Giá trị dự kiến, Tháng dự kiến chốt và Giai đoạn. **[CONFIRMED — AC-008; BR-004]** Giai đoạn được ghi nhận theo đúng phân loại mở/đóng đã xác định. **[CONFIRMED — AC-008; BR-US003-01]** Cơ hội hoàn tất được gắn vào đúng Công ty đang thao tác.

**BF-003-02 — Tạo Cơ hội thứ hai dưới cùng Công ty.** **[CONFIRMED — AC-009]** Một Công ty đã có sẵn một Cơ hội. **[CONFIRMED — AC-009; BR-003]** Sales tạo thêm một Cơ hội thứ hai tại đúng Công ty đó. **[CONFIRMED — AC-009; BR-US003-02]** Cả hai Cơ hội cùng tồn tại dưới Công ty; không có Cơ hội nào bị thay thế hay gỡ bỏ.

## 11. Acceptance Criteria

**AC-008 — Tạo Cơ hội**

```gherkin
Scenario: Tạo cơ hội
  Given tôi ở màn hình một công ty
  When tôi tạo cơ hội với tên, giá trị dự kiến, tháng dự kiến chốt, giai đoạn
  Then cơ hội được gắn vào công ty đó.
```

**AC-009 — Nhiều Cơ hội một Công ty**

```gherkin
Scenario: Nhiều cơ hội một công ty
  Given công ty đã có một cơ hội
  When tôi tạo cơ hội thứ hai
  Then cả hai cùng tồn tại dưới công ty.
```

**[CONFIRMED — user-stories.md]** Hai acceptance criteria trên được giữ nguyên văn từ nguồn; US-003 không có thêm AC nào khác trong `docs/02-analysis`.

## 12. Screen Specification

| Screen ID | Business area | Required information / behavior | Evidence |
|---|---|---|---|
| `SCR-US003-01` | Cơ hội của Công ty | Hiển thị các Cơ hội cùng tồn tại dưới Công ty đang thao tác (Tên, Giá trị dự kiến, Tháng dự kiến chốt, Giai đoạn) và lối vào tạo Cơ hội mới. | **[CONFIRMED]** AC-008; AC-009; BR-003 |
| `SCR-US003-02` | Tạo Cơ hội | Giữ rõ ngữ cảnh Công ty đang thao tác và thu thập Tên cơ hội, Giá trị dự kiến, Tháng dự kiến chốt, Giai đoạn. | **[CONFIRMED]** AC-008; REQ-103 |
| `SCR-US003-03` | Trạng thái kết quả | Thể hiện empty state, kết quả sau khi tạo Cơ hội đầu tiên và Cơ hội thứ hai, đều gắn đúng Công ty; không suy diễn sửa/xóa/chi tiết. | **[CONFIRMED]** AC-008; AC-009; **[OPEN QUESTION]** Q-003-01 |

## 13. Screen Design

> **UI-DESIGN UPDATE — 2026-08-15:** Ba wireframe dưới đây chuẩn hoá lại theo đúng ngôn ngữ hình ảnh đã được người dùng duyệt ngày 2026-08-14 cho US-001 (nền `#f7f9fc`, card bo góc 14px viền `#d9e2ef`, thanh nhấn mục 5px `#695cff`, nút chính tím `#5236f5`, nút phụ viền `#bfcee0`, bảng có header uppercase `#60718f`, khối trạng thái rỗng/lỗi dùng icon tròn + tiêu đề + mô tả + hành động). Chỉ nhãn, dữ liệu minh hoạ và màu badge giai đoạn được đổi cho đúng miền Cơ hội; không đổi bố cục hay framework.

### 13.1 Tổng quan luồng

![US-003 screen flow](./assets/screen-flow.svg)

### 13.2 `SCR-US003-01` — Cơ hội của Công ty

![US-003 opportunity list](./assets/opportunity-list.svg)

### 13.3 `SCR-US003-02` — Tạo Cơ hội

![US-003 opportunity create](./assets/opportunity-create.svg)

### 13.4 `SCR-US003-03` — Trạng thái kết quả

![US-003 opportunity states](./assets/opportunity-states.svg)

**[ASSUMPTION — A-003-01]** Visual language kế thừa nguyên mẫu đã duyệt cho US-001. Asset không đưa sửa/xóa/chi tiết thành phạm vi đã xác nhận và không tự đặt validation, tiền tệ hoặc quy ước tháng đang mở tại Q-003-03..05.

## 14. Screen States

| State | Visible business outcome | Screen / asset | Evidence |
|---|---|---|---|
| Tạo Cơ hội trong ngữ cảnh Công ty | Sales thấy rõ Công ty sở hữu trong khi nhập các thông tin AC-008. | `SCR-US003-02` | **[CONFIRMED]** AC-008 |
| Cơ hội đầu tiên đã được tạo | Cơ hội xuất hiện dưới đúng Công ty. | `SCR-US003-01`, `SCR-US003-03` | **[CONFIRMED]** AC-008; BR-US003-01 |
| Công ty có nhiều Cơ hội | Cơ hội đã có và Cơ hội thứ hai cùng tồn tại, không cái nào bị thay thế. | `SCR-US003-01`, `SCR-US003-03` | **[CONFIRMED]** AC-009; BR-003 |
| Giai đoạn thuộc phân loại mở | Giai đoạn hiển thị đúng một trong năm giá trị mở của BR-004. | `SCR-US003-01`, `SCR-US003-02` | **[CONFIRMED]** BR-004 |
| Giai đoạn thuộc phân loại đóng | Giai đoạn hiển thị đúng Thắng hoặc Thua theo BR-004. | `SCR-US003-01`, `SCR-US003-02` | **[CONFIRMED]** BR-004 |
| Chưa có Cơ hội | Empty state giữ ngữ cảnh Công ty và dẫn tới tạo Cơ hội mới. | `SCR-US003-03` | **[ASSUMPTION]** A-003-01 |
| Không tải được danh sách Cơ hội | Giữ ngữ cảnh Công ty và cho phép thử lại thao tác tải. | `SCR-US003-03` | **[ASSUMPTION]** A-003-01 |
| Đang tải danh sách hoặc đang lưu | Hiển thị skeleton/khoá hành động trong lúc xử lý, giữ nguyên ngữ cảnh Công ty. | `SCR-US003-03` | **[ASSUMPTION]** A-003-01 |

## 15. Validation

| Condition | Expected business response | Evidence |
|---|---|---|
| Sales tạo Cơ hội ở ngữ cảnh một Công ty với đủ bốn thông tin AC-008 | Cơ hội được gắn vào đúng Công ty đó. | **[CONFIRMED]** AC-008 |
| Công ty đã có một Cơ hội và Sales tạo Cơ hội thứ hai | Hai Cơ hội cùng tồn tại dưới Công ty, không cái nào bị gỡ. | **[CONFIRMED]** AC-009; BR-003 |
| Giai đoạn được ghi nhận khi tạo | Phải thuộc đúng một trong bảy giá trị mở/đóng của BR-004. | **[CONFIRMED]** BR-004 |
| Giai đoạn nằm ngoài bảy giá trị của BR-004 | Nguồn chưa nêu phản hồi nghiệp vụ cụ thể. | **[OPEN QUESTION]** Q-003-04 |
| Tên, Giá trị dự kiến hoặc Tháng dự kiến chốt bị bỏ trống hoặc sai định dạng | Nguồn chưa xác định điều kiện bắt buộc, định dạng, giới hạn hay phản hồi lỗi tương ứng. | **[OPEN QUESTION]** Q-003-03 |

## 16. Dependencies

| Direction | Item | Dependency | Evidence |
|---|---|---|---|
| Upstream | US-001 / FEAT-001 | Cung cấp ngữ cảnh Công ty để Sales tạo Cơ hội dưới Công ty đó. | **[CONFIRMED]** US-003 "Dep: US-001"; AC-008 |
| Downstream | US-004 / FEAT-004 | Đổi giai đoạn Cơ hội bằng kéo-thả thao tác trên dữ liệu Cơ hội được tạo ở US-003. | **[CONFIRMED]** US-004 "Dep: US-003"; function-decomposition.md |
| Downstream | US-008 / FEAT-008 | Việc tiếp theo và ngày hạn được gắn vào Cơ hội đã tạo ở US-003. | **[CONFIRMED]** US-008 "Dep: US-003"; function-decomposition.md |
| Downstream | US-009 / FEAT-009 | Lọc Cơ hội theo giai đoạn/quá hạn thao tác trên dữ liệu Cơ hội của US-003. | **[CONFIRMED]** US-009 "Dep: US-001, US-003" |
| Downstream | US-010 / FEAT-010 | Màn hình tổng quan thống kê Cơ hội & giá trị theo giai đoạn dựa trên dữ liệu US-003. | **[CONFIRMED]** US-010 "Dep: US-001, US-003, US-008" |
| Cross-cutting | REQ-113 | CRM thủ công, gồm hành vi trong story này, tiếp tục hoạt động khi toàn bộ AI tắt. | **[CONFIRMED]** REQ-113; T-1 |
| Cross-cutting | BR-017 / US-040 | Bảo toàn ranh giới automation đối với giai đoạn và giá trị Cơ hội, kể cả khi gọi ngoài giao diện. | **[CONFIRMED]** BR-017; US-040; architect-handoff.md (AR-1) |
| Acceptance | T-1 | CRM lõi, gồm tạo Cơ hội, thuộc bộ nghiệm thu T-1. | **[CONFIRMED]** architect-handoff.md traceability matrix |

## 17. Business-level NFR Expectations

- **[CONFIRMED — REQ-113; T-1]** Sales vẫn thực hiện được luồng CRM thủ công của US-003 khi toàn bộ AI tắt.
- **[CONFIRMED — REQ-704; architect-handoff.md NFR-3]** Dữ liệu CRM, gồm Cơ hội, được kỳ vọng bền qua khởi động lại của sản phẩm; đây là kỳ vọng cấp hệ thống, không bổ sung quy tắc nghiệp vụ riêng cho US-003.
- **[OPEN QUESTION — Q-003-05]** Nguồn chưa nêu kỳ vọng về thời gian phản hồi, quy tắc tiền tệ hoặc múi giờ cho Tháng dự kiến chốt.

## 18. Test Scenarios

Chưa có `test-scenarios.md` riêng cho US-003. Các tình huống dưới đây là truy vết nghiệp vụ, không phải kiểm thử thực thi; chúng đóng góp vào bộ nghiệm thu **T-1**. **[CONFIRMED — architect-handoff.md; PRD §6]**

| ID | Business scenario | AC / BR | Expected business result | Acceptance trace |
|---|---|---|---|---|
| TC-US003-01 | Sales ở màn hình một Công ty và tạo Cơ hội với Tên, Giá trị dự kiến, Tháng dự kiến chốt và Giai đoạn. | AC-008; BR-US003-01 | Cơ hội được gắn vào đúng Công ty đó. | T-1 |
| TC-US003-02 | Một Công ty đã có một Cơ hội; Sales tạo Cơ hội thứ hai dưới cùng Công ty. | AC-009; BR-003; BR-US003-02 | Hai Cơ hội cùng tồn tại dưới Công ty, không cái nào bị gỡ. | T-1 |
| TC-US003-03 | Sales tạo Cơ hội với Giai đoạn thuộc tập mở của BR-004 (ví dụ Tiếp cận). | BR-004; BR-US003-03 | Cơ hội được ghi nhận đúng phân loại mở. | T-1 |
| TC-US003-04 | Sales tạo Cơ hội với Giai đoạn thuộc tập đóng của BR-004 (ví dụ Thắng). | BR-004; BR-US003-03 | Cơ hội được ghi nhận đúng phân loại đóng. | T-1 |
| TC-US003-05 | Sales tạo và xem Cơ hội trong khi toàn bộ AI đang tắt. | REQ-113; BR-US003-04 | Luồng CRM thủ công của US-003 vẫn hoạt động đầy đủ. | T-1 |

## 19. Traceability

| Chain | Evidence |
|---|---|
| `D1 → EPIC-01 → FEAT-003 → US-003 → AC-008, AC-009 → T-1` | **[CONFIRMED]** function-decomposition.md; user-stories.md; architect-handoff.md |
| `REQ-103 → FEAT-003 → US-003 → AC-008 → TC-US003-01` | **[CONFIRMED]** requirement-analysis.md; function-decomposition.md; user-stories.md |
| `BR-003 → US-003 → AC-009 → BR-US003-02 → TC-US003-02` | **[CONFIRMED]** requirement-analysis.md; architect-handoff.md |
| `BR-004 → US-003 → BR-US003-03 → TC-US003-03, TC-US003-04` | **[CONFIRMED]** requirement-analysis.md; §8, §18 |
| `REQ-113 → BR-US003-04 → TC-US003-05 → T-1` | **[CONFIRMED]** requirement-analysis.md; §17, §18 |
| `BR-017 → US-040 → BR-US003-05` | **[CONFIRMED]** requirement-analysis.md; architect-handoff.md (AR-1) |
| `US-003 → US-004, US-008, US-009, US-010` (downstream) | **[CONFIRMED]** user-stories.md "Dep:" fields |

## 20. Assumptions

| ID | Assumption | Rationale / status |
|---|---|---|
| A-003-01 | Visual language dùng nguyên mẫu đã được duyệt cho US-001 ngày 2026-08-14; empty/loading/recoverable-error là biểu đạt UX và không thêm quy tắc nghiệp vụ. | **[ASSUMPTION]** Không thay đổi AC-008/AC-009 hoặc đóng Q-003-01..05. |

## 21. Open Questions

| ID | Question | Owner / impact |
|---|---|---|
| Q-003-01 | "Quản lý" trong US-003 có bao gồm sửa và xóa Cơ hội hay cần được tách/thêm tiêu chí chấp nhận riêng? | PO; xác định phạm vi thao tác ngoài AC-008/AC-009 hiện có. |
| Q-003-02 | Admin có được tạo hoặc xem Cơ hội giống Sales không? | PO; làm rõ quyền nghiệp vụ (song song Q-003-02 chưa có quyết định tương đương "human decision 2026-08-14" của US-001). |
| Q-003-03 | Tên, Giá trị dự kiến và Tháng dự kiến chốt có điều kiện bắt buộc, định dạng, giới hạn hay ý nghĩa hợp lệ nào ngoài nội dung AC-008 không? | PO; tránh tự đặt validation. |
| Q-003-04 | Khi Giai đoạn không thuộc tập bảy giá trị của BR-004, phản hồi nghiệp vụ cần là gì? | PO; làm rõ cách bảo toàn BR-004. |
| Q-003-05 | Giá trị dự kiến dùng đơn vị tiền tệ nào, và Tháng dự kiến chốt cần được hiểu/trình bày theo quy ước nào? | PO; làm rõ ý nghĩa dữ liệu mà không tự đặt quy ước. |

## 22. Definition of Ready

| Check | Status | Evidence / note |
|---|---|---|
| Actor và giá trị nghiệp vụ rõ ràng | Ready | **[CONFIRMED]** US-003; dor-review.md |
| Phạm vi và AC nguồn truy vết được | Ready | **[CONFIRMED]** REQ-103; AC-008, AC-009; dor-review.md |
| BR-003, BR-004 và quan hệ Công ty–Cơ hội rõ ràng | Ready | **[CONFIRMED]** requirement-analysis.md; AC-008, AC-009 |
| Phụ thuộc và T-1 đã nhận diện | Ready | **[CONFIRMED]** architect-handoff.md; dor-review.md |
| Priority và backlog được phê duyệt | Ready | Must (17); dor-review.md xác nhận US-003 READY và backlog đã được PO phê duyệt 2026-08-13. |
| Câu hỏi không làm thay đổi AC-008/AC-009 được ghi nhận | Ready with questions | Q-003-01..05 vẫn mở; docs/02-analysis không cung cấp thêm dữ kiện để đóng. |
| Business specification được người có thẩm quyền phê duyệt | Pending | Gate 1; trạng thái tài liệu giữ `AWAITING_SPECIFICATION_APPROVAL`. |

**[CONFIRMED — human-approval rule]** Tài liệu dừng tại `AWAITING_SPECIFICATION_APPROVAL`; chỉ con người có thể đặt `SPECIFICATION_APPROVED`.

## 23. Technical Handoff

| Type | Constraint, touchpoint, risk or decision for Tech Lead | Evidence |
|---|---|---|
| Constraint | Bảo toàn việc Cơ hội được gắn đúng Công ty (BR-US003-01) và một Công ty có thể có nhiều Cơ hội (BR-003). | **[CONFIRMED]** AC-008; BR-003 |
| Constraint | Bảo toàn phân loại giai đoạn mở/đóng của BR-004; không thêm quy tắc đổi giai đoạn vào phạm vi US-003. | **[CONFIRMED]** BR-004; function-decomposition.md |
| Constraint | Không tạo phụ thuộc vào AI cho thao tác CRM thủ công của Sales trong story này. | **[CONFIRMED]** REQ-113; T-1 |
| Constraint | Mọi automation có chạm Cơ hội (ở use case khác) phải tuân guardrail tầng service của US-040; không có đường vòng qua giao diện. | **[CONFIRMED]** BR-017; US-040; architect-handoff.md (AR-1) |
| Touchpoint | Story cần ngữ cảnh Công ty còn hoạt động (chưa xóa mềm) từ US-001 trước khi tạo Cơ hội. | **[CONFIRMED]** US-003 dependency; AC-008 |
| Touchpoint | Giai đoạn Cơ hội là dữ liệu nền cho US-004 (kéo-thả), US-005 (chốt Đủ điều kiện), US-008 (Việc tiếp theo), US-009/US-010 (lọc & tổng quan). | **[CONFIRMED]** function-decomposition.md; user-stories.md "Dep:" fields |
| Risk | Nếu quan hệ Cơ hội–Công ty không nhất quán, Sales có thể theo dõi thương vụ dưới sai khách hàng. | **[INFERRED]** AC-008, AC-009 |
| Risk | Nếu phân loại mở/đóng của Giai đoạn không nhất quán, các story downstream (US-004, US-008, US-010) có thể đọc sai trạng thái nghiệp vụ của Cơ hội. | **[INFERRED]** BR-004 |
| Question | Tech Lead không nên tự suy diễn quy tắc bắt buộc/định dạng/tiền tệ cho Tên, Giá trị dự kiến, Tháng dự kiến chốt hoặc phản hồi khi Giai đoạn ngoài BR-004; chuyển Q-003-01..05 lại cho PO thay vì tự quyết. | **[CONFIRMED]** anti-hallucination rule; §21 |

## 24. Change Log

| Version | Date | Change | Author/Approver |
|---|---|---|---|
| 1.2 | 2026-08-15 | Viết lại toàn diện theo chuẩn 24 mục US-001 v1.2, đối chiếu docs/02-analysis, chuẩn hoá SVG theo ngôn ngữ hình ảnh đã duyệt. | Codex — comprehensive refinement pass; specification approval unchanged |
| 1.1 | 2026-08-14 | Bổ sung ba SVG chi tiết cho danh sách, tạo và trạng thái Cơ hội; loại cách diễn đạt UI có thể suy ra sửa/xóa/chi tiết khi Q-003-01 còn mở. | Codex — UI pattern approved; specification approval unchanged |
| 1.0 | 2026-08-14 | Chuẩn hóa business specification 24 mục cho US-003; bảo toàn REQ-103, BR-003/004, AC-008/009 và T-1. | Codex / awaiting human specification approval |
