# Business Specification — US-007: Hoạt động & Dòng thời gian công ty

## 1. Document Information
| Field | Value |
|---|---|
| Story | US-007 |
| Version | 1.1 |
| Status | AWAITING_SPECIFICATION_APPROVAL |
| Sources | REQ-107, REQ-108, US-007, Q-09, architect handoff, DoR review |

## 2. Purpose
**[CONFIRMED — REQ-107..108]** Ghi hoạt động và tập hợp hoạt động, đổi giai đoạn, ghi chú thành một lịch sử công ty mới nhất ở trên.

## 3. User Story
**[CONFIRMED — US-007]** As a Sales, I want ghi hoạt động và xem chúng cùng đổi giai đoạn/ghi chú trên một dòng thời gian, so that tôi có toàn cảnh lịch sử công ty.

## 4. Business Goal
**[CONFIRMED — REQ-108]** Sales có một nơi xem lịch sử thống nhất thay vì phân tán theo từng loại sự kiện.

## 5. Scope
- **[CONFIRMED — AC-017]** Ghi hoạt động với ngày, loại, mô tả, người liên hệ liên quan và hiển thị trên timeline.
- **[CONFIRMED — AC-018]** Gộp hoạt động, lần đổi giai đoạn, ghi chú; xếp mới nhất ở trên.
- **[CONFIRMED — Q-09]** Dùng tập loại hoạt động cố định đã duyệt.

## 6. Out of Scope
- **[CONFIRMED — REQ-103..105]** Quản lý cơ hội/đổi giai đoạn; US-003, US-004.
- **[CONFIRMED — REQ-201..210]** Bản lưu/phát hiện ở vùng đọc, không phải timeline Sales.
- **[CONFIRMED — REQ-502]** Mục timeline tự thêm của vòng quét; US-031.

## 7. Actor / Permission
| Actor | Business permission | Evidence |
|---|---|---|
| Sales | Ghi hoạt động và xem timeline công ty. | **[CONFIRMED]** US-007, AC-017..018 |
| A-AI | Không thuộc thao tác ghi hoạt động thủ công này. | **[CONFIRMED]** REQ-113 |

## 8. Business Rules
| ID | Rule | Evidence |
|---|---|---|
| BR-US007-01 | Hoạt động gồm ngày, loại, mô tả, người liên hệ liên quan. | **[CONFIRMED]** REQ-107, AC-017 |
| BR-US007-02 | Loại hoạt động cố định: Gặp mặt, Gọi điện, Gửi tài liệu, Email, Khách phản hồi, Khác. | **[CONFIRMED]** AC-017, Q-09 |
| BR-US007-03 | Timeline công ty gộp hoạt động, đổi giai đoạn và ghi chú, mới nhất ở trên. | **[CONFIRMED]** REQ-108, AC-018 |
| BR-US007-04 | Bản lưu/phát hiện không phải nội dung timeline trong story này. | **[CONFIRMED]** REQ-204, REQ-206 |

## 9. Business Data Dictionary
| Business data | Meaning | Rule | Evidence |
|---|---|---|---|
| Hoạt động | Việc đã xảy ra được Sales ghi nhận. | Có bốn thông tin quy định. | **[CONFIRMED]** REQ-107 |
| Người liên hệ liên quan | Contact được gắn với hoạt động. | Thuộc công ty của hoạt động. | **[INFERRED]** REQ-102, REQ-107 |
| Dòng thời gian | Lịch sử gộp của công ty. | Mới nhất ở trên. | **[CONFIRMED]** REQ-108 |
| Ghi chú | Một loại nội dung xuất hiện cùng trên timeline. | Cách tạo/sửa ngoài scope. | **[CONFIRMED]** AC-018 |

## 10. Business Flow
### BF-007-01 — Ghi hoạt động
1. **[CONFIRMED — AC-017]** Sales đang ở màn hình một công ty.
2. **[CONFIRMED — AC-017]** Sales ghi ngày, loại, mô tả, người liên hệ liên quan.
3. **[CONFIRMED — AC-017]** Hoạt động xuất hiện trên timeline.

### BF-007-02 — Xem lịch sử gộp
1. **[CONFIRMED — AC-018]** Công ty có hoạt động, đổi giai đoạn và ghi chú.
2. **[CONFIRMED — AC-018]** Sales mở timeline.
3. **[CONFIRMED — AC-018]** Cả ba loại hiển thị chung, mới nhất trên.

## 11. Acceptance Criteria
### AC-017 — Ghi hoạt động
```gherkin
Scenario: Ghi hoạt động
  Given tôi ở màn hình một công ty
  When tôi ghi hoạt động với ngày, loại, mô tả, người liên hệ liên quan
  Then hoạt động xuất hiện trên dòng thời gian.
```
### AC-018 — Timeline gộp
```gherkin
Scenario: Dòng thời gian gộp, mới nhất trên
  Given công ty có hoạt động, lần đổi giai đoạn và ghi chú
  When tôi mở dòng thời gian
  Then cả ba loại hiện chung, sắp mới-nhất-ở-trên.
```

## 12. Screen Specification
| Screen ID | Area | Required behavior | Evidence |
|---|---|---|---|
| `SCR-US007-01` | Ghi Hoạt động | Thu thập ngày, loại cố định, mô tả và người liên hệ trong ngữ cảnh Công ty. | **[CONFIRMED]** AC-017; BR-US007-01..02 |
| `SCR-US007-02` | Timeline Công ty | Gộp hoạt động, đổi giai đoạn và ghi chú; mới nhất ở trên. | **[CONFIRMED]** AC-018; BR-US007-03 |
| `SCR-US007-03` | Trạng thái Timeline | Empty/loading/recoverable-error giữ ngữ cảnh; không thêm loại hoặc quy tắc thứ tự phụ. | **[ASSUMPTION]** A-007-01; **[OPEN QUESTION]** Q-007-01..02 |

