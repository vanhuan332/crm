# Business Specification — US-040: Chặn bốn ranh giới cứng cho automation

## 1. Document Information

| Field | Value |
|---|---|
| Story | US-040 |
| Feature / Domain | FEAT-040 / D7 — Ranh giới, Nghiệm thu & Vận hành |
| Version | 1.0 |
| Status | **AWAITING_SPECIFICATION_APPROVAL** |
| Priority / DoR | Must (17) / READY (khuyến nghị DoR) |
| Primary actor | A-Admin/chủ hệ thống; A-AI là đối tượng bị áp ràng buộc |
| Sources | PRD §4 Nhóm 1, §5, §6; REQ-113, REQ-206; BR-017; US-040; AC-075..077; T-1, T-10; Q-10 |
| Evidence convention | `CONFIRMED`, `INFERRED`, `ASSUMPTION`, `OPEN QUESTION` theo nguồn nêu tại từng mục |

## 2. Purpose

**[CONFIRMED — BR-017; US-040]** Đặc tả này xác định trần hành vi không thể vượt qua của A-AI trên mọi tính năng và mọi đường gọi, kể cả khi thao tác không đi qua giao diện người dùng. Mục đích là bảo đảm automation không thể tự thay đổi giai đoạn, kết quả Thắng/Thua hoặc giá trị tiền; không tự liên hệ khách; và không tự xóa dữ liệu do người tạo.

**[CONFIRMED — REQ-113; AC-077]** Đặc tả cũng giữ ranh giới giữa AI và CRM làm tay: tắt toàn bộ AI không được làm giảm bất kỳ chức năng nào của Nhóm 1.

## 3. User Story

**[CONFIRMED — US-040]** As a Quản trị (chủ hệ thống), I want bốn ranh giới được chặn ở tầng service, kể cả khi gọi ngoài UI, so that A-AI không bao giờ vượt trần dù bị nhắc sai.

## 4. Business Goal

**[CONFIRMED — PRD §5; BG-3/BG-4]** Bảo vệ quyền kiểm soát của Sales đối với quyết định thương mại và dữ liệu họ tạo, đồng thời cho phép AI hữu ích trong phạm vi được cấp. Ranh giới phải là sự bảo vệ có hiệu lực trong thực thi nghiệp vụ, không phải chỉ là hướng dẫn cho AI.

## 5. Scope

**[CONFIRMED — BR-017; REQ-206; REQ-113; AC-075..077]** US-040 bao gồm:

- Từ chối hành vi dưới danh nghĩa hệ thống/A-AI nhằm tự đổi giai đoạn cơ hội.
- Từ chối hành vi dưới danh nghĩa hệ thống/A-AI nhằm tự đánh dấu Thắng hoặc Thua, hay tự sửa giá trị tiền của cơ hội.
- Từ chối hành vi dưới danh nghĩa hệ thống/A-AI nhằm tự liên hệ khách bằng thư hoặc tin nhắn; việc gọi dịch vụ mạng để vận hành AI không bị cấm bởi riêng ranh giới này.
- Từ chối hành vi dưới danh nghĩa hệ thống/A-AI nhằm tự xóa dữ liệu do người tạo.
- Giữ việc sinh Phát hiện là hoạt động không làm đổi hồ sơ công ty, dòng thời gian hay cơ hội.
- Bảo đảm các chức năng CRM làm tay Nhóm 1 vẫn hoạt động đầy đủ khi AI bị tắt.
- Áp dụng các ràng buộc trên cho automation của mọi feature, bao gồm vòng quét Đang theo dõi và lệnh đến từ ngoài UI.

## 6. Out of Scope

**[CONFIRMED — phân rã US/REQ liên quan]** US-040 không bao gồm:

