# Business Specification — US-044: Bản dựng production giả lập, cấu hình môi trường, dữ liệu bền và khởi động một lệnh

## 1. Document Information

| Field | Value |
|---|---|
| Story | US-044 — Bản dựng production giả lập, cấu hình môi trường, dữ liệu bền và khởi động một lệnh |
| Feature | FEAT-044 |
| Version | 1.0 |
| Date | 2026-08-14 |
| Status | AWAITING_SPECIFICATION_APPROVAL |
| Primary actor | Vận hành/Ops |
| Sources | REQ-704; US-044 và AC-082..AC-085; FEAT-044; kiến trúc/handoff liên quan |
| Evidence convention | `CONFIRMED` = nguồn nói rõ; `INFERRED` = suy ra có dẫn chiếu; `ASSUMPTION` = chờ phê duyệt; `OPEN QUESTION` = Q-xxx cần quyết định |

## 2. Purpose

`[CONFIRMED — REQ-704, US-044]` Đặc tả này xác định kết quả vận hành mà Ops cần đạt để sản phẩm chạy như một môi trường production on-premise giả lập: không còn đặc tính môi trường phát triển, cấu hình vận hành được lấy từ biến môi trường, dữ liệu còn nguyên sau khi khởi động lại và hệ thống khởi động được bằng một lệnh với đầu ra vận hành có thể xem.

Đây là business specification cho vận hành/NFR. Tài liệu không quy định công nghệ, cấu trúc mã, cơ chế lưu trữ, lệnh cụ thể hay cách triển khai.

## 3. User Story

`[CONFIRMED — US-044]` **Là** người vận hành, **tôi muốn** sản phẩm chạy ở cấu hình production giả lập **để** giám khảo và người dùng vào được môi trường giống thật.

## 4. Business Goal

| Goal | Outcome observable | Evidence |
|---|---|---|
| Cung cấp môi trường trình diễn đáng tin cậy | Sản phẩm chạy không phụ thuộc đặc tính dev và có thể được khởi động nhất quán. | `CONFIRMED — REQ-704, AC-082, AC-085` |
| Bảo vệ tính liên tục của dữ liệu | Dữ liệu có trước khi khởi động lại vẫn còn nguyên sau đó. | `CONFIRMED — REQ-704, AC-084` |
| Tách cấu hình khỏi mã nguồn | Khóa dịch vụ ngoài, thông tin kết nối DB và chu kỳ vòng quét được cấu hình qua biến môi trường. | `CONFIRMED — REQ-704, AC-083` |

## 5. Scope

- `[CONFIRMED — AC-082]` Bản dựng nộp không có dev server, hot reload hoặc chế độ gỡ lỗi.
- `[CONFIRMED — AC-083]` Ba nhóm cấu hình: khóa dịch vụ ngoài, chuỗi kết nối DB và chu kỳ vòng quét, nằm ở biến môi trường thay vì trong mã.
- `[CONFIRMED — AC-084]` Dữ liệu đã có trong DB vẫn còn nguyên khi tiến trình được khởi động lại.
- `[CONFIRMED — AC-085]` Ops khởi động hệ thống bằng một lệnh và xem được đầu ra vận hành của lần khởi động.
- `[INFERRED — REQ-704, AS-03, US-042]` Môi trường này là nơi bộ dữ liệu mẫu do US-042 chuẩn bị có thể được sử dụng sau khi sản phẩm đã chạy.

## 6. Out of Scope

- `[CONFIRMED — user-stories.md, US-046]` Đăng nhập, xác thực, tạo tài khoản và phân vai Sales/Quản trị không thuộc US-044; chúng thuộc US-046, dù tiêu đề cũ của FEAT-044 có thể còn nhắc “2 vai”.
- `[CONFIRMED — US-042]` Nạp/reset dữ liệu mẫu idempotent thuộc US-042.
- `[CONFIRMED — US-043]` Bộ kiểm thử T-1..T-10 thuộc US-043.
- `[CONFIRMED — US-045; architecture.md; project-rules.md]` GitLab, Grafana, telemetry, log shipping, agent/prompt log và dashboard quan sát không thuộc US-044; dự án không bổ sung các năng lực đó.
- `[INFERRED — REQ-704]` Không mở rộng thành quản trị cấu hình trên giao diện, quản lý bí mật tập trung, sao lưu/khôi phục, CI/CD hoặc triển khai cloud vì nguồn không yêu cầu.

