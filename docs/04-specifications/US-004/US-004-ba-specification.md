# Business Specification — US-004: Bảng 7 giai đoạn kéo-thả

## 1. Document Information

| Field | Value |
|---|---|
| Story | `US-004` — Bảng 7 giai đoạn kéo-thả |
| Feature / domain | `FEAT-004` / `D1 — CRM lõi làm tay` / `EPIC-02 — Vận hành phễu bán hàng` |
| Version | `1.2` |
| Status | `AWAITING_SPECIFICATION_APPROVAL` |
| Date | `2026-08-15` |
| Priority | Should (14) |
| Sources | `REQ-104`, `REQ-105`, `BR-004`, `BR-017`; `US-004`, `AC-010..012`; `T-1`; DoR review; architect handoff |

## 2. Purpose

**[CONFIRMED — REQ-104, REQ-105]** Xác định hành vi nghiệp vụ để Sales đổi giai đoạn của một cơ hội đã tồn tại bằng thao tác kéo-thả trên một bảng phễu bán hàng hiển thị đúng bảy giai đoạn cố định, cho phép kéo tiến, kéo lùi và nhảy cóc mà không bị hệ thống chặn.

## 3. User Story

**[CONFIRMED — US-004]** As a Sales, I want đổi giai đoạn cơ hội bằng kéo-thả, so that tôi cập nhật tiến độ nhanh mà không mở biểu mẫu.

## 4. Business Goal

**[CONFIRMED — US-004]** Sales cập nhật tiến độ cơ hội nhanh mà không phải mở biểu mẫu. **[INFERRED — REQ-105]** Cho phép kéo tiến, kéo lùi và nhảy cóc phản ánh đúng thực tế bán hàng, nơi một thương vụ có thể quay lại giai đoạn trước hoặc bỏ qua giai đoạn trung gian; việc không chặn hướng di chuyển giữ cho thao tác luôn nhanh và không cản trở Sales.

## 5. Scope

- **[CONFIRMED — REQ-104, AC-012]** Bảng Pipeline hiển thị đúng bảy giai đoạn cố định theo đúng thứ tự: Tiếp cận → Đủ điều kiện → Soạn đề xuất → Thương lượng → Thắng → Thua → Tạm dừng.
- **[CONFIRMED — AC-012]** Tên và thứ tự bảy giai đoạn không cho phép người dùng đổi.
- **[CONFIRMED — REQ-105, AC-010]** Sales kéo một cơ hội từ giai đoạn hiện tại sang giai đoạn liền kế tiếp theo (kéo tiến) và giai đoạn đổi ngay.
- **[CONFIRMED — REQ-105, AC-011]** Sales được phép kéo lùi về giai đoạn trước đó hoặc nhảy cóc thẳng sang một giai đoạn không liền kề; hệ thống cho phép cả hai, không chặn.
- **[CONFIRMED — BR-004]** Cơ hội đặt tại một trong bảy giai đoạn tại mọi thời điểm, thuộc đúng một nhóm mở (Tiếp cận, Đủ điều kiện, Soạn đề xuất, Thương lượng, Tạm dừng) hoặc đóng (Thắng, Thua).
- **[CONFIRMED — BR-017]** A-AI không có hành vi tự đổi giai đoạn trong phạm vi story này; mọi thay đổi giai đoạn trong US-004 do Sales chủ động thực hiện bằng kéo-thả.

## 6. Out of Scope

- **[CONFIRMED — REQ-103, US-003]** Tạo, sửa hoặc xoá dữ liệu cơ hội (tên, giá trị dự kiến, tháng dự kiến chốt) — thuộc US-003.
- **[CONFIRMED — REQ-106, US-005]** Hỏi và lưu hai dấu hiệu nhu cầu/ngân sách khi vào Đủ điều kiện — thuộc US-005; US-004 chỉ ghi nhận rằng việc kéo vào Đủ điều kiện có thể kích hoạt luồng đó.
- **[CONFIRMED — REQ-110; dor-review Won't-now]** Hỏi lý do khi kéo sang Thua — thuộc US-006, đã bị đưa ra ngoài phạm vi backlog hiện hành (Won't-now, không qua DoR); US-004 không hiện thực luồng này.
- **[CONFIRMED — REQ-108, US-007]** Cách một lần đổi giai đoạn được trình bày trên dòng thời gian công ty — thuộc US-007; US-004 chỉ tạo ra sự kiện đổi giai đoạn.
- **[CONFIRMED — BR-017]** Bất kỳ hành vi A-AI tự đổi giai đoạn hoặc tự chuyển Thắng/Thua, kể cả trong vòng quét công ty Đang theo dõi (US-031).

