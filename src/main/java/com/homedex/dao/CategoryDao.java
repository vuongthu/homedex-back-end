package com.homedex.dao;

import com.homedex.dao.entities.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryDao extends CrudRepository<CategoryEntity, UUID> {

    List<CategoryEntity> findAllByHouseholdEntityId(UUID householdId);
}
