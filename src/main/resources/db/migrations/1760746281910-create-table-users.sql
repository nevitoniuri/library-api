--liquibase formatted sql
--changeset tiala:1760746281910

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

COMMENT ON TABLE users IS 'System users table';
COMMENT ON COLUMN users.password IS 'Password should be stored using secure hash (e.g., BCrypt)';

-- Create triggers
CREATE OR REPLACE TRIGGER trigger_updated_at
    BEFORE UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION trigger_updated_at();