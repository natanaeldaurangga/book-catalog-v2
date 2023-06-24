package com.nael.catalog.service;

import java.util.List;
import java.util.Map;

import com.nael.catalog.DTO.CategoryCreateUpdateRequestDTO;
import com.nael.catalog.DTO.CategoryListResponseDTO;
import com.nael.catalog.DTO.ResultPageResponseDTO;
import com.nael.catalog.domain.Category;

public interface CategoryService {

    public void createUpdateCategory(CategoryCreateUpdateRequestDTO dto);

    public ResultPageResponseDTO<CategoryListResponseDTO> findCategoryList(
            Integer pages,
            Integer limit,
            String sortBy,
            String direction,
            String categoryName);

    public List<Category> findCategories(List<String> categoryCodeList);

    public List<CategoryListResponseDTO> contructDTO(List<Category> categories);

    public Map<Long, List<String>> findCategoriesMap(List<Long> bookIdList);

}
