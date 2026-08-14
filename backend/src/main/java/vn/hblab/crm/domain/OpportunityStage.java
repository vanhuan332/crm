package vn.hblab.crm.domain;

public enum OpportunityStage {
    APPROACH, QUALIFIED, PROPOSAL, NEGOTIATION, WON, LOST, ON_HOLD;
    public boolean isOpen() { return this != WON && this != LOST; }
}

