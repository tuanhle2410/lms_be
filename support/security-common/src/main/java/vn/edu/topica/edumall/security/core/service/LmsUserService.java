package vn.edu.topica.edumall.security.core.service;

import vn.edu.topica.edumall.data.model.User;
import vn.edu.topica.edumall.security.core.model.SSOUserInfo;

public interface LmsUserService {
    /**
     * Used create teacher default:
     * a user, a teacher, a warehouse, a folder_root
     * for teacher
     *
     * @param ssoUserInfo
     */
    User createTeacher(SSOUserInfo ssoUserInfo, User userFind);

    /**
     *Used update user info
     *
     * @param ssoUserInfo
     * @param userFind
     */
    void updateUserInfo(SSOUserInfo ssoUserInfo, User userFind);
}
