# Requirement Analysis — AI Native CRM (HBLAB Hackathon)

> Trạng thái: **PO REVIEW** (chưa được phê duyệt). Tác giả: Software Product Owner Agent.
> Nguồn gốc: `docs/01-product/prd.md` + tài liệu `docs/01-product/source/`.
> Quy tắc: Evidence-first · Traceability REQ→EPIC→FEAT→US→AC · Không tự phê duyệt.

## Nguồn & mã nguồn (Source IDs)
| ID | Tài liệu | Vai trò |
|---|---|---|
| **PRD** | `docs/01-product/prd.md` (§1–§7) | Yêu cầu chính — "cần cái gì & hệ thống cư xử ra sao" |
| **PLB** | Business Playbook (Sales B2B ITO) | Tri thức nghiệp vụ nền — signal/PIC/SQL/ICP |
| **DSG** | Thiết kế phần mềm AI-native | Observation/Claim/Proposal/Provenance, trần tự chủ AI |
| **RUB** | Checklist chấm điểm | Tiêu chí nghiệm thu quá trình (không phải feature) |
| **QNA** | Hỏi–Đáp cuộc thi | Làm rõ ràng buộc nộp bài & dữ liệu |

> PRD nêu rõ chỉ mô tả **cần cái gì**; cách dựng/chia tầng/chọn công cụ/bố cục UI do đội tự quyết. Mọi phát biểu kỹ thuật/UI cụ thể là quyết định của đội, không phải yêu cầu.

## Business Goal
- **BG-1** Giải quyết 2 nỗi đau: hồ sơ luôn cũ + gõ lại thông tin công khai ăn hết thời gian. *(CONFIRMED — PRD §1)*
- **BG-2** Một sản phẩm hai nửa liền khối: CRM làm tay trọn vẹn + lớp AI chủ động đẩy thông tin đúng chỗ. *(CONFIRMED — PRD §1)*
- **BG-3** Phần mềm thế hệ AIX/AI-native: AI trong lõi + bằng chứng truy nguồn + mức tin cậy hiển thị + người duyệt trước khi ghi + đo độ đúng + có "phanh". *(CONFIRMED — DSG; PLB mục 11)*
- **BG-4** Nguyên tắc vàng: Bằng chứng trước–khẳng định sau; Fact/suy luận phân biệt bằng mắt; Sales sở hữu dữ liệu; Độ đúng đo được; Next step là nhịp tim của deal. *(CONFIRMED — PLB mục 11)*

## Actors
- **A-Sales** — người dùng thường, bán hàng hằng ngày. *(CONFIRMED)*
- **A-Admin (Quản trị)** — vận hành; xem đo lường; chỉnh tham số; bật/tắt AI. *(CONFIRMED)*
  - *Bao gồm vai "Người sở hữu":* Sales sở hữu công ty; dữ liệu mẫu chỉ 1 tài khoản Sales → **không làm phân quyền theo người sở hữu** (không tách thành actor riêng). *(CONFIRMED)*
- **A-AI (Tác nhân AI tự chủ / Autonomous AI Agent)** — lớp AI **tự khởi động hành động, không do người bấm**, hoạt động trong **trần tự chủ khai báo tường minh** (DSG Phần 5). Đây là "user kiểu mới" của hệ thống AI-native, **không phải** phần mềm CRM nói chung. Sở hữu các hành vi tự động: đọc nguồn & tạo bản lưu, rút phát hiện, *đề xuất* gợi ý (không commit), tự đặt Việc tiếp theo, vòng quét. Ba vùng (DSG): *tự do* (đọc/tạo bản lưu) · *chạy ngầm* (rút phát hiện/gợi ý/tự đặt/vòng quét) · *cấm tuyệt đối* (BR-017). **A-AI là đối tượng BỊ chặn bởi BR-017, không phải người đặt luật chặn.** *(CONFIRMED — PRD §4/§5; DSG Phần 5)*
  - Lưu ý mô hình hoá: khi phần mềm chỉ *phản hồi thao tác của người* (validate, tìm kiếm, hiển thị) thì actor là **Sales/Admin**, KHÔNG mô hình hoá "hệ thống" thành actor.
