# Function Decomposition — AI Native CRM (HBLAB Hackathon)

> Trạng thái: **PO REVIEW** (chưa phê duyệt). Dựa trên `requirement-analysis.md`.
> Phân rã theo **năng lực nghiệp vụ / mục tiêu người dùng**, không theo tầng kỹ thuật.
> Traceability: Product → Domain → Epic → Feature → (User Story stubs). Mọi node truy về REQ/BR.

## Product
**AI-Native CRM cho Sales B2B ngành ITO (HBLAB)** — CRM làm tay trọn vẹn + lớp AI đọc nguồn công khai, có bằng chứng truy nguồn, người-trong-vòng-lặp và phanh an toàn.

## Cây phân rã (Domain → Epic → Feature → US)

### D1. CRM lõi làm tay  *(Nhóm 1)*
- **EPIC-01 — Quản lý thực thể CRM**
  - **FEAT-001** Quản lý Công ty *(REQ-101)* → US: tạo công ty (bắt buộc tên/ngành/loại) · sửa · xoá · xem chi tiết
  - **FEAT-002** Quản lý Người liên hệ & đầu mối chính *(REQ-102)* → US: thêm/sửa/xoá contact · đặt đúng-một PIC
  - **FEAT-003** Quản lý Cơ hội *(REQ-103)* → US: tạo/sửa/xoá cơ hội với giá trị & tháng chốt
- **EPIC-02 — Vận hành phễu bán hàng**
  - **FEAT-004** Bảng 7 giai đoạn kéo-thả *(REQ-104, REQ-105)* → US: kéo tiến/lùi/nhảy cóc không bị chặn
  - **FEAT-005** Chốt chặn Đủ điều kiện *(REQ-106)* → US: nhập 2 dấu hiệu · bỏ qua → mang cờ cảnh báo
  - **FEAT-006** Ghi lý do Thua *(REQ-110)* → US: hỏi lý do khi sang Thua · bỏ qua → cờ + ngoài thống kê
- **EPIC-03 — Nhịp làm việc hằng ngày**
  - **FEAT-007** Hoạt động & Dòng thời gian công ty *(REQ-107, REQ-108)* → US: ghi hoạt động · xem timeline gộp mới-nhất-trên
  - **FEAT-008** Việc tiếp theo & ngày hạn *(REQ-109)* → US: đặt Next step · cờ khi thiếu · ẩn khỏi to-do tới khi đủ
  - **FEAT-009** Tìm kiếm & Lọc *(REQ-111)* → US: tìm công ty theo tên · lọc công ty · lọc cơ hội
  - **FEAT-010** Màn hình tổng quan *(REQ-112)* → US: thống kê ngành/giai đoạn/Next step quá hạn

### D2. Đọc nguồn & Tri thức — Observation/Claim/Provenance  *(Nhóm 2)*
- **EPIC-04 — Thu thập & lưu nguồn (Observation)**
  - **FEAT-011** Bản lưu nguồn *(REQ-201)* → US: đọc nguồn → lưu nguyên văn + nguồn + thời điểm, gắn công ty
  - **FEAT-012** Xử lý nguồn không đọc được *(REQ-211)* → US: ghi "không đọc được", không đoán
- **EPIC-05 — Rút phát hiện (Claim) & Truy nguồn (Provenance)**
  - **FEAT-013** Rút phát hiện có provenance *(REQ-202, REQ-203, REQ-207)* → US: sinh phát hiện (loại tin/câu trích/vị trí/mức) · chặn lưu nếu thiếu câu trích
  - **FEAT-014** Đọc phát hiện theo góc loại công ty *(REQ-205)* → US: nhận định phản ánh loại công ty
  - **FEAT-015** Vùng đọc & hiển thị mức chắc chắn *(REQ-204, REQ-209)* → US: khu riêng trong màn hình công ty · 3 mức bằng ký hiệu/màu
  - **FEAT-016** Xem nguồn gốc phát hiện *(REQ-208)* → US: bấm phát hiện → nhảy tới đoạn trích có đánh dấu
  - **FEAT-017** Tích lũy phát hiện qua nhiều lần đọc *(REQ-210)* → US: không xoá cũ; mỗi phát hiện mang thời điểm riêng

