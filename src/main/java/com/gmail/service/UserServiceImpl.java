package com.gmail.service;

import com.gmail.dao.UserDao;
import com.gmail.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by Space on 06.05.2019.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Override
    public void createUser(User user) {
        userDao.insertUser(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userDao.deleteUserById(id);
    }

    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public List<User> getUsersByDateCreate(LocalDateTime dateCreate) {
        return userDao.getUsersByDateCreate(dateCreate);
    }
}