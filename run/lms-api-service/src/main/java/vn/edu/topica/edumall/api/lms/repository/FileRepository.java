package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.topica.edumall.data.enumtype.FileTypeEnum;
import vn.edu.topica.edumall.data.model.File;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    File findByName(String name);

    File findByNameAndFolderId(String name, Long folderId);

    List<File> findByCreatedBy(Long userId);

    File findByIdAndCreatedBy(Long fileId, Long userId);

    List<File> findTop8ByFileTypeAndFolder_IdOrderByCreatedAtDesc(FileTypeEnum fileType, Long folderId);

    List<File> findTop8ByFileTypeNotAndFolder_IdOrderByCreatedAtDesc(FileTypeEnum fileType, Long folderId);

    Page<File> findByFolder_IdAndFileTypeOrderByCreatedAtDesc(Long folderId, FileTypeEnum fileType, Pageable pageable);

    Page<File> findByFolder_IdAndFileTypeNotOrderByCreatedAtDesc(Long folderId, FileTypeEnum fileType, Pageable pageable);

    long countByFileTypeAndFolder_Id(FileTypeEnum fileType, Long folderId);

    long countByFileTypeNotAndFolder_Id(FileTypeEnum fileType, Long folderId);


    @Modifying
    @Query(value = "update File file set file.deletedAt = :deletedAt where file.id in :listId")
    int deleteMultiFileById(@Param("deletedAt") Date deletedAt, @Param("listId") List<Long> listId);

    @Transactional
    @Modifying
    @Query(value = "delete from File file where file.id = :id")
    void deleteFileById(@Param("id") long id);

    @Query("select file from File file where file.id in :fileIdList")
    List<File> getListFileByIds(@Param("fileIdList") List<Long> fileIdList);
}
