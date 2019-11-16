package vn.edu.topica.edumall.api.lms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.topica.edumall.api.lms.repository.TeacherRepository;
import vn.edu.topica.edumall.api.lms.repository.TeacherRevenueRepository;
import vn.edu.topica.edumall.api.lms.service.TeacherRevenueService;
import vn.edu.topica.edumall.api.lms.utility.UserAuthenticationInfo;
import vn.edu.topica.edumall.data.model.TeacherRevenue;

@Service
public class TeacherRevenueServiceImpl implements TeacherRevenueService {

    @Autowired
    TeacherRevenueRepository teacherRevenueRepository;

    @Override
    public TeacherRevenue addRevenueRecord(TeacherRevenue teacherRevenue) {
        TeacherRevenue revenue = teacherRevenueRepository.findByMonthAndYearAndIsValid(teacherRevenue.getMonth(), teacherRevenue.getYear(), 1);
        if(revenue != null) {
            revenue.setIsValid(0);
        }
        return teacherRevenueRepository.save(teacherRevenue);
    }

    @Override
    public Page<TeacherRevenue> getListRevenue(Pageable pageable) {
        String email = UserAuthenticationInfo.getUserAuthenticationInfo().getEmail();
        return teacherRevenueRepository.findByEmailAndIsValid(email, 1, pageable);
    }

    @Override
    public TeacherRevenue updateRevenueRecord(TeacherRevenue teacherRevenue) {
        return teacherRevenueRepository.save(teacherRevenue);
    }
}
