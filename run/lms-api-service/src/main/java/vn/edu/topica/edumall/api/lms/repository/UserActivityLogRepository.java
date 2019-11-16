package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.topica.edumall.data.model.UserActivityLog;

public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, Integer>{

}
