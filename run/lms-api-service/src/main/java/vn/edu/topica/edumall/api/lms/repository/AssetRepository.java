package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import vn.edu.topica.edumall.data.model.Asset;
import vn.edu.topica.edumall.data.model.Lecture;

import java.util.List;

@Repository
public interface AssetRepository extends CrudRepository<Asset, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete a from asset a\n" +
            "left join lecture l on l.id = a.lecture_id\n" +
            "where a.lecture_id = l.id and l.chapter_id = :chapterId", nativeQuery = true)
    void deleteAllByChapterId(@Param("chapterId") long chapterId);

    @Query("select asset from Asset asset where asset.lecture in :lectureList")
    List<Asset> getAssetByLectureList(@Param("lectureList") List<Lecture> lectureList);

    @Transactional
    @Modifying
    @Query(value = "delete from Asset asset where asset.id = :id")
    void deleteAssetById(@Param("id") long id);

    List<Asset> findByFileIdIn(List<Long> ids);
}
