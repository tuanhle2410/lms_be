package vn.edu.topica.edumall.api.lms.web.service;

import vn.edu.topica.edumall.api.lms.web.model.StudyOverview;

public interface StudyReportService {
    StudyOverview getStudyOverview(String email);
}
