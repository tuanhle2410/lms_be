package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.topica.edumall.data.model.Chapter;
import vn.edu.topica.edumall.data.model.Lecture;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    Lecture findByIdAndCreatedBy(Long id, Long userId);

    @Query("select lecture from Lecture lecture where lecture.chapter in :chapterList")
    List<Lecture> getLectureByChapter(@Param("chapterList") List<Chapter> chapterList);

    @Transactional
    @Modifying
    @Query(value = "delete from lecture where chapter_id = :chapterId", nativeQuery = true)
    void deleteByChapterId(@Param("chapterId") Long chapterId);

    @Query("select lecture from Lecture lecture where lecture.id in :idList")
    List<Lecture> getLectureByListId(@Param("idList") List<Long> idList);
}