### D3. Gợi ý & Duyệt — Proposal / Human-in-the-loop  *(Nhóm 3)*
- **EPIC-06 — Hàng đợi gợi ý chờ duyệt**
  - **FEAT-018** Sinh gợi ý 2 loại vào hàng đợi *(REQ-301)* → US: gợi ý thêm-tin · gợi ý điền/sửa-ô  *(phụ thuộc Q-01/Q-06)*
  - **FEAT-019** Thẻ gợi ý đủ 4 thứ tại chỗ *(REQ-302)* → US: hiện tại→đề nghị · câu trích · mức · hệ quả-nếu-sai  *(Q-07)*
  - **FEAT-020** Quyết định Duyệt/Sửa-rồi-duyệt/Bỏ *(REQ-303, REQ-305)* → US: 3 nút · Bỏ kèm lý do · tách số sửa/duyệt
  - **FEAT-021** Không-duyệt-không-đổi *(REQ-304)* → US: không auto-apply/tự-duyệt qua nhiều chu kỳ
  - **FEAT-022** Nhật ký quyết định & đo thời gian quyết *(REQ-306)* → US: lưu ai/lúc nào/quyết gì/số giây  *(Q-05→AS-05)*
  - **FEAT-023** Chống sinh lại gợi ý đã bỏ *(REQ-307)* → US: không tái sinh cùng nội dung trừ khi có bản lưu mới
  - **FEAT-024** Chỉ báo "đang có gợi ý chờ duyệt" *(REQ-308)* → US: badge ở danh sách cơ hội & màn hình công ty

### D4. Tự đặt Việc tiếp theo — Autonomy có kiểm soát  *(Nhóm 4)*
- **EPIC-07 — Tự đặt Việc tiếp theo & Hoàn tác**
  - **FEAT-025** Tự điền Next step + ngày hạn theo độ gấp *(REQ-401, REQ-402, REQ-403)* → US: kích hoạt khi có cơ hội mở · kèm câu trích · hạn theo loại tín hiệu  *(Q-03/Q-04)*
  - **FEAT-026** Dấu hiệu hệ thống & không đè tay *(REQ-404, REQ-409)* → US: đánh dấu ô hệ thống · không đè Next step tay chưa tới hạn
  - **FEAT-027** Thông báo bền tới khi xem *(REQ-405)* → US: báo đặt gì/cơ hội nào/vì sao  *(Q-08)*
  - **FEAT-028** Hoàn tác 1-cú-bấm trong 7 ngày *(REQ-406)* → US: hoàn tác về giá trị cũ · hiện cửa sổ 7 ngày · hết hạn ẩn nút
  - **FEAT-029** Ghi vết tự đặt & hoàn tác *(REQ-407, REQ-408)* → US: log tự đặt · log hoàn tác (feed số liệu Quản trị)

### D5. Vòng quét theo dõi — Autonomous loop  *(Nhóm 5)*
- **EPIC-08 — Theo dõi & vòng quét khép kín**
  - **FEAT-030** Nhãn Đang theo dõi & danh sách riêng *(REQ-501)* → US: bật/tắt 1 thao tác · màn hình danh sách theo dõi
  - **FEAT-031** Vòng lặp tự đọc→so→rút→thêm timeline *(REQ-502, REQ-503)* → US: tự thêm mục "do hệ thống thêm" + câu trích · không dừng chờ duyệt  *(Q-01/Q-02/Q-05)*
  - **FEAT-032** Chu kỳ cấu hình mặc định 60s *(REQ-504)* → US: đọc chu kỳ từ cấu hình *(chỉnh ở FEAT-036)*
  - **FEAT-033** Nhật ký vòng quét + tổng hợp mỗi 10 vòng *(REQ-505)* → US: dòng tổng kết mỗi vòng · dòng cộng dồn mỗi 10 vòng
  - **FEAT-034** Sales xoá mục hệ thống thêm *(REQ-506)* → US: xoá như mọi mục timeline khác

