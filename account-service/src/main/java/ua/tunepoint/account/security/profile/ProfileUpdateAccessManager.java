package ua.tunepoint.account.security.profile;

import ua.tunepoint.account.data.entity.Profile;
import ua.tunepoint.account.security.AccessManager;
import ua.tunepoint.security.UserPrincipal;
import ua.tunepoint.web.exception.ForbiddenException;

public class ProfileUpdateAccessManager implements AccessManager<UserPrincipal, Profile> {

    @Override
    public void authorize(UserPrincipal user, Profile entity) {
        if (user == null || !entity.getId().equals(user.getId())) {
            throw new ForbiddenException();
        }
    }
}
