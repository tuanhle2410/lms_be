package vn.edu.topica.edumall.api.lms.service;

import org.springframework.security.core.Authentication;
import vn.edu.topica.edumall.api.lms.dto.TeacherDetailDTO;
import vn.edu.topica.edumall.api.lms.dto.TeacherInfoUpdateDTO;
import vn.edu.topica.edumall.api.lms.dto.TeacherInfoUpdateToKelleyDTO;

public interface TeacherService {
    TeacherDetailDTO getTeacherDetail(String email);

    TeacherInfoUpdateToKelleyDTO updateTeacherDetail(Authentication authentication, TeacherInfoUpdateDTO teacherInfoUpdateDTO);

    boolean validateTeacherInfo(Authentication authentication);
}
