package vn.edu.topica.edumall.api.lms.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.topica.edumall.api.lms.web.model.StudyOverview;
import vn.edu.topica.edumall.api.lms.web.repository.StudyReportRepository;
import vn.edu.topica.edumall.api.lms.web.service.StudyReportService;

@Service
public class StudyReportServiceImpl implements StudyReportService {

    @Autowired
    StudyReportRepository studyReRepository;

    @Override
    public StudyOverview getStudyOverview(String email) {
        return studyReRepository.findOverviewByEmail(email);
    }
}
