package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.topica.edumall.data.model.SubCategory;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query(value = "SELECT * FROM sub_category WHERE parent_category_id = :cateId", nativeQuery = true)
    List<SubCategory> getSubsByCateId(@Param("cateId") Long cateId);
}
