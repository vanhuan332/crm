# BA Specification — US-042: Nạp/Reset dữ liệu idempotent một lệnh

> Sản phẩm: AI Native CRM — Sales B2B ITO  
> Phiên bản: 1.0  
> Ngày: 2026-08-14  
> Trạng thái: AWAITING_SPECIFICATION_APPROVAL  
> Ngôn ngữ giao diện: Tiếng Việt  
> Story: US-042 · Feature: FEAT-042 · Epic: EPIC-12 · Domain: D7 — Ranh giới, Nghiệm thu & Vận hành

## 1. Mục đích tài liệu

Tài liệu này mô tả nghiệp vụ của US-042 ở mức vận hành:

- cho phép nạp dữ liệu mẫu bằng một lệnh;
- cho phép chạy lại cùng lệnh để đưa môi trường về trạng thái ban đầu;
- tạo sẵn bộ dữ liệu đủ cho demo, kiểm thử và diễn lại kịch bản;
- bảo đảm quy trình nạp không cần gõ tay và không phụ thuộc vào thao tác CRM.

Tài liệu không mô tả cách triển khai, script, migration hay cơ chế kỹ thuật cụ thể.

## 2. Thông tin User Story

| Thuộc tính | Giá trị |
|---|---|
| User Story | US-042 — Nạp/Reset dữ liệu idempotent một lệnh |
| Actor chính | Vận hành/Ops |
| User story statement | Là người vận hành, tôi muốn nạp và reset dữ liệu bằng một lệnh để diễn lại kịch bản demo từ đầu. |
| Business value | Tạo môi trường chuẩn, lặp lại được, giảm sai số khi demo và nghiệm thu. |
| Priority | Must — 18/20 |
| Dependency đầu vào | Không phụ thuộc story CRM khác |
| Downstream dependency | US-044, US-046 và toàn bộ luồng demo/kiểm thử cần dữ liệu mẫu |
| Requirement | REQ-702 |
| Business assumption | AS-03 |
| Acceptance test cấp hệ thống | Nền cho các kịch bản T-1..T-10 thông qua dữ liệu mẫu |
| DoR | Draft trong nguồn, chờ chuẩn hóa theo spec này |

## 3. Mục tiêu và tiêu chí thành công

### 3.1 Mục tiêu nghiệp vụ

- Người vận hành có thể dựng bộ dữ liệu chuẩn chỉ bằng một lệnh.
- Chạy lại lệnh đó đưa hệ thống về đúng trạng thái ban đầu.
- Bộ dữ liệu mẫu đủ để diễn lại các kịch bản demo và kiểm thử của dự án.
- Có sẵn 2 tài khoản Sales và Admin theo assumption AS-03.

### 3.2 Tiêu chí thành công

- Không cần nhập tay để tạo dữ liệu nền.
- Chạy lại nhiều lần vẫn cho kết quả giống nhau ở trạng thái đầu vào.
- Bộ dữ liệu mẫu có đủ công ty, người liên hệ, cơ hội và bản chụp trước/sau.
- Trạng thái sau reset phải hỗ trợ demo lại từ đầu một cách ổn định.

## 4. Phạm vi

### 4.1 Trong phạm vi

- Nạp bộ dữ liệu mẫu ban đầu.
- Reset dữ liệu về đúng trạng thái ban đầu.
- Tạo dữ liệu nền cho công ty, người liên hệ, cơ hội và bản chụp demo.
- Tạo sẵn 2 tài khoản: Sales và Admin.
- Đảm bảo thao tác nạp/reset là idempotent.

### 4.2 Ngoài phạm vi

- Đăng nhập và phân vai người dùng — US-046.
- Production build / env config / persistence — US-044.
- Chuyển bản chụp trước→sau — US-041.
- Kiểm thử tự động — US-043.
- Bất kỳ hành vi business CRM nào cho Sales ngoài việc tạo dữ liệu mẫu.

## 5. Vai trò và quyền

| Hành động | Vận hành/Ops | Sales | Admin |
|---|---:|---:|---:|
| Chạy lệnh nạp/reset | Có | Không | Không |
| Xem trạng thái dữ liệu sau nạp | Có | Có, nếu đã đăng nhập bằng tài khoản seed | Có, nếu đã đăng nhập bằng tài khoản seed |
| Tự chỉnh dữ liệu mẫu bằng tay trong story này | Không | Không | Không |

Ghi chú: story này là thao tác vận hành, không phải tính năng CRM cho Sales. Người dùng CRM chỉ hưởng lợi từ kết quả dữ liệu đã được nạp.

## 6. Mô hình nghiệp vụ

### 6.1 Khái niệm chính

