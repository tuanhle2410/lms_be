package vn.edu.topica.edumall.api.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.edu.topica.edumall.api.lms.dto.TeacherInfoUpdateDTO;
import vn.edu.topica.edumall.api.lms.service.TeacherService;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;

import javax.validation.Valid;

@RestController
@RequestMapping("/personal-info")
public class PersonalInfoController {

    @Autowired
    TeacherService teacherService;

    @GetMapping("/teacher-detail")
    public ResponseEntity<Object> getTeacherDetail(Authentication authentication) {
        String email = ((UserPrincipal) authentication.getCredentials()).getEmail();
        return new ResponseEntity<>(teacherService.getTeacherDetail(email), HttpStatus.OK);
    }

    @PutMapping("/teacher-update")
    public ResponseEntity<Object> updateTeacherPersonalDetail(@ModelAttribute @Valid TeacherInfoUpdateDTO teacherInfoUpdateDTO, Authentication authentication) {
        return new ResponseEntity<>(teacherService.updateTeacherDetail(authentication, teacherInfoUpdateDTO), HttpStatus.OK);
    }
}
