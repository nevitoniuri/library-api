--liquibase formatted sql
--changeset iuri:1760412940331

CREATE TABLE books
(
    id              UUID         NOT NULL DEFAULT uuidv7(),
    title           VARCHAR(255) NOT NULL,
    isbn            VARCHAR(13)  NOT NULL,
    number_of_pages INT          NOT NULL,
    published_year  VARCHAR(4)   NOT NULL,
    created_at      timestamptz  NOT NULL DEFAULT now(),
    updated_at      timestamptz  NOT NULL DEFAULT now(),
    CONSTRAINT pk_books PRIMARY KEY (id),
    CONSTRAINT uq_books_isbn UNIQUE (isbn)
);