- Bộ dữ liệu mẫu: tập dữ liệu chuẩn dùng cho demo, kiểm thử và nộp bài.
- Reset: đưa dữ liệu trở lại đúng trạng thái đầu.
- Idempotent: chạy lại cùng lệnh vẫn về đúng trạng thái mong muốn.
- Tài khoản seed: tài khoản được tạo sẵn để phục vụ đăng nhập demo.

### 6.2 Thành phần dữ liệu tối thiểu

- Công ty mẫu.
- Người liên hệ mẫu.
- Cơ hội mẫu.
- Bản chụp trước/sau phục vụ kịch bản demo AI.
- 2 tài khoản seed: Sales và Admin.

### 6.3 Ý nghĩa nghiệp vụ của reset

Reset không chỉ xóa dữ liệu phát sinh mà còn phải đưa môi trường về đúng bộ mẫu ban đầu để:

- giáo viên/chấm bài có thể lặp lại kịch bản;
- demo không bị lệch vì thao tác trước đó;
- hệ thống luôn có điểm xuất phát thống nhất.

## 7. Từ điển dữ liệu nghiệp vụ

| Trường | Ý nghĩa nghiệp vụ | Bắt buộc | Quy tắc |
|---|---|---:|---|
| Công ty mẫu | Dữ liệu đầu vào cho CRM lõi và AI | Có | Phải đủ để các story phụ thuộc chạy được |
| Người liên hệ mẫu | Dữ liệu quan hệ dưới công ty | Có | Phải liên kết đúng công ty |
| Cơ hội mẫu | Dữ liệu theo dõi bán hàng | Có | Phải đủ cho board, next step và các luồng demo |
| Bản chụp trước | Trạng thái nguồn trước khi kích hoạt kịch bản | Có | Phải tạo sẵn cho demo AI |
| Bản chụp sau | Trạng thái đích sau khi kích hoạt kịch bản | Có | Phải tạo sẵn cho demo AI |
| Tài khoản Sales | Tài khoản người dùng chính | Có | Dùng để trải nghiệm phần Sales |
| Tài khoản Admin | Tài khoản vận hành/quản trị | Có | Dùng để trải nghiệm phần quản trị |
| Trạng thái ban đầu | Bộ trạng thái chuẩn sau reset | Có | Phải lặp lại được |

## 8. Luồng nghiệp vụ

### BF-01 — Nạp dữ liệu lần đầu

1. Người vận hành chạy một lệnh nạp dữ liệu.
2. Hệ thống tạo bộ dữ liệu mẫu đầy đủ.
3. Hệ thống tạo 2 tài khoản seed.
4. Người vận hành có thể dùng bộ dữ liệu đó để demo hoặc kiểm thử ngay.

### BF-02 — Chạy lại để reset

1. Sau khi dữ liệu đã bị thay đổi do demo hoặc kiểm thử, người vận hành chạy lại cùng lệnh.
2. Hệ thống xóa hoặc đưa dữ liệu trở về trạng thái đầu theo cách idempotent.
3. Sau khi chạy lại, bộ dữ liệu phải giống trạng thái chuẩn ban đầu.

### BF-03 — Dùng dữ liệu sau nạp

1. Sales hoặc Admin đăng nhập bằng tài khoản seed.
2. Họ thấy đúng dữ liệu mẫu cần cho demo.
3. Các kịch bản phụ thuộc dữ liệu nền có thể chạy ngay.

## 9. Quy tắc nghiệp vụ

| ID | Quy tắc | Mức |
|---|---|---|
| BR-US042-01 | Nạp dữ liệu phải làm bằng một lệnh duy nhất, không gõ tay. | Bắt buộc |
| BR-US042-02 | Chạy lại cùng lệnh phải đưa hệ thống về trạng thái ban đầu. | Bắt buộc |
| BR-US042-03 | Dữ liệu mẫu phải bao gồm công ty, người liên hệ, cơ hội, bản chụp trước/sau và 2 tài khoản seed. | Bắt buộc |
| BR-US042-04 | Bộ dữ liệu nạp lại phải ổn định để phục vụ demo và nghiệm thu. | Bắt buộc |
| BR-US042-05 | Story này không thay thế các story đăng nhập hay production deployment. | Bắt buộc |

## 10. Acceptance Criteria chi tiết

### AC-079 — Nạp một lệnh

```gherkin
Scenario: Nạp dữ liệu từ môi trường trống
  Given môi trường trống
  When người vận hành chạy lệnh nạp dữ liệu
  Then bộ dữ liệu mẫu được tạo
  And có công ty, người liên hệ, cơ hội, bản chụp trước/sau
  And có 2 tài khoản Sales và Admin
  And không cần gõ tay hay sửa mã
```

### AC-080 — Chạy lại về trạng thái ban đầu

