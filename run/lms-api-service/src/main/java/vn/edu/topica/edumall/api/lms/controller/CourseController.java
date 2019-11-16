package vn.edu.topica.edumall.api.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.topica.edumall.api.lms.dto.CourseDTO;
import vn.edu.topica.edumall.api.lms.dto.CreateCourseDTO;
import vn.edu.topica.edumall.api.lms.dto.CreateFullCourseDTO;
import vn.edu.topica.edumall.api.lms.service.CourseService;

import javax.validation.Valid;


@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping("")
    public ResponseEntity<Object> createCourse(@RequestBody @Valid CreateCourseDTO createCourseDTO) {
        return new ResponseEntity<>(courseService.createCourse(createCourseDTO), HttpStatus.OK);
    }

    @PostMapping("/create-full-course")
    public ResponseEntity<Object> createCourse(@RequestBody @Valid CreateFullCourseDTO createFullCourseDTO) {
        return new ResponseEntity<>(courseService.createFullCourse(createFullCourseDTO), HttpStatus.OK);
    }
}
