package com.homedex.dao;

import com.homedex.dao.entities.HouseholdEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HouseholdDao extends CrudRepository<HouseholdEntity, UUID> {
    List<HouseholdEntity> findHouseholdEntitiesByUserEntitiesId(UUID userId);

    @Modifying
    @Query("delete from HouseholdEntity h where h.id=:id")
    void deleteHouseholdEntityById(@Param("id") UUID id);
}
