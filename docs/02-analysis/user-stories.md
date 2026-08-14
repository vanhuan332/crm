# User Stories + Acceptance Criteria — AI Native CRM (HBLAB)

> Trạng thái: **PO REVIEW** (chưa DoR-check, chưa phê duyệt). Nguồn: `requirement-analysis.md`, `function-decomposition.md`, `backlog-prioritization.md`.
> Format: INVEST · AC dạng Gherkin. Priority = MoSCoW (Score). DoR Status = Draft (chạy `/po-dor` sau).
> Assumption đã được PO duyệt (Q-01..Q-11, AS-01/03/05, C-2) — dùng làm cơ sở, ghi rõ ở story liên quan.

---

## D1 — CRM lõi làm tay

### US-001 — Quản lý Công ty (CRUD + chi tiết)
FEAT-001 · REQ-101 · BR-001 · Actor: Sales · Priority: Must (17) · Dep: — · DoR: Draft
**As a** Sales **I want** tạo/sửa/xoá/xem chi tiết công ty **so that** tôi có hồ sơ khách chuẩn để làm việc và chịu trách nhiệm.
- **[AC-001]** Scenario: Tạo công ty đủ trường bắt buộc
  Given tôi ở màn hình tạo công ty
  When tôi nhập tên, ngành, loại công ty (1 trong 5 loại) và lưu
  Then công ty được tạo và hiện ở danh sách.
- **[AC-002]** Scenario: Thiếu trường bắt buộc
  Given tôi đang tạo công ty
  When tôi bỏ trống tên hoặc ngành hoặc loại công ty và lưu
  Then hệ thống từ chối lưu và chỉ rõ trường còn thiếu.
- **[AC-003]** Scenario: Ô tuỳ chọn bỏ trống
  Given tôi tạo công ty chỉ với 3 trường bắt buộc
  When tôi lưu
  Then công ty vẫn được tạo với các ô còn lại để trống.
- **[AC-004]** Scenario: Sửa và xoá
  Given một công ty đã tồn tại
  When tôi sửa một trường rồi lưu / hoặc xoá công ty
  Then thay đổi được ghi / công ty bị gỡ khỏi danh sách.
> Split gợi ý nếu cần nhỏ hơn: US-001a Tạo, US-001b Sửa, US-001c Xoá, US-001d Xem chi tiết.

### US-002 — Người liên hệ & đầu mối chính (PIC)
FEAT-002 · REQ-102 · BR-002 · Actor: Sales · Priority: Should (12, cần PO duyệt) · Dep: US-001 · DoR: Draft
**As a** Sales **I want** quản lý người liên hệ dưới công ty và chỉ định đúng một đầu mối chính **so that** tôi biết ai là người sở hữu nỗi đau (PIC) để tiếp cận đúng người.
- **[AC-005]** Scenario: Thêm người liên hệ
  Given tôi ở màn hình một công ty
  When tôi thêm người liên hệ với tên, chức danh, email
  Then người liên hệ thuộc đúng công ty đó.
- **[AC-006]** Scenario: Chỉ một đầu mối chính
  Given công ty đã có một người được đánh dấu đầu mối chính
  When tôi đánh dấu người thứ hai làm đầu mối chính
  Then hệ thống chuyển nhãn sang người mới và bỏ nhãn ở người cũ (luôn còn đúng một).
- **[AC-007]** Scenario: Sửa/xoá người liên hệ
  Given một người liên hệ tồn tại
  When tôi sửa hoặc xoá
  Then thay đổi được ghi lại.

### US-003 — Quản lý Cơ hội
FEAT-003 · REQ-103 · BR-003, BR-004 · Actor: Sales · Priority: Must (17) · Dep: US-001 · DoR: Draft
**As a** Sales **I want** tạo và quản lý cơ hội thuộc một công ty **so that** tôi theo dõi từng thương vụ.
- **[AC-008]** Scenario: Tạo cơ hội
  Given tôi ở màn hình một công ty
  When tôi tạo cơ hội với tên, giá trị dự kiến, tháng dự kiến chốt, giai đoạn
  Then cơ hội được gắn vào công ty đó.
- **[AC-009]** Scenario: Nhiều cơ hội một công ty
  Given công ty đã có một cơ hội
  When tôi tạo cơ hội thứ hai
  Then cả hai cùng tồn tại dưới công ty.

### US-004 — Bảng 7 giai đoạn kéo-thả
FEAT-004 · REQ-104, REQ-105 · BR-004 · Actor: Sales · Priority: Should (14) · Dep: US-003 · DoR: Draft
**As a** Sales **I want** đổi giai đoạn cơ hội bằng kéo-thả **so that** tôi cập nhật tiến độ nhanh mà không mở biểu mẫu.
- **[AC-010]** Scenario: Kéo tiến
  Given một cơ hội ở giai đoạn Tiếp cận
  When tôi kéo sang Đủ điều kiện
  Then cơ hội đổi giai đoạn ngay.
- **[AC-011]** Scenario: Kéo lùi và nhảy cóc
  Given một cơ hội ở Thương lượng
  When tôi kéo về Tiếp cận / hoặc nhảy thẳng sang Soạn đề xuất
  Then hệ thống cho phép, không chặn.
- **[AC-012]** Scenario: Tên & thứ tự giai đoạn cố định
  Given bảng giai đoạn
  Then hiển thị đúng 7 giai đoạn theo thứ tự Tiếp cận→Đủ điều kiện→Soạn đề xuất→Thương lượng→Thắng→Thua→Tạm dừng, không cho đổi tên.

### US-005 — Chốt chặn Đủ điều kiện (2 dấu hiệu)
FEAT-005 · REQ-106 · BR-005 · Actor: Sales · Priority: Should (12) · Dep: US-004 · DoR: Draft
**As a** Sales **I want** khi vào Đủ điều kiện được hỏi dấu hiệu nhu cầu & ngân sách **so that** tôi chỉ theo đuổi khi kiểm được cả hai chiều.
- **[AC-013]** Scenario: Nhập đủ hai dấu hiệu
  Given tôi kéo cơ hội sang Đủ điều kiện
  When màn hình hỏi và tôi nhập cả dấu hiệu nhu cầu và dấu hiệu ngân sách (mỗi ô một câu + nguồn)
  Then cơ hội ở Đủ điều kiện không mang cờ cảnh báo.
