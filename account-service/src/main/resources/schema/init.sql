CREATE SCHEMA account;

CREATE TABLE account.users
(
    id BIGINT PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    email VARCHAR(256) NOT NULL
);

CREATE TABLE account.profiles
(
    id         BIGINT PRIMARY KEY REFERENCES account.users(id),
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

CREATE TABLE account.users_followers
(
    user_id BIGINT REFERENCES account.users(id),
    follower_id BIGINT REFERENCES account.users(id),
    following_date TIMESTAMP,

    CONSTRAINT users_followers_pk PRIMARY KEY(user_id, follower_id)
);

CREATE INDEX profiles_id_idx ON account.profiles(id);