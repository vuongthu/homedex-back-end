package com.homedex.dao;

import com.homedex.dao.entities.HouseholdEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HouseholdDao extends CrudRepository<HouseholdEntity, UUID> {
}
