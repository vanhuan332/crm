CREATE TABLE companies (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    industry VARCHAR(255) NOT NULL,
    company_type VARCHAR(40) NOT NULL,
    country VARCHAR(100),
    website_url VARCHAR(2048),
    watching BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE observations (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT NOT NULL REFERENCES companies(id),
    source_url VARCHAR(2048) NOT NULL,
    raw_content TEXT NOT NULL,
    normalized_content_hash VARCHAR(64) NOT NULL,
    read_at TIMESTAMP WITH TIME ZONE NOT NULL,
    readable BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_observation_company_hash UNIQUE (company_id, normalized_content_hash)
);

CREATE TABLE claims (
    id BIGSERIAL PRIMARY KEY,
    observation_id BIGINT NOT NULL REFERENCES observations(id),
    summary VARCHAR(1000) NOT NULL,
    signal_type VARCHAR(40) NOT NULL,
    confidence VARCHAR(40) NOT NULL,
    quote TEXT NOT NULL,
    quote_start INTEGER NOT NULL CHECK (quote_start >= 0),
    quote_end INTEGER NOT NULL CHECK (quote_end > quote_start),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE proposals (
    id BIGSERIAL PRIMARY KEY,
    claim_id BIGINT NOT NULL REFERENCES claims(id),
    current_value TEXT NOT NULL,
    proposed_value TEXT NOT NULL,
    risk_if_wrong TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_observations_company_read_at ON observations(company_id, read_at DESC);
CREATE INDEX idx_claims_observation ON claims(observation_id);
CREATE INDEX idx_proposals_claim ON proposals(claim_id);

