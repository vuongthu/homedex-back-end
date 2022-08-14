package com.homedex.categories;

import com.homedex.categories.models.Category;
import com.homedex.categories.models.Item;
import com.homedex.dao.CategoryDao;
import com.homedex.dao.HouseholdDao;
import com.homedex.dao.entities.CategoryEntity;
import com.homedex.dao.entities.HouseholdEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoriesService {

    private final CategoryDao categoryDao;

    private final HouseholdDao householdDao;

    public CategoriesService(CategoryDao categoryDao, HouseholdDao householdDao) {
        this.categoryDao = categoryDao;
        this.householdDao = householdDao;
    }

    public Category createCategory(String name, UUID householdId) {
        HouseholdEntity householdEntity = householdDao.findById(householdId).orElseThrow();
        CategoryEntity categoryEntity = categoryDao.save(CategoryEntity.builder().name(name).householdEntity(householdEntity).build());
        return mapToCategory(categoryEntity);
    }

    public List<Category> getCategories(UUID householdId) {
        return categoryDao.findAllByHouseholdEntityId(householdId).stream()
                .map(this::mapToCategory)
                .toList();
    }

    public Category getCategory(UUID categoryId) {
        return mapToCategory(categoryDao.findById(categoryId).orElseThrow());
    }

    public void deleteCategory(UUID categoryId) {
        categoryDao.deleteCategoryEntityById(categoryId);
    }

    public void updateCategoryName(String name, UUID categoryId) {
        CategoryEntity entity = categoryDao.findById(categoryId).orElseThrow();
        entity.setName(name);
        categoryDao.save(entity);
    }

    private Category mapToCategory(CategoryEntity entity) {
        return new Category(entity.getId(), entity.getName(), entity.getHouseholdEntity().getId());
    }
}
