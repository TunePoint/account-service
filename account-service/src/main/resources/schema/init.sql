CREATE SCHEMA account;

CREATE TABLE account.users
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    is_verified   BOOLEAN DEFAULT FALSE,
    is_enabled    BOOLEAN DEFAULT TRUE,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP
);

CREATE TABLE account.roles
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(64)
);

CREATE TABLE account.users_roles
(
    user_id INTEGER REFERENCES account.users (id),
    role_id INTEGER REFERENCES account.roles (id),

    CONSTRAINT users_roles_fk PRIMARY KEY (user_id, role_id)
);

CREATE TABLE account.verification_codes
(
    id              SERIAL PRIMARY KEY,
    user_id         INTEGER REFERENCES account.users (id) NOT NULL,
    code            VARCHAR(32)                           NOT NULL,
    expiration_date TIMESTAMP
);

CREATE TABLE account.profiles
(
    id         INTEGER PRIMARY KEY REFERENCES account.users (id),
    avatar_id VARCHAR(255),
    first_name VARCHAR(64),
    last_name  VARCHAR(64),
    bio        VARCHAR(512),
    birth_date DATE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX users_id_idx ON account.users (id);
CREATE INDEX verification_codes_user_id_idx ON account.verification_codes (user_id);
CREATE INDEX roles_name_idx ON account.roles (name);

INSERT INTO account.roles
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER'),
       (3, 'ROLE_MODERATOR');

CREATE FUNCTION create_profile() RETURNS trigger AS $$
BEGIN
INSERT INTO account.profiles(id, created_at, updated_at)
VALUES (new.id, new.created_at, new.updated_at);
RETURN new;
END
$$
LANGUAGE plpgsql;

CREATE TRIGGER user_created_profile
    AFTER INSERT
    ON account.users
    FOR EACH ROW EXECUTE procedure create_profile();