package vn.edu.topica.edumall.security.jdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.topica.edumall.data.model.Folder;

@Repository("folderRepositoryInSecurity")
public interface FolderRepository extends JpaRepository<Folder, Long> {
}
