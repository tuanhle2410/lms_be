package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.topica.edumall.data.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
