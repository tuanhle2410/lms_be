package vn.edu.topica.edumall.security.core.service;

import org.springframework.stereotype.Service;

import vn.edu.topica.edumall.security.core.model.SSOUserInfo;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;

@Service
public class LmsUserDetailService {

    public UserPrincipal loadUser(SSOUserInfo ssoUserInfo) {
        return UserPrincipal.create(ssoUserInfo);
    }

}
