package com.gmail.service;


import com.gmail.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by Space on 06.05.2019.
 */
@Service
public interface UserService {
    void createUser(User user);

    Optional<User> findById(Long id);

    void updateUser(User user);

    void deleteUserById(Long id);

    void deleteUser(User user);

    List<User> getAllUsers();

    List<User> getUsersByDateCreate(LocalDateTime dateCreate);
}