### Không phải actor của CRM (đã loại khỏi danh sách)
- **Ban giám khảo** — KHÔNG có vai riêng; đăng nhập bằng chính tài khoản **Sales/Quản trị** để thử (PRD §7.3). Việc chạy test/thử tay là hoạt động đánh giá bên ngoài, không phải người dùng hệ thống.
- **Dịch vụ ngoài / LLM** — phụ thuộc được A-AI gọi ra, không phải người dùng → xem **Dependency D-5**.
- **Grafana / GitLab HBLAB** — hạ tầng/hệ thống ngoài, không đăng nhập CRM → xem **C-LOG-1, D-6**.

## Confirmed Requirements

### Nhóm 1 — CRM làm tay (không AI)
- **REQ-101** CRUD + xem chi tiết Công ty; tạo bắt buộc tên/ngành/loại; ô khác tùy chọn.
- **REQ-102** CRUD Người liên hệ (tên/chức danh/email); đúng một đầu mối chính (PIC).
- **REQ-103** Tạo/quản lý Cơ hội (tên/giá trị/tháng chốt/giai đoạn).
- **REQ-104** 7 giai đoạn cố định, không đổi tên: Tiếp cận→Đủ điều kiện→Soạn đề xuất→Thương lượng→Thắng→Thua→Tạm dừng.
- **REQ-105** Đổi giai đoạn bằng kéo thả; cho lùi & nhảy cóc; không chặn.
- **REQ-106** Vào Đủ điều kiện: hỏi 2 ô dấu hiệu nhu cầu + ngân sách; bỏ qua được → cờ cảnh báo; không chặn.
- **REQ-107** Ghi Hoạt động (ngày/loại/mô tả/người liên hệ).
- **REQ-108** Một dòng thời gian công ty gom hoạt động + đổi giai đoạn + ghi chú, mới nhất trên.
- **REQ-109** Cơ hội có Việc tiếp theo + ngày hạn; mở mà thiếu vẫn lưu, mang cờ, không vào danh sách việc phải làm tới khi đủ.
- **REQ-110** Sang Thua → hỏi lý do; bỏ qua được → cờ + ngoài thống kê lý do thua.
- **REQ-111** Tìm công ty theo tên; lọc công ty theo ngành/loại/quốc gia/Đang theo dõi; lọc cơ hội theo giai đoạn & quá hạn.
- **REQ-112** Màn hình tổng quan: công ty theo ngành; cơ hội & tổng giá trị theo giai đoạn; Next step quá hạn.
- **REQ-113** Tắt sạch AI → Nhóm 1 vẫn chạy đủ. *(ràng buộc chéo)*

### Nhóm 2 — Đọc nguồn & rút phát hiện
- **REQ-201** Đọc web → Bản lưu (nguyên văn + nguồn + thời điểm); mỗi bản lưu đúng một công ty; nhiều bản lưu xếp theo thời điểm.
- **REQ-202** Rút Phát hiện: nhận định ngắn + loại tin + câu trích nguyên văn + vị trí trong bản lưu + mức chắc chắn.
- **REQ-203** Mỗi phát hiện đúng một công ty (thừa kế bản lưu); không gắn thẳng cơ hội/người liên hệ/hoạt động.
- **REQ-204** Bản lưu & phát hiện ở khu riêng (vùng đọc) trong màn hình công ty; Sales xem được.
- **REQ-205** Nhận định cho thấy tín hiệu đã đọc dưới góc loại công ty nào.
- **REQ-206** Sinh phát hiện không đổi hồ sơ/timeline/cơ hội. *(ranh giới)*
- **REQ-207** Không lưu được phát hiện thiếu câu trích. *(→T-2)*
- **REQ-208** Bấm phát hiện → mở đúng đoạn gốc có đánh dấu vị trí. *(→T-3)*
- **REQ-209** 3 mức chắc chắn phân biệt không cần đọc chữ (ký hiệu/màu).
- **REQ-210** Đọc lại không xóa phát hiện cũ; mới nằm cạnh, mỗi cái thời điểm riêng.
- **REQ-211** Nguồn không đọc được → ghi "không đọc được"; không đoán.

