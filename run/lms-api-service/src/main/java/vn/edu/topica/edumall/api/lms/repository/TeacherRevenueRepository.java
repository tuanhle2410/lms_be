package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.topica.edumall.data.model.TeacherRevenue;

public interface TeacherRevenueRepository extends JpaRepository<TeacherRevenue, Long> {
    Page<TeacherRevenue> findByEmailAndIsValid(String email, Integer isValid, Pageable pageable);

    TeacherRevenue findByMonthAndYearAndIsValid(int month, int year, int isValid);
}
