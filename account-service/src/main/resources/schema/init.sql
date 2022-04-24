CREATE SCHEMA account;

CREATE TABLE account.profiles
(
    id         INTEGER PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    email VARCHAR(256) NOT NULL,
    avatar_id  VARCHAR(64),
    first_name VARCHAR(64),
    last_name  VARCHAR(64),
    bio        VARCHAR(512),
    birth_date DATE,
    country VARCHAR(32),
    city VARCHAR(32),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX profiles_id_idx ON account.profiles(id);