# PO → Architect Handoff — AI Native CRM (HBLAB)

> Do PO Agent lập. **PO không quyết định kỹ thuật** (kiến trúc, DB, mã, deploy) — mọi mục kỹ thuật dưới đây là **Recommendation/Question gửi Architect**, không phải quyết định chốt. Nguồn: bộ hồ sơ trong `docs/02-analysis/`.
> Phạm vi bàn giao: **32 story tính năng READY** + 5 mục phi tính năng (Ops/NFR) + 9 feature deferred.

## 1. Ràng buộc kiến trúc bắt nguồn từ yêu cầu (Architect quyết cách hiện thực)

### AR-1 — Guardrail tầng service (rủi ro cao nhất)
- Nguồn: BR-017, US-040, T-10, DSG Phần 5.
- Ràng buộc: 3 ranh giới (không tự đổi giai đoạn / không tự đổi tiền & Thắng-Thua / không tự liên hệ khách) + không tự xoá dữ liệu người tạo **phải chặn ở tầng domain/service**, hiệu lực **kể cả khi gọi ngoài UI** (gọi thẳng API/service/tool-call). "Dặn suông AI" không tính.
- Recommendation: mọi lệnh ghi (đổi giai đoạn, sửa tiền, xoá, gửi ra ngoài) đi qua **một policy-guard trung tâm**; A-AI chạy dưới principal bị hạn quyền (least-privilege).
- [ARQ-1] Architect: chọn điểm chặn (middleware/service policy/DB constraint) và cách chứng minh T-10 trong test.

### AR-2 — Mô hình dữ liệu AI-native
- Nguồn: BR-018, DSG Phần 4.
- Ràng buộc ngữ nghĩa: **Observation (Bản lưu) → Claim (Phát hiện) → Proposal (Gợi ý)**, với **Provenance (câu trích + vị trí trong bản lưu)** là sợi truy vết. Không provenance thì không lưu/không hiển thị (BR-006).
- Ràng buộc quan hệ: bản lưu thuộc đúng 1 công ty; phát hiện thừa kế công ty từ bản lưu; phát hiện KHÔNG gắn thẳng cơ hội/người liên hệ/hoạt động.
- [ARQ-2] Architect: cách lưu vị trí câu trích để **bấm→nhảy tới đoạn gốc có đánh dấu** (US-016/T-3).

### AR-3 — Trần tự chủ AI theo vùng
- Nguồn: DSG Phần 5.
- Vùng tự do: đọc nguồn/tạo bản lưu (US-011). Vùng chạy ngầm: rút phát hiện/đề xuất gợi ý/tự đặt Next step/vòng quét (US-013/018/025/031). Vùng cấm: AR-1.

### AR-4 — Công tắc & tham số vận hành
- Kill switch toàn bộ AI, **hiệu lực ngay, không restart**; khi tắt dừng mọi hoạt động AI, dữ liệu đã sinh không xoá (US-037/T-9).
- Chu kỳ vòng quét **cấu hình được, mặc định 60s**, đọc từ **biến môi trường** (US-032, REQ-704).
- [ARQ-3] Architect: cơ chế bật/tắt tức thời an toàn với vòng lặp đang chạy (không để lại tác dụng phụ giữa chu kỳ).

### AR-5 — Vòng lặp tự chủ (Nhóm 5)
- Đọc lại nguồn → so bản lưu gần nhất → nếu có nội dung mới thì rút phát hiện → tự thêm mục timeline (nhãn "do hệ thống thêm" + câu trích) (US-031).
- [ARQ-4] Architect/Dev: thuật toán xác định "nội dung mới" (Q-05, đã duyệt hướng: so đoạn văn chuẩn hoá / phát hiện tương đương). PO không chốt thuật toán.

### AR-6 — Auth & phân vai
- Đăng nhập 2 vai Sales/Quản trị; Quản trị thấy phần đo lường Sales không thấy (US-046, PRD §2). Không phân quyền theo người sở hữu (1 tài khoản Sales).

## 2. Yêu cầu phi chức năng (NFR) — từ §7
| NFR | Nội dung | Nguồn |
|-----|----------|-------|
| NFR-1 | Production build: không dev server/hot reload/debug | REQ-704 |
| NFR-2 | Cấu hình ở biến môi trường (khóa dịch vụ ngoài, chuỗi kết nối DB, chu kỳ vòng quét) | REQ-704 |
| NFR-3 | DB thật, dữ liệu bền qua restart | REQ-704 |
| NFR-4 | Khởi động một lệnh, log ra chỗ xem được | REQ-704 |
| NFR-5 | Nạp dữ liệu một lệnh + reset idempotent | REQ-702 |
| NFR-6 | Chuyển bản chụp trước→sau từ UI hoặc lệnh (test-harness) | REQ-703 |
| NFR-7 | Bộ kiểm thử T-1..T-10 chạy một lệnh, in kết quả rõ | REQ-701 |
| NFR-8 | Log AI Agent (Claude Code) tự thu qua tokens→Grafana | REQ-705, C-LOG-1 |

