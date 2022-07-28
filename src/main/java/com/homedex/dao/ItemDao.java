package com.homedex.dao;

import com.homedex.dao.entities.ItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemDao extends CrudRepository<ItemEntity, UUID> {
}