- **[AC-014]** Scenario: Bỏ qua vẫn kéo được
  Given tôi kéo cơ hội sang Đủ điều kiện
  When tôi bỏ qua hai ô
  Then cơ hội vẫn sang Đủ điều kiện và mang cờ cảnh báo cho tới khi bổ sung; thao tác kéo không bị chặn.

### US-006 — Ghi lý do Thua
FEAT-006 · REQ-110 · Actor: Sales · Priority: Could (9, cần PO duyệt) · Dep: US-004 · DoR: Draft
**As a** Sales **I want** khi chuyển sang Thua được hỏi lý do **so that** đội học được vì sao mất deal.
- **[AC-015]** Scenario: Nhập lý do
  Given tôi kéo cơ hội sang Thua
  When tôi nhập lý do
  Then cơ hội vào Thua và xuất hiện trong bảng thống kê lý do thua.
- **[AC-016]** Scenario: Bỏ qua lý do
  Given tôi kéo cơ hội sang Thua
  When tôi bỏ qua
  Then cơ hội vẫn sang Thua, mang cờ cảnh báo và đứng ngoài bảng thống kê lý do thua cho tới khi bổ sung.

### US-007 — Hoạt động & Dòng thời gian công ty
FEAT-007 · REQ-107, REQ-108 · Actor: Sales · Priority: Should (14) · Dep: US-001 · DoR: Draft · Ref: Q-09 (duyệt: tập loại cố định)
**As a** Sales **I want** ghi hoạt động và xem chúng cùng đổi giai đoạn/ghi chú trên một dòng thời gian **so that** tôi có toàn cảnh lịch sử công ty.
- **[AC-017]** Scenario: Ghi hoạt động
  Given tôi ở màn hình một công ty
  When tôi ghi hoạt động với ngày, loại (Gặp mặt/Gọi điện/Gửi tài liệu/Email/Khách phản hồi/Khác), mô tả, người liên hệ liên quan
  Then hoạt động xuất hiện trên dòng thời gian.
- **[AC-018]** Scenario: Dòng thời gian gộp, mới nhất trên
  Given công ty có hoạt động, lần đổi giai đoạn và ghi chú
  When tôi mở dòng thời gian
  Then cả ba loại hiện chung, sắp mới-nhất-ở-trên.

### US-008 — Việc tiếp theo & ngày hạn
FEAT-008 · REQ-109 · Actor: Sales · Priority: Must (16) · Dep: US-003 · DoR: Draft
**As a** Sales **I want** mỗi cơ hội có Việc tiếp theo + ngày hạn **so that** tôi luôn biết deal nào cần làm gì hôm nay.
- **[AC-019]** Scenario: Đủ hai ô
  Given một cơ hội mở
  When tôi điền Việc tiếp theo và ngày hạn
  Then cơ hội xuất hiện trong danh sách việc phải làm theo hạn.
- **[AC-020]** Scenario: Thiếu một ô vẫn lưu, mang cờ
  Given một cơ hội mở
  When tôi để trống Việc tiếp theo hoặc ngày hạn và lưu
  Then cơ hội vẫn lưu, mang cờ cảnh báo và KHÔNG xuất hiện trong danh sách việc phải làm tới khi điền đủ; thao tác lưu không bị chặn.

### US-009 — Tìm kiếm & Lọc
FEAT-009 · REQ-111 · Actor: Sales · Priority: Could (10, acceptance-mandatory T-1) · Dep: US-001, US-003 · DoR: Draft
**As a** Sales **I want** tìm và lọc công ty/cơ hội **so that** tôi tìm lại nhanh thứ đã nhập.
- **[AC-021]** Scenario: Tìm công ty theo tên
  Given có nhiều công ty
  When tôi gõ một phần tên
  Then danh sách chỉ còn công ty khớp.
- **[AC-022]** Scenario: Lọc công ty
  Given danh sách công ty
  When tôi lọc theo ngành / loại công ty / quốc gia / nhãn Đang theo dõi
  Then chỉ còn công ty thoả bộ lọc.
- **[AC-023]** Scenario: Lọc cơ hội
  Given danh sách cơ hội
  When tôi lọc theo giai đoạn / tình trạng quá hạn Việc tiếp theo
  Then chỉ còn cơ hội thoả bộ lọc.

### US-010 — Màn hình tổng quan
FEAT-010 · REQ-112 · Actor: Sales · Priority: Could (10, acceptance-mandatory T-1) · Dep: US-001, US-003, US-008 · DoR: Draft
**As a** Sales **I want** một màn hình tổng quan **so that** tôi nắm nhanh tình hình.
- **[AC-024]** Scenario: Hiển thị tổng quan
  Given có dữ liệu công ty và cơ hội
  When tôi mở màn hình tổng quan
  Then thấy số công ty theo ngành, số cơ hội & tổng giá trị theo từng giai đoạn, và danh sách Việc tiếp theo quá hạn.

---

## D2 — Đọc nguồn & Tri thức (Observation/Claim/Provenance)

### US-011 — Bản lưu nguồn (Observation)
FEAT-011 · REQ-201 · BR-018 · Actor: A-AI · Priority: Must (16) · Dep: US-001 · DoR: Draft · Ref: AS-02 (nguồn HTML nội bộ)
**As a** Tác nhân AI tự chủ (A-AI) **I want** đọc nội dung web công ty và lưu bản lưu nguyên văn **so that** mọi phát hiện về sau đều truy được về nguồn.
- **[AC-025]** Scenario: Tạo bản lưu
  Given một công ty có địa chỉ nguồn (bản chụp HTML)
  When hệ thống đọc nguồn
  Then một bản lưu được tạo, giữ nguyên văn, kèm địa chỉ nguồn và thời điểm đọc, thuộc đúng công ty đó.
