# Backlog Prioritization — AI Native CRM (HBLAB Hackathon)

> Trạng thái: **PO REVIEW**. Framework: **Business Value + Urgency + Dependency + Risk** (1–5 mỗi yếu tố; Score 4–20).
> Nguyên tắc urgency: feature bị 10 test T-1..T-10 hoặc yêu cầu nộp bài chạm tới → urgency cao (deadline cứng).
> Hard rule: agent đề xuất, PO chốt. MoSCoW luôn cần PO xác nhận.

## Bảng chấm (sắp theo Score giảm dần)
| ID | Feature | BV | Urg | Dep | Risk | Score | MoSCoW | Conf | PO Approval |
|----|---------|----|----|-----|------|-------|--------|------|-------------|
| FEAT-013 | Rút phát hiện + provenance | 5 | 5 | 5 | 4 | 19 | Must | High | no |
| FEAT-042 | Nạp/Reset dữ liệu idempotent | 4 | 5 | 5 | 4 | 18 | Must | High | no |
| FEAT-001 | Quản lý Công ty | 5 | 4 | 5 | 3 | 17 | Must | High | no |
| FEAT-003 | Quản lý Cơ hội | 5 | 4 | 5 | 3 | 17 | Must | High | no |
| FEAT-041 | Chuyển bản chụp trước→sau | 4 | 5 | 5 | 3 | 17 | Must | High | no |
| FEAT-044 | Production + env + 2 vai | 4 | 5 | 4 | 4 | 17 | Must | High | no |
| FEAT-025 | Tự điền Next step theo độ gấp | 5 | 5 | 3 | 4 | 17 | Must | High | no |
| FEAT-031 | Vòng lặp tự thêm timeline | 5 | 5 | 3 | 4 | 17 | Must | High | no |
| FEAT-040 | Chặn 4 ranh giới ở tầng service | 4 | 5 | 3 | 5 | 17 | Must | High | no |
| FEAT-008 | Việc tiếp theo & hạn | 5 | 4 | 4 | 3 | 16 | Must | High | no |
| FEAT-011 | Bản lưu nguồn | 4 | 4 | 5 | 3 | 16 | Must | High | no |
| FEAT-028 | Hoàn tác 7 ngày | 5 | 5 | 2 | 4 | 16 | Must | High | no |
| FEAT-043 | Bộ kiểm thử T-1..T-10 | 4 | 5 | 3 | 4 | 16 | Must | High | no |
| FEAT-018 | Sinh gợi ý 2 loại | 4 | 4 | 4 | 3 | 15 | Should | Med | no |
| FEAT-037 | Kill switch toàn bộ AI | 4 | 5 | 2 | 4 | 15 | Should | High | no |
| FEAT-004 | Bảng 7 giai đoạn kéo-thả | 5 | 4 | 3 | 2 | 14 | Should | High | no |
| FEAT-007 | Hoạt động & Dòng thời gian | 4 | 4 | 4 | 2 | 14 | Should | High | no |
| FEAT-021 | Không-duyệt-không-đổi | 4 | 4 | 2 | 4 | 14 | Should | High | no |
| FEAT-026 | Dấu hiệu hệ thống & không đè tay | 4 | 4 | 2 | 4 | 14 | Should | High | no |
| FEAT-030 | Nhãn Đang theo dõi & danh sách | 4 | 4 | 4 | 2 | 14 | Should | High | no |
| FEAT-016 | Xem nguồn gốc phát hiện | 4 | 4 | 2 | 3 | 13 | Should | High | no |
| FEAT-020 | Quyết định gợi ý | 4 | 4 | 2 | 3 | 13 | Should | High | no |
| FEAT-029 | Ghi vết tự đặt & hoàn tác | 3 | 4 | 3 | 3 | 13 | Should | High | no |
| FEAT-002 | Người liên hệ & PIC | 4 | 3 | 3 | 2 | 12 | Should | Med | YES |
| FEAT-005 | Chốt chặn Đủ điều kiện | 4 | 4 | 2 | 2 | 12 | Should | High | no |
| FEAT-019 | Thẻ gợi ý đủ 4 thứ | 4 | 3 | 2 | 3 | 12 | Should | Med | no |
| FEAT-022 | Nhật ký & thời gian quyết | 3 | 4 | 3 | 2 | 12 | Should | High | no |
| FEAT-032 | Chu kỳ cấu hình 60s | 3 | 4 | 3 | 2 | 12 | Should | High | no |
| FEAT-033 | Nhật ký vòng quét | 3 | 5 | 2 | 2 | 12 | Should | High | no |
| FEAT-015 | Vùng đọc & mức chắc chắn | 4 | 3 | 3 | 2 | 12 | Should | Med | no |
| FEAT-045 | GitLab + log Grafana | 3 | 4 | 2 | 3 | 12 | Should | High | no |
| FEAT-027 | Thông báo bền tới khi xem | 3 | 4 | 2 | 2 | 11 | Could | High | no |
| FEAT-038 | Trạng thái AI tắt cho Sales | 3 | 5 | 1 | 2 | 11 | Could | High | no |
| FEAT-009 | Tìm kiếm & Lọc | 3 | 5 | 1 | 1 | 10 | Could | High | no |
| FEAT-010 | Màn hình tổng quan | 3 | 5 | 1 | 1 | 10 | Could | High | no |
| FEAT-035 | Bảng đo lường chất lượng AI | 3 | 3 | 2 | 2 | 10 | Could | Med | YES |
| FEAT-036 | Chỉnh chu kỳ vòng quét | 3 | 3 | 2 | 2 | 10 | Could | Med | YES |
| FEAT-006 | Ghi lý do Thua | 3 | 2 | 2 | 2 | 9 | Could | Med | YES |
| FEAT-014 | Phát hiện theo loại công ty | 3 | 2 | 2 | 2 | 9 | Could | Med | YES |
| FEAT-017 | Tích lũy phát hiện | 3 | 2 | 2 | 2 | 9 | Could | Med | YES |
| FEAT-039 | Ghi vết bật/tắt | 2 | 4 | 1 | 2 | 9 | Could | High | no |
| FEAT-012 | Nguồn không đọc được | 2 | 2 | 2 | 2 | 8 | Could | Med | YES |
| FEAT-023 | Chống sinh lại gợi ý đã bỏ | 2 | 2 | 1 | 2 | 7 | Won't-now | Med | YES |
| FEAT-034 | Xoá mục hệ thống thêm | 2 | 2 | 1 | 2 | 7 | Won't-now | Med | YES |
| FEAT-024 | Chỉ báo gợi ý chờ duyệt | 2 | 2 | 1 | 1 | 6 | Won't-now | Med | YES |

