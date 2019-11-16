package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.topica.edumall.data.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from quiz where chapter_id = :chapterId", nativeQuery = true)
    void deleteByChapterId(@Param("chapterId") Long chapterId);
}