- Thay đổi giai đoạn do Sales thao tác (US-004), hay quản lý giá trị/giai đoạn cơ hội trong luồng CRM làm tay (US-003).
- Chức năng tự đặt Việc tiếp theo trong phạm vi được phép (US-025..029), với điều kiện vẫn chịu BR-017.
- Cơ chế hàng đợi, quyết định Duyệt/Sửa-rồi-duyệt/Bỏ của Sales (US-018..023).
- Vòng quét, xác định nội dung mới, hoặc tự thêm mục timeline hợp lệ (US-031..033).
- Kill switch AI, thông báo trạng thái AI tắt, và audit bật/tắt (US-037..039), dù trạng thái AI tắt là điều kiện quan trọng của REQ-113.
- Thiết kế endpoint, mô hình lưu trữ, cơ chế phân quyền kỹ thuật, cấu trúc mã nguồn, hay cách cài đặt kiểm thử.

## 7. Actor / Permission

| Actor / context | Business permission / responsibility | Evidence |
|---|---|---|
| A-Admin / chủ hệ thống | Chịu trách nhiệm quản trị trần tự chủ; không được cấp quyền miễn trừ BR-017 cho A-AI. | CONFIRMED — US-040; BR-017 |
| A-AI / danh nghĩa hệ thống | Có thể thực hiện các hành vi tự động chỉ khi một story khác cho phép; luôn bị từ chối với bốn vùng cấm. | CONFIRMED — BR-017; INFERRED — Q-10 |
| Sales | Thực hiện các thao tác CRM thủ công, gồm hành động thay đổi giai đoạn hoặc dữ liệu theo các story CRM tương ứng; không phải đối tượng bị cấm như A-AI. | CONFIRMED — PRD §4 Nhóm 1, §5 |
| Đường gọi ngoài UI | Không phải actor độc lập; khi mang danh nghĩa hệ thống/A-AI, phải chịu cùng ràng buộc như thao tác từ UI. | CONFIRMED — BR-017; AC-075 |

## 8. Business Rules

| ID | Rule | Evidence |
|---|---|---|
| BR-US040-01 | A-AI không được tự đổi giai đoạn cơ hội dưới bất kỳ tính năng nào. | CONFIRMED — BR-017(1); AC-075 |
| BR-US040-02 | A-AI không được tự đánh dấu cơ hội Thắng/Thua và không được tự sửa giá trị tiền của cơ hội. | CONFIRMED — BR-017(2); AC-075 |
| BR-US040-03 | A-AI không được tự gửi thư, nhắn tin, hay có hành vi liên hệ khách. Ranh giới này không đồng nghĩa cấm A-AI gọi dịch vụ mạng phục vụ vận hành AI. | CONFIRMED — BR-017(3); AC-076 |
| BR-US040-04 | A-AI không được tự xóa dữ liệu do người tạo. Quy tắc không cấm Sales tự xóa mục mà họ có quyền quản lý. | CONFIRMED — BR-017(4); PRD §5; US-034 note |
| BR-US040-05 | Việc sinh Phát hiện chỉ tạo nguyên liệu tri thức; không được tự đổi hồ sơ công ty, dòng thời gian hoặc cơ hội. Hành vi ghi dữ liệu hợp lệ, nếu có, thuộc use case riêng được cấp rõ ràng. | CONFIRMED — REQ-206; PRD §4 Nhóm 2; INFERRED — phân định use case |
| BR-US040-06 | Ba ranh giới đầu phải bị từ chối cả khi lệnh đi ngoài UI; chỉ chỉ dẫn trong prompt hoặc lời dặn A-AI không đủ để đáp ứng quy tắc. | CONFIRMED — BR-017; PRD §5; T-10 |
| BR-US040-07 | Khi AI tắt, CRM làm tay Nhóm 1 phải tiếp tục hoạt động đầy đủ và không phụ thuộc vào A-AI. | CONFIRMED — REQ-113; AC-077 |
| BR-US040-08 | Các ranh giới được áp dụng tại lớp thực thi nghiệp vụ chung cho automation, không chỉ ở biểu hiện UI. Điểm chặn/cách chứng minh cụ thể là quyết định kỹ thuật. | INFERRED — Q-10; architect-handoff AR-1 |

## 9. Business Data Dictionary

