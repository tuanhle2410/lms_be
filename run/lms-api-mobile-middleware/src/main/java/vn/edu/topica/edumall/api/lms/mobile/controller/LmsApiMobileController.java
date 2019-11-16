package vn.edu.topica.edumall.api.lms.mobile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile-api")
public class LmsApiMobileController {

    @GetMapping("")
    public ResponseEntity<Object> apiMobile() {
        return new ResponseEntity<>("mobile-api", HttpStatus.OK);
    }
}
