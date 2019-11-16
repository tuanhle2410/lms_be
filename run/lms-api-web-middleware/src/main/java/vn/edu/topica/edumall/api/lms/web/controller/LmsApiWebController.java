package vn.edu.topica.edumall.api.lms.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web-api")
public class LmsApiWebController {

    @GetMapping("")
    public ResponseEntity<Object> apiWeb() {
        return new ResponseEntity<>("web-api", HttpStatus.OK);
    }
}