- **[AC-026]** Scenario: Nhiều bản lưu một công ty
  Given công ty đã có một bản lưu
  When hệ thống đọc lại nguồn
  Then bản lưu mới được thêm và danh sách xếp theo thời điểm đọc.

### US-012 — Nguồn không đọc được
FEAT-012 · REQ-211 · Actor: A-AI · Priority: Could (8, cần PO duyệt) · Dep: US-011 · DoR: Draft
**As a** Tác nhân AI tự chủ (A-AI) **I want** ghi lại khi nguồn không đọc được **so that** hệ thống không đoán bừa.
- **[AC-027]** Scenario: Nguồn lỗi
  Given nguồn của một công ty không đọc được
  When hệ thống thử đọc
  Then hệ thống ghi trạng thái "không đọc được" và KHÔNG sinh phát hiện nào từ lần đọc đó.

### US-013 — Rút phát hiện có provenance (Claim)
FEAT-013 · REQ-202, REQ-203, REQ-207 · BR-006, BR-007, BR-008, BR-018 · Actor: A-AI · Priority: Must (19) · Dep: US-011 · DoR: Draft · Ref: C-2 (danh mục 6 loại tin)
**As a** Tác nhân AI tự chủ (A-AI) **I want** rút phát hiện từ bản lưu, luôn kèm câu trích và vị trí **so that** Sales tin được vì có bằng chứng.
- **[AC-028]** Scenario: Rút phát hiện hợp lệ
  Given một bản lưu có nội dung tín hiệu
  When hệ thống rút phát hiện
  Then mỗi phát hiện có: nhận định ngắn, loại tin (gọi vốn/nhân sự cấp cao/mở rộng/tuyển dụng/mảng kinh doanh mới/khác), câu trích nguyên văn, vị trí câu trích trong bản lưu, mức chắc chắn; và thuộc đúng công ty của bản lưu.
- **[AC-029]** Scenario: Chặn phát hiện thiếu câu trích (T-2)
  Given một phát hiện không có câu trích
  When hệ thống thử lưu nó (kể cả ghi thẳng)
  Then thao tác bị từ chối.
- **[AC-030]** Scenario: Không gắn thẳng vào thực thể khác
  Given một phát hiện vừa sinh
  Then nó chỉ gắn với công ty, không gắn thẳng vào cơ hội/người liên hệ/hoạt động.

### US-014 — Phát hiện đọc theo góc loại công ty
FEAT-014 · REQ-205 · Actor: A-AI · Priority: Could (9, cần PO duyệt) · Dep: US-013 · DoR: Draft
**As a** Tác nhân AI tự chủ (A-AI) **I want** câu nhận định phản ánh loại công ty **so that** cùng một tín hiệu mang đúng nghĩa theo loại khách.
- **[AC-031]** Scenario: Cùng tín hiệu, khác loại công ty
  Given hai công ty khác loại cùng có tín hiệu "gọi vốn"
  When hệ thống sinh phát hiện
  Then câu nhận định cho thấy tín hiệu được đọc dưới góc loại công ty tương ứng (ví dụ startup vs product khác nhau).

### US-015 — Vùng đọc & hiển thị mức chắc chắn
FEAT-015 · REQ-204, REQ-209 · BR-007 · Actor: Sales · Priority: Should (12) · Dep: US-013 · DoR: Draft
**As a** Sales **I want** xem bản lưu & phát hiện ở khu riêng với mức chắc chắn nhìn-là-biết **so that** tôi đánh giá độ tin cậy trước cả khi đọc chữ.
- **[AC-032]** Scenario: Vùng đọc tách biệt
  Given tôi mở màn hình một công ty
  Then bản lưu & phát hiện hiện ở khu riêng (vùng đọc), tách khỏi hồ sơ và dòng thời gian.
- **[AC-033]** Scenario: Ba mức phân biệt không cần đọc chữ
  Given các phát hiện có mức Chắc/Có thể/Đoán
  Then ba mức phân biệt được bằng ký hiệu hoặc màu, không chỉ bằng nhãn chữ.

### US-016 — Xem nguồn gốc phát hiện (Provenance jump)
FEAT-016 · REQ-208 · BR-018 · Actor: Sales · Priority: Should (13) · Dep: US-013 · DoR: Draft
**As a** Sales **I want** bấm vào phát hiện để nhảy tới đúng đoạn gốc **so that** tôi kiểm chứng ngay, không phải tự dò.
- **[AC-034]** Scenario: Nhảy tới đoạn trích (T-3)
  Given một phát hiện hiển thị ở bất cứ đâu
  When tôi bấm vào nó
  Then hệ thống mở đúng đoạn văn gốc trong bản lưu và đánh dấu vị trí câu trích.

### US-017 — Tích lũy phát hiện qua nhiều lần đọc
FEAT-017 · REQ-210 · Actor: A-AI · Priority: Could (9, cần PO duyệt) · Dep: US-013 · DoR: Draft
**As a** Tác nhân AI tự chủ (A-AI) **I want** giữ phát hiện cũ khi đọc lại nguồn **so that** lịch sử tín hiệu không bị mất.
- **[AC-035]** Scenario: Đọc lại không xoá cũ
  Given một công ty đã có phát hiện từ lần đọc trước
  When hệ thống đọc lại nguồn và sinh phát hiện mới
  Then phát hiện mới nằm cạnh phát hiện cũ, mỗi cái mang thời điểm riêng.

---

## D3 — Gợi ý & Duyệt (Proposal / Human-in-the-loop)

### US-018 — Sinh gợi ý vào hàng đợi chờ duyệt
FEAT-018 · REQ-301 · BR-018 · Actor: A-AI · Priority: Should (15) · Dep: US-013 · DoR: Draft · Ref: Q-01 (duyệt), Q-06 (duyệt: định nghĩa "đã cũ")
**As a** Tác nhân AI tự chủ (A-AI) **I want** sinh gợi ý từ phát hiện mới vào hàng đợi của người sở hữu **so that** Sales chỉ việc bấm thay vì tự gõ.
- **[AC-036]** Scenario: Gợi ý thêm tin
  Given có phát hiện mới về một công ty
  When hệ thống sinh gợi ý loại "thêm tin mới vào dòng thời gian"
  Then gợi ý xuất hiện trong hàng đợi chờ duyệt.
