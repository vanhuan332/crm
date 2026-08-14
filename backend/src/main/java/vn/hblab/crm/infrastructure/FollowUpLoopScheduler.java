package vn.hblab.crm.infrastructure;

import vn.hblab.crm.application.AiRuntimeState;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** Scheduler adapter only. The follow-up use case will be injected when implemented. */
@Component
public class FollowUpLoopScheduler {
    private final AiRuntimeState aiRuntimeState;
    public FollowUpLoopScheduler(AiRuntimeState aiRuntimeState) { this.aiRuntimeState = aiRuntimeState; }

    @Scheduled(fixedDelayString = "${crm.ai.scan-interval-seconds:60}000")
    public void trigger() {
        if (!aiRuntimeState.isEnabled()) return;
        // Intentionally no-op until FollowUpLoopApplicationService is implemented.
    }
}

