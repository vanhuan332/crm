# Deployment Docker

`docker-compose.yml` khởi tạo ba container:

| Service | Vai trò | Public port |
|---|---|---|
| `web` | Nginx phục vụ Vue SPA và reverse-proxy `/api` | `${WEB_PORT:-8080}` |
| `app` | Spring Boot REST API | chỉ nội bộ Docker |
| `postgres` | PostgreSQL persistent | `${POSTGRES_PORT:-5432}` |

Backend và frontend đều build theo multi-stage Dockerfile, nên image runtime không chứa Maven, Node hoặc source build. Docker health check chỉ dùng để chờ PostgreSQL sẵn sàng; hệ thống không bao gồm bất cứ monitoring, telemetry hay log-shipping nào.

Các biến bắt buộc/có thể thay đổi nằm trong `.env.example`. Không commit file `.env`.