## 7. Actor / Permission

| Actor | Business permission | Evidence |
|---|---|---|
| Sales | Kéo-thả cơ hội để đổi giai đoạn: kéo tiến, kéo lùi hoặc nhảy cóc giữa bảy giai đoạn cố định. | **[CONFIRMED]** US-004; AC-010; AC-011 |
| A-AI | Không có quyền tự đổi giai đoạn cơ hội hoặc tự chuyển Thắng/Thua trong bất kỳ tình huống nào, kể cả ngoài giao diện người dùng. | **[CONFIRMED]** BR-017; T-10 |

## 8. Business Rules

| ID | Rule | Evidence |
|---|---|---|
| BR-US004-01 | Bảy giai đoạn cố định, đúng thứ tự: Tiếp cận → Đủ điều kiện → Soạn đề xuất → Thương lượng → Thắng → Thua → Tạm dừng. | **[CONFIRMED]** REQ-104; AC-012 |
| BR-US004-02 | Tên và thứ tự bảy giai đoạn không được đổi trong quá trình sử dụng. | **[CONFIRMED]** REQ-104; AC-012 |
| BR-US004-03 | Sales đổi giai đoạn cơ hội bằng thao tác kéo-thả; được phép kéo tiến, kéo lùi hoặc nhảy cóc qua nhiều giai đoạn; hệ thống không chặn bất kỳ hướng di chuyển nào trong danh mục bảy giai đoạn. | **[CONFIRMED]** REQ-105; AC-010; AC-011 |
| BR-US004-04 | Giai đoạn mở gồm Tiếp cận, Đủ điều kiện, Soạn đề xuất, Thương lượng, Tạm dừng; giai đoạn đóng gồm Thắng, Thua. | **[CONFIRMED]** BR-004 |
| BR-US004-05 | A-AI không được tự đổi giai đoạn cơ hội hoặc tự chuyển sang Thắng/Thua trong bất kỳ tình huống nào, kể cả vòng quét công ty Đang theo dõi; ranh giới này phải chặn được cả khi lệnh gọi ngoài giao diện người dùng. | **[CONFIRMED]** BR-017; T-10 |
| BR-US004-06 | Kéo cơ hội vào Đủ điều kiện có thể kích hoạt việc hỏi hai dấu hiệu nhu cầu và ngân sách (thuộc US-005); việc trả lời hay bỏ qua câu hỏi đó không chặn hoặc đảo ngược việc đổi giai đoạn đã diễn ra. | **[CONFIRMED]** REQ-106; AC-014 (US-005) |
| BR-US004-07 | Việc hỏi lý do khi kéo sang Thua (REQ-110) không thuộc phạm vi US-004; US-006 đã bị đưa ra ngoài phạm vi backlog hiện hành. | **[CONFIRMED]** dor-review (Won't-now); REQ-110 |

## 9. Business Data Dictionary

| Business data | Meaning | Applicability / rule | Evidence |
|---|---|---|---|
| Cơ hội | Thương vụ thuộc một công ty, đang được theo dõi tiến độ qua các giai đoạn. | Đối tượng được kéo-thả trên bảng Pipeline. | **[CONFIRMED]** REQ-103; US-004 |
| Giai đoạn | Vị trí hiện tại của một cơ hội trong phễu bán hàng. | Đúng một trong bảy giá trị cố định, đúng thứ tự BR-US004-01; không cho đổi tên. | **[CONFIRMED]** REQ-104; AC-012 |
| Trạng thái mở/đóng | Phân loại nghiệp vụ áp cho từng giai đoạn, không phải một trường lưu riêng biệt. | Theo BR-004: năm giai đoạn mở, hai giai đoạn đóng. | **[CONFIRMED]** BR-004 |
| Bảng Pipeline | Khu vực hiển thị bảy cột giai đoạn và toàn bộ cơ hội đang ở giai đoạn tương ứng. | Nơi Sales thực hiện thao tác kéo-thả để đổi giai đoạn. | **[CONFIRMED]** AC-012; **[INFERRED]** REQ-104, REQ-105 |

## 10. Business Flow

**BF-004-01 — Kéo tiến.** **[CONFIRMED — AC-010]** Sales xem một cơ hội đang ở giai đoạn hiện tại trên bảng Pipeline. Sales kéo thẻ cơ hội sang cột giai đoạn kế tiếp và thả. Hệ thống đổi giai đoạn của cơ hội ngay lập tức, không yêu cầu xác nhận thêm.

**BF-004-02 — Kéo lùi và nhảy cóc.** **[CONFIRMED — AC-011]** Sales kéo một cơ hội đang ở Thương lượng về lại Tiếp cận (kéo lùi), hoặc kéo thẳng sang Soạn đề xuất bỏ qua giai đoạn trung gian (nhảy cóc). Hệ thống cho phép cả hai hướng di chuyển và đổi giai đoạn ngay, không chặn theo hướng tiến hay lùi.

**BF-004-03 — Hiển thị bảng giai đoạn cố định.** **[CONFIRMED — AC-012]** Bảng Pipeline luôn hiển thị đúng bảy cột theo đúng thứ tự Tiếp cận → Đủ điều kiện → Soạn đề xuất → Thương lượng → Thắng → Thua → Tạm dừng. Sales không có thao tác nào để đổi tên cột hay đổi thứ tự cột.

**BF-004-04 — Kéo vào Đủ điều kiện kích hoạt luồng liên quan (US-005).** **[CONFIRMED — REQ-106; related US-005]** Khi Sales kéo một cơ hội vào Đủ điều kiện, giai đoạn đổi ngay theo BF-004-01; hệ thống có thể tiếp tục hỏi hai dấu hiệu nhu cầu và ngân sách thuộc US-005 sau khi đổi giai đoạn. Việc Sales trả lời hay bỏ qua câu hỏi đó không chặn và không đảo ngược việc đổi giai đoạn đã xảy ra.

## 11. Acceptance Criteria

**AC-010 — Kéo tiến**

```gherkin
Scenario: Kéo tiến
  Given một cơ hội ở giai đoạn Tiếp cận
  When tôi kéo sang Đủ điều kiện
  Then cơ hội đổi giai đoạn ngay.
```

**AC-011 — Kéo lùi và nhảy cóc**

```gherkin
Scenario: Kéo lùi và nhảy cóc
  Given một cơ hội ở Thương lượng
  When tôi kéo về Tiếp cận / hoặc nhảy thẳng sang Soạn đề xuất
  Then hệ thống cho phép, không chặn.
```

**AC-012 — Tên & thứ tự giai đoạn cố định**

```gherkin
Scenario: Tên & thứ tự giai đoạn cố định
  Given bảng giai đoạn
  Then hiển thị đúng 7 giai đoạn theo thứ tự Tiếp cận→Đủ điều kiện→Soạn đề xuất→Thương lượng→Thắng→Thua→Tạm dừng, không cho đổi tên.
```

**[CONFIRMED — user-stories]** Ba acceptance criteria trên được bảo toàn nguyên nghĩa và nguyên văn từ nguồn; US-004 không bổ sung acceptance criteria nào ngoài AC-010..012.

## 12. Screen Specification

| Screen ID | Business area | Required information / behavior | Evidence |
|---|---|---|---|
| `SCR-US004-01` | Bảng Pipeline | Hiển thị đủ bảy cột đúng tên và đúng thứ tự BR-US004-01; mỗi cột hiển thị các cơ hội hiện đang ở giai đoạn đó. | **[CONFIRMED]** AC-012; BR-US004-01, BR-US004-04 |
| `SCR-US004-02` | Chuyển giai đoạn (kéo-thả) | Minh hoạ kéo tiến, kéo lùi và nhảy cóc; thẻ cơ hội phản ánh đích đến ngay sau khi thả, không có bước xác nhận chặn thao tác. | **[CONFIRMED]** AC-010; AC-011 |
| `SCR-US004-03` | Trạng thái bảng Pipeline | Minh hoạ cột trống, đang tải và lưu thất bại có thể thử lại; các trạng thái này không làm thay đổi quyền kéo-thả của Sales và không tự thêm bước xác nhận Thắng/Thua. | **[CONFIRMED]** AC-010..012; **[OPEN QUESTION]** Q-004-01 |

## 13. Screen Design

> **UI-DESIGN UPDATE — 2026-08-15:** Wireframe BA dưới đây được viết lại theo đúng ngôn ngữ hình ảnh đã được người dùng duyệt cho US-001 v1.2 (nền `#f7f9fc`, card bo góc 14px viền `#d9e2ef`, thanh nhấn mục 5px `#695cff`, nút chính tím `#5236f5`, badge/tag bo góc theo phân loại, khối trạng thái rỗng/lỗi dùng icon tròn + tiêu đề + mô tả + nút hành động). Các asset chỉ đổi nhãn/nội dung cho đúng miền dữ liệu Cơ hội/Giai đoạn của US-004, không đổi bố cục hay quyết định framework, component library, endpoint hoặc cấu trúc triển khai.

### 13.1 Tổng quan luồng

![US-004 screen flow](./assets/screen-flow.svg)

### 13.2 `SCR-US004-01` — Bảng Pipeline 7 giai đoạn

![US-004 pipeline board](./assets/pipeline-board.svg)

### 13.3 `SCR-US004-02` — Chuyển giai đoạn (kéo-thả)

![US-004 pipeline drag](./assets/pipeline-drag.svg)

### 13.4 `SCR-US004-03` — Trạng thái bảng Pipeline

![US-004 pipeline states](./assets/pipeline-states.svg)

Các SVG chỉ minh hoạ bảy giai đoạn đã chốt (BR-US004-01) và các thao tác/ trạng thái được AC-010..012 cho phép; không tự thêm hộp thoại xác nhận Thắng/Thua vì Q-004-01 còn mở.

## 14. Screen States

| State | Visible business outcome | Screen / asset | Evidence |
|---|---|---|---|
| Cơ hội ở giai đoạn mở | Thẻ cơ hội hiển thị tại cột mở tương ứng (Tiếp cận, Đủ điều kiện, Soạn đề xuất, Thương lượng hoặc Tạm dừng). | `SCR-US004-01` | **[CONFIRMED]** BR-004; BR-US004-04 |
| Cơ hội ở giai đoạn đóng | Thẻ cơ hội hiển thị tại cột Thắng hoặc Thua. | `SCR-US004-01` | **[CONFIRMED]** BR-004; BR-US004-04 |
| Sau kéo tiến | Thẻ chuyển sang cột kế tiếp ngay, không có bước xác nhận thêm. | `SCR-US004-02` | **[CONFIRMED]** AC-010 |
| Sau kéo lùi hoặc nhảy cóc | Thẻ chuyển sang cột đích ngay; thao tác được phép dù đích không liền kề giai đoạn hiện tại. | `SCR-US004-02` | **[CONFIRMED]** AC-011 |
| Kéo vào Đủ điều kiện | Giai đoạn đổi ngay; luồng hỏi hai dấu hiệu (nếu có) thuộc US-005 và không chặn việc đổi giai đoạn đã xảy ra. | `SCR-US004-02` | **[CONFIRMED]** BR-US004-06; REQ-106 |
| Cột không có cơ hội | Cột vẫn hiển thị đúng tên và đúng vị trí thứ tự, không bị ẩn hay đổi nhãn. | `SCR-US004-03` | **[CONFIRMED]** AC-012 |
| Đang tải bảng Pipeline | Bảng hiển thị trạng thái đang tải, giữ nguyên khung bảy cột, chưa cho kéo-thả tới khi dữ liệu sẵn sàng. | `SCR-US004-03` | **[ASSUMPTION]** A-004-01 |
| Lưu thay đổi giai đoạn thất bại | Thẻ quay về cột/vị trí cũ và hiển thị lựa chọn thử lại; dữ liệu giai đoạn không bị mất đồng bộ. | `SCR-US004-03` | **[ASSUMPTION]** A-004-01 |

## 15. Validation

| Condition | Expected business response | Evidence |
|---|---|---|
| Kéo cơ hội sang một giai đoạn khác bất kỳ trong danh mục bảy giai đoạn cố định (tiến, lùi hoặc nhảy cóc) | Đổi giai đoạn ngay, không chặn theo hướng di chuyển. | **[CONFIRMED]** AC-010; AC-011 |
| Cố gắng đổi tên hoặc đổi thứ tự cột giai đoạn | Không cho phép; bảy giai đoạn giữ nguyên tên và thứ tự. | **[CONFIRMED]** AC-012 |
| Kéo cơ hội vào Đủ điều kiện | Giai đoạn đổi ngay; câu hỏi hai dấu hiệu (nếu hệ thống hỏi) thuộc US-005 và không được dùng để chặn thao tác kéo. | **[CONFIRMED]** REQ-106; AC-011; BR-US004-06 |
| Lệnh tự đổi giai đoạn hoặc tự chuyển Thắng/Thua dưới danh nghĩa hệ thống, kể cả gọi ngoài giao diện người dùng | Bị từ chối ở tầng service. | **[CONFIRMED]** BR-017; BR-US004-05; T-10 |

## 16. Dependencies

| Direction | Item | Dependency | Evidence |
|---|---|---|---|
| Upstream | US-003 / FEAT-003 | Cơ hội và giai đoạn hiện tại phải tồn tại trước khi có thể kéo-thả. | **[CONFIRMED]** US-004 (Dep: US-003); REQ-103 |
| Downstream | US-005 / FEAT-005 | Kéo vào Đủ điều kiện là điểm kích hoạt luồng hỏi hai dấu hiệu nhu cầu/ngân sách. | **[CONFIRMED]** REQ-106; US-005 (Dep: US-004) |
| Downstream | US-007 / FEAT-007 | Mỗi lần đổi giai đoạn xuất hiện trên dòng thời gian công ty, gộp cùng hoạt động và ghi chú. | **[CONFIRMED]** REQ-108; function-decomposition |
| Descoped | US-006 / FEAT-006 | Luồng hỏi lý do khi kéo sang Thua không được hiện thực; US-006 đã bị đưa ra ngoài phạm vi backlog (Won't-now). | **[CONFIRMED]** dor-review |
| Cross-cutting | US-040 / FEAT-040 | Ràng buộc chặn A-AI tự đổi giai đoạn hoặc tự Thắng/Thua áp dụng cho bảng Pipeline, kể cả ngoài UI. | **[CONFIRMED]** BR-017; architect handoff |
| Acceptance | T-1 | CRM lõi, gồm đổi giai đoạn bằng kéo-thả, hoạt động khi toàn bộ AI tắt. | **[CONFIRMED]** PRD §6; REQ-113; architect handoff |

## 17. Business-level NFR Expectations

- **[CONFIRMED — REQ-113; PRD §6]** CRM làm tay của story hoạt động khi toàn bộ AI tắt; T-1 bao gồm đổi giai đoạn cơ hội trong điều kiện này.
- **[INFERRED — AC-010, AC-011]** Giai đoạn mới của cơ hội cần phản ánh nhất quán ngay trên bảng Pipeline và ở mọi nơi khác hiển thị giai đoạn đó sau khi Sales thả thẻ, để Sales tin vào thao tác vừa thực hiện.
- **[CONFIRMED — human-approval rule]** US-004 không đặt SLA riêng ngoài kỳ vọng chất lượng chung của hệ thống; không có ngưỡng thời gian phản hồi cụ thể nào được xác nhận trong `docs/02-analysis`.

## 18. Test Scenarios

Chưa có `test-scenarios.md` riêng cho US-004. Các tình huống dưới đây là truy vết nghiệp vụ, không phải kiểm thử thực thi; chúng đóng góp vào bộ nghiệm thu **T-1** (và **T-10** cho ranh giới AI). **[CONFIRMED — architect handoff; requirement-analysis]**

| ID | Business scenario | AC / BR | Expected business result | Acceptance trace |
|---|---|---|---|---|
| TC-004-01 | Sales kéo một cơ hội từ Tiếp cận sang Đủ điều kiện. | AC-010 | Cơ hội đổi giai đoạn ngay. | T-1 |
| TC-004-02 | Sales kéo một cơ hội từ Thương lượng về Tiếp cận (kéo lùi). | AC-011 | Được phép, không chặn. | T-1 |
| TC-004-03 | Sales kéo một cơ hội từ Thương lượng nhảy thẳng sang Soạn đề xuất (nhảy cóc, bỏ qua giai đoạn Thắng/Thua/Tạm dừng nằm giữa theo thứ tự hiển thị). | AC-011 | Được phép, không chặn. | T-1 |
| TC-004-04 | Sales xem bảng Pipeline và thử tìm thao tác đổi tên hoặc đổi thứ tự cột. | AC-012; BR-US004-01, BR-US004-02 | Đúng bảy giai đoạn, đúng thứ tự, không có thao tác đổi tên/thứ tự. | T-1 |
| TC-004-05 | Sales kéo một cơ hội vào Đủ điều kiện và bỏ qua câu hỏi hai dấu hiệu do hệ thống hỏi (thuộc US-005). | BR-US004-06; REQ-106 | Giai đoạn vẫn đổi sang Đủ điều kiện; việc bỏ qua câu hỏi không chặn hay đảo ngược giai đoạn. | T-1 |
| TC-004-06 | Gửi một lệnh tự đổi giai đoạn hoặc tự chuyển Thắng/Thua dưới danh nghĩa hệ thống, không đi qua giao diện người dùng. | BR-US004-05; BR-017 | Lệnh bị từ chối ở tầng service. | T-10 |
| TC-004-07 | Toàn bộ AI bị tắt; Sales thực hiện kéo-thả đổi giai đoạn. | REQ-113 | Thao tác kéo-thả của Sales vẫn hoạt động bình thường. | T-1 |

## 19. Traceability

| Chain | Evidence |
|---|---|
| `D1 → EPIC-02 → FEAT-004 → US-004 → AC-010..012 → TC-004-01..04 (T-1)` | **[CONFIRMED]** function-decomposition; user-stories; architect handoff |
| `REQ-104, REQ-105 → FEAT-004 → US-004 → AC-010..012` | **[CONFIRMED]** requirement-analysis; user-stories |
| `BR-004 → BR-US004-04 → TC-004-01..03` | **[CONFIRMED]** requirement-analysis |
| `BR-017 → US-040 → BR-US004-05 → TC-004-06 (T-10)` | **[CONFIRMED]** requirement-analysis; architect handoff |
| `REQ-113 → T-1 → US-004 → TC-004-07` | **[CONFIRMED]** requirement-analysis; PRD §6; architect handoff |
| `REQ-106 → US-005 (Dep: US-004) → BR-US004-06 → TC-004-05` | **[CONFIRMED]** requirement-analysis; user-stories |
| `REQ-108 → US-007 (downstream) ` | **[CONFIRMED]** requirement-analysis; function-decomposition |
| `REQ-110 → US-006 (descoped, Won't-now)` | **[CONFIRMED]** dor-review |

## 20. Assumptions

| ID | Assumption | Rationale / status |
|---|---|---|
| A-004-01 | Bố cục và visual language của bảng Pipeline tiếp tục theo hướng đã duyệt ngày 2026-08-14 cho US-001 v1.2: nền sáng, card viền mảnh, thanh nhấn mục màu tím, khối trạng thái rỗng/lỗi dùng icon tròn + tiêu đề + mô tả + nút hành động. | **[ASSUMPTION]** Không quyết định framework hoặc component library; không đóng Q-004-01. |

## 21. Open Questions

| ID | Question | Owner / impact |
|---|---|---|
| Q-004-01 | Có cần một bước hỏi xác nhận riêng (ví dụ hộp thoại) khi Sales kéo cơ hội vào Thắng hoặc Thua hay không? `docs/02-analysis` không nêu yêu cầu này; AC-010..012 chỉ yêu cầu đổi giai đoạn ngay và không chặn. | **[OPEN QUESTION]** PO xác nhận; nếu có, cần bổ sung AC mới trước khi thay đổi màn hình `SCR-US004-02`. |

## 22. Definition of Ready

| Check | Status | Evidence / note |
|---|---|---|
| Actor và giá trị nghiệp vụ rõ ràng | Ready | **[CONFIRMED]** US-004; dor-review |
| Phạm vi và AC nguồn truy vết được | Ready | **[CONFIRMED]** REQ-104, REQ-105; AC-010..012; dor-review |
| BR-004 và bảy giai đoạn cố định rõ ràng | Ready | **[CONFIRMED]** requirement-analysis; AC-012 |
| Phụ thuộc và T-1 đã nhận diện | Ready | **[CONFIRMED]** architect handoff; dor-review |
| Câu hỏi nghiệp vụ được quyết định hoặc được PO chấp nhận làm mở | Ready (1 câu hỏi còn mở, không chặn AC hiện có) | Q-004-01 chưa được quyết định trong `docs/02-analysis`; không ảnh hưởng AC-010..012. |
| Đánh giá DoR của nguồn | READY | **[CONFIRMED]** `docs/02-analysis/dor-review.md` |

**[CONFIRMED — human-approval rule]** Tài liệu dừng tại `AWAITING_SPECIFICATION_APPROVAL`; chỉ con người có thể đặt `SPECIFICATION_APPROVED`.

## 23. Technical Handoff

| Type | Constraint, touchpoint, risk or decision for Tech Lead | Evidence |
|---|---|---|
| Constraint | `AutomationPolicyGuard` (hoặc cơ chế policy-guard trung tâm tương đương) phải chặn A-AI tự đổi giai đoạn cơ hội hoặc tự chuyển Thắng/Thua, kể cả khi gọi ngoài giao diện người dùng. | **[CONFIRMED]** BR-017; T-10; architect handoff (ARQ-1) |
| Touchpoint | Kéo cơ hội vào Đủ điều kiện là điểm kích hoạt luồng hỏi hai dấu hiệu của US-005; không dùng luồng đó để chặn hay đảo ngược việc đổi giai đoạn của US-004. | **[CONFIRMED]** REQ-106; user-stories |
| Touchpoint | Mỗi lần đổi giai đoạn là một sự kiện cần xuất hiện trên dòng thời gian công ty; cách trình bày thuộc trách nhiệm của US-007, không phải US-004. | **[CONFIRMED]** REQ-108; function-decomposition |
| Acceptance constraint | Thao tác kéo-thả của Sales trong US-004 vẫn hoạt động khi toàn bộ AI bị tắt, theo T-1. | **[CONFIRMED]** REQ-113; PRD §6 |
| Question | [OPEN QUESTION — Q-004-01] Cần xác nhận có yêu cầu bước xác nhận riêng khi kéo vào Thắng/Thua hay không, trước khi thiết kế tương tác kéo-thả chi tiết. | **[OPEN QUESTION]** PO |

## 24. Change Log

| Version | Date | Change | Author/Approver |
|---|---|---|---|
| 1.2 | 2026-08-15 | Viết lại toàn diện theo chuẩn 24 mục US-001 v1.2, đối chiếu docs/02-analysis, chuẩn hoá SVG theo ngôn ngữ hình ảnh đã duyệt. | Codex — comprehensive refinement pass; specification approval unchanged |
| 1.1 | 2026-08-14 | Bổ sung ba SVG chi tiết cho bảng 7 giai đoạn, thao tác kéo và trạng thái phục hồi; không tự thêm xác nhận Thắng/Thua. | Codex — UI pattern approved; specification approval unchanged |
| 1.0 | 2026-08-14 | Tạo specification 24 mục cho US-004. | Codex / awaiting human specification approval |
