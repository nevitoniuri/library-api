--liquibase formatted sql
--changeset iuri:1760746281910

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create users table
-- changeset library_team:2
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Create employees table
-- changeset library_team:3
CREATE TABLE IF NOT EXISTS employees (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    registration_number VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    position VARCHAR(100),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Create indexes
-- changeset library_team:4
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_employees_registration ON employees(registration_number);

-- Documentation comments
-- changeset library_team:5
COMMENT ON TABLE users IS 'System users table';
COMMENT ON COLUMN users.password IS 'Password should be stored using secure hash (e.g., BCrypt)';
COMMENT ON TABLE employees IS 'Library employees table';
COMMENT ON COLUMN employees.password IS 'Password should be stored using secure hash (e.g., BCrypt)';

-- Create function to update timestamps
-- changeset library_team:6 splitStatements:false
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $BODY$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$BODY$ LANGUAGE plpgsql;

-- Create triggers
-- changeset library_team:7
CREATE OR REPLACE TRIGGER update_users_updated_at
BEFORE UPDATE ON users
FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE OR REPLACE TRIGGER update_employees_updated_at
BEFORE UPDATE ON employees
FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
