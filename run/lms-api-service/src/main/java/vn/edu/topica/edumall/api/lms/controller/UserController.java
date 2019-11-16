package vn.edu.topica.edumall.api.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.edu.topica.edumall.api.lms.dto.SubCategoryDTO;
import vn.edu.topica.edumall.api.lms.dto.UserKellyDTO;
import vn.edu.topica.edumall.api.lms.service.UserService;
import vn.edu.topica.edumall.data.model.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/detail")
    public ResponseEntity<Object> getUserDetail(Authentication authentication) {
        return new ResponseEntity<>(userService.getUserDetail(authentication), HttpStatus.OK);
    }

    @PostMapping("/kelley")
    public ResponseEntity<User> importUserKelly(@RequestBody @Valid UserKellyDTO userKellyDTO) {
        return new ResponseEntity<>(userService.importUserFromKelly(userKellyDTO), HttpStatus.OK);
    }

}
