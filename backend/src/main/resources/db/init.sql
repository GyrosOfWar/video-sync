CREATE TABLE IF NOT EXISTS participant (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "name" VARCHAR NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS video (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "url" VARCHAR NOT NULL,
    added_by BIGINT REFERENCES participant (id) NOT NULL,
    added_at TIMESTAMPTZ NOT NULL,
    "current_time" INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS room (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "name" VARCHAR NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL,
    active_video BIGINT REFERENCES video (id)
);