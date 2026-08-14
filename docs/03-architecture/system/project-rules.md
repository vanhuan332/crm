# Quy tắc dự án

1. Mỗi thay đổi nghiệp vụ phải truy vết được về `docs/01-product/prd.md` hoặc `docs/02-analysis/`; không xóa/sửa tài liệu nguồn.
2. Không được thêm monitoring, log shipping, Grafana, telemetry hay prompt logging.
3. AI không tự đổi stage, giá trị deal, không xóa dữ liệu người dùng và không liên hệ khách. Chặn ở application service, không chỉ ở UI/prompt.
4. Claim bắt buộc có provenance (quote và offsets); thiếu thì từ chối lưu.
5. AI tắt phải không ảnh hưởng CRM thủ công. Chỉ Admin đổi kill switch; hiệu lực không restart.
6. Thay đổi schema chỉ bằng Flyway migration; không dùng `ddl-auto=update`.
7. Không gọi repository xuyên mô-đun. Dùng use case/domain event, giữ một transaction rõ ràng.
8. Không hard-code secret, URL DB hoặc scan interval. Cập nhật `.env.example` khi thêm cấu hình.
9. Test guardrail ở tầng application service, bao gồm đường gọi không đi qua UI.
10. Docker deployment gồm Vue/Nginx, Spring Boot và PostgreSQL; không thêm agent monitoring/logging vào stack.
