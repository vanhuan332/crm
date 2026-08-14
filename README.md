# AI-Native CRM — HBLAB

Monolith CRM cho Hackathon HBLAB. Kiến trúc gồm Vue.js 3 SPA độc lập và Spring Boot modular monolith, thiết kế theo `docs/03-architecture/system/architecture.md`; yêu cầu và phân tích gốc được giữ nguyên trong `docs/`.

## Khởi động

1. Sao chép `.env.example` thành `.env` và điều chỉnh cấu hình nếu cần.
2. Chạy PostgreSQL: `docker compose --env-file .env up -d`.
3. Chạy backend: `mvn -f backend/pom.xml -s .mvn/settings.xml spring-boot:run`.
4. Chạy frontend (terminal khác): `cd frontend; npm install --cache ../.npm-cache; npm run dev`.

API có base path `/api`. Kiểm tra nhanh: `GET /api/health`. Chạy test backend: `mvn -f backend/pom.xml -s .mvn/settings.xml test`.

## Deploy bằng Docker

1. Sao chép `.env.example` thành `.env`; thay `POSTGRES_PASSWORD` bằng mật khẩu đủ mạnh.
2. Build và chạy toàn bộ stack: `docker compose --env-file .env up --build -d`.
3. Mở ứng dụng tại `http://localhost:8080`; frontend Nginx proxy `/api` tới Spring Boot nội bộ.

Tắt stack: `docker compose --env-file .env down`. Dữ liệu PostgreSQL được giữ trong volume `postgres-data`; thêm `-v` nếu chủ động muốn xoá cả dữ liệu.

Không có thành phần monitoring/log shipping/Grafana trong mã nguồn này theo phạm vi dự án.

## Kiểm thử nghiệm thu trình duyệt

1. Sao chép `.env.example` thành `.env`, sau đó khởi động stack giống production: `docker compose --env-file .env up --build -d`.
2. Chạy lệnh seed/reset idempotent của môi trường trước khi chạy lại kịch bản demo. Base hiện tại chưa cung cấp lệnh seed/reset; acceptance test tạo bản ghi tên duy nhất và tự xóa nó, nên không phụ thuộc dữ liệu seed.
3. Cài dependencies và Chromium một lần: `cd frontend; npm install --cache ../.npm-cache; npx playwright install chromium`.
4. Chạy các delivery gate: `mvn -f backend/pom.xml -s .mvn/settings.xml test`; `npm run test:unit -- --run`; `npm run typecheck`; `npm run build`; `npm run test:e2e`.

`npm run test:e2e` mặc định chạy tại `http://localhost`. Đặt `E2E_BASE_URL` (ví dụ `http://localhost:8080`) để chạy với địa chỉ stack khác; không đưa URL hay thông tin xác thực vào mã nguồn.
