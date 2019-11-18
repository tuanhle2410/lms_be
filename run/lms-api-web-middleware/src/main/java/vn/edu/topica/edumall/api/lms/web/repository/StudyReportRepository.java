package vn.edu.topica.edumall.api.lms.web.repository;

import org.springframework.stereotype.Repository;
import vn.edu.topica.edumall.api.lms.web.model.StudyOverview;
import vn.edu.topica.edumall.data.model.model.StudyReport;

@Repository
public class StudyReportRepository {
    public StudyOverview findOverviewByEmail(String email) {

        //data test
        StudyReport studyReport = StudyReport.builder()
                .courseFinished(5L)
                .courseTotal(10L)
                .build();
        StudyOverview studyOverview = StudyOverview.builder()
                .courseFinished(studyReport.getCourseFinished())
                .build();
        return studyOverview;
    }
}
