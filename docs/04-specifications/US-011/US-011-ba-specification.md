# Business Specification — US-011: Bản lưu nguồn (Observation)

## 1. Document Information
| Field | Value |
|---|---|
| Story | US-011 |
| Version | 1.1 |
| Status | AWAITING_SPECIFICATION_APPROVAL |
| Sources | REQ-201, BR-018, US-011, architect handoff, DoR review |

## 2. Purpose
**[CONFIRMED — REQ-201]** Lưu nguồn đã đọc thành bản lưu nguyên văn để các phát hiện sau này có thể truy về nguồn.

## 3. User Story
**[CONFIRMED — US-011]** As a A-AI, I want đọc nội dung web công ty và lưu bản lưu nguyên văn, so that mọi phát hiện về sau đều truy được về nguồn.

## 4. Business Goal
**[CONFIRMED — REQ-201]** Tạo nền Observation đáng tin cậy cho chuỗi Observation → Claim → Proposal.

## 5. Scope
- **[CONFIRMED — AC-025]** Đọc nguồn của công ty và tạo bản lưu nguyên văn, kèm địa chỉ nguồn, thời điểm đọc, thuộc đúng công ty đó.
- **[CONFIRMED — AC-026]** Khi đọc lại, thêm bản lưu mới và xếp danh sách theo thời điểm đọc.

## 6. Out of Scope
- **[CONFIRMED — REQ-202..210]** Rút/hiển thị phát hiện và provenance chi tiết; xem US-013, US-015, US-016.
- **[CONFIRMED — REQ-211]** Xử lý nguồn không đọc được; xem US-012.
- **[CONFIRMED — REQ-206, BR-017]** Thay đổi hồ sơ, timeline, cơ hội, liên hệ hoặc xóa dữ liệu người tạo.

## 7. Actor / Permission
| Actor | Business permission | Evidence |
|---|---|---|
| A-AI | Đọc nguồn nội bộ của công ty và tạo bản lưu. | **[CONFIRMED]** US-011, AC-025 |
| Sales | Không có thao tác được xác định trong story này. | **[OPEN QUESTION]** Q-011-01 |

## 8. Business Rules
| ID | Rule | Evidence |
|---|---|---|
| BR-US011-01 | Mỗi bản lưu giữ nguyên văn nội dung, địa chỉ nguồn và thời điểm đọc. | **[CONFIRMED]** REQ-201 |
| BR-US011-02 | Mỗi bản lưu thuộc đúng một công ty; một công ty có thể có nhiều bản lưu, xếp theo thời điểm đọc. | **[CONFIRMED]** REQ-201, AC-026 |
| BR-US011-03 | Bản lưu là Observation trong chuỗi Observation → Claim → Proposal. | **[INFERRED]** BR-018 |
| BR-US011-04 | Nguồn nội dung là bản chụp HTML nội bộ, không phải web thật. | **[CONFIRMED]** C-DATA-1, AS-02 |
| BR-US011-05 | Story không được tự thay đổi dữ liệu Sales hoặc vượt các ranh giới BR-017. | **[CONFIRMED]** REQ-206, BR-017 |

## 9. Business Data Dictionary
| Business data | Meaning | Rule | Evidence |
|---|---|---|---|
| Công ty | Pháp nhân mà nguồn và bản lưu thuộc về. | Một bản lưu thuộc đúng một công ty. | **[CONFIRMED]** REQ-201 |
| Bản lưu | Observation chứa nội dung nguồn đã đọc nguyên văn. | Không phải phát hiện hay dữ liệu hồ sơ Sales. | **[CONFIRMED]** REQ-201, BR-018 |
| Địa chỉ nguồn | Địa chỉ của bản chụp được đọc. | Lưu cùng bản lưu. | **[CONFIRMED]** AC-025 |
| Thời điểm đọc | Thời điểm hệ thống đọc nguồn. | Dùng để xếp nhiều bản lưu. | **[CONFIRMED]** REQ-201 |

