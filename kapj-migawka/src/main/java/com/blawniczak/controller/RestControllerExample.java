package com.blawniczak.controller;

import com.blawniczak.domain.RestUser;
import com.blawniczak.domain.User;
import com.blawniczak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestControllerExample {

    @Autowired
    private UserService userService;

    @GetMapping("/rest/users/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        User user = userService.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity("No User found for id " + id, HttpStatus.NOT_FOUND);
        }
        RestUser restUser = new RestUser();
        restUser.convertFields(user);
        return new ResponseEntity(restUser, HttpStatus.OK);
    }

    @GetMapping("/rest/users")
    public ResponseEntity getUsers() {
        List<User> usersList = userService.findAll();
        if (usersList == null) {
            return new ResponseEntity("No User found", HttpStatus.NOT_FOUND);
        }

        List<RestUser> restUsersList = new ArrayList<>(usersList.size());
        for(User user : usersList) {
            RestUser restUser = new RestUser();
            restUser.convertFields(user);
            restUsersList.add(restUser);
        }

        return new ResponseEntity(restUsersList, HttpStatus.OK);
    }
}
