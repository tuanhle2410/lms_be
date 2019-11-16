package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.topica.edumall.data.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query(value = "SELECT * FROM teacher WHERE user_id = :userId", nativeQuery = true)
    Teacher getTeacherByUserId(@Param("userId") Long userId);

    boolean existsByTeacherCode(String teacherCode);
}