### D6. Quản trị & An toàn AI — Governance  *(Nhóm 6)*
- **EPIC-09 — Bảng điều khiển Quản trị**
  - **FEAT-035** Bảng đo lường chất lượng AI *(REQ-601)* → US: phân bố mức · tỉ lệ duyệt/sửa/bỏ + lý do · thời gian quyết TB · tỉ lệ hoàn tác
  - **FEAT-036** Chỉnh chu kỳ vòng quét *(REQ-602)* → US: sửa tham số (đơn vị/mặc định/giải thích) · hiệu lực ngay
- **EPIC-10 — Phanh an toàn AI**
  - **FEAT-037** Kill switch toàn bộ AI *(REQ-603)* → US: tắt ngay không restart · dừng mọi hoạt động AI · giữ dữ liệu
  - **FEAT-038** Hiển thị trạng thái AI tắt cho Sales *(REQ-604)* → US: dòng thông báo đang tắt
  - **FEAT-039** Ghi vết bật/tắt *(REQ-605)* → US: log mỗi lần tắt/bật + thời điểm

### D7. Ranh giới, Nghiệm thu & Vận hành — Guardrails & Delivery  *(§5, §6, §7)*
- **EPIC-11 — Ranh giới cứng của AI**
  - **FEAT-040** Chặn 4 ranh giới ở tầng service (cả ngoài UI) *(BR-017, REQ-206, REQ-113)* → US: chặn tự đổi giai đoạn/tiền/xoá · chặn qua API bỏ UI  *(Q-10)*
- **EPIC-12 — Kịch bản demo & dữ liệu**
  - **FEAT-041** Chuyển bản chụp trước→sau *(REQ-703)* → US: đổi từ UI hoặc một lệnh
  - **FEAT-042** Nạp/Reset dữ liệu idempotent một lệnh *(REQ-702, AS-03)* → US: nạp data + seed 2 tài khoản · chạy lại → trạng thái ban đầu
- **EPIC-13 — Nghiệm thu & production**
  - **FEAT-043** Bộ kiểm thử tự động T-1..T-10 *(REQ-701)* → US: chạy một lệnh · in kết quả rõ
  - **FEAT-044** Bản dựng production + env + đăng nhập 2 vai *(REQ-704)* → US: một lệnh khởi động · config env · DB bền qua restart · log xem được
  - **FEAT-045** Mã nguồn GitLab + log Claude Code → Grafana *(REQ-705, C-LOG-1, Q-11)* → US: repo GitLab · log AI Agent lên Grafana · lưu prompt log

---

## Function List
`Status` ∈ {Draft, Ready-for-Story, In-Refinement, Blocked}. `Priority` = TBD (chờ skill prioritization).
"In-Refinement" = phụ thuộc Proposed Resolution đang chờ PO chốt.

