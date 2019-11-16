package vn.edu.topica.edumall.security.jdbc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.topica.edumall.data.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	/**
	 * @param username
	 * @param email
	 * @return
	 */
	Optional<User> findByUsernameOrEmail(String username, String email);

	/**
	 * @param username
	 * @return
	 */
	boolean existsByUsername(String username);

	/**
	 * @param email
	 * @return
	 */
	boolean existsByEmail(String email);
}
