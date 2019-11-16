package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.topica.edumall.data.model.Chapter;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Chapter findFirstByCourseVersionIdOrderByChapterOrderDesc(long courseVersionId);

    Chapter findByIdAndCreatedBy(Long id, Long userId);

    @Query("select chapter from Chapter chapter where chapter.id in :chapterIdList")
    List<Chapter> getLectureByIdList(@Param("chapterIdList") List<Long> chapterIdList);
}
