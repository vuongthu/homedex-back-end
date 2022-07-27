package com.homedex.users;

import com.homedex.dao.UserDao;
import com.homedex.dao.entities.UserEntity;
import com.homedex.users.models.User;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UserDao userDao;

    public UsersService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser(String username, String email) {
        UserEntity userEntity = userDao.save(UserEntity.builder().username(username).email(email).build());
        return new User(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail());
    }
}
