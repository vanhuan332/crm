package vn.hblab.crm.application;

import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AiRuntimeState {
    private final AtomicBoolean enabled;
    public AiRuntimeState(@Value("${crm.ai.enabled:true}") boolean enabled) { this.enabled = new AtomicBoolean(enabled); }
    public boolean isEnabled() { return enabled.get(); }
    public void setEnabled(boolean value) { enabled.set(value); }
    public void requireEnabled() { if (!isEnabled()) throw new AiDisabledException(); }
}

