package vn.edu.topica.edumall.api.lms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.topica.edumall.data.model.TeacherRevenue;

public interface TeacherRevenueService {

    TeacherRevenue addRevenueRecord(TeacherRevenue teacherRevenue);

    Page<TeacherRevenue> getListRevenue(Pageable pageable);

    TeacherRevenue updateRevenueRecord(TeacherRevenue teacherRevenue);
}
