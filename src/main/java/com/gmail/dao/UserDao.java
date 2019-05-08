package com.gmail.dao;

import com.gmail.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User data access object interface
 *
 * @author Oleksii Chernii
 * @version 1.0
 */
public interface UserDao {
    void insertUser(User user);

    Optional<User> getUserById(Long id);

    void updateUser(User user);

    void deleteUserById(Long id);

    void deleteUser(User user);

    List<User> getAllUsers();

    List<User> getUsersByDateCreate(LocalDateTime dateCreate);
}