## 3. Ma trận truy vết (Feature → US → REQ/BR → AC → Test)
| US | FEAT | REQ/BR | AC | T |
|----|------|--------|----|---|
| US-001 | FEAT-001 | REQ-101, BR-001 | AC-001..004 | T-1 |
| US-002 | FEAT-002 | REQ-102, BR-002 | AC-005..007 | T-1 |
| US-003 | FEAT-003 | REQ-103, BR-003/004 | AC-008..009 | T-1 |
| US-004 | FEAT-004 | REQ-104/105 | AC-010..012 | T-1 |
| US-005 | FEAT-005 | REQ-106, BR-005 | AC-013..014 | T-1 |
| US-007 | FEAT-007 | REQ-107/108 | AC-017..018 | T-1 |
| US-008 | FEAT-008 | REQ-109 | AC-019..020 | T-1 |
| US-009 | FEAT-009 | REQ-111 | AC-021..023 | T-1 |
| US-010 | FEAT-010 | REQ-112 | AC-024 | T-1 |
| US-011 | FEAT-011 | REQ-201, BR-018 | AC-025..026 | T-8 |
| US-013 | FEAT-013 | REQ-202/203/207, BR-006/007 | AC-028..030 | T-2 |
| US-015 | FEAT-015 | REQ-204/209 | AC-032..033 | — |
| US-016 | FEAT-016 | REQ-208, BR-018 | AC-034 | T-3 |
| US-018 | FEAT-018 | REQ-301 | AC-036..037 | T-4/T-5 |
| US-019 | FEAT-019 | REQ-302 | AC-038 | T-5 |
| US-020 | FEAT-020 | REQ-303/305, BR-009/010 | AC-039..041 | T-5 |
| US-021 | FEAT-021 | REQ-304, BR-011 | AC-042 | T-4 |
| US-022 | FEAT-022 | REQ-306 | AC-043 | T-5 |
| US-025 | FEAT-025 | REQ-401/402/403, BR-012 | AC-047..049 | T-6 |
| US-026 | FEAT-026 | REQ-404/409 | AC-050..051 | T-6 |
| US-027 | FEAT-027 | REQ-405 | AC-052..053 | T-6 |
| US-028 | FEAT-028 | REQ-406, BR-013 | AC-054..055 | T-7 |
| US-029 | FEAT-029 | REQ-407/408 | AC-056..057 | T-7 |
| US-030 | FEAT-030 | REQ-501 | AC-058..059 | T-8 |
| US-031 | FEAT-031 | REQ-502/503, BR-017 | AC-060..062 | T-8 |
| US-032 | FEAT-032 | REQ-504, BR-014 | AC-063..064 | T-8 |
| US-033 | FEAT-033 | REQ-505, BR-015 | AC-065..066 | T-8 |
| US-037 | FEAT-037 | REQ-603, BR-016 | AC-070..072 | T-9 |
| US-038 | FEAT-038 | REQ-604 | AC-073 | T-9 |
| US-039 | FEAT-039 | REQ-605, BR-016 | AC-074 | T-9 |
| US-040 | FEAT-040 | BR-017, REQ-206/113 | AC-075..077 | T-10/T-1 |
| US-046 | FEAT-046 | REQ-704, PRD §2 | AC-088..090 | — |

> Phi tính năng phục vụ test: US-041 (T-6/T-8 harness), US-042 (nền mọi T), US-043 (chạy T-1..T-10), US-044 (NFR), US-045 (nộp bài).
> 10/10 điểm nghiệm thu T-1..T-10 đều có US/AC neo tương ứng.

## 4. Câu hỏi/quyết định dành cho Architect (PO không tự quyết)
- [ARQ-1] Điểm & cơ chế enforce guardrail ngoài UI (AR-1).
- [ARQ-2] Lưu & trỏ vị trí câu trích cho provenance jump (AR-2).
- [ARQ-3] Bật/tắt AI tức thời an toàn giữa chu kỳ vòng lặp (AR-4).
- [ARQ-4] Thuật toán "nội dung mới" cho vòng quét (AR-5, Q-05).
- [ARQ-5] Chiến lược idempotent reset dữ liệu (NFR-5) — gồm cả dữ liệu sinh lúc chạy.
- [ARQ-6] Mô hình lưu trữ 4 đối tượng AI-native + độ tin cậy (AR-2) trên DB đội chọn.
- [ARQ-7] Cách chống trùng khi cùng một phát hiện có thể tạo mục timeline từ 2 đường (Nhóm 3 duyệt vs Nhóm 5 auto — Q-01/Q-02 đã duyệt hướng, cần hiện thực chống trùng).

## 5. Ghi chú bàn giao
- Backlog đã PO-approved (32 tính năng READY). Thay đổi phạm vi sau bàn giao cần qua `/po-refine`.
- Ưu tiên kiến trúc trước: AR-1 (guardrail) + AR-2 (mô hình dữ liệu) vì chặn phần lớn story AI phía sau.