## 7. Actor / Permission

| Actor | Business responsibility in this story | Scope of authority | Evidence |
|---|---|---|---|
| Vận hành/Ops | Chuẩn bị và khởi động môi trường production giả lập; quan sát kết quả khởi động. | Chỉ thao tác vận hành cần thiết để đưa hệ thống vào trạng thái chạy. | `CONFIRMED — US-044, AC-085` |
| Người dùng CRM | Sử dụng sản phẩm sau khi Ops khởi động thành công. | Không có thao tác vận hành nào được đặc tả tại đây. | `INFERRED — mục tiêu US-044` |

`[CONFIRMED — US-046]` Không xác định quyền Sales, Admin, đăng nhập hoặc phân quyền trong mục này.

## 8. Business Rules

| ID | Rule | Evidence |
|---|---|---|
| BR-US044-01 | Bản dựng được dùng để nộp/chạy production giả lập không được vận hành bằng dev server, hot reload hoặc chế độ gỡ lỗi. | `CONFIRMED — REQ-704, AC-082` |
| BR-US044-02 | Khóa dịch vụ ngoài, chuỗi kết nối DB và chu kỳ vòng quét phải được cung cấp qua biến môi trường; không đặt trong mã. | `CONFIRMED — REQ-704, AC-083` |
| BR-US044-03 | Khởi động lại tiến trình không được làm mất dữ liệu đang có trong DB. | `CONFIRMED — REQ-704, AC-084` |
| BR-US044-04 | Ops phải có một lệnh khởi động để đưa hệ thống vào trạng thái chạy và phải xem được đầu ra vận hành của lần khởi động. | `CONFIRMED — REQ-704, AC-085` |
| BR-US044-05 | Quy tắc về đăng nhập và hai vai không được suy diễn hoặc hiện thực trong phạm vi US-044. | `CONFIRMED — US-044 note, US-046` |

## 9. Business Data Dictionary

| Business data / concept | Meaning | Required by this story | Rule / evidence |
|---|---|---:|---|
| Cấu hình production giả lập | Tập giá trị vận hành dùng để chạy sản phẩm theo điều kiện nộp bài, không phải cấu hình phát triển. | Có | `CONFIRMED — REQ-704` |
| Khóa dịch vụ ngoài | Giá trị cấu hình cho dịch vụ bên ngoài mà sản phẩm sử dụng. Nội dung/nhà cung cấp không được nguồn xác định. | Có, khi áp dụng | `CONFIRMED — REQ-704`; chi tiết là `OPEN QUESTION — Q-044-01` |
| Chuỗi kết nối DB | Giá trị cấu hình xác định nơi dữ liệu CRM được lưu và truy cập. | Có | `CONFIRMED — REQ-704` |
| Chu kỳ vòng quét | Khoảng thời gian vận hành của vòng quét công ty theo dõi. | Có | `CONFIRMED — REQ-704`; mặc định 60 giây ở BR-014/architecture.md |
| Dữ liệu trong DB | Dữ liệu CRM và dữ liệu liên quan đã tồn tại trước khi tiến trình khởi động lại. | Có | `CONFIRMED — AC-084` |
| Lệnh khởi động | Một thao tác dòng lệnh mà Ops dùng để khởi động hệ thống. Tên/cú pháp không được đặc tả. | Có | `CONFIRMED — AC-085`; `OPEN QUESTION — Q-044-02` |
| Đầu ra vận hành | Thông tin Ops có thể xem để biết việc khởi động đang/đã xảy ra. Nội dung và nơi xem chưa được nguồn quy định. | Có | `CONFIRMED — AC-085`; `OPEN QUESTION — Q-044-03` |

## 10. Business Flow

### BF-044-01 — Chuẩn bị cấu hình vận hành

