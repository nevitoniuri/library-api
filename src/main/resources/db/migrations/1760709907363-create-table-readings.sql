--liquibase formatted sql
--changeset iuri:1760709907363

CREATE TABLE readings
(
    id             UUID        NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_id        UUID        NOT NULL REFERENCES users (id),
    book_id        UUID        NOT NULL REFERENCES books (id),
    status         VARCHAR(20) NOT NULL,
    current_page   INTEGER     NOT NULL CHECK (current_page >= 0),
    started_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    last_readed_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    finished_at    TIMESTAMPTZ,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);