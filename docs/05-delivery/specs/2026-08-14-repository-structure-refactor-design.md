# Repository structure refactor

## Mục tiêu

Tổ chức repository theo ba phạm vi dễ nhận biết: backend Spring Boot, frontend Vue và tài liệu theo vòng đời. Thay đổi chỉ là cấu trúc, đường dẫn và cấu hình build/deploy tương ứng; không thay đổi nghiệp vụ CRM, database schema hay kiến trúc modular monolith.

## Cấu trúc đích

```text
/
├── backend/
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/{main,test}/...
├── frontend/
│   ├── Dockerfile
│   └── src/...
├── docs/
│   ├── 01-product/
│   ├── 02-analysis/
│   ├── 03-architecture/
│   ├── 04-specifications/
│   └── 05-delivery/
├── docker-compose.yml
├── .env.example
├── README.md
└── AGENTS.md
```

## Quy tắc di chuyển

- Maven backend (`pom.xml`, `src/`, `Dockerfile`) chuyển vào `backend/` mà không đổi package Java hoặc nội dung migration Flyway.
- Frontend tiếp tục ở `frontend/`; chỉ những cấu hình tham chiếu backend/root được cập nhật khi cần.
- `docs/prd.md` và thư mục tài liệu hackathon nguồn chuyển vào `docs/01-product/` và không sửa nội dung nguồn.
- `docs/analysis/` chuyển vào `docs/02-analysis/`; `docs/system/` vào `docs/03-architecture/`; `docs/specification/` vào `docs/04-specifications/`; các design và implementation plan chuyển vào `docs/05-delivery/`.
- Mọi liên kết Markdown, `AGENTS.md`, README và đường dẫn trong cấu hình được đổi đồng bộ. Lệnh backend chuẩn là `mvn -f backend/pom.xml -s .mvn/settings.xml <goal>`.

## Build, deploy và artefact

- Docker Compose vẫn là điểm khởi động tại root. Context/build path được đổi để build backend từ `backend/` và frontend từ `frontend/`.
- Dockerfile backend giữ multi-stage; image runtime không chứa source, Maven hay Node. Dockerfile frontend giữ image runtime Nginx không chứa source/Node.
- `.gitignore` loại trừ artefact build/test: `backend/target/`, `frontend/node_modules/`, `frontend/dist/`, `frontend/verification-dist/`, `frontend/test-results/`, `.codegraph/` và các thư mục verify tạm theo mẫu `.task*-verify-dist/`.
- Các artefact hiện đang có và thuộc các mẫu trên được xóa. Không xóa code nguồn, tài liệu nguồn hay thay đổi người dùng chưa commit.

## Tác động và kiểm chứng

Tác động có chủ ý là thay đổi đường dẫn build, Docker context và link tài liệu. Không có API, schema, business rule hoặc module backend nào đổi.

Sau khi di chuyển, cần xác nhận:

1. `mvn -f backend/pom.xml -s .mvn/settings.xml test` chạy thành công.
2. Các script frontend vẫn hợp lệ từ `frontend/`.
3. `docker compose config` phân giải được context và Dockerfile mới.
4. Không còn artefact build/test được Git theo dõi hoặc xuất hiện trong `git status` sau lần build/kiểm tra thích hợp.
