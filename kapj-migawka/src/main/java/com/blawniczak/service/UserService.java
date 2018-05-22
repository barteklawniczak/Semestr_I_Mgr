package com.blawniczak.service;

import com.blawniczak.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findByUsername(String username);
    User findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findAll();
    List<User> findAllUsers(String roleName);
    void save(User user);
    void saveEmployee(User user);
    void delete(User user);
    void update(User user);
}