### Nhóm 3 — Hàng đợi gợi ý (chờ duyệt)
- **REQ-301** Phát hiện mới → sinh Gợi ý vào hàng đợi. 2 loại: (a) thêm tin vào timeline; (b) điền/sửa ô trống/cũ trong hồ sơ.
- **REQ-302** Mỗi gợi ý hiện đủ 4 thứ tại chỗ: hiện tại→đề nghị, câu trích, mức chắc chắn, dòng hệ quả nếu sai.
- **REQ-303** 3 nút Duyệt/Sửa-rồi-duyệt/Bỏ; Bỏ kèm lý do (5 mục); thao tác Bỏ ≤ thao tác Duyệt.
- **REQ-304** Không duyệt = không đổi; không auto-apply/hết-hạn-thành-hành-động/tự-duyệt. *(→T-4)*
- **REQ-305** Sửa-rồi-duyệt ghi là *sửa*, tách khỏi *duyệt*. *(→T-5)*
- **REQ-306** Lưu mỗi gợi ý & quyết định: nội dung/ai/lúc nào/quyết gì/lý do/số giây từ mở→bấm.
- **REQ-307** Gợi ý đã bỏ không sinh lại cùng nội dung trừ khi có bản lưu mới.
- **REQ-308** Danh sách cơ hội & màn hình công ty hiện "đang có gợi ý chờ duyệt".

### Nhóm 4 — Tự đặt Việc tiếp theo
- **REQ-401** Phát hiện đáng chú ý + công ty có ≥1 cơ hội mở → tự điền Next step + ngày hạn ngay, không hỏi.
- **REQ-402** Nội dung tự điền nhắc sự kiện kích hoạt + kèm câu trích.
- **REQ-403** Ngày hạn phản ánh độ gấp theo loại tín hiệu (không cố định).
- **REQ-404** Ô do hệ thống đặt có dấu hiệu phân biệt với ô người gõ.
- **REQ-405** Người sở hữu được báo ngay (đặt gì/cơ hội nào/vì sao); không tự biến mất trước khi xem.
- **REQ-406** Hoàn tác 1-cú-bấm về giá trị cũ; dùng trong 7 ngày; cửa sổ hiện rõ; hết hạn nút biến mất. *(→T-6/T-7)*
- **REQ-407** Ghi mọi lần tự đặt (cơ hội/cũ/mới/phát hiện/lúc nào).
- **REQ-408** Ghi mọi lần hoàn tác (ai/lúc nào/về gì); số & tỉ lệ hoàn tác xem ở Quản trị.
- **REQ-409** Không tự đặt đè lên Next step người nhập tay chưa tới hạn.

### Nhóm 5 — Vòng quét công ty Đang theo dõi
- **REQ-501** Bật/tắt nhãn Đang theo dõi một thao tác; có màn hình danh sách riêng.
- **REQ-502** Vòng lặp khép kín: đọc lại→so bản lưu gần nhất→có mới thì rút phát hiện→tự thêm mục timeline (nhãn "do hệ thống thêm"+câu trích)→lặp.
- **REQ-503** Vòng lặp không dừng chờ duyệt; tự quyết ghi dựa trên "có mới không".
- **REQ-504** Chu kỳ cấu hình được, mặc định 60s (giá trị demo).
- **REQ-505** Sau mỗi vòng ghi 1 dòng Nhật ký (lúc nào/số công ty/số nội dung mới/số mục thêm/thời lượng/lỗi); mỗi 10 vòng 1 dòng cộng dồn. *(→T-8)*
- **REQ-506** Sales xóa được mục hệ thống thêm như mọi mục khác.