- **[AC-037]** Scenario: Gợi ý điền/sửa ô hồ sơ
  Given có phát hiện {Chắc/Có thể} bổ sung hoặc mâu thuẫn một ô hồ sơ (trống hoặc đã cũ)
  When hệ thống sinh gợi ý loại "điền/sửa ô"
  Then gợi ý xuất hiện trong hàng đợi chờ duyệt.
> Ghi chú Q-01 (đã duyệt): với công ty Đang theo dõi, gợi ý "thêm tin" do vòng quét tự thực hiện (US-031); hàng đợi chỉ giữ gợi ý "sửa ô hồ sơ". Một phát hiện → đúng một mục timeline.

### US-019 — Thẻ gợi ý đủ 4 thứ tại chỗ
FEAT-019 · REQ-302 · Actor: Sales · Priority: Should (12) · Dep: US-018 · DoR: Draft · Ref: Q-07 (duyệt: dòng hệ quả theo mẫu)
**As a** Sales **I want** mỗi gợi ý hiện đủ bằng chứng tại chỗ **so that** tôi quyết định mà không phải chuyển màn hình.
- **[AC-038]** Scenario: Hiển thị đủ 4 thành phần
  Given một gợi ý trong hàng đợi
  When tôi mở nó
  Then tôi thấy: nội dung "hiện tại → đề nghị", câu trích bằng chứng, mức chắc chắn, và một dòng nói rõ hệ quả nếu thông tin này sai — tất cả ngay tại chỗ.

### US-020 — Quyết định gợi ý (Duyệt/Sửa-rồi-duyệt/Bỏ)
FEAT-020 · REQ-303, REQ-305 · BR-009, BR-010 · Actor: Sales · Priority: Should (13) · Dep: US-019 · DoR: Draft
**As a** Sales **I want** duyệt/sửa-rồi-duyệt/bỏ một gợi ý **so that** tôi kiểm soát dữ liệu vào hồ sơ.
- **[AC-039]** Scenario: Duyệt (T-5)
  Given một gợi ý
  When tôi bấm Duyệt
  Then thay đổi được áp vào hồ sơ/dòng thời gian và ghi nhận là *duyệt*.
- **[AC-040]** Scenario: Sửa rồi duyệt tách khỏi duyệt (T-5)
  Given một gợi ý
  When tôi sửa nội dung rồi duyệt
  Then thay đổi được áp và ghi nhận là *sửa*, KHÔNG cộng vào con số *duyệt*.
- **[AC-041]** Scenario: Bỏ kèm lý do, thao tác không nhiều hơn duyệt
  Given một gợi ý
  When tôi bấm Bỏ
  Then tôi chọn lý do trong danh sách {thông tin sai, đúng nhưng không liên quan, đã cũ, hiểu sai ngữ cảnh, khác}; số thao tác để bỏ không nhiều hơn số thao tác để duyệt.

### US-021 — Không-duyệt-không-đổi
FEAT-021 · REQ-304 · BR-011 · Actor: Sales · Priority: Should (14) · Dep: US-018 · DoR: Draft
**As a** Sales **I want** hồ sơ không đổi khi tôi chưa duyệt **so that** tôi giữ toàn quyền với dữ liệu của mình.
- **[AC-042]** Scenario: Không làm gì sau ≥3 chu kỳ (T-4)
  Given một gợi ý được sinh ra và tôi không thao tác
  When ít nhất ba chu kỳ vòng quét trôi qua
  Then hồ sơ công ty vẫn y nguyên; gợi ý không tự áp dụng, không tự hết hạn thành hành động, không có chế độ tự duyệt.

### US-022 — Nhật ký quyết định & đo thời gian quyết
FEAT-022 · REQ-306 · Actor: A-Admin · Priority: Should (12) · Dep: US-020 · DoR: Draft · Ref: AS-05 (duyệt)
**As a** Quản trị **I want** mọi gợi ý & quyết định được ghi lại kèm thời gian quyết **so that** tôi đo được chất lượng và mức độ tin dùng.
- **[AC-043]** Scenario: Ghi bản ghi quyết định
  Given tôi mở và quyết một gợi ý
  When tôi bấm Duyệt/Sửa-rồi-duyệt/Bỏ
  Then hệ thống lưu: nội dung gợi ý, ai quyết, lúc nào, quyết gì, lý do (nếu bỏ), và số giây từ lúc mở tới lúc bấm.

### US-023 — Chống sinh lại gợi ý đã bỏ
FEAT-023 · REQ-307 · Actor: A-AI · Priority: Won't-now (7, cần PO duyệt) · Dep: US-018 · DoR: Draft
**As a** Sales **I want** gợi ý đã bỏ không hiện lại **so that** tôi không bị làm phiền lặp lại.
- **[AC-044]** Scenario: Không tái sinh cùng nội dung
  Given tôi đã bỏ một gợi ý
  When chưa có bản lưu mới cho công ty đó
  Then hệ thống không sinh lại gợi ý cùng nội dung.
- **[AC-045]** Scenario: Có bản lưu mới thì được sinh lại
  Given tôi đã bỏ một gợi ý
  When có bản lưu mới dẫn tới cùng nội dung
  Then hệ thống được phép sinh lại gợi ý.

### US-024 — Chỉ báo "đang có gợi ý chờ duyệt"
FEAT-024 · REQ-308 · Actor: Sales · Priority: Won't-now (6, cần PO duyệt) · Dep: US-018 · DoR: Draft
**As a** Sales **I want** thấy dấu hiệu công ty/cơ hội đang có gợi ý chờ **so that** tôi không phải nhớ đi kiểm tra hàng đợi.
- **[AC-046]** Scenario: Hiện chỉ báo
  Given một công ty có gợi ý chờ duyệt
  Then màn hình danh sách cơ hội và màn hình công ty đều hiện dấu hiệu "đang có gợi ý chờ duyệt".

---

