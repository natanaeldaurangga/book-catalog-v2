package com.nael.catalog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nael.catalog.DTO.CategoryCreateUpdateRequestDTO;
import com.nael.catalog.DTO.CategoryListResponseDTO;
import com.nael.catalog.DTO.CategoryQueryDTO;
import com.nael.catalog.DTO.ResultPageResponseDTO;
import com.nael.catalog.domain.Category;
import com.nael.catalog.exception.BadRequestException;
import com.nael.catalog.repository.CategoryRepository;
import com.nael.catalog.repository.PublisherRepository;
import com.nael.catalog.service.CategoryService;
import com.nael.catalog.util.PaginationUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void createUpdateCategory(CategoryCreateUpdateRequestDTO dto) {
        // TODO Auto-generated method stub
        Category category = categoryRepository.findByCode(dto.getCode().toLowerCase()).orElse(new Category());
        if (category.getCode() == null) {
            category.setCode(dto.getCode().toLowerCase()); // jika data code cateogry tidak ada, atau membuat category
                                                           // baru
        }
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        categoryRepository.save(category);
    }

    @Override
    public ResultPageResponseDTO<CategoryListResponseDTO> findCategoryList(
            Integer pages, Integer limit, String sortBy,
            String direction, String categoryName) {
        categoryName = StringUtils.isEmpty(categoryName) ? "%" : categoryName + "%";
        Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(pages, limit, sort);
        Page<Category> pageResult = categoryRepository.findByNameLikeIgnoreCase(categoryName, pageable);
        // TODO: Lanjut untuk pagination data category
        List<CategoryListResponseDTO> dtos = pageResult.stream().map(c -> {
            CategoryListResponseDTO dto = new CategoryListResponseDTO();
            dto.setCode(c.getCode());
            dto.setName(c.getName());
            dto.setDescription(c.getDescription());
            return dto;
        }).collect(Collectors.toList());
        return PaginationUtil.createResultPageDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
    }

    @Override
    public List<Category> findCategories(List<String> categoryCodeList) {
        List<Category> categories = categoryRepository.findByCodeIn(categoryCodeList);
        if (categories.isEmpty())
            throw new BadRequestException("category can't empty");
        return categories;
    }

    @Override
    public List<CategoryListResponseDTO> contructDTO(List<Category> categories) {
        return categories.stream().map((c) -> {
            CategoryListResponseDTO dto = new CategoryListResponseDTO();
            dto.setCode(c.getCode());
            dto.setName(c.getName());
            dto.setDescription(c.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Map<Long, List<String>> findCategoriesMap(List<Long> bookIdList) {
        List<CategoryQueryDTO> queryList = categoryRepository.findCategoryByBookIdList(bookIdList);
        Map<Long, List<String>> categoryMaps = new HashMap<>();
        List<String> categoryCodeList = null;
        for (CategoryQueryDTO q : queryList) {// menambahkan pasangan dari book id engn list dari categoryNya
            if (!categoryMaps.containsKey(q.getBookId())) {
                categoryCodeList = new ArrayList<>();
            } else {
                categoryCodeList = categoryMaps.get(q.getBookId());
            }
            categoryCodeList.add(q.getCategoryCode());
            categoryMaps.put(q.getBookId(), categoryCodeList);
        }
        return categoryMaps;
    }

}
