package vn.edu.topica.edumall.api.lms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.topica.edumall.api.lms.web.service.StudyReportService;

@RestController
@RequestMapping("/study-report")
public class StudyReportController {

    @Autowired
    StudyReportService studyReportService;

    @GetMapping("/overview")
    public ResponseEntity<Object> getStudyOverview(@RequestParam("email") String email) {
        return new ResponseEntity<>(studyReportService.getStudyOverview(email), HttpStatus.OK);
    }
}
