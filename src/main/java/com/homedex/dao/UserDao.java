package com.homedex.dao;

import com.homedex.dao.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDao extends CrudRepository<UserEntity, UUID> {
    UserEntity findByEmailOrUsername(String user, String email);
}