| ID | Domain | Epic | Feature | Mô tả | Actor | Source | Priority | Status |
|----|--------|------|---------|-------|-------|--------|----------|--------|
| FEAT-001 | CRM lõi | EPIC-01 | Quản lý Công ty | CRUD + chi tiết; tạo bắt buộc tên/ngành/loại | Sales | REQ-101 | TBD | Ready-for-Story |
| FEAT-002 | CRM lõi | EPIC-01 | Người liên hệ & PIC | CRUD contact; đúng-một đầu mối chính | Sales | REQ-102 | TBD | Ready-for-Story |
| FEAT-003 | CRM lõi | EPIC-01 | Quản lý Cơ hội | CRUD cơ hội; giá trị & tháng chốt | Sales | REQ-103 | TBD | Ready-for-Story |
| FEAT-004 | CRM lõi | EPIC-02 | Bảng 7 giai đoạn kéo-thả | Đổi giai đoạn kéo thả, lùi/nhảy cóc, không chặn | Sales | REQ-104,105 | TBD | Ready-for-Story |
| FEAT-005 | CRM lõi | EPIC-02 | Chốt chặn Đủ điều kiện | 2 dấu hiệu nhu cầu/ngân sách; bỏ qua → cờ | Sales | REQ-106 | TBD | Ready-for-Story |
| FEAT-006 | CRM lõi | EPIC-02 | Ghi lý do Thua | Hỏi lý do; bỏ qua → cờ + ngoài thống kê | Sales | REQ-110 | TBD | Ready-for-Story |
| FEAT-007 | CRM lõi | EPIC-03 | Hoạt động & Dòng thời gian | Ghi hoạt động; timeline gộp mới-nhất-trên | Sales | REQ-107,108 | TBD | In-Refinement (Q-09) |
| FEAT-008 | CRM lõi | EPIC-03 | Việc tiếp theo & hạn | Next step + hạn; cờ khi thiếu; ẩn khỏi to-do | Sales | REQ-109 | TBD | Ready-for-Story |
| FEAT-009 | CRM lõi | EPIC-03 | Tìm kiếm & Lọc | Tìm công ty; lọc công ty & cơ hội | Sales | REQ-111 | TBD | Ready-for-Story |
| FEAT-010 | CRM lõi | EPIC-03 | Màn hình tổng quan | Thống kê ngành/giai đoạn/Next step quá hạn | Sales | REQ-112 | TBD | Ready-for-Story |
| FEAT-011 | Đọc nguồn | EPIC-04 | Bản lưu nguồn | Đọc → lưu nguyên văn + nguồn + thời điểm | A-AI | REQ-201 | TBD | Ready-for-Story |
| FEAT-012 | Đọc nguồn | EPIC-04 | Nguồn không đọc được | Ghi "không đọc được", không đoán | A-AI | REQ-211 | TBD | Ready-for-Story |
| FEAT-013 | Đọc nguồn | EPIC-05 | Rút phát hiện + provenance | Loại tin/câu trích/vị trí/mức; chặn thiếu câu trích | A-AI | REQ-202,203,207 | TBD | In-Refinement (C-2) |
| FEAT-014 | Đọc nguồn | EPIC-05 | Phát hiện theo loại công ty | Nhận định phản ánh loại công ty | A-AI | REQ-205 | TBD | Ready-for-Story |
| FEAT-015 | Đọc nguồn | EPIC-05 | Vùng đọc & mức chắc chắn | Khu riêng; 3 mức ký hiệu/màu | Sales | REQ-204,209 | TBD | Ready-for-Story |
| FEAT-016 | Đọc nguồn | EPIC-05 | Xem nguồn gốc phát hiện | Bấm → nhảy đoạn trích có đánh dấu | Sales | REQ-208 | TBD | Ready-for-Story |
| FEAT-017 | Đọc nguồn | EPIC-05 | Tích lũy phát hiện | Không xoá cũ; mỗi cái thời điểm riêng | A-AI | REQ-210 | TBD | Ready-for-Story |
| FEAT-018 | Gợi ý & Duyệt | EPIC-06 | Sinh gợi ý 2 loại | Thêm-tin / điền-sửa-ô vào hàng đợi | A-AI | REQ-301 | TBD | In-Refinement (Q-01,Q-06) |
| FEAT-019 | Gợi ý & Duyệt | EPIC-06 | Thẻ gợi ý đủ 4 thứ | Hiện tại→đề nghị/câu trích/mức/hệ quả | Sales | REQ-302 | TBD | In-Refinement (Q-07) |
| FEAT-020 | Gợi ý & Duyệt | EPIC-06 | Quyết định gợi ý | Duyệt/Sửa-rồi-duyệt/Bỏ + lý do; tách số | Sales | REQ-303,305 | TBD | Ready-for-Story |
| FEAT-021 | Gợi ý & Duyệt | EPIC-06 | Không-duyệt-không-đổi | Không auto-apply/tự-duyệt | A-AI | REQ-304 | TBD | Ready-for-Story |
| FEAT-022 | Gợi ý & Duyệt | EPIC-06 | Nhật ký & đo thời gian quyết | Lưu ai/lúc nào/quyết gì/số giây | A-Admin | REQ-306 | TBD | In-Refinement (AS-05) |
| FEAT-023 | Gợi ý & Duyệt | EPIC-06 | Chống sinh lại gợi ý đã bỏ | Không tái sinh trừ khi có bản lưu mới | A-AI | REQ-307 | TBD | Ready-for-Story |
| FEAT-024 | Gợi ý & Duyệt | EPIC-06 | Chỉ báo gợi ý chờ duyệt | Badge ở danh sách cơ hội & công ty | Sales | REQ-308 | TBD | Ready-for-Story |
| FEAT-025 | Tự đặt Next step | EPIC-07 | Tự điền Next step theo độ gấp | Kích hoạt khi có cơ hội mở; kèm câu trích; hạn theo tín hiệu | A-AI | REQ-401,402,403 | TBD | In-Refinement (Q-03,Q-04) |
| FEAT-026 | Tự đặt Next step | EPIC-07 | Dấu hiệu hệ thống & không đè tay | Đánh dấu ô hệ thống; không đè tay chưa tới hạn | Sales / A-AI | REQ-404,409 | TBD | Ready-for-Story |
| FEAT-027 | Tự đặt Next step | EPIC-07 | Thông báo bền tới khi xem | Đặt gì/cơ hội nào/vì sao | Sales | REQ-405 | TBD | In-Refinement (Q-08) |
| FEAT-028 | Tự đặt Next step | EPIC-07 | Hoàn tác 1-cú-bấm 7 ngày | Về giá trị cũ; cửa sổ 7 ngày; hết hạn ẩn | Sales | REQ-406 | TBD | Ready-for-Story |
| FEAT-029 | Tự đặt Next step | EPIC-07 | Ghi vết tự đặt & hoàn tác | Log tự đặt & hoàn tác | A-Admin | REQ-407,408 | TBD | Ready-for-Story |
| FEAT-030 | Vòng quét | EPIC-08 | Nhãn Đang theo dõi & danh sách | Bật/tắt 1 thao tác; màn hình riêng | Sales | REQ-501 | TBD | Ready-for-Story |
| FEAT-031 | Vòng quét | EPIC-08 | Vòng lặp tự thêm timeline | Đọc→so→rút→tự thêm "do hệ thống thêm"+câu trích | A-AI | REQ-502,503 | TBD | In-Refinement (Q-01,Q-02,Q-05) |
| FEAT-032 | Vòng quét | EPIC-08 | Chu kỳ cấu hình 60s | Đọc chu kỳ từ cấu hình | A-AI | REQ-504 | TBD | Ready-for-Story |
| FEAT-033 | Vòng quét | EPIC-08 | Nhật ký vòng quét | Dòng mỗi vòng + cộng dồn mỗi 10 vòng | A-Admin | REQ-505 | TBD | Ready-for-Story |
| FEAT-034 | Vòng quét | EPIC-08 | Xoá mục hệ thống thêm | Sales xoá như mọi mục timeline | Sales | REQ-506 | TBD | Ready-for-Story |
| FEAT-035 | Quản trị | EPIC-09 | Bảng đo lường chất lượng AI | Mức/tỉ lệ duyệt-sửa-bỏ/thời gian quyết/hoàn tác | A-Admin | REQ-601 | TBD | Ready-for-Story |
| FEAT-036 | Quản trị | EPIC-09 | Chỉnh chu kỳ vòng quét | Đơn vị/mặc định/giải thích; hiệu lực ngay | A-Admin | REQ-602 | TBD | Ready-for-Story |
| FEAT-037 | Quản trị | EPIC-10 | Kill switch toàn bộ AI | Tắt ngay không restart; giữ dữ liệu | A-Admin | REQ-603 | TBD | Ready-for-Story |
| FEAT-038 | Quản trị | EPIC-10 | Trạng thái AI tắt cho Sales | Dòng thông báo đang tắt | Sales | REQ-604 | TBD | Ready-for-Story |
| FEAT-039 | Quản trị | EPIC-10 | Ghi vết bật/tắt | Log mỗi lần + thời điểm | A-Admin | REQ-605 | TBD | Ready-for-Story |
| FEAT-040 | Guardrails | EPIC-11 | Chặn 4 ranh giới ở tầng service | Chặn tự đổi giai đoạn/tiền/xoá; cả ngoài UI | A-Admin (A-AI bị chặn) | BR-017,REQ-206,113 | TBD | Ready-for-Story |
| FEAT-041 | Delivery | EPIC-12 | Chuyển bản chụp trước→sau | Đổi từ UI hoặc một lệnh | A-Admin | REQ-703 | TBD | Test-harness (phi tính năng) |
| FEAT-042 | Delivery | EPIC-12 | Nạp/Reset dữ liệu idempotent | Một lệnh; seed 2 tài khoản; reset về ban đầu | Ops (không login CRM) | REQ-702,AS-03 | TBD | Ops/Delivery |
| FEAT-043 | Delivery | EPIC-13 | Bộ kiểm thử T-1..T-10 | Một lệnh; in kết quả rõ | Ops (không login CRM) | REQ-701 | TBD | Ops/Delivery |
| FEAT-044 | Delivery | EPIC-13 | Production build + env + persistence | Một lệnh khởi động; DB bền; log xem được | Ops (dựng) | REQ-704 | TBD | NFR/Ops (phi tính năng) |
| FEAT-045 | Delivery | EPIC-13 | GitLab + log Claude Code→Grafana | Repo GitLab; log AI Agent tự thu qua tokens→Grafana | Ops (không login CRM) | REQ-705,C-LOG-1 | TBD | Ops/Delivery |
| FEAT-046 | Truy cập | EPIC-14 | Đăng nhập & phân vai Sales/Quản trị | Auth + hiển thị theo vai (Quản trị thấy phần Sales không thấy) | Sales, Admin | REQ-704, PRD §2 | TBD | Ready-for-Story |

