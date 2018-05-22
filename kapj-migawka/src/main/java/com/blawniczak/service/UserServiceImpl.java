package com.blawniczak.service;

import com.blawniczak.controller.AdminController;
import com.blawniczak.domain.Role;
import com.blawniczak.domain.User;
import com.blawniczak.repository.RoleRepository;
import com.blawniczak.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) { return userRepository.findByEmail(email); }

    public Optional<User> findById(Long id) { return userRepository.findById(id); }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllUsers(String roleName) {
        List<User> usersList = new ArrayList<>(0);
        for(User user : userRepository.findAll()) {
            Set<Role> roles = user.getRoles();
            for(Role role : roles) {
                if(role.getName().equals(roleName)) {
                    usersList.add(user);
                }
            }
        }
        return usersList;
    }

    @Override
    @Transactional
    public void save(User user) {
        logger.info(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        HashSet<Role> hashSet = new HashSet<Role>();
        hashSet.add(roleRepository.findByName("ROLE_USER"));
        user.setRoles(hashSet);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void saveEmployee(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        HashSet<Role> hashSet = new HashSet<Role>();
        hashSet.add(roleRepository.findByName("ROLE_EMPLOYEE"));
        user.setRoles(hashSet);
        userRepository.save(user);
    }

    @Override
    public void delete(User user) { userRepository.delete(user); }

    public void update(User user) { userRepository.save(user); }
}
