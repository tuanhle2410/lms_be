package vn.edu.topica.edumall.api.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.topica.edumall.api.lms.dto.CategoryDTO;
import vn.edu.topica.edumall.api.lms.dto.SubCategoryDTO;
import vn.edu.topica.edumall.api.lms.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("")
	public ResponseEntity<List<CategoryDTO>> allCategory() {
		return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}/subcategories")
	public ResponseEntity<List<SubCategoryDTO>> getSubsByCateId(@PathVariable("id") Long cateId) {
		return new ResponseEntity<>(categoryService.getSubsByCateId(cateId), HttpStatus.OK);
	}

}
