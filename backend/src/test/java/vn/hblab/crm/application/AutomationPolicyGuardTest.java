package vn.hblab.crm.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import vn.hblab.crm.domain.ActorType;

class AutomationPolicyGuardTest {
    private final AutomationPolicyGuard guard = new AutomationPolicyGuard();
    @Test void aiCannotChangeDealsEvenWithoutUi() {
        assertThrows(AutomationPolicyViolation.class, () -> guard.assertCanChangeOpportunity(ActorType.AI_SYSTEM));
    }
    @Test void humanCanChangeDeals() {
        assertDoesNotThrow(() -> guard.assertCanChangeOpportunity(ActorType.HUMAN));
    }
    @Test void aiCannotDeleteOrContactCustomers() {
        assertThrows(AutomationPolicyViolation.class, () -> guard.assertCanDelete(ActorType.AI_SYSTEM));
        assertThrows(AutomationPolicyViolation.class, () -> guard.assertCanContactCustomer(ActorType.AI_SYSTEM));
    }
}