## Phân loại tính năng vs phi-tính-năng (sau review actor & feature)
- **Tính năng hệ thống (feature backlog):** toàn bộ D1–D6 + FEAT-040 (guardrail) + **FEAT-046** (đăng nhập & phân vai — thuộc **D8 Truy cập / EPIC-14**, tách từ FEAT-044).
- **Phi tính năng (Ops/Delivery/NFR/Test-harness — ngoài feature backlog):** FEAT-041 (test-harness), FEAT-042, FEAT-043, FEAT-044, FEAT-045. Vẫn bắt buộc theo §6/§7.

## Ghi chú traceability
- Mọi FEAT truy về ít nhất một REQ/BR — không có node mồ côi.
- REQ-113 & REQ-206 là ràng buộc chéo → hiện thực trong **FEAT-040** (guardrail), đồng thời kiểm ở **FEAT-043** (T-1, T-2/T-3).
- Danh mục loại tin (C-2) ảnh hưởng FEAT-013/018/025 → cần chốt trước khi viết AC.
- **FEAT-046** neo REQ-704 (đăng nhập) + PRD §2 (phân vai) — trước đây bị gộp trong FEAT-044, nay tách ra để giữ đúng là tính năng.

## ⏸ PO REVIEW — dừng tại đây
Đề nghị PO: (1) duyệt Proposed Resolutions ở `requirement-analysis.md`; (2) chốt priority (chạy `/po-prioritize`); (3) cho phép viết User Stories + Acceptance Criteria (`/po-stories`, `/po-ac`) cho các FEAT "Ready-for-Story" trước, các "In-Refinement" sau khi chốt Q/AS.