1. Ops chuẩn bị các giá trị biến môi trường cần thiết.
2. Các giá trị gồm khóa dịch vụ ngoài, chuỗi kết nối DB và chu kỳ vòng quét.
3. Ops không sửa mã để thay đổi các giá trị này.
4. Sản phẩm sử dụng cấu hình đó khi được khởi động.

`[CONFIRMED — REQ-704, AC-083]`

### BF-044-02 — Khởi động môi trường production giả lập

1. Ops thực hiện một lệnh khởi động.
2. Hệ thống bắt đầu chạy ở cấu hình production giả lập, không có đặc tính dev bị cấm.
3. Ops xem đầu ra vận hành để nhận biết kết quả khởi động.
4. Khi hệ thống đã sẵn sàng, người dùng CRM có thể sử dụng các phạm vi chức năng đã được giao cho story khác.

`[CONFIRMED — AC-082, AC-085]` Bước 4 là hệ quả mục tiêu vận hành, không bao gồm đăng nhập hoặc phân vai.

### BF-044-03 — Kiểm tra tính bền dữ liệu sau restart

1. Có dữ liệu trong DB khi hệ thống đang chạy.
2. Ops khởi động lại tiến trình.
3. Hệ thống chạy lại với cấu hình vận hành.
4. Dữ liệu có trước restart vẫn còn nguyên.

`[CONFIRMED — AC-084]`

## 11. Acceptance Criteria

### AC-082 — Production build

```gherkin
Scenario: Bản dựng production giả lập
  Given bản dựng nộp
  Then không có dev server
  And không có hot reload
  And không có chế độ gỡ lỗi
```

`[CONFIRMED — user-stories.md, AC-082]`

### AC-083 — Cấu hình ở biến môi trường

```gherkin
Scenario: Cấu hình hệ thống
  Given cấu hình hệ thống
  Then khóa dịch vụ ngoài nằm ở biến môi trường
  And chuỗi kết nối DB nằm ở biến môi trường
  And chu kỳ vòng quét nằm ở biến môi trường
  And các giá trị đó không nằm trong mã
```

`[CONFIRMED — user-stories.md, AC-083]`

### AC-084 — Dữ liệu bền qua restart

```gherkin
Scenario: Dữ liệu còn nguyên khi khởi động lại
  Given có dữ liệu trong DB
  When tiến trình khởi động lại
  Then dữ liệu còn nguyên
```

`[CONFIRMED — user-stories.md, AC-084]`

### AC-085 — Khởi động một lệnh

```gherkin
Scenario: Khởi động môi trường
  Given máy chạy
  When người vận hành khởi động bằng một lệnh
  Then hệ thống chạy
  And đầu ra vận hành được đưa ra chỗ xem được
```

`[CONFIRMED — user-stories.md, AC-085]` Ghi chú nguồn “Đăng nhập 2 vai: xem US-046” được giữ như một loại trừ phạm vi, không phải tiêu chí của US-044.

## 12. Screen Specification

`[CONFIRMED — US-044]` US-044 không yêu cầu một màn hình CRM mới. Tương tác bắt buộc của Ops là qua một lệnh khởi động, còn đầu ra vận hành phải xem được.

| Surface | Audience | Minimum business information | Evidence |
|---|---|---|---|
| Điểm xem đầu ra vận hành | Ops | Trạng thái/đầu ra đủ để Ops nhận biết việc khởi động có thể quan sát. | `CONFIRMED — AC-085` |

`[OPEN QUESTION — Q-044-03]` Nguồn không xác định điểm xem này là terminal, trang trạng thái hay hình thức khác; quyết định đó thuộc thiết kế kỹ thuật/vận hành sau phê duyệt.

## 13. Screen Design

Không có screen asset hoặc wireframe cho US-044 vì nguồn không yêu cầu UI. `CONFIRMED — US-044`

## 14. Screen States

Không có màn hình CRM chuyên biệt trong phạm vi này. Các trạng thái vận hành quan sát được tối thiểu là:

