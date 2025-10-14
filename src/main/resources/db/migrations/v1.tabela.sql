-- liquibase formatted sql

-- Habilita a extensão para gerar UUIDs no PostgreSQL
-- changeset library_team:1
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criação da tabela de usuários
-- changeset library_team:2
CREATE TABLE IF NOT EXISTS usuarios (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Criação da tabela de funcionários
-- changeset library_team:3
CREATE TABLE IF NOT EXISTS funcionarios (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(255) NOT NULL,
    matricula VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    cargo VARCHAR(100),
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Criação de índices
-- changeset library_team:4
CREATE INDEX IF NOT EXISTS idx_usuarios_email ON usuarios(email);
CREATE INDEX IF NOT EXISTS idx_funcionarios_matricula ON funcionarios(matricula);

-- Comentários para documentação
-- changeset library_team:5
COMMENT ON TABLE usuarios IS 'Tabela de usuários do sistema';
COMMENT ON COLUMN usuarios.senha IS 'Senha deve ser armazenada usando hash seguro (ex: BCrypt)';
COMMENT ON TABLE funcionarios IS 'Tabela de funcionários da biblioteca';
COMMENT ON COLUMN funcionarios.senha IS 'Senha deve ser armazenada usando hash seguro (ex: BCrypt)';

-- Criação da função para atualizar timestamps
-- changeset library_team:6 splitStatements:false
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $BODY$
BEGIN
    NEW.atualizado_em = NOW();
    RETURN NEW;
END;
$BODY$ LANGUAGE plpgsql;

-- Criação das triggers
-- changeset library_team:7
CREATE OR REPLACE TRIGGER update_usuarios_updated_at
BEFORE UPDATE ON usuarios
FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- changeset library_team:8
CREATE OR REPLACE TRIGGER update_funcionarios_updated_at
BEFORE UPDATE ON funcionarios
FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
