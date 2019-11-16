package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.topica.edumall.data.model.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    Folder findByName(String name);

    Folder findByIdAndCreatedBy(Long folderId, Long userId);

    Folder findByNameAndParentFolder_Id(String name, Long folderId);

    @Query("select folder from User user " +
            "left join user.teacher teacher " +
            "left join teacher.warehouse warehouse " +
            "left join warehouse.folder folder " +
            "where folder.deletedAt is null and folder.parentFolder is null and user.id = :userId")
    Folder getFolderByUserId(@Param("userId") Long userId);
}