| State | Meaning | Evidence |
|---|---|---|
| Chưa khởi động | Ops chưa thực hiện lệnh khởi động. | `INFERRED — BF-044-02` |
| Đang khởi động | Lệnh đã được thực hiện và Ops đang xem đầu ra vận hành. | `INFERRED — AC-085` |
| Đã chạy | Hệ thống đã chạy để người dùng CRM có thể sử dụng các phạm vi được giao ở story khác. | `CONFIRMED — AC-085` |
| Không thể xác nhận | Đầu ra vận hành không đủ để Ops xác nhận tình trạng; cách biểu đạt cụ thể chưa được nguồn quy định. | `ASSUMPTION — cần xác nhận theo Q-044-03` |

## 15. Validation

| Validation | Expected business result | Evidence |
|---|---|---|
| Kiểm tra đặc tính bản dựng | Bản dựng không được chấp nhận nếu còn dev server, hot reload hoặc gỡ lỗi. | `CONFIRMED — AC-082` |
| Kiểm tra nguồn cấu hình | Ba nhóm cấu hình bắt buộc không được lấy từ mã. | `CONFIRMED — AC-083` |
| Kiểm tra restart | Sau restart, dữ liệu có trước đó vẫn còn nguyên. | `CONFIRMED — AC-084` |
| Kiểm tra thao tác khởi động | Ops dùng được một lệnh và quan sát được đầu ra của lần khởi động. | `CONFIRMED — AC-085` |
| Kiểm tra giá trị cấu hình thiếu/sai | Thông điệp và tiêu chí nhận biết lỗi chưa được nguồn xác định. | `OPEN QUESTION — Q-044-04` |

## 16. Dependencies

| Dependency | Relationship | Evidence |
|---|---|---|
| US-042 — Nạp/reset dữ liệu idempotent | US-044 phụ thuộc US-042 theo user story; dữ liệu mẫu được nạp phải tiếp tục tồn tại sau restart. | `CONFIRMED — US-044 dependency; AC-084` |
| REQ-704 | Là nguồn yêu cầu bắt buộc cho toàn bộ scope. | `CONFIRMED — requirement-analysis.md` |
| US-032 / BR-014 | Cung cấp ý nghĩa nghiệp vụ và mặc định 60 giây cho chu kỳ vòng quét được cấu hình. | `CONFIRMED — architect-handoff.md, architecture.md` |
| US-046 | Liên quan cùng REQ-704 nhưng tách scope: đăng nhập và phân vai không phải điều kiện của US-044. | `CONFIRMED — user-stories.md, US-046` |
| Quy tắc kiến trúc dự án | Không thêm monitoring, telemetry, log shipping hoặc prompt/agent logs trong khi đáp ứng yêu cầu về đầu ra vận hành có thể xem. | `CONFIRMED — architecture.md, project-rules.md` |

## 17. Business-level NFR Expectations

| ID | Expectation | Evidence |
|---|---|---|
| NFR-US044-01 | Môi trường nộp bài vận hành như production giả lập, không có dev server, hot reload hay debug. | `CONFIRMED — REQ-704, AC-082` |
| NFR-US044-02 | Cấu hình nhạy cảm/vận hành được tách khỏi mã bằng biến môi trường. | `CONFIRMED — REQ-704, AC-083` |
| NFR-US044-03 | Dữ liệu DB bền qua restart của tiến trình. | `CONFIRMED — REQ-704, AC-084` |
| NFR-US044-04 | Ops khởi động được hệ thống bằng một lệnh và có đầu ra vận hành xem được. | `CONFIRMED — REQ-704, AC-085` |
| NFR-US044-05 | Không bổ sung telemetry, Grafana, monitoring, log shipping hoặc lưu prompt/log agent để đáp ứng story này. | `CONFIRMED — architecture.md, project-rules.md` |

## 18. Test Scenarios

Chưa có artifact `test-scenarios.md` dành riêng cho US-044. Các scenario dưới đây là scenario nghiệp vụ tham chiếu AC, không phải test code hay chỉ dẫn hiện thực.

