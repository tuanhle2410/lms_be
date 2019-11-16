package vn.edu.topica.edumall.api.lms.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.edu.topica.edumall.api.lms.dto.CategoryDTO;
import vn.edu.topica.edumall.api.lms.dto.SubCategoryDTO;
import vn.edu.topica.edumall.api.lms.repository.CategoryRepository;
import vn.edu.topica.edumall.api.lms.repository.FileRepository;
import vn.edu.topica.edumall.api.lms.repository.SubCategoryRepository;
import vn.edu.topica.edumall.api.lms.service.CategoryService;
import vn.edu.topica.edumall.data.model.Category;
import vn.edu.topica.edumall.data.model.SubCategory;
import vn.edu.topica.edumall.locale.config.Translator;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.size() == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("categories.notfound"));
        }
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category cate : categories) {
            CategoryDTO categoryDTO = modelMapper.map(cate, CategoryDTO.class);
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }

    @Override
    public List<SubCategoryDTO> getSubsByCateId(Long categoryId) {
        List<SubCategory> subCategories = subCategoryRepository.getSubsByCateId(categoryId);
        if (subCategories.size() == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("subcategories.notfound"));
        }
        List<SubCategoryDTO> subCategoryDTOS = new ArrayList<>();
        for (SubCategory subCategory : subCategories) {
            SubCategoryDTO subCategoryDTO = modelMapper.map(subCategory, SubCategoryDTO.class);
            subCategoryDTOS.add(subCategoryDTO);
        }
        return subCategoryDTOS;
    }

}