## 13. Screen Design

> **UI-DESIGN UPDATE — 2026-08-14:** Wireframe BA dưới đây được tạo từ các US/AC hiện hành và thay thế trạng thái “chưa có asset” được ghi nhận trước bước UI Design.

![US-007 screen flow](./assets/screen-flow.svg)

### `SCR-US007-01` — Ghi Hoạt động
![US-007 activity form](./assets/activity-form.svg)

### `SCR-US007-02` — Timeline Công ty
![US-007 company timeline](./assets/company-timeline.svg)

### `SCR-US007-03` — Trạng thái Timeline
![US-007 timeline states](./assets/timeline-states.svg)

**[ASSUMPTION — A-007-01]** Visual language kế thừa mẫu đã duyệt cho US-001; nhãn/biểu tượng không bổ sung loại ngoài tập cố định và không đóng Q-007-01..02.

## 14. Screen States
| State | Outcome | Screen | Evidence |
|---|---|---|---|
| Có hoạt động vừa ghi | Mục hoạt động hiện trên timeline. | `SCR-US007-01` → `SCR-US007-02` | **[CONFIRMED]** AC-017 |
| Có nhiều loại lịch sử | Hoạt động, đổi stage, ghi chú gộp theo thời điểm giảm dần. | `SCR-US007-02` | **[CONFIRMED]** AC-018 |
| Chưa có lịch sử / lỗi tải | Có empty state hoặc hành động thử lại, không đổi rule timeline. | `SCR-US007-03` | **[ASSUMPTION]** A-007-01 |

## 15. Validation
| Condition | Response | Evidence |
|---|---|---|
| Loại hoạt động trong tập cố định | Cho ghi hoạt động. | **[CONFIRMED]** BR-US007-02 |
| Loại ngoài tập cố định | Không được quy định. | **[OPEN QUESTION]** Q-007-01 |
| Thứ tự cùng thời điểm | Chưa được quy định. | **[OPEN QUESTION]** Q-007-02 |

## 16. Dependencies
| Direction | Item | Dependency | Evidence |
|---|---|---|---|
| Upstream | US-001 | Cung cấp công ty. | **[CONFIRMED]** US-007 dependency |
| Related | US-002 | Cung cấp người liên hệ liên quan. | **[INFERRED]** REQ-102, REQ-107 |
| Related | US-004 | Cung cấp lần đổi giai đoạn vào timeline. | **[CONFIRMED]** REQ-108 |

## 17. Business-level NFR Expectations
- **[CONFIRMED — REQ-113]** Timeline thủ công vẫn hoạt động khi AI tắt.
- **[CONFIRMED — REQ-704]** Dữ liệu lịch sử cần bền qua khởi động lại.

## 18. Test Scenarios
| TC | Business scenario | AC / rule | Expected result |
|---|---|---|---|
| TC-007-01 | Sales ghi hoạt động với mỗi loại hợp lệ. | AC-017, BR-US007-02 | Hoạt động xuất hiện trên timeline. |
| TC-007-02 | Công ty có hoạt động, đổi stage, ghi chú ở thời điểm khác nhau. | AC-018, BR-US007-03 | Một timeline gộp, mới nhất ở trên. |

## 19. Traceability
| Chain | Evidence |
|---|---|
| `REQ-107/108 → EPIC-03 → FEAT-007 → US-007 → AC-017..018 → TC-007-01..02 (T-1)` | **[CONFIRMED]** architect handoff |
| `Q-09 → BR-US007-02 → TC-007-01` | **[CONFIRMED]** user-stories note |

## 20. Assumptions
| ID | Assumption | Status |
|---|---|---|
| A-007-01 | Visual language dùng mẫu đã duyệt cho US-001; cách hiển thị loại hoạt động giữ nguyên tập cố định. | **[ASSUMPTION]** Không đóng Q-007-01..02. |

## 21. Open Questions
| ID | Question | Owner / impact |
|---|---|---|
| Q-007-01 | Hệ thống phản hồi ra sao nếu Sales chọn loại ngoài tập cố định? | PO; validation. |
| Q-007-02 | Quy tắc thứ tự khi các mục cùng thời điểm là gì? | PO; trình bày timeline. |

## 22. Definition of Ready
| DoR item | Status | Evidence |
|---|---|---|
| Actor, giá trị và scope rõ | READY | US-007, REQ-107..108 |
| AC quan sát được | READY | AC-017..018 |
| Dependencies xác định | READY | US-001, US-002, US-004 |
| Traceability rõ | READY | REQ → FEAT → US → AC → TC |
| Human approval | AWAITING_SPECIFICATION_APPROVAL | Gate 1 |

## 23. Technical Handoff
**[CONFIRMED — REQ-107..108]** Bảo toàn bốn thông tin hoạt động, tập loại cố định và timeline gộp mới nhất trên. **[CONFIRMED — REQ-113]** Không làm CRM thủ công phụ thuộc AI. Tech Lead xử lý các câu hỏi trình bày mà không tự tạo quy tắc nghiệp vụ.

## 24. Change Log
| Version | Date | Change | Author/Approver |
|---|---|---|---|
| 1.1 | 2026-08-14 | Bổ sung ba SVG chi tiết cho form hoạt động, timeline gộp và trạng thái phục hồi; giữ tập loại cố định và các câu hỏi thứ tự. | Codex — UI pattern approved; specification approval unchanged |
| 1.0 | 2026-08-14 | Tạo specification 24 mục cho US-007. | Codex / awaiting human specification approval |
