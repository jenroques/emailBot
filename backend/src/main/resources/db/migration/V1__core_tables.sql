CREATE TABLE IF NOT EXISTS app_user (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    display_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS app_role (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS app_user_role (
    user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES app_role(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS email_message (
    id BIGSERIAL PRIMARY KEY,
    external_message_id VARCHAR(255) NOT NULL UNIQUE,
    sender_email VARCHAR(255) NOT NULL,
    subject VARCHAR(500) NOT NULL,
    body TEXT NOT NULL,
    received_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS email_classification (
    id BIGSERIAL PRIMARY KEY,
    email_message_id BIGINT NOT NULL REFERENCES email_message(id) ON DELETE CASCADE,
    label VARCHAR(100) NOT NULL,
    confidence NUMERIC(5,4) NOT NULL,
    classified_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS routing_rule (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    condition_expression TEXT NOT NULL,
    action_expression TEXT NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS audit_event (
    id BIGSERIAL PRIMARY KEY,
    event_type VARCHAR(120) NOT NULL,
    actor VARCHAR(255) NOT NULL,
    details JSONB NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_email_message_received_at ON email_message(received_at DESC);
CREATE INDEX IF NOT EXISTS idx_email_classification_email_message_id ON email_classification(email_message_id);
CREATE INDEX IF NOT EXISTS idx_audit_event_created_at ON audit_event(created_at DESC);

INSERT INTO app_role (role_name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN')
ON CONFLICT (role_name) DO NOTHING;