## D4 — Tự đặt Việc tiếp theo (Autonomy có kiểm soát)

### US-025 — Tự điền Việc tiếp theo theo độ gấp
FEAT-025 · REQ-401, REQ-402, REQ-403 · BR-012 · Actor: A-AI · Priority: Must (17) · Dep: US-008, US-013 · DoR: Draft · Ref: Q-03, Q-04 (duyệt)
**As a** Tác nhân AI tự chủ (A-AI) **I want** tự điền Việc tiếp theo + ngày hạn cho cơ hội mở khi có tín hiệu đáng chú ý **so that** Sales liên hệ đúng cửa sổ thời gian mà không phải chờ mở hàng đợi.
- **[AC-047]** Scenario: Tự đặt khi có cơ hội mở (T-6)
  Given một công ty có ≥1 cơ hội mở
  And xuất hiện phát hiện đáng chú ý (loại: gọi vốn/nhân sự cấp cao/mở rộng/tuyển dụng; mức ∈ {Chắc, Có thể})
  When phát hiện được sinh ra
  Then hệ thống tự điền Việc tiếp theo và ngày hạn cho cơ hội đó ngay, không hỏi ai; nội dung nhắc sự kiện kích hoạt và kèm chính câu trích.
- **[AC-048]** Scenario: Ngày hạn phản ánh độ gấp
  Given phát hiện loại "gọi vốn" (cửa sổ tính bằng ngày) so với loại "mở rộng"/"tuyển dụng"
  When hệ thống đặt ngày hạn
  Then hạn của "gọi vốn" sát hơn hạn của "mở rộng"/"tuyển dụng" (theo bảng độ gấp đã duyệt Q-04, cấu hình được).
- **[AC-049]** Scenario: Không có cơ hội mở thì không tự đặt
  Given một công ty không có cơ hội mở nào
  When có phát hiện đáng chú ý
  Then hệ thống KHÔNG tự đặt Việc tiếp theo.

### US-026 — Dấu hiệu hệ thống & không đè tay
FEAT-026 · REQ-404, REQ-409 · BR-012 · Actor: Sales (nhận giá trị) / A-AI (thực thi) · Priority: Should (14) · Dep: US-025 · DoR: Draft
**As a** Sales **I want** phân biệt ô do A-AI đặt và A-AI không đè lên việc tôi đang làm **so that** tôi tin tưởng phần tự động. *(Hành vi tự đặt/không-đè do A-AI thực thi.)*
- **[AC-050]** Scenario: Dấu hiệu phân biệt
  Given một Việc tiếp theo do hệ thống đặt
  Then ô mang dấu hiệu phân biệt được với ô do người gõ.
- **[AC-051]** Scenario: Không đè Next step tay chưa tới hạn
  Given một cơ hội có Việc tiếp theo do người nhập tay và chưa tới hạn
  When có phát hiện đáng chú ý
  Then hệ thống KHÔNG tự đặt đè lên ô đó.

### US-027 — Thông báo bền tới khi xem
FEAT-027 · REQ-405 · Actor: Sales · Priority: Could (11, acceptance-mandatory T-6) · Dep: US-025 · DoR: Draft · Ref: Q-08 (duyệt: định nghĩa "đã xem")
**As a** Sales **I want** được báo ngay khi hệ thống tự đặt **so that** tôi biết chuyện gì vừa xảy ra với cơ hội của mình.
- **[AC-052]** Scenario: Thông báo nội dung rõ
  Given hệ thống vừa tự đặt Việc tiếp theo
  Then tôi nhận thông báo trong sản phẩm nói rõ đặt gì, cho cơ hội nào, vì sao.
- **[AC-053]** Scenario: Không tự biến mất trước khi xem
  Given có thông báo chưa xem
  When tôi chưa mở màn hình cơ hội đó và chưa bấm "Đã hiểu"
  Then thông báo vẫn còn (không tự biến mất).

### US-028 — Hoàn tác 1-cú-bấm trong 7 ngày
FEAT-028 · REQ-406 · BR-013, BR-017(4) · Actor: Sales · Priority: Must (16) · Dep: US-025 · DoR: Draft
**As a** Sales **I want** hoàn tác việc hệ thống tự đặt bằng một cú bấm trong 7 ngày **so that** sai thì sửa dễ hơn cả lúc máy làm.
- **[AC-054]** Scenario: Hoàn tác về nguyên trạng (T-7)
  Given một Việc tiếp theo do hệ thống đặt
  When tôi bấm Hoàn tác (một cú bấm) trong vòng 7 ngày
  Then Việc tiếp theo và ngày hạn trở về đúng giá trị trước đó.
- **[AC-055]** Scenario: Cửa sổ 7 ngày hiển thị & hết hạn
  Given một ô do hệ thống đặt
  Then cửa sổ 7 ngày hiện rõ trên màn hình; sau 7 ngày nút Hoàn tác biến mất và ô sửa tay như ô bình thường.

### US-029 — Ghi vết tự đặt & hoàn tác
FEAT-029 · REQ-407, REQ-408 · Actor: A-Admin · Priority: Should (13) · Dep: US-025, US-028 · DoR: Draft
**As a** Quản trị **I want** mọi lần tự đặt và hoàn tác được ghi lại **so that** tôi đo được tỉ lệ hoàn tác.
- **[AC-056]** Scenario: Ghi lần tự đặt
  Given hệ thống tự đặt Việc tiếp theo
  Then lưu: cơ hội nào, giá trị cũ, giá trị mới, phát hiện nào kích hoạt, lúc nào.
- **[AC-057]** Scenario: Ghi lần hoàn tác (T-7)
  Given tôi bấm Hoàn tác
  Then lưu: ai bấm, lúc nào, đưa về giá trị gì; số lần & tỉ lệ hoàn tác/tổng tự đặt xem được ở màn hình Quản trị.

---

## D5 — Vòng quét công ty Đang theo dõi

