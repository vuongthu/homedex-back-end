package com.homedex.users;

import com.homedex.dao.UserDao;
import com.homedex.dao.entities.UserEntity;
import com.homedex.users.models.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class UsersService {
    private final UserDao userDao;

    public UsersService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser(String username, String email) {
        UserEntity userEntity = userDao.save(UserEntity.builder().username(username).email(email).build());
        return mapToUser(userEntity);
    }

    public List<User> getUsers() {
        return StreamSupport.stream(userDao.findAll().spliterator(), true)
                .map(this::mapToUser).toList();
    }

    public User getUserById(UUID userId) {
        UserEntity entity = userDao.findById(userId).orElseThrow();
        return mapToUser(entity);
    }

    private User mapToUser(UserEntity entity) {
        return new User(entity.getId(), entity.getUsername(), entity.getEmail());
    }

    public void deleteUser(UUID userId) {
        userDao.deleteById(userId);
    }

    public void updateUserInfo(String username, String email, UUID userId) {
        UserEntity entity = userDao.findById(userId).orElseThrow();
        entity.setUsername(username);
        entity.setEmail(email);
        userDao.save(entity);
    }

    public User findUser(String username, String password) {
        UserEntity entity = userDao.findByEmailOrUsername(username, username);
        return mapToUser(entity);
    }
}