| Business data | Meaning in US-040 | Required for | Evidence |
|---|---|---|---|
| Danh nghĩa thực hiện | Ngữ cảnh cho biết hành vi là do con người hay A-AI/hệ thống chủ động thực hiện. | Xác định có áp bốn ranh giới tự động hay không. | INFERRED — Q-10; architecture guardrail |
| Lệnh nghiệp vụ | Yêu cầu thực hiện một thay đổi hoặc liên hệ trong CRM. | Đánh giá hành vi có thuộc vùng cấm trước khi phát sinh kết quả nghiệp vụ. | INFERRED — BR-017; AC-075 |
| Cơ hội | Thương vụ thuộc công ty; có giai đoạn, kết quả và giá trị dự kiến. | Đối tượng được bảo vệ khỏi tự đổi stage, Thắng/Thua và tiền. | CONFIRMED — PRD §2, §4; BR-017 |
| Dữ liệu do người tạo | Dữ liệu CRM do người dùng tạo và chịu trách nhiệm. | Đối tượng A-AI không được tự xóa. | CONFIRMED — BR-017(4) |
| Khách | Người thật bên ngoài CRM mà Sales có thể liên hệ. | Đối tượng A-AI không được tự gửi thư/nhắn tin. | CONFIRMED — BR-017(3) |
| Phát hiện | Nhận định rút từ Bản lưu, có provenance. | Phân biệt hoạt động tạo nguyên liệu với thay đổi dữ liệu Sales. | CONFIRMED — REQ-202, REQ-206 |
| Trạng thái AI | Trạng thái bật/tắt toàn bộ phần AI. | Xác nhận CRM Nhóm 1 vẫn độc lập khi AI tắt. | CONFIRMED — REQ-113, REQ-603 |

## 10. Business Flow

| Step | Business flow | Evidence |
|---|---|---|
| BF-US040-01 | Một automation khởi tạo lệnh nghiệp vụ dưới danh nghĩa hệ thống/A-AI. | INFERRED — Q-10; US-040 |
| BF-US040-02 | Trước khi lệnh tạo hiệu lực, chính sách nghiệp vụ chung đánh giá danh nghĩa thực hiện và loại hành vi. | INFERRED — Q-10; architect-handoff AR-1 |
| BF-US040-03 | Nếu lệnh là tự đổi stage, tự đổi tiền/Thắng/Thua, tự liên hệ khách, hoặc tự xóa dữ liệu do người tạo, lệnh bị từ chối và không tạo kết quả nghiệp vụ bị cấm. | CONFIRMED — BR-017; AC-075/076 |
| BF-US040-04 | Nếu A-AI rút Phát hiện, kết quả chỉ ở vùng Phát hiện; không đổi hồ sơ, timeline hoặc cơ hội trong bước rút này. | CONFIRMED — REQ-206 |
| BF-US040-05 | Khi AI tắt, Sales tiếp tục dùng các chức năng Nhóm 1; các hành vi AI không là điều kiện để luồng CRM thủ công hoàn thành. | CONFIRMED — REQ-113; AC-077 |

## 11. Acceptance Criteria

| AC | Observable business outcome | Evidence |
|---|---|---|
| AC-075 | Given một lệnh đổi giai đoạn, đổi giá trị tiền, hoặc xóa công ty dưới danh nghĩa hệ thống và không đi qua UI, when lệnh tới tầng service, then cả ba đều bị từ chối. | CONFIRMED — US-040; T-10 |
| AC-076 | Given phần AI đang chạy, kể cả vòng quét, then hệ thống không gửi thư hoặc nhắn tin tới khách trong mọi tình huống; gọi dịch vụ mạng để chạy AI vẫn được. | CONFIRMED — US-040; BR-017(3) |
| AC-077 | Given toàn bộ AI bị tắt, when Sales dùng các chức năng Nhóm 1, then không chức năng nào của Nhóm 1 hỏng. | CONFIRMED — US-040; REQ-113; T-1 |

## 12. Screen Specification

**[CONFIRMED — BR-017; AC-075]** US-040 không yêu cầu một màn hình chuyên biệt: hiệu lực của ràng buộc phải bao phủ cả thao tác ngoài UI. Vì vậy không có màn hình guardrail độc lập được xác nhận trong phạm vi story này.