### US-030 — Nhãn Đang theo dõi & danh sách riêng
FEAT-030 · REQ-501 · Actor: Sales · Priority: Should (14) · Dep: US-001 · DoR: Draft
**As a** Sales **I want** bật/tắt Đang theo dõi và có danh sách riêng **so that** tôi giao hẳn việc canh chừng công ty quan trọng.
- **[AC-058]** Scenario: Bật/tắt một thao tác
  Given một công ty
  When tôi bật (hoặc tắt) nhãn Đang theo dõi
  Then trạng thái đổi ngay bằng một thao tác.
- **[AC-059]** Scenario: Danh sách riêng
  Given có công ty Đang theo dõi
  Then có một màn hình danh sách riêng cho nhóm này.

### US-031 — Vòng lặp tự đọc→so→rút→thêm timeline
FEAT-031 · REQ-502, REQ-503 · BR-017 · Actor: A-AI · Priority: Must (17) · Dep: US-011, US-013, US-030 · DoR: Draft · Ref: Q-01, Q-02, Q-05 (duyệt)
**As a** Tác nhân AI tự chủ (A-AI) **I want** tự chạy vòng lặp khép kín trên công ty Đang theo dõi **so that** tin mới tự được ghi mà không cần ai bấm.
- **[AC-060]** Scenario: Tự thêm mục khi có nội dung mới (T-8)
  Given hai công ty Đang theo dõi được đổi sang bản chụp "sau"
  When vòng quét chạy và phát hiện nội dung mới so với bản lưu gần nhất
  Then hệ thống rút phát hiện và tự thêm một mục vào dòng thời gian, gắn nhãn "do hệ thống thêm", kèm câu trích — không ai bấm gì.
- **[AC-061]** Scenario: Không có nội dung mới thì không thêm
  Given một công ty Đang theo dõi không đổi nội dung nguồn
  When vòng quét chạy
  Then không có mục nào được thêm vào dòng thời gian.
- **[AC-062]** Scenario: Không dừng chờ duyệt
  Given vòng lặp đang chạy
  Then nó tự quyết ghi hay không dựa trên nội dung có mới không, không dừng chờ ai duyệt ở bất kỳ bước nào.
> Ghi chú BR-017: vòng quét vẫn tuyệt đối không đổi giai đoạn/tiền/liên hệ khách/xoá dữ liệu người tạo.

### US-032 — Chu kỳ vòng quét cấu hình (mặc định 60s)
FEAT-032 · REQ-504 · BR-014 · Actor: A-AI · Priority: Should (12) · Dep: US-031 · DoR: Draft
**As a** Tác nhân AI tự chủ (A-AI) **I want** chu kỳ quét cấu hình được, mặc định 60s **so that** demo chấm được nhưng vẫn đổi được cho vận hành thật.
- **[AC-063]** Scenario: Mặc định 60s
  Given chưa cấu hình gì
  Then chu kỳ vòng quét là 60 giây.
- **[AC-064]** Scenario: Đổi chu kỳ có hiệu lực
  Given chu kỳ được đổi (ở màn hình Quản trị hoặc biến môi trường)
  When giá trị mới được áp
  Then vòng quét chạy theo chu kỳ mới.

### US-033 — Nhật ký vòng quét
FEAT-033 · REQ-505 · BR-015 · Actor: A-Admin · Priority: Should (12, acceptance-mandatory T-8) · Dep: US-031 · DoR: Draft
**As a** Quản trị **I want** mỗi vòng ghi một dòng tổng kết **so that** tôi thấy vòng quét đang làm gì mà không đọc từng mục.
- **[AC-065]** Scenario: Dòng tổng kết mỗi vòng (T-8)
  Given vòng quét chạy xong một vòng
  Then Nhật ký vòng quét có một dòng: chạy lúc nào, quét bao nhiêu công ty, bao nhiêu nội dung mới, thêm bao nhiêu mục, mất bao lâu, có lỗi gì.
- **[AC-066]** Scenario: Dòng cộng dồn mỗi 10 vòng
  Given đã chạy 10 vòng
  Then có thêm một dòng tổng hợp cộng dồn.

### US-034 — Sales xoá mục hệ thống thêm
FEAT-034 · REQ-506 · BR-017(4) · Actor: Sales · Priority: Won't-now (7, cần PO duyệt) · Dep: US-031 · DoR: Draft
**As a** Sales **I want** xoá được mục do hệ thống thêm **so that** tôi vẫn làm chủ dòng thời gian.
- **[AC-067]** Scenario: Xoá mục hệ thống
  Given một mục "do hệ thống thêm" trên dòng thời gian
  When tôi (Sales) xoá nó
  Then mục bị xoá như mọi mục khác. (Lưu ý: đây là người xoá, không vi phạm BR-017(4) cấm *hệ thống tự* xoá.)

---

## D6 — Quản trị & An toàn AI

### US-035 — Bảng đo lường chất lượng AI
FEAT-035 · REQ-601 · Actor: A-Admin · Priority: Could (10, cần PO duyệt) · Dep: US-013, US-022, US-029 · DoR: Draft
**As a** Quản trị **I want** một màn hình gom số liệu chất lượng AI **so that** tôi biết hệ thống đang được tin dùng hay bị bỏ qua.
- **[AC-068]** Scenario: Hiển thị số liệu
  Given có dữ liệu vận hành
  When tôi mở bảng Quản trị
  Then thấy: số phát hiện & phân bố ba mức chắc chắn; số gợi ý & tỉ lệ duyệt/sửa-rồi-duyệt/bỏ; phân bố lý do bỏ; thời gian quyết trung bình; số lần tự đặt Việc tiếp theo & tỉ lệ bị hoàn tác.

### US-036 — Chỉnh chu kỳ vòng quét từ Quản trị
FEAT-036 · REQ-602 · BR-014 · Actor: A-Admin · Priority: Could (10, cần PO duyệt) · Dep: US-032 · DoR: Draft
**As a** Quản trị **I want** chỉnh chu kỳ vòng quét tại chỗ **so that** tôi điều tiết tải và nhịp demo.
- **[AC-069]** Scenario: Chỉnh tham số
  Given tôi ở bảng Quản trị
  When tôi đổi chu kỳ vòng quét
  Then tham số hiện đơn vị, giá trị mặc định và câu giải thích; thay đổi có hiệu lực ngay.

