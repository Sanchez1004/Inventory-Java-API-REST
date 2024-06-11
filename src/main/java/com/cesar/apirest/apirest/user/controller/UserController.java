package com.cesar.apirest.apirest.user.controller;

import com.cesar.apirest.apirest.user.entity.UserEntity;
import com.cesar.apirest.apirest.user.exception.UserException;
import com.cesar.apirest.apirest.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        try {
            List<UserEntity> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching all users");
        }
    }
}
