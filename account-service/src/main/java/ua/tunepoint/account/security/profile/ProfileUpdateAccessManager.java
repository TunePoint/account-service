package ua.tunepoint.account.security.profile;

import org.springframework.stereotype.Component;
import ua.tunepoint.account.data.entity.Profile;
import ua.tunepoint.account.security.AccessManager;
import ua.tunepoint.security.UserPrincipal;
import ua.tunepoint.web.exception.ForbiddenException;

@Component
public class ProfileUpdateAccessManager implements AccessManager<UserPrincipal, Profile> {

    @Override
    public void authorize(UserPrincipal user, Profile entity) {
        if (user == null || !entity.getId().equals(user.getId())) {
            throw new ForbiddenException();
        }
    }
}