## Đề xuất chia wave (thứ tự thực thi)
- **Wave 0 — Hạ tầng & khung dữ liệu:** FEAT-042, FEAT-001, FEAT-003, FEAT-011, FEAT-041, FEAT-044.
- **Wave 1 — Lõi AIX + acceptance:** FEAT-013, FEAT-008, FEAT-040, FEAT-025, FEAT-028, FEAT-031, FEAT-043.
- **Wave 2 — HITL + Nhóm1 hoàn thiện + Governance:** FEAT-018/019/020/021/022, FEAT-016, FEAT-004/005/007/002, FEAT-030/032/033, FEAT-026/029, FEAT-037.
- **Wave 3 — Phụ trợ & Could:** FEAT-009/010/006, FEAT-014/015/017/012, FEAT-027/038/039, FEAT-035/036, FEAT-045, FEAT-023/024/034.

## Rationale & Open questions ảnh hưởng thứ hạng
- FEAT-013 cao nhất: lõi AIX, chặn Nhóm 3/4/5, bị T-2 kiểm trực tiếp.
- FEAT-042/041/044: hạ tầng demo — không có thì không test nào chạy được (T-6/T-8), không diễn lại demo.
- FEAT-040: Risk=5 vì T-10 đòi chặn ngoài UI — điểm dễ trượt nhất.
- **Acceptance-mandatory dù điểm thấp:** FEAT-009/010 (T-1), FEAT-038/039 (T-9), FEAT-033 (T-8), FEAT-027 (T-6) — vẫn phải làm.
- Nếu BTC không chấm đo lường Quản trị vòng 1 → FEAT-035/036 xuống Won't-now.
- Nếu Deployment (15%) đòi CI/CD thật → FEAT-044/045 tăng urgency.

## ⏸ PO REVIEW
MoSCoW & các item flag "Needs PO Approval = YES" cần PO xác nhận trước khi khoá priority.
