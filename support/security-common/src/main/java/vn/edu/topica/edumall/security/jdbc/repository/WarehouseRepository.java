package vn.edu.topica.edumall.security.jdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.topica.edumall.data.model.Folder;
import vn.edu.topica.edumall.data.model.WareHouse;

@Repository("warehouseRepositoryInSecurity")
public interface WarehouseRepository extends JpaRepository<WareHouse, Long> {
}
