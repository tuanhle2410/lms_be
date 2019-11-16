package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.topica.edumall.data.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
