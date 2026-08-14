package vn.hblab.crm.application;

import vn.hblab.crm.domain.ActorType;
import org.springframework.stereotype.Component;

/** Enforces BR-017 for every application command, including non-UI callers. */
@Component
public class AutomationPolicyGuard {
    public void assertCanChangeOpportunity(ActorType actor) {
        rejectAi(actor, "AI cannot change opportunity stage or monetary value");
    }
    public void assertCanDelete(ActorType actor) {
        rejectAi(actor, "AI cannot delete human-created CRM data");
    }
    public void assertCanContactCustomer(ActorType actor) {
        rejectAi(actor, "AI cannot contact customers");
    }
    private void rejectAi(ActorType actor, String message) {
        if (actor == ActorType.AI_SYSTEM) throw new AutomationPolicyViolation(message);
    }
}