### US-037 — Kill switch toàn bộ AI
FEAT-037 · REQ-603 · BR-016 · Actor: A-Admin · Priority: Should (15) · Dep: US-013, US-018, US-025, US-031 · DoR: Draft
**As a** Quản trị **I want** một nút tắt toàn bộ AI **so that** tôi có phanh khi mọi thứ đi sai.
- **[AC-070]** Scenario: Tắt AI có hiệu lực ngay (T-9)
  Given AI đang chạy (vòng quét đang chạy)
  When tôi bấm tắt toàn bộ AI
  Then trong hai chu kỳ kế tiếp: vòng quét dừng, không sinh phát hiện mới, không sinh gợi ý mới, không tự đặt Việc tiếp theo — có hiệu lực ngay, không chạy lại sản phẩm.
- **[AC-071]** Scenario: Dữ liệu đã sinh còn nguyên (T-9)
  Given tôi vừa tắt AI
  Then dữ liệu đã sinh trước đó không bị xoá.
- **[AC-072]** Scenario: Bật lại chạy tiếp
  Given AI đang tắt
  When tôi bật lại
  Then vòng quét chạy tiếp.

### US-038 — Trạng thái AI tắt hiển thị cho Sales
FEAT-038 · REQ-604 · Actor: Sales · Priority: Could (11, acceptance-mandatory T-9) · Dep: US-037 · DoR: Draft
**As a** Sales **I want** thấy khi AI đang tắt **so that** tôi không tưởng nhầm hệ thống vẫn đang chuẩn bị cho mình.
- **[AC-073]** Scenario: Dòng thông báo đang tắt (T-9)
  Given Quản trị đã tắt AI
  When tôi (Sales) dùng sản phẩm
  Then tôi thấy một dòng thông báo nói rõ tính năng gợi ý đang tắt; trạng thái không im lặng biến mất.

### US-039 — Ghi vết bật/tắt AI
FEAT-039 · REQ-605 · BR-016 · Actor: A-Admin · Priority: Could (9, acceptance-mandatory T-9) · Dep: US-037 · DoR: Draft
**As a** Quản trị **I want** mỗi lần tắt/bật được ghi vết **so that** có audit trail.
- **[AC-074]** Scenario: Ghi vết (T-9)
  Given tôi bấm tắt hoặc bật lại AI
  Then hệ thống ghi vết kèm thời điểm cho cả hai lần bấm.

---

## D7 — Ranh giới, Nghiệm thu & Vận hành

### US-040 — Chặn 4 ranh giới cứng ở tầng service
FEAT-040 · BR-017 · REQ-206, REQ-113 · Actor: A-Admin/chủ hệ thống (A-AI là đối tượng bị chặn) · Priority: Must (17) · Dep: — (nền cho mọi lệnh ghi) · DoR: Draft · Ref: Q-10 (duyệt: enforce tầng service)
**As a** Quản trị (chủ hệ thống) **I want** 4 ranh giới được chặn ở tầng service (cả khi gọi ngoài UI) **so that** A-AI không bao giờ vượt trần dù bị nhắc sai.
- **[AC-075]** Scenario: Chặn tự đổi giai đoạn/tiền/xoá ngoài UI (T-10)
  Given một lệnh đổi giai đoạn, đổi giá trị tiền, hoặc xoá công ty dưới danh nghĩa hệ thống, không đi qua giao diện người dùng
  When lệnh được gửi tới tầng service
  Then cả ba đều bị từ chối.
- **[AC-076]** Scenario: Không tự liên hệ khách
  Given phần AI đang chạy (kể cả vòng quét)
  Then hệ thống không gửi thư/nhắn tin tới khách trong mọi tình huống (gọi dịch vụ mạng để chạy AI vẫn được).
- **[AC-077]** Scenario: Tắt AI, Nhóm 1 vẫn đủ (T-1)
  Given toàn bộ AI bị tắt
  When tôi dùng các chức năng Nhóm 1
  Then không chức năng nào của Nhóm 1 hỏng.

### US-041 — Chuyển bản chụp trước→sau  ⟶ *Test-harness (KHÔNG phải tính năng hệ thống)*
FEAT-041 · REQ-703 · Actor: A-Admin (điều khiển demo/test-harness) · Priority: Must (17) · Dep: US-011 · DoR: Draft · Ref: AS-02
**As a** Quản trị/người vận hành demo **I want** chuyển một công ty từ bản chụp "trước" sang "sau" **so that** kích hoạt và diễn lại mọi kịch bản AI. *(Trong buổi chấm, giám khảo thao tác qua vai này.)*
- **[AC-078]** Scenario: Chuyển từ UI hoặc lệnh (T-6/T-8)
  Given một công ty ở bản chụp "trước"
  When tôi chuyển sang bản chụp "sau" từ giao diện hoặc bằng một lệnh
  Then nguồn của công ty đổi sang phiên bản "sau" để lần đọc kế tiếp thấy nội dung mới.

### US-042 — Nạp/Reset dữ liệu idempotent một lệnh  ⟶ *Ops/Nộp bài (không phải story người dùng CRM)*
FEAT-042 · REQ-702 · Actor: Vận hành/Ops (chạy bằng lệnh, không login CRM) · Priority: Must (18) · Dep: — · DoR: Draft · Ref: AS-03 (seed 2 tài khoản)
**As a** người vận hành **I want** nạp & reset dữ liệu bằng một lệnh **so that** diễn lại kịch bản demo từ đầu.
- **[AC-079]** Scenario: Nạp một lệnh
  Given môi trường trống
  When tôi chạy lệnh nạp dữ liệu
  Then bộ dữ liệu mẫu (công ty, người liên hệ, cơ hội, bản chụp trước/sau) và 2 tài khoản Sales & Quản trị được tạo, không cần gõ tay hay sửa mã.
- **[AC-080]** Scenario: Chạy lại về trạng thái ban đầu
  Given dữ liệu đã bị thay đổi sau khi demo
  When tôi chạy lại lệnh nạp
  Then hệ thống trở về đúng trạng thái ban đầu (idempotent).

