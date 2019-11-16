package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.topica.edumall.data.model.Category;
import vn.edu.topica.edumall.data.model.MediaToolResult;

import java.util.List;

@Repository
public interface MediaToolResultRepository extends JpaRepository<MediaToolResult, Long> {

    @Query(value = "select * from media_tool_result where file_id in :fileIds", nativeQuery = true)
    List<MediaToolResult> getMediaToolResultListByFileIds(@Param("fileIds") List<Long> fileIds);
}
