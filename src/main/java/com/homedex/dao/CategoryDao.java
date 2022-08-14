package com.homedex.dao;

import com.homedex.dao.entities.CategoryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryDao extends CrudRepository<CategoryEntity, UUID> {

    List<CategoryEntity> findAllByHouseholdEntityId(UUID householdId);

    @Modifying
    @Query("delete from categories c where c.id=:id")
    void deleteCategoryEntityById(@Param("id") UUID id);
}