### US-043 — Bộ kiểm thử tự động T-1..T-10  ⟶ *Ops/Nộp bài (không phải story người dùng CRM)*
FEAT-043 · REQ-701 · Actor: Vận hành/Ops (chạy bằng lệnh) · Priority: Must (16) · Dep: nhiều US · DoR: Draft
**As a** người vận hành **I want** chạy toàn bộ nghiệm thu bằng một lệnh **so that** giám khảo chấm khách quan.
- **[AC-081]** Scenario: Một lệnh, kết quả rõ
  Given mã nguồn đã dựng
  When tôi chạy một lệnh test
  Then bộ kiểm thử phủ đủ 10 điểm T-1..T-10 chạy và in kết quả rõ ràng (đạt/không đạt từng điểm).

### US-044 — Bản dựng production + env + persistence  ⟶ *NFR/Ops (KHÔNG phải tính năng hệ thống; phần đăng nhập đã tách sang US-046)*
FEAT-044 · REQ-704 · Actor: Ops (dựng production) · Priority: Must (17) · Dep: US-042 · DoR: Draft
**As a** người vận hành **I want** sản phẩm chạy ở cấu hình production giả lập **so that** giám khảo & người dùng vào được môi trường giống thật.
- **[AC-082]** Scenario: Production build
  Given bản dựng nộp
  Then không có dev server / hot reload / chế độ gỡ lỗi.
- **[AC-083]** Scenario: Cấu hình ở biến môi trường
  Given cấu hình hệ thống
  Then khoá dịch vụ ngoài, chuỗi kết nối DB và chu kỳ vòng quét nằm ở biến môi trường, không nằm trong mã.
- **[AC-084]** Scenario: Dữ liệu bền qua restart
  Given có dữ liệu trong DB
  When tiến trình khởi động lại
  Then dữ liệu còn nguyên.
- **[AC-085]** Scenario: Khởi động một lệnh
  Given máy chạy
  When tôi khởi động bằng một lệnh
  Then hệ thống chạy và log ra chỗ xem được. *(Đăng nhập 2 vai: xem US-046.)*

### US-045 — Mã nguồn GitLab + log Claude Code → Grafana
FEAT-045 · REQ-705 · C-LOG-1 · Actor: A-Ops-Infra · Priority: Should (12) · Dep: — · DoR: Draft · Ref: Q-11 (duyệt: lưu prompt log)
**As a** đội thi **I want** nộp mã trên GitLab HBLAB và đẩy log Claude Code về Grafana **so that** giám khảo thấy đội đã dùng AI thế nào.
- **[AC-086]** Scenario: Mã nguồn trên GitLab
  Then mã nguồn nằm trên GitLab của HBLAB.
- **[AC-087]** Scenario: Log AI Agent lên Grafana
  Given đã setup theo hướng dẫn tokens.hblab.ai:8443
  When phần AI (Claude Code) hoạt động trong thời gian sự kiện
  Then log AI Agent hiển thị được trên bảng Grafana; kèm prompt log/lịch sử tương tác lưu trong repo.

---

## D8 — Truy cập & Phân vai (tính năng hệ thống, tách từ US-044)

### US-046 — Đăng nhập & phân vai Sales/Quản trị
FEAT-046 · REQ-704 (đăng nhập), PRD §2 (Quản trị xem phần Sales không thấy) · Actor: Sales, Admin · Priority: Must · Dep: US-042 (seed tài khoản) · DoR: Draft
**As a** người dùng (Sales hoặc Quản trị) **I want** đăng nhập bằng tài khoản của mình và chỉ thấy đúng phần được phép **so that** dữ liệu & công cụ vận hành được bảo vệ theo vai.
- **[AC-088]** Scenario: Đăng nhập hợp lệ
  Given có tài khoản Sales và Quản trị
  When tôi nhập đúng thông tin đăng nhập
  Then tôi vào được hệ thống đúng với vai của mình.
- **[AC-089]** Scenario: Phân vai hiển thị
  Given tôi đăng nhập vai Sales
  Then tôi KHÔNG thấy bảng đo lường chất lượng AI và nút tắt AI của Quản trị;
  And khi đăng nhập vai Quản trị thì thấy các phần đó.
- **[AC-090]** Scenario: Đăng nhập sai bị từ chối
  Given thông tin đăng nhập không hợp lệ
  When tôi thử đăng nhập
  Then hệ thống từ chối, không cho vào.

---

## Phần Phi-tính-năng — Ops/Delivery/NFR & Test-harness (KHÔNG thuộc feature backlog)
Vẫn bắt buộc theo §6/§7 nhưng không mô hình hoá thành tính năng người dùng:
- **US-041** Chuyển bản chụp trước→sau — Test-harness (mồi kịch bản demo).
- **US-042** Nạp/Reset dữ liệu một lệnh — Ops/Delivery.
- **US-043** Bộ kiểm thử T-1..T-10 — Ops/Delivery.
- **US-044** Production build + env + persistence + khởi động một lệnh — NFR/Ops.
- **US-045** GitLab + log Claude Code→Grafana (công ty tự thu log) — Ops/Submission.

## Đề xuất Split (INVEST) cần PO cân nhắc
- **US-001** (CRUD Công ty): có thể tách theo thao tác (Tạo/Sửa/Xoá/Xem) nếu muốn story nhỏ hơn.
- **US-013** (Rút phát hiện): phần "chặn thiếu câu trích" (AC-029) có thể tách thành story ràng buộc riêng nếu đội muốn test độc lập T-2.
- **US-031** (Vòng lặp): phần "so sánh phát hiện nội dung mới" (Q-05) có thể tách thành story kỹ thuật nếu thuật toán diff cần thảo luận riêng.
- **US-044** (Production): có thể tách build / env-config / persistence / auth thành 4 story hạ tầng.

## ⏸ PO REVIEW — dừng tại đây
Bước tiếp: `/po-dor` để kiểm Definition of Ready từng story; sau đó khoá backlog. Chưa story nào được coi là Ready cho tới khi qua DoR + PO duyệt.
