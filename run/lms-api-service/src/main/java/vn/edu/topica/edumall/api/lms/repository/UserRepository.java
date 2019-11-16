package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.topica.edumall.data.model.User;

@Repository("userRepositoryInLmsApi")
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String email);
}
