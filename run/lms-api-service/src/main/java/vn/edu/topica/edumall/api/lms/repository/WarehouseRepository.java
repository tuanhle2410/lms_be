package vn.edu.topica.edumall.api.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.topica.edumall.data.model.WareHouse;

@Repository
public interface WarehouseRepository extends JpaRepository<WareHouse, Long> {
    WareHouse findByTeacherId(long teacherId);
}