| Area | Required business information / behavior | Evidence |
|---|---|---|
| Các luồng CRM Nhóm 1 | Vẫn sử dụng được khi AI tắt; yêu cầu hiển thị cụ thể thuộc các story Nhóm 1. | CONFIRMED — REQ-113; AC-077 |
| Kênh ngoài UI | Lệnh dưới danh nghĩa hệ thống bị từ chối khi vi phạm; không có yêu cầu về một bề mặt hiển thị riêng. | CONFIRMED — AC-075 |
| Thông báo AI tắt | Nằm trong US-038, không được mở rộng thành thiết kế mới cho US-040. | CONFIRMED — REQ-604; OUT OF SCOPE — US-038 |

## 13. Screen Design

**[CONFIRMED — phạm vi nguồn]** Không có wireframe, tài sản màn hình, hay thiết kế hiển thị đã được phê duyệt riêng cho US-040. Không tạo tài sản thiết kế mới trong đặc tả này.

**[INFERRED — BR-017, AC-075]** Thiết kế UI sau này không được xem là cơ chế duy nhất để bảo đảm ranh giới; ràng buộc vẫn phải có hiệu lực khi UI không tham gia.

## 14. Screen States

| State | Required presentation / behavior | Evidence |
|---|---|---|
| AI hoạt động | Không có trạng thái màn hình riêng trong US-040; mọi automation vẫn bị giới hạn bởi BR-017. | CONFIRMED — BR-017 |
| AI tắt | Nhóm 1 vẫn hoạt động; dòng thông báo cho Sales thuộc US-038. | CONFIRMED — REQ-113; REQ-604 |
| Lệnh automation vi phạm ngoài UI | Lệnh bị từ chối; nguồn không xác định nội dung hoặc cách trình bày thông báo cho người vận hành. | CONFIRMED — AC-075; OPEN QUESTION — Q-US040-02 |

## 15. Validation

| Validation | Expected business result | Evidence |
|---|---|---|
| Danh nghĩa system/A-AI + đổi stage | Từ chối trước khi giai đoạn cơ hội thay đổi, kể cả đường gọi ngoài UI. | CONFIRMED — BR-017(1); AC-075 |
| Danh nghĩa system/A-AI + đổi tiền hoặc Thắng/Thua | Từ chối trước khi giá trị hoặc kết quả cơ hội thay đổi. | CONFIRMED — BR-017(2); AC-075 |
| Danh nghĩa system/A-AI + liên hệ khách | Không gửi thư hoặc tin nhắn đến khách. | CONFIRMED — BR-017(3); AC-076 |
| Danh nghĩa system/A-AI + xóa dữ liệu người tạo | Từ chối hành vi xóa. | CONFIRMED — BR-017(4) |
| Sinh Phát hiện | Không làm thay đổi hồ sơ, timeline hoặc cơ hội chỉ vì Phát hiện được sinh. | CONFIRMED — REQ-206 |
| AI tắt + thao tác Nhóm 1 | CRM thủ công vẫn hoàn thành các hành vi của Nhóm 1. | CONFIRMED — REQ-113; AC-077 |

## 16. Dependencies

| Dependency | Why it is needed | Evidence |
|---|---|---|
| US-003/US-004 — Cơ hội và stage | Cung cấp các hành vi stage, giá trị và kết quả mà A-AI bị cấm tự thay đổi. | CONFIRMED — BR-017; AC-075 |
| US-001 — Công ty | Cung cấp đối tượng CRM có thể bị thử xóa ngoài UI trong T-10. | CONFIRMED — AC-075; T-10 |
| US-011/US-013 — Bản lưu và Phát hiện | Làm rõ Phát hiện là nguyên liệu và việc sinh Phát hiện không tự thay đổi dữ liệu Sales. | CONFIRMED — REQ-206; US-013 |
| US-025/US-031 — Automation được cấp hẹp | Là các điểm chạm automation phải tuân BR-017 nhưng không mất hành vi hợp lệ của story riêng. | CONFIRMED — BR-017; US-025/US-031 |
| US-037/US-038 — Kill switch và thông báo AI tắt | Cung cấp trạng thái tắt AI và hiển thị trạng thái; US-040 xác nhận CRM Nhóm 1 không bị ảnh hưởng. | CONFIRMED — REQ-113, REQ-603/604 |