### Nhóm 6 — Bảng điều khiển Quản trị
- **REQ-601** Số liệu: phát hiện & phân bố 3 mức; gợi ý & tỉ lệ duyệt/sửa/bỏ + phân bố lý do bỏ; thời gian quyết TB; số tự đặt & tỉ lệ hoàn tác.
- **REQ-602** Chỉnh chu kỳ vòng quét (đơn vị/mặc định/giải thích); hiệu lực ngay.
- **REQ-603** Kill switch toàn bộ AI, hiệu lực ngay không chạy lại; khi tắt dừng mọi hoạt động AI; dữ liệu đã sinh không xóa. *(→T-9)*
- **REQ-604** AI tắt → Sales thấy trạng thái (dòng thông báo); không im lặng.
- **REQ-605** Mỗi lần tắt/bật ghi vết + thời điểm.

### Nhóm 7 — Nghiệm thu & Nộp bài
- **REQ-701** Bộ kiểm thử tự động một lệnh, phủ T-1..T-10, in kết quả rõ.
- **REQ-702** Nạp dữ liệu một lệnh, không gõ tay; chạy lại → về trạng thái ban đầu (idempotent).
- **REQ-703** Chuyển bản chụp trước→sau từ UI hoặc một lệnh (kích hoạt kịch bản AI).
- **REQ-704** Production on-prem giả lập: không dev server/hot reload/debug; config ở env; DB thật bền qua restart; đăng nhập 2 vai; một lệnh khởi động; log xem được.
- **REQ-705** Mã nguồn GitLab HBLAB; log Claude Code → Grafana (log AI Agent; chỉ chấm trong sự kiện).

## Business Rules
- **BR-001** 5 loại công ty: Traditional, IT Solution, IT Product, Tech-based/Startup, ITO khác.
- **BR-002** Người liên hệ thuộc đúng 1 công ty; mỗi công ty đúng 1 đầu mối chính (PIC).
- **BR-003** Một công ty có nhiều cơ hội.
- **BR-004** Mở = {Tiếp cận, Đủ điều kiện, Soạn đề xuất, Thương lượng, Tạm dừng}; Đóng = {Thắng, Thua}.
- **BR-005** Qualify = kiểm cả 2 chiều nhu cầu + ngân sách, mỗi chiều cần fact có nguồn.
- **BR-006** Phát hiện phải có câu trích; không nguồn = không lưu/không hiển thị.
- **BR-007** 3 mức: Chắc (trích thẳng) / Có thể (suy một bước) / Đoán (không bằng chứng trực tiếp).
- **BR-008** Danh mục loại tin — xem xung đột C-2 (đề xuất chốt theo Nhóm 2).
- **BR-009** Bỏ gợi ý cần lý do trong 5 mục; thao tác Bỏ ≤ thao tác Duyệt.
- **BR-010** Sửa-rồi-duyệt ≠ Duyệt; không trộn số.
- **BR-011** Không auto-approve/auto-apply/hết-hạn-thành-hành-động.
- **BR-012** Auto next-step chỉ cho công ty có ≥1 cơ hội mở; không đè Next step tay chưa tới hạn.
- **BR-013** Cửa sổ Hoàn tác = 7 ngày.
- **BR-014** Chu kỳ vòng quét mặc định 60s, cấu hình được, đổi hiệu lực ngay.
- **BR-015** Mỗi 10 vòng 1 dòng cộng dồn.
- **BR-016** Tắt AI: dữ liệu đã sinh không xóa; mọi lần tắt/bật ghi vết.
- **BR-017** **Ranh giới cứng (áp mọi tính năng, cả vòng quét):** (1) không tự đổi giai đoạn; (2) không tự Thắng/Thua, không tự sửa tiền; (3) không tự liên hệ khách (không thư/nhắn) — ranh giới chạm người thật, không cấm gọi mạng; (4) không tự xóa dữ liệu người tạo. **Ba ranh giới đầu chặn được cả khi thao tác ngoài UI; dặn suông AI không tính.** *(→T-10)*
- **BR-018** Kiến trúc gợi ý theo 4 đối tượng AI-native: Observation(=Bản lưu)→Claim(=Phát hiện)→Proposal(=Gợi ý), Provenance(=câu trích+vị trí) là sợi truy vết. *(INFERRED — DSG)*

