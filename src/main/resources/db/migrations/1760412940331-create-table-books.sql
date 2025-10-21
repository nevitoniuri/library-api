--liquibase formatted sql
--changeset iuri:1760412940331

CREATE TABLE books
(
    id               UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    isbn             VARCHAR(13)  NOT NULL,
    number_of_pages  INTEGER      NOT NULL CHECK (number_of_pages >= 1),
    publication_date DATE         NOT NULL,
    created_at       TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at       TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT uq_books_isbn UNIQUE (isbn)
);

CREATE OR REPLACE TRIGGER trigger_updated_at
    BEFORE UPDATE
    ON books
    FOR EACH ROW
EXECUTE FUNCTION trigger_updated_at();