```gherkin
Scenario: Reset dữ liệu về trạng thái đầu
  Given dữ liệu đã bị thay đổi sau khi demo
  When người vận hành chạy lại lệnh nạp
  Then hệ thống trở về đúng trạng thái ban đầu
  And trạng thái đó giống bộ dữ liệu mẫu chuẩn
```

## 11. Màn hình/hiển thị dự kiến

Story này không yêu cầu màn hình CRM mới. Nếu có phần hiển thị phục vụ vận hành, thì chỉ cần:

- thông báo đã nạp xong;
- thông báo đã reset xong;
- xác nhận trạng thái sẵn sàng cho demo/kiểm thử.

Không mở rộng thành giao diện quản trị chi tiết nếu chưa có story riêng.

## 12. Handoff kỹ thuật

### 12.1 Ràng buộc cho Tech Lead

- Cần một cơ chế đảm bảo lệnh nạp/reset có thể chạy lặp lại an toàn.
- Bộ dữ liệu mẫu phải bao phủ các dependency demo đã biết.
- Hai tài khoản seed là bắt buộc theo AS-03.

### 12.2 Điểm cần quyết định khi thiết kế

- Thứ tự nạp dữ liệu nào để reset luôn cho ra cùng một trạng thái.
- Cách đảm bảo bộ mẫu không bị trùng, thiếu hoặc lệch khi chạy lại.
- Cách trình bày kết quả vận hành để người dùng biết lệnh đã hoàn tất.

### 12.3 Rủi ro nghiệp vụ

- Nếu reset không thật sự idempotent, demo sẽ lệch theo thời gian.
- Nếu dữ liệu mẫu thiếu một phần, story phụ thuộc sẽ không chạy được.
- Nếu tài khoản seed không tồn tại, luồng đăng nhập demo sẽ đứt.

## 13. Truy vết

| Nguồn | Nội dung | Được cụ thể hóa tại | Test |
|---|---|---|---|
| REQ-702 | Nạp dữ liệu một lệnh, chạy lại về trạng thái ban đầu | Mục 6, 8, 9, 10 | T-1..T-10 phụ thuộc dữ liệu nền |
| AS-03 | 2 tài khoản seed sẵn khi nạp dữ liệu | Mục 2, 6.2, 7 | T-1..T-10 |
| FEAT-042 | Nạp/Reset dữ liệu idempotent | Toàn bộ tài liệu | T-1..T-10 |

## 14. Bộ tình huống kiểm thử tối thiểu

| ID | Tình huống | Kỳ vọng |
|---|---|---|
| TC-042-01 | Nạp từ môi trường trống | Tạo đủ bộ dữ liệu mẫu |
| TC-042-02 | Nạp lại sau khi dữ liệu bị thay đổi | Về đúng trạng thái ban đầu |
| TC-042-03 | Kiểm tra 2 tài khoản seed | Có Sales và Admin |
| TC-042-04 | Kiểm tra dữ liệu mẫu phục vụ demo | Có công ty, người liên hệ, cơ hội, bản chụp |
| TC-042-05 | Chạy nhiều lần liên tiếp | Kết quả ổn định, không drift |

## 15. Definition of Ready

- [x] Actor rõ: Vận hành/Ops.
- [x] Nguồn trace rõ: REQ-702 và AS-03.
- [x] Phạm vi trong/ngoài phạm vi rõ.
- [x] Có tiêu chí idempotent.
- [x] Story này được xếp Wave 0 trong backlog.
- [ ] PO xác nhận bộ dữ liệu mẫu cuối cùng phục vụ demo.
- [ ] PO xác nhận danh sách tài khoản seed ngoài Sales và Admin nếu có mở rộng.

## 16. Definition of Done đề xuất

- [ ] Một lệnh nạp/reset tạo ra đúng bộ dữ liệu mẫu.
- [ ] Chạy lại đưa hệ thống về trạng thái chuẩn.
- [ ] Hai tài khoản seed luôn được tạo.
- [ ] Các luồng phụ thuộc dữ liệu nền có thể dùng ngay.
- [ ] Bộ test dữ liệu nền pass.

## 17. Giả định và câu hỏi mở

- Giả định: bộ dữ liệu mẫu chuẩn đã được PO chốt theo nhu cầu demo hiện tại.
- Giả định: bản chụp trước/sau đã có nội dung mẫu tương ứng cho các kịch bản AI.
- Q-042-01: Có cần hiển thị số lượng bản ghi sau nạp để kiểm tra nhanh hay không?
- Q-042-02: Có cần lưu riêng một trạng thái “seed chuẩn” để đối chiếu khi reset hay không?

## 18. Change log

| Version | Date | Nội dung |
|---|---|---|
| 1.0 | 2026-08-14 | Tạo BA specification cho US-042 dựa trên REQ-702 và AS-03. |
