# US-001 — Thiết kế quản lý Công ty

## Trạng thái và quyết định

Thiết kế này hiện thực hóa US-001/FEAT-001 (REQ-101, BR-001) của AI Native CRM.

- Giữ năm trường tùy chọn: quốc gia, website, điện thoại, địa chỉ, mô tả/ghi chú.
- Tên công ty là duy nhất với các bản ghi đang hoạt động, sau khi trim và không phân biệt hoa/thường.
- Xóa là soft delete: bản ghi bị ẩn khỏi danh sách và chi tiết, không bị xóa cứng.
- `industry` là text tự do trong MVP; nguồn chưa cung cấp danh mục ngành.
- UI dùng trang riêng cho danh sách, tạo, chi tiết và sửa; modal chỉ dùng để xác nhận xóa.
- API cập nhật dùng `PUT` với optimistic locking qua `version`.

## Kiến trúc

US-001 là vertical slice thuộc module `crm-core`, tuân thủ hướng phụ thuộc API → application → domain → infrastructure.

- API có `CompanyController`, request/response DTO và exception mapping; controller không mang business rule.
- Application có các use case create, list, get, update, delete. Mỗi use case là một transaction và điều phối domain/repository.
- Domain `Company` chứa invariant trường bắt buộc, chuẩn hóa trim và trạng thái soft delete. `CompanyType` có đúng năm code: `TRADITIONAL`, `IT_SOLUTION`, `IT_PRODUCT`, `TECH_STARTUP`, `OTHER_ITO`.
- Infrastructure có JPA repository chỉ truy vấn công ty active và Flyway migration. Không repository nào bị gọi xuyên module.

Hành động xóa từ UI luôn là `ActorType.HUMAN`. Khi xuất hiện luồng tự động trong các story sau, toàn bộ lệnh ghi tiếp tục đi qua application service và `AutomationPolicyGuard`; A-AI không có đường xóa công ty.

## Mô hình dữ liệu

Migration Flyway tiếp theo hoàn thiện bảng `companies` với:

- Các trường business: `name`, `industry`, `company_type`, `country`, `website`, `phone`, `address`, `description`.
- Các trường hệ thống: `created_at`, `updated_at`, `deleted_at`, `version`.
- Ràng buộc unique partial index trên `lower(btrim(name))` với điều kiện `deleted_at IS NULL`.

`name` (1–255), `industry` (1–100) và `companyType` là bắt buộc. Chuỗi chỉ gồm khoảng trắng bị coi là rỗng. Các trường còn lại cho phép null; `website`, nếu có, phải dùng `http` hoặc `https`. Tên đã soft-delete có thể tái sử dụng.

## API contract

| Hành vi | Endpoint | Kết quả |
|---|---|---|
| Danh sách | `GET /api/companies` | `200`, chỉ company active |
| Tạo | `POST /api/companies` | `201` + company vừa tạo |
| Chi tiết | `GET /api/companies/{id}` | `200`; `404` nếu không tồn tại/đã xóa |
| Cập nhật | `PUT /api/companies/{id}` | `200`; request chứa đủ payload và `version` |
| Xóa mềm | `DELETE /api/companies/{id}` | `204`; lần gọi sau là `404` |

Lỗi validation trả `400 VALIDATION_ERROR` với `fieldErrors`. Tên trùng trả `409 COMPANY_NAME_CONFLICT`. Version cũ trả `409 COMPANY_MODIFIED`. Sửa/xóa company đã bị xóa trả `404`; không tự tạo lại dữ liệu.

## UX và wireflow

Routes:

```text
/companies → danh sách
/companies/new → form tạo
/companies/:id → chi tiết
/companies/:id/edit → form sửa
```

`CompaniesListView` hiển thị tên, ngành, loại, quốc gia, thời điểm cập nhật và actions xem/sửa/xóa; empty state dẫn đến create. `CompanyForm` dùng chung cho create/edit, hiển thị ba trường bắt buộc và năm trường tùy chọn. Sau create điều hướng về detail. Detail là extension point cho Contact, Opportunity, Timeline và AI trong các story sau. Xóa dùng dialog có tên company và hành động nguy hiểm được ghi rõ “Xóa công ty”.

Client validation phản hồi sớm, nhưng server là nguồn quyết định. Khi lỗi, UI giữ dữ liệu đã nhập và hiển thị lỗi theo trường. Nút lưu bị vô hiệu hóa trong request để tránh submit lặp. Khi `409 COMPANY_MODIFIED`, UI yêu cầu tải lại dữ liệu trước khi lưu tiếp.

## Kiểm thử và xác minh

- Unit test domain validation, chuẩn hóa, enum và website URL.
- Application/integration test create/list/detail/update/delete, unique name, soft delete, invalid enum, not found và optimistic-lock conflict.
- Frontend test form dùng chung, lỗi validation, hủy thao tác và điều hướng sau lưu.
- E2E test Create → Detail → Update → Delete cùng AC-002, TC-010, TC-015, TC-016 và TC-018.
- Quality gate: `mvn -f backend/pom.xml -s .mvn/settings.xml test`, `npm run typecheck`, `npm run build`.

CRM CRUD không phụ thuộc AI, nên T-1 vẫn chạy khi toàn bộ AI tắt.

## Ngoài phạm vi

Không thêm Contact, Opportunity, Activity/Timeline, theo dõi company, AI data/automation, import/export, phân quyền theo owner, phân trang/sắp xếp nâng cao, audit UI hoặc cascade delete.

## Rà soát thiết kế

Các quyết định về trường tùy chọn, uniqueness và soft delete nhất quán giữa data model, API, UX và test. Scope chỉ bao gồm US-001; các điểm mở rộng không tạo thêm hành vi của story downstream.