## 10. Business Flow
### BF-011-01 — Đọc nguồn lần đầu
1. **[CONFIRMED — AC-025]** A-AI nhận công ty có địa chỉ nguồn bản chụp HTML.
2. **[CONFIRMED — AC-025]** A-AI đọc nguồn và tạo bản lưu nguyên văn kèm nguồn, thời điểm đọc, công ty.

### BF-011-02 — Đọc lại nguồn
1. **[CONFIRMED — AC-026]** Công ty đã có một bản lưu.
2. **[CONFIRMED — AC-026]** A-AI đọc lại nguồn.
3. **[CONFIRMED — AC-026]** Bản lưu mới được thêm và danh sách được xếp theo thời điểm đọc.

## 11. Acceptance Criteria
### AC-025 — Tạo bản lưu
```gherkin
Scenario: Tạo bản lưu
  Given một công ty có địa chỉ nguồn (bản chụp HTML)
  When hệ thống đọc nguồn
  Then một bản lưu nguyên văn, kèm địa chỉ nguồn và thời điểm đọc, thuộc đúng công ty đó được tạo.
```
### AC-026 — Nhiều bản lưu
```gherkin
Scenario: Nhiều bản lưu một công ty
  Given công ty đã có một bản lưu
  When hệ thống đọc lại nguồn
  Then bản lưu mới được thêm và danh sách xếp theo thời điểm đọc.
```

## 12. Screen Specification
| Screen ID | Business area | Required behavior | Evidence |
|---|---|---|---|
| `SCR-US011-01` | Danh sách Bản lưu | Hiển thị nhiều Observation của đúng Công ty, mới nhất ở trên; không có hành động tạo thủ công. | **[CONFIRMED]** AC-025..026; **[OPEN QUESTION]** Q-011-01 |
| `SCR-US011-02` | Nội dung Bản lưu | Hiển thị nguyên văn, địa chỉ nguồn, thời điểm đọc và Công ty; không biến Observation thành Claim. | **[CONFIRMED]** AC-025; BR-US011-01..03 |
| `SCR-US011-03` | Trạng thái Bản lưu | Chưa có Observation, thêm bản mới không ghi đè bản cũ; lỗi đọc nguồn để US-012 quyết định. | **[CONFIRMED]** AC-026; **[OPEN QUESTION]** Q-011-02 |

## 13. Screen Design

> **UI-DESIGN UPDATE — 2026-08-14:** Wireframe BA dưới đây được tạo từ các US/AC hiện hành và thay thế trạng thái “chưa có asset” được ghi nhận trước bước UI Design.

![US-011 screen flow](./assets/screen-flow.svg)

### `SCR-US011-01` — Danh sách Bản lưu
![US-011 observation list](./assets/observation-list.svg)

### `SCR-US011-02` — Nội dung Bản lưu
![US-011 observation detail](./assets/observation-detail.svg)

### `SCR-US011-03` — Trạng thái Bản lưu
![US-011 observation states](./assets/observation-states.svg)

**[ASSUMPTION — A-011-01]** Visual language kế thừa mẫu đã duyệt cho US-001. Asset mô tả khả năng truy cứu nhưng không tự cấp quyền cho Sales và không định nghĩa lỗi đọc nguồn của US-012.

## 14. Screen States
| State | Outcome | Screen | Evidence |
|---|---|---|---|
| Chưa có bản lưu | Chưa có Observation để truy cứu và không có nút tạo thủ công. | `SCR-US011-03` | **[INFERRED]** REQ-201 |
| Có một hoặc nhiều bản lưu | Danh sách thể hiện các lần đọc, mới nhất trên. | `SCR-US011-01` | **[CONFIRMED]** AC-026 |
| Đọc lại thành công | Bản mới được thêm, bản cũ vẫn tồn tại. | `SCR-US011-03` | **[CONFIRMED]** AC-026 |

