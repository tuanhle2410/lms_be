package vn.edu.topica.edumall.api.lms.service;

import java.util.List;

import vn.edu.topica.edumall.api.lms.dto.CategoryDTO;
import vn.edu.topica.edumall.api.lms.dto.SubCategoryDTO;

public interface CategoryService {
    /**
     * @return
     */
    List<CategoryDTO> getAll();

	/**
	 * @param categoryId
	 * @return
	 */
	List<SubCategoryDTO> getSubsByCateId(Long categoryId);
}
