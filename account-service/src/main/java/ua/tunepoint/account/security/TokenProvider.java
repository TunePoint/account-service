package ua.tunepoint.account.security;

import ua.tunepoint.account.data.entity.User;

public interface TokenProvider {

    String accessToken(User user);

    String refreshToken(User user);
}
