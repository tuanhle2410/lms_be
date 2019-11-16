package vn.edu.topica.edumall.api.lms.service;

import vn.edu.topica.edumall.api.lms.dto.CourseDetailDTO;
import vn.edu.topica.edumall.api.lms.dto.CreateCourseDTO;
import vn.edu.topica.edumall.api.lms.dto.CreateFullCourseDTO;
import vn.edu.topica.edumall.api.lms.dto.UpdateCourseDTO;
import vn.edu.topica.edumall.data.model.Course;

public interface CourseService {

    /**
     * Used to create course
     *
     * @param createCourseDTO
     * @return
     */
    CourseDetailDTO createCourse(CreateCourseDTO createCourseDTO);

    boolean updateCourse(Course course, UpdateCourseDTO updateCourseDTO);


    CourseDetailDTO createFullCourse(CreateFullCourseDTO createFullCourseDTO);
}
