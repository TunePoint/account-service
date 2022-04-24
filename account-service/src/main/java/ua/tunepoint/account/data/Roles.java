package ua.tunepoint.account.data;

public enum Roles {

    ROLE_ADMIN(1), ROLE_USER(2), ROLE_MODERATOR(3);

    private final int id;

    Roles(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }
}
