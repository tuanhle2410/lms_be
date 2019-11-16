package vn.edu.topica.edumall.security.jdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.topica.edumall.data.model.RoleInfo;

@Repository("RoleInfoRepositoryInSecurity")
public interface RoleInfoRepository extends JpaRepository<RoleInfo, Long> {
    RoleInfo findByName(String name);
}
