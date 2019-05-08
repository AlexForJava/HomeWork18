package com.gmail.service;


import com.gmail.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User service interface
 *
 * @author Oleksii Chernii
 * @version 1.0
 */
@Service
public interface UserService {
    void createUser(User user);

    User findById(Long id);

    void updateUser(User user);

    void deleteUserById(Long id);

    void deleteUser(User user);

    List<User> getAllUsers();

    List<User> getUsersByDateCreate(LocalDateTime dateCreate);
}
