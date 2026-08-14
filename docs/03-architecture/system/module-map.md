# Module map và backlog khởi tạo

| Module | Trách nhiệm đầu tiên | Story chính |
|---|---|---|
| `crm-core` | Company, Contact, Opportunity, Activity, Timeline, Next Step | US-001..010 |
| `observation` | Source adapter, Observation, Claim, provenance UI | US-011, 013, 015, 016 |
| `proposal` | Proposal queue, quyết định Sales, business audit | US-018..022 |
| `autonomy` | Auto next step, undo, watch scan, kill switch | US-025..033, 037..039 |
| `follow-up-loop` | scheduler, so sánh nội dung và timeline tự động | US-031..033 |
| `admin-safety` | kill switch, cấu hình AI, role/permission | US-037, 039, 046 |
| `notification` | thông báo trong ứng dụng, không email/push khách | US-027 |

Thứ tự khuyến nghị: hoàn thiện CRM thủ công → model Insight/provenance → Proposal → automation → auth/UI → test harness/reset. Các phần deferred được giữ trong tài liệu nguồn nhưng không đưa vào base implementation.