## 17. Business-level NFR Expectations

| Expectation | Statement | Evidence |
|---|---|---|
| Enforce xuyên kênh | Cùng một hành vi tự động bị cấm phải bị từ chối dù đến từ UI hay ngoài UI. | CONFIRMED — BR-017; AC-075 |
| An toàn tự chủ | Automation chỉ được hành động trong phạm vi được cấp; không có ngoại lệ cho vòng quét hoặc tính năng AI khác. | CONFIRMED — BR-017; PRD §5 |
| Tách biệt vận hành | Tắt AI không làm gián đoạn CRM làm tay Nhóm 1. | CONFIRMED — REQ-113; AC-077 |
| Không suy diễn ngoài nguồn | Việc rút Phát hiện không tự biến thành thay đổi CRM. | CONFIRMED — REQ-206 |

## 18. Test Scenarios

Các scenario nghiệp vụ dưới đây là đầu vào truy vết cho `test-scenarios.md`; chúng không phải kiểm thử thực thi.

| TC | Scenario nghiệp vụ | AC / BR trace |
|---|---|---|
| TC-US040-01 | Một lệnh dưới danh nghĩa hệ thống cố đổi giai đoạn cơ hội mà không đi qua UI và bị từ chối. | AC-075; BR-US040-01/06; T-10 |
| TC-US040-02 | Một lệnh dưới danh nghĩa hệ thống cố đổi giá trị tiền, đánh dấu Thắng/Thua, hoặc xóa công ty mà không đi qua UI và từng hành vi bị từ chối. | AC-075; BR-US040-02/04/06; T-10 |
| TC-US040-03 | AI đang chạy, bao gồm vòng quét; không có thư hoặc tin nhắn nào được gửi tới khách. | AC-076; BR-US040-03 |
| TC-US040-04 | Một Phát hiện được sinh cho công ty; hồ sơ, timeline và cơ hội không đổi chỉ do bước sinh Phát hiện. | REQ-206; BR-US040-05 |
| TC-US040-05 | Tắt toàn bộ AI rồi thực hiện các hành vi CRM Nhóm 1 theo T-1; tất cả vẫn hoạt động. | AC-077; BR-US040-07; T-1 |

## 19. Traceability

| Requirement chain | Specification coverage | Acceptance / test evidence |
|---|---|---|
| BR-017 → FEAT-040 → US-040 | Purpose, Scope, Rules BR-US040-01..04/06, Flow BF-US040-01..03, Validation | AC-075/076; TC-US040-01..03; T-10 |
| REQ-206 → FEAT-040 → US-040 | Scope, Rule BR-US040-05, Flow BF-US040-04, Validation | TC-US040-04 |
| REQ-113 → FEAT-040 → US-040 | Purpose, Scope, Rule BR-US040-07, Flow BF-US040-05, NFR | AC-077; TC-US040-05; T-1 |
| Q-10 → US-040 | Rule BR-US040-08 and the outside-UI enforcement boundary; no implementation mechanism decided here. | Technical handoff Q-US040-01 |
| BR-017 → US-040 → US-025/US-031 | Dependencies and cross-cutting automation constraint. | TC-US040-01..03; T-10 |

## 20. Assumptions

| ID | Assumption | Evidence / approval needed |
|---|---|---|
| AS-US040-01 | Các automation của story khác sẽ luôn có danh nghĩa thực hiện đủ rõ để áp dụng cùng chính sách ranh giới; cách phân loại cụ thể không được quyết định tại BA specification. | INFERRED — Q-10; cần Tech Lead chốt Q-US040-01 |
| AS-US040-02 | Các hành vi automation được yêu cầu rõ bởi story riêng, như tự đặt Việc tiếp theo hoặc tự thêm timeline, vẫn được phép khi không vi phạm BR-017 và các rule của story đó. | INFERRED — BR-017; US-025/US-031 |

