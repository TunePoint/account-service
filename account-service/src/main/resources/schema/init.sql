CREATE SCHEMA account;

CREATE TABLE account.users
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    is_confirmed   BOOLEAN DEFAULT FALSE,
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
    user_id INTEGER REFERENCES account.users (id) ON DELETE CASCADE,
    role_id INTEGER REFERENCES account.roles (id),

    CONSTRAINT users_roles_fk PRIMARY KEY (user_id, role_id)
);

CREATE TABLE account.confirmation_codes
(
    id       INTEGER REFERENCES account.users (id) ON DELETE CASCADE NOT NULL,
    code     VARCHAR(32)                                             NOT NULL,
    due_date TIMESTAMP,
    last_sent TIMESTAMP,
    attempts INTEGER DEFAULT 0
);

CREATE TABLE account.profiles
(
    id         INTEGER PRIMARY KEY REFERENCES account.users (id),
    avatar_id  VARCHAR(64),
    first_name VARCHAR(64),
    last_name  VARCHAR(64),
    bio        VARCHAR(512),
    birth_date DATE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT profile_user_fk FOREIGN KEY (id) REFERENCES account.users (id) ON DELETE CASCADE
);

CREATE INDEX users_id_idx ON account.users (id);
CREATE INDEX verification_codes_user_id_idx ON account.confirmation_codes (id);
CREATE INDEX roles_name_idx ON account.roles (name);

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