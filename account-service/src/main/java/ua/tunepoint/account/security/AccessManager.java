package ua.tunepoint.account.security;

public interface AccessManager<U, E> {

    void authorize(U user, E entity);
}