| ID | Scenario business-level | AC / BR traced | Expected result |
|---|---|---|---|
| TC-US044-01 | Đánh giá bản dựng nộp | AC-082, BR-US044-01 | Không quan sát được dev server, hot reload hoặc chế độ gỡ lỗi. |
| TC-US044-02 | Xem xét nguồn ba nhóm cấu hình | AC-083, BR-US044-02 | Khóa dịch vụ ngoài, chuỗi kết nối DB và chu kỳ vòng quét đều được cung cấp bằng biến môi trường, không trong mã. |
| TC-US044-03 | Restart khi đã có dữ liệu | AC-084, BR-US044-03 | Dữ liệu có trước restart vẫn còn nguyên sau khi tiến trình chạy lại. |
| TC-US044-04 | Khởi động theo thao tác chuẩn | AC-085, BR-US044-04 | Một lệnh đưa hệ thống vào trạng thái chạy và Ops xem được đầu ra vận hành. |
| TC-US044-05 | Phân định scope với đăng nhập | BR-US044-05 | Không dùng US-044 để xác minh xác thực hoặc quyền của hai vai; các kiểm tra đó truy về US-046. |

## 19. Traceability

| Canonical chain | Source / target | Evidence |
|---|---|---|
| REQ → FEAT → US | REQ-704 → FEAT-044 → US-044 | `CONFIRMED — requirement-analysis.md; backlog-prioritization.md; user-stories.md` |
| US → AC | US-044 → AC-082, AC-083, AC-084, AC-085 | `CONFIRMED — user-stories.md` |
| AC → TC | AC-082 → TC-US044-01; AC-083 → TC-US044-02; AC-084 → TC-US044-03; AC-085 → TC-US044-04 | `CONFIRMED source / INFERRED scenario mapping` |
| Related dependency | US-042 → US-044; US-046 separated from US-044 | `CONFIRMED — user-stories.md` |
| Architecture constraint | REQ-704 / US-044 → NFR-US044-05 | `CONFIRMED — architecture.md; project-rules.md` |

## 20. Assumptions

| ID | Assumption | Basis / approval need |
|---|---|---|
| AS-US044-01 | “Hệ thống chạy” nghĩa là môi trường đã sẵn sàng cho người dùng CRM sử dụng các chức năng đã được triển khai, nhưng không bao hàm đăng nhập/phân vai trong story này. | `ASSUMPTION — suy từ AC-085; cần PO xác nhận` |
| AS-US044-02 | Đầu ra vận hành xem được có thể đủ cho Ops nhận biết khởi động, mà không cần bổ sung một hệ thống monitoring hay log shipping. | `ASSUMPTION — AC-085` được giới hạn bởi `architecture.md` và `project-rules.md`; cần xác nhận Tech Lead/Ops |
| AS-US044-03 | Dữ liệu được kiểm tra ở AC-084 bao gồm dữ liệu mẫu đã có trong môi trường sau US-042 khi các dữ liệu đó tồn tại trước restart. | `ASSUMPTION — quan hệ dependency US-042 → US-044; cần PO xác nhận` |

## 21. Open Questions

| ID | Question | Why it matters | Owner |
|---|---|---|---|
| Q-044-01 | Những dịch vụ ngoài nào thực sự cần khóa cấu hình trong phạm vi bản dựng nộp? | Tránh suy diễn danh mục khóa hoặc nhà cung cấp. | PO / Tech Lead |
| Q-044-02 | Lệnh khởi động chuẩn được công bố cho Ops là gì? | AC-085 yêu cầu một lệnh nhưng không định danh lệnh. | Tech Lead / Ops |
| Q-044-03 | “Chỗ xem được” cho đầu ra vận hành là đâu và thông tin tối thiểu để xác nhận khởi động là gì? | Cần tiêu chí quan sát mà không mở rộng sang monitoring/telemetry. | Tech Lead / Ops |
| Q-044-04 | Khi biến môi trường thiếu hoặc không hợp lệ, Ops cần nhận biết tình trạng đó theo tiêu chí nghiệp vụ nào? | Nguồn không quy định thông điệp hay trạng thái lỗi. | PO / Tech Lead |
| Q-044-05 | Có cần quy ước rõ “còn nguyên” ở AC-084 cho dữ liệu phát sinh trong lúc demo, ngoài dữ liệu mẫu hay không? | Làm rõ phạm vi chứng minh persistence mà không thiết kế cơ chế lưu trữ. | PO |

