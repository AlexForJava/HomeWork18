package com.gmail.service;

import com.gmail.dao.UserDao;
import com.gmail.entity.User;
import com.gmail.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * User service implementation
 *
 * @author Oleksii Chernii
 * @version 1.0
 */
@Service
@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public void createUser(User user) {
        userDao.insertUser(user);
    }

    @Override
    public User findById(Long id) {
        try {
            return userDao.getUserById(id).orElseThrow(UserNotFoundException::new);
        } catch (UserNotFoundException e) {
            log.error("UserServiceImpl method findByUser ", e);
            return new User();
        }
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
