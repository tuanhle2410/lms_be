package vn.edu.topica.edumall.api.lms.service;

import org.springframework.security.core.Authentication;
import vn.edu.topica.edumall.api.lms.dto.UserDetailDTO;
import vn.edu.topica.edumall.api.lms.dto.UserKellyDTO;
import vn.edu.topica.edumall.data.model.User;

public interface UserService {
    UserDetailDTO getUserDetail(Authentication authentication);

    User importUserFromKelly(UserKellyDTO userKellyDTO);

}