## 15. Validation
| Condition | Response | Evidence |
|---|---|---|
| Có địa chỉ nguồn và đọc được | Tạo bản lưu đủ nội dung, nguồn, thời điểm, công ty. | **[CONFIRMED]** AC-025 |
| Nguồn không đọc được | Hành vi chi tiết thuộc US-012, không suy diễn tại đây. | **[OPEN QUESTION]** Q-011-02 |

## 16. Dependencies
| Direction | Item | Dependency | Evidence |
|---|---|---|---|
| Upstream | US-001 | Cung cấp công ty và địa chỉ nguồn. | **[CONFIRMED]** US-011 dependency |
| Downstream | US-013 | Dùng bản lưu để rút phát hiện. | **[CONFIRMED]** BR-018, D-1 |

## 17. Business-level NFR Expectations
- **[CONFIRMED — REQ-704]** Dữ liệu bản lưu cần bền qua khởi động lại.
- **[CONFIRMED — C-DATA-1]** Nội dung dùng bản chụp tĩnh để kịch bản có thể lặp lại.
- **[CONFIRMED — BR-017]** Không có hành vi tự thay đổi hoặc xóa dữ liệu Sales.

## 18. Test Scenarios
| TC | Business scenario | AC / rule | Expected result |
|---|---|---|---|
| TC-011-01 | Đọc nguồn lần đầu của một công ty. | AC-025, BR-US011-01..02 | Có một bản lưu nguyên văn, nguồn, thời điểm, đúng công ty. |
| TC-011-02 | Đọc lại nguồn cho công ty đã có bản lưu. | AC-026, BR-US011-02 | Có thêm bản lưu; danh sách theo thời điểm đọc. |

## 19. Traceability
| Chain | Evidence |
|---|---|
| `REQ-201 → EPIC-04 → FEAT-011 → US-011 → AC-025..026 → TC-011-01..02 (T-8)` | **[CONFIRMED]** architect handoff |
| `BR-018 → BR-US011-03 → AC-025` | **[INFERRED]** requirement analysis |

## 20. Assumptions
| ID | Assumption | Status |
|---|---|---|
| A-011-01 | Visual language dùng mẫu đã duyệt cho US-001; khả năng truy cứu không đồng nghĩa Sales đã được cấp quyền xem. | **[ASSUMPTION]** Không đóng Q-011-01..02. |

## 21. Open Questions
| ID | Question | Owner / impact |
|---|---|---|
| Q-011-01 | Sales có được xem danh sách bản lưu trong story này hay thuộc US-015? | PO; quyền xem. |
| Q-011-02 | Khi nguồn không đọc được, liên kết sang US-012 được thể hiện ra sao? | PO; không tự đặt hành vi. |

## 22. Definition of Ready
| DoR item | Status | Evidence |
|---|---|---|
| Actor, giá trị và scope rõ | READY | US-011, REQ-201 |
| AC quan sát được | READY | AC-025..026 |
| Dependency xác định | READY | US-001; US-013 downstream |
| Traceability rõ | READY | REQ-201 → FEAT-011 → US-011 → AC → TC |
| Human approval | AWAITING_SPECIFICATION_APPROVAL | Gate 1 |

## 23. Technical Handoff
**[CONFIRMED — C-DATA-1, BR-017]** Giữ nội dung từ bản chụp nội bộ và không để luồng này ghi vào dữ liệu Sales. **[INFERRED — BR-018]** Tech Lead cần bảo toàn liên kết business từ Observation đến Claim; không có quyết định kỹ thuật, endpoint, schema hay kế hoạch triển khai trong specification này.

## 24. Change Log
| Version | Date | Change | Author/Approver |
|---|---|---|---|
| 1.1 | 2026-08-14 | Bổ sung ba SVG chi tiết cho danh sách, nội dung và trạng thái Observation; không tự cấp quyền Sales hoặc định nghĩa lỗi đọc nguồn. | Codex — UI pattern approved; specification approval unchanged |
| 1.0 | 2026-08-14 | Tạo specification 24 mục cho US-011. | Codex / awaiting human specification approval |
