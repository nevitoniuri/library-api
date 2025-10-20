--liquibase formatted sql
--changeset iuri:1760709907363
--validCheckSum 9:6bdc05bc79fa79f034003646b29df0c3

-- Primeiro, garante que a extensão uuid-ossp esteja disponível
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Cria a tabela sem as chaves estrangeiras inicialmente
CREATE TABLE IF NOT EXISTS readings (
    id             UUID        NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_id        UUID        NOT NULL,
    book_id        UUID        NOT NULL,
    status         VARCHAR(20) NOT NULL,
    current_page   INTEGER     NOT NULL CHECK (current_page >= 1),
    started_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    last_readed_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    finished_at    TIMESTAMPTZ,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uk_readings_user_book UNIQUE (user_id, book_id)
);

-- Cria os índices
CREATE INDEX IF NOT EXISTS idx_readings_user_id ON readings (user_id);
CREATE INDEX IF NOT EXISTS idx_readings_book_id ON readings (book_id);
CREATE INDEX IF NOT EXISTS idx_readings_status ON readings (status);

-- Adiciona as constraints de chave estrangeira em um changeset separado
-- Isso garante que as tabelas referenciadas já existam
--changeset iuri:add-foreign-keys-to-readings
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_name = 'fk_readings_users' AND table_name = 'readings'
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_name = 'fk_readings_books' AND table_name = 'readings'
ALTER TABLE readings
    ADD CONSTRAINT fk_readings_users
    FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE CASCADE
    DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE readings
    ADD CONSTRAINT fk_readings_books
    FOREIGN KEY (book_id)
    REFERENCES books (id)
    ON DELETE CASCADE
    DEFERRABLE INITIALLY DEFERRED;