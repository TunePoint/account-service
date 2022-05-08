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
    pseudonym VARCHAR(64),
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

CREATE TABLE account.users_statistics
(
    id BIGINT PRIMARY KEY REFERENCES account.users(id),
    follower_count BIGINT DEFAULT 0,
    following_count BIGINT DEFAULT 0,
    audio_count BIGINT DEFAULT 0
);

CREATE INDEX profiles_id_idx ON account.profiles(id);

CREATE FUNCTION create_profile() RETURNS trigger AS $$
BEGIN
    INSERT INTO account.profiles(id) values (new.id);
RETURN new;
END $$ LANGUAGE plpgsql;

CREATE TRIGGER user_created_profile AFTER INSERT ON account.users
    FOR EACH ROW EXECUTE procedure create_profile();

CREATE FUNCTION create_user_statistics() RETURNS trigger AS $$
BEGIN
    INSERT INTO account.users_statistics(id) values (new.id);
RETURN new;
END $$ LANGUAGE plpgsql;

CREATE TRIGGER user_created AFTER INSERT ON account.users
    FOR EACH ROW EXECUTE procedure create_user_statistics();

CREATE FUNCTION update_user_statistics_follow() RETURNS trigger AS $$
BEGIN
    UPDATE account.users_statistics SET follower_count = follower_count + 1 WHERE id = new.user_id;
    UPDATE account.users_statistics SET following_count = following_count + 1 WHERE id = new.follower_id;
RETURN new;
END $$ LANGUAGE plpgsql;

CREATE TRIGGER follow_event AFTER INSERT ON account.users_followers
    FOR EACH ROW EXECUTE procedure update_user_statistics_follow();

CREATE FUNCTION update_user_statistics_unfollow() RETURNS trigger AS $$
BEGIN
    UPDATE account.users_statistics SET follower_count = follower_count - 1 WHERE id = old.user_id;
    UPDATE account.users_statistics SET following_count = following_count - 1 WHERE id = old.follower_id;
RETURN new;
END $$ LANGUAGE plpgsql;

CREATE TRIGGER unfollow_event AFTER DELETE ON account.users_followers
    FOR EACH ROW EXECUTE procedure update_user_statistics_unfollow();


-- drop function create_user_statistics;
-- drop function update_user_statistics_follow;
-- drop function update_user_statistics_unfollow;