## Constraints
- **C-DATA-1** Nội dung công ty phải lấy từ bản chụp (tệp tĩnh HTML), không từ web thật. *(QNA=HTML)*
- **C-DATA-2** Dữ liệu mẫu: 12–15 công ty (đủ 5 loại) + ~30 người liên hệ + 8 cơ hội + mỗi công ty 2 bản chụp (trước/sau). BTC phát 15/08/2026.
- **C-TECH-1** Được gọi dịch vụ ngoài & LLM; không giới hạn ngôn ngữ FE/BE/DB.
- **C-DEPLOY-1** Production on-prem giả lập; công ty không cấp VPS/domain; deploy local được.
- **C-TIME-1** Đề phát 12/8; build trước được; thứ 7 chỉ cấp data mẫu; thi ~4 tiếng.
- **C-SCORE-1** Trọng số: Requirement 20/System Design 20/Development 25/Testing 20/Deployment 15. Quality gate: prompt log→bonus; không giải thích được→penalty hộp đen; team đồng bộ 1 cách tiếp cận AI→bonus.
- **C-LOG-1** Log Grafana = log AI Agent (Claude Code), không phải log app; setup tại tokens.hblab.ai:8443; chỉ chấm trong sự kiện.

## Dependencies
- **D-1** Nhóm 2 là nền cho Nhóm 3/4/5.
- **D-2** Nhóm 3/4/5 chạm dữ liệu Sales; Nhóm 2 không chạm.
- **D-3** Nhóm 6 phụ thuộc log/counter của Nhóm 2/3/4/5.
- **D-4** T-6..T-9 phụ thuộc cơ chế chuyển bản chụp trước→sau (REQ-703).
- **D-5** Rút phát hiện có thể phụ thuộc LLM ngoài (tùy chọn kỹ thuật).
- **D-6** Nộp bài phụ thuộc GitLab HBLAB + Grafana/tokens.

## Proposed Resolutions (đề xuất PO — CHỜ DUYỆT)
> Đây là đề xuất của agent để mở khóa phân rã. Chưa phải yêu cầu chính thức.

| Mã | Đề xuất | Phân loại |
|---|---|---|
| Q-01 | Đang theo dõi: gợi ý (a) thêm tin → Nhóm 5 auto-add timeline; gợi ý (b) sửa hồ sơ → luôn qua duyệt Nhóm 3. Một phát hiện → đúng 1 mục timeline. | INFERRED |
| Q-02 | 3 đường (Nhóm 3/4/5) độc lập, không chồng ghi cùng trường; timeline chỉ ghi từ Nhóm 3-duyệt HOẶC Nhóm 5-auto. | INFERRED |
| Q-03 | "Đáng chú ý" = 4 loại kinh điển (gọi vốn/nhân sự cấp cao/mở rộng/tuyển dụng) & mức ∈ {Chắc, Có thể}. | ASSUMPTION |
| Q-04 | Hạn theo độ gấp (cấu hình): gọi vốn +2d · nhân sự cấp cao +5d · mở rộng +10d · tuyển dụng +10d · mảng KD mới/khác +14d. | ASSUMPTION |
| Q-05 | "Nội dung mới" = khác bản lưu gần nhất (so đoạn văn chuẩn hóa) hoặc phát hiện chưa tồn tại tương đương (loại tin + câu trích). | ASSUMPTION |
| Q-06 | Ô "đã cũ" = có phát hiện {Chắc/Có thể} mâu thuẫn/bổ sung; ô trống luôn đủ điều kiện gợi ý điền. | ASSUMPTION |
| Q-07 | Dòng "hệ quả nếu sai" theo mẫu cố định per loại gợi ý × loại tin, AI điền biến. | ASSUMPTION |
| Q-08 | "Đã xem" thông báo = mở màn hình cơ hội đó hoặc bấm "Đã hiểu". | ASSUMPTION |
| Q-09 | Loại Hoạt động cố định {Gặp mặt, Gọi điện, Gửi tài liệu, Email, Khách phản hồi, Khác}. | ASSUMPTION |
| Q-10 | Enforce ranh giới tại tầng service/domain (guard chung); T-10 gọi thẳng service/API. | INFERRED |
| Q-11 | Có lưu prompt log/lịch sử AI (repo docs/ai-logs + Grafana). | INFERRED |
| AS-01 | UI tiếng Việt. | ASSUMPTION |
| AS-02 | Nguồn = HTML nội bộ, không crawl web thật. | CONFIRMED* |
| AS-03 | 2 tài khoản seed sẵn khi nạp dữ liệu; không cần đăng ký. | ASSUMPTION |
| AS-05 | Thời gian quyết đo từ mở→bấm (cùng đồng hồ REQ-306). | ASSUMPTION |
| C-2 | Chốt danh mục loại tin theo Nhóm 2 (6 loại); "nhân sự cấp cao" gồm bổ nhiệm CTO/CIO. | Đề xuất chốt |

## Conflicts / Tensions
- **C-1** Nhóm 2 "không đổi gì" vs Nhóm 4/5 "ghi dữ liệu" → PRD đã giải quyết: phát hiện là nguyên liệu; ghi là việc Nhóm 3/4/5. Cần giữ đúng ranh giới trách nhiệm.
- **C-2** Danh mục loại tin khác nhau giữa §2/§3 và Nhóm 2 → cần chốt một danh mục (đề xuất theo Nhóm 2).
- **C-3** RUB thưởng tự động vs PLB/DSG cảnh báo "duyệt chỉ còn bấm accept" → Nhóm 3 phải giữ verify thật.
- **C-4** Chu kỳ 60s là để demo → tránh race condition/trùng bản ghi khi quét dồn.

## Risks
- **R-1** Niềm tin sập vì thiếu provenance → siết REQ-207/BR-006 (T-2/T-3).
- **R-2** Ranh giới chỉ ở UI → phải enforce tầng service (T-10) — điểm dễ mất nhất.
- **R-3** Trùng lặp timeline do Nhóm 3 & Nhóm 5 cùng ghi (Q-01/Q-02).
- **R-4** Reset dữ liệu không thật idempotent → không diễn lại demo.
- **R-5** Phạt hộp đen nếu không giải thích được AI → cần prompt log (Q-11).
- **R-6** Cửa sổ thi 4 giờ, phạm vi 6 nhóm → phải build trước.
- **R-7** Ngày hạn tự đặt sai độ gấp → mất Right Timing.

## Coverage nghiệm thu (T → REQ)
T-1→101/103/105/106/107/111/112/113 · T-2→207 · T-3→208 · T-4→304 · T-5→303/305/306 · T-6→401/404/405/703 · T-7→406/407/408 · T-8→501/502/505 · T-9→603/604/605 · T-10→BR-017.

## ⏸ PO REVIEW — dừng tại đây (không tự phê duyệt)
Cần PO: (1) duyệt/bác Proposed Resolutions; (2) chốt C-2; (3) xác nhận trước khi viết User Stories & Acceptance Criteria.
