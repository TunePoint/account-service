package ua.tunepoint.account.utils;

import lombok.experimental.UtilityClass;
import ua.tunepoint.security.UserPrincipal;

@UtilityClass
public class UserUtils {

    public static Long extractId(UserPrincipal user) {
        return user == null ? null : user.getId();
    }
}