## 22. Definition of Ready

| Check | Status | Evidence / note |
|---|---|---|
| Actor Ops xác định rõ | Ready | `CONFIRMED — US-044` |
| Business value và scope NFR xác định rõ | Ready | `CONFIRMED — REQ-704, US-044` |
| AC-082..AC-085 được bảo toàn | Ready | `CONFIRMED — user-stories.md` |
| Đăng nhập/phân vai đã tách khỏi scope | Ready | `CONFIRMED — US-046` |
| Ràng buộc không thêm monitoring/telemetry/log shipping được ghi nhận | Ready | `CONFIRMED — architecture.md, project-rules.md` |
| Cú pháp lệnh khởi động và điểm xem đầu ra được chốt | Pending | `OPEN QUESTION — Q-044-02, Q-044-03` |
| Tiêu chí lỗi cấu hình và nghĩa chi tiết của “còn nguyên” được chốt | Pending | `OPEN QUESTION — Q-044-04, Q-044-05` |

`[CONFIRMED — human-approval.md]` Tài liệu dừng ở trạng thái `AWAITING_SPECIFICATION_APPROVAL`; chỉ con người có thể chuyển sang `SPECIFICATION_APPROVED`.

## 23. Technical Handoff

### Approved constraints

- `[CONFIRMED — architecture.md, project-rules.md]` Mọi cấu hình phải đi qua biến môi trường; không hard-code secret, URL DB hoặc scan interval.
- `[CONFIRMED — architecture.md]` Chu kỳ quét có mặc định 60 giây; giá trị vận hành được cấu hình qua biến môi trường.
- `[CONFIRMED — project-rules.md]` Không thêm Grafana, monitoring, telemetry, log shipping hay prompt logging.
- `[CONFIRMED — REQ-704]` Dữ liệu phải bền qua restart và việc khởi động cần thực hiện bằng một lệnh.

### Integration touchpoints

- `[CONFIRMED — US-042]` Môi trường persistence phải duy trì được dữ liệu mẫu sau khi US-042 đã nạp/reset dữ liệu.
- `[CONFIRMED — US-032, AR-4]` Cấu hình chu kỳ vòng quét là điểm chạm với chức năng vòng quét, nhưng không thay đổi nghiệp vụ của US-032.
- `[CONFIRMED — US-046]` Login/RBAC là điểm chạm tách biệt; không đưa vào quyết định hoặc phạm vi hiện thực US-044.

### Risks

- `[CONFIRMED — REQ-704]` Nếu cấu hình còn nằm trong mã, môi trường nộp không đáp ứng AC-083.
- `[CONFIRMED — AC-084]` Nếu restart làm mất dữ liệu, demo và nghiệm thu không có tính liên tục.
- `[INFERRED — AC-085]` Nếu đầu ra vận hành không đủ quan sát, Ops không thể xác nhận việc khởi động dù hệ thống có thể đã chạy.
- `[CONFIRMED — project-rules.md]` Dùng telemetry/log shipping để giải quyết khả năng quan sát sẽ vi phạm quy tắc dự án.

### Decisions required from Tech Lead / Ops

- `[OPEN QUESTION — Q-044-02]` Chốt lệnh khởi động chuẩn.
- `[OPEN QUESTION — Q-044-03]` Chốt nơi xem và ngưỡng thông tin tối thiểu của đầu ra vận hành.
- `[OPEN QUESTION — Q-044-04]` Chốt cách Ops nhận biết cấu hình thiếu/không hợp lệ.

## 24. Change Log

| Version | Date | Change | Author/Approver |
|---|---|---|---|
| 1.0 | 2026-08-14 | Tạo business specification 24 section cho US-044 từ REQ-704, US-044/AC-082..085, backlog, architect handoff và kiến trúc dự án; tách rõ auth/2 vai sang US-046. | Codex / awaiting human approval |
