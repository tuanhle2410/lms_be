package vn.edu.topica.edumall.api.lms.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;

public class UserAuthenticationInfo {

    public static UserPrincipal getUserAuthenticationInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = ((UserPrincipal)authentication.getCredentials());
        return user;
    }
}