## 21. Open Questions

| ID | Question | Impact / owner |
|---|---|---|
| Q-US040-01 | Theo Q-10/AR-1, dấu hiệu danh nghĩa nào là nguồn xác định nhất quán để phân biệt lệnh A-AI/hệ thống với lệnh của con người trên mọi đường gọi? | High — Tech Lead; cần quyết định thực thi nhưng không làm thay đổi bốn ranh giới hay AC-075..077 |
| Q-US040-02 | Khi lệnh vi phạm bị từ chối, người vận hành hoặc kênh gọi ngoài UI cần nhận hình thức phản hồi và dữ liệu audit nghiệp vụ nào? | Medium — Tech Lead/PO; nguồn chỉ yêu cầu bị từ chối, không quy định cách trình bày phản hồi |

## 22. Definition of Ready

| DoR check | Status | Evidence |
|---|---|---|
| Actor, business value, scope và priority xác định | Ready | CONFIRMED — US-040; backlog-prioritization.md |
| AC bao phủ các ranh giới kiểm trực tiếp và CRM thủ công khi AI tắt | Ready | CONFIRMED — AC-075..077; T-1/T-10 |
| Business rules và dependencies đã nhận diện | Ready | CONFIRMED — BR-017, REQ-206/113; §8, §16 |
| Có nguồn traceability | Ready | CONFIRMED — PRD §5/§6; REQ-113/206; BR-017 |
| Câu hỏi còn lại là quyết định cách thực thi/phản hồi, không thay đổi hành vi được yêu cầu | Ready with technical decisions open | INFERRED — Q-10; OPEN QUESTION — Q-US040-01/02 |
| BA specification được người có thẩm quyền phê duyệt | Pending | Required by Gate 1 |

**Current outcome:** `AWAITING_SPECIFICATION_APPROVAL`. Chỉ người phê duyệt có thẩm quyền được đổi trạng thái thành `SPECIFICATION_APPROVED`.

## 23. Technical Handoff

| Category | Constraint / touchpoint / risk / decision for Tech Lead |
|---|---|
| Approved business constraints | Tất cả automation phải đi qua `AutomationPolicyGuard`; A-AI bị chặn tự đổi stage, tiền/Thắng/Thua, liên hệ khách và xóa dữ liệu người tạo. Chặn có hiệu lực cả ngoài UI. |
| Required touchpoints | Ngữ cảnh danh nghĩa thực hiện; các hành vi thay đổi Cơ hội/Công ty; các luồng sinh Phát hiện; automation Next Step/vòng quét; trạng thái AI bật/tắt; CRM Nhóm 1. |
| Boundary preservation | REQ-206 giữ bước sinh Phát hiện tách khỏi thay đổi CRM. REQ-113 giữ CRM thủ công hoạt động khi AI tắt. Không mở đường gọi tắt guardrail. |
| Primary risk | Chỉ chặn ở UI hoặc một luồng automation sẽ bỏ sót lệnh ngoài UI và không đáp ứng T-10; đồng thời chặn quá rộng có thể làm hỏng CRM thủ công hoặc hành vi automation được cấp hẹp. |
| Decisions required | Chốt Q-US040-01 về nhận diện danh nghĩa và Q-US040-02 về phản hồi/audit; quyết định cách chứng minh T-10 theo ARQ-1 mà không làm thay đổi business constraints. |

Không có endpoint, schema, migration, cấu trúc mã nguồn, thuật toán, hay kế hoạch coding trong phần bàn giao này.

## 24. Change Log

| Version | Date | Change | Author/Approver |
|---|---|---|---|
| 1.0 | 2026-08-14 | Tạo business specification US-040 đủ 24 section, truy vết BR-017/REQ-206/REQ-113 đến AC-075..077 và giữ Q-10 là ràng buộc enforce ngoài UI. | BA Agent / Awaiting human approval |
