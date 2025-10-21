--liquibase formatted sql
--changeset tiala:1761049980056

CREATE TABLE IF NOT EXISTS employees
(
    id                  UUID PRIMARY KEY      DEFAULT uuid_generate_v4(),
    name                VARCHAR(255) NOT NULL,
    registration_number VARCHAR(50)  NOT NULL UNIQUE,
    password            VARCHAR(255) NOT NULL,
    email               VARCHAR(255) UNIQUE,
    position            VARCHAR(100),
    active              BOOLEAN               DEFAULT TRUE,
    created_at          TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_employees_registration ON employees(registration_number);

COMMENT ON TABLE employees IS 'Library employees table';
COMMENT ON COLUMN employees.password IS 'Password should be stored using secure hash (e.g., BCrypt)';

CREATE OR REPLACE TRIGGER trigger_updated_at
    BEFORE UPDATE
    ON employees
    FOR EACH ROW
EXECUTE FUNCTION trigger_updated_at();
