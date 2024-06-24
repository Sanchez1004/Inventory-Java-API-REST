package com.cesar.apirest.apirest.user.controller;

import com.cesar.apirest.apirest.user.dto.UserRequest;
import com.cesar.apirest.apirest.exception.UserException;
import com.cesar.apirest.apirest.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controller class for handling user-related endpoints.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * Constructs a new UserController with the given UserService.
     *
     * @param userService the UserService to use for user-related operations
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves a list of all users.
     *
     * @return a ResponseEntity containing the list of users, or an error response if an exception occurs
     */
    @GetMapping("/admin/list-users")
    public ResponseEntity<List<UserRequest>> getAllUsers() {
        try {
            List<UserRequest> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching all users");
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user details wrapped in a {@link ResponseEntity}
     * @throws ResponseStatusException if the user is not found or any error occurs during the search
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/search-user-by-id/{id}")
    public ResponseEntity<UserRequest> getUserById(@PathVariable Long id) {
        try {
            UserRequest userRequest = userService.searchUserById(id);
            return ResponseEntity.ok(userRequest);
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user to retrieve
     * @return the user details wrapped in a {@link ResponseEntity}
     * @throws ResponseStatusException if the user is not found or any error occurs during the search
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/search-user-by-email/{email}")
    public ResponseEntity<UserRequest> getUserById(@PathVariable String email) {
        try {
            UserRequest userRequest = userService.searchUserByEmail(email);
            return ResponseEntity.ok(userRequest);
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * Updates a user's information.
     *
     * @param userRequest the updated user information
     * @param id          the ID of the user to update
     * @return a ResponseEntity containing the updated user information, or an error response if an exception occurs
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/admin/update-users/{id}")
    public ResponseEntity<UserRequest> updateUser(@RequestBody UserRequest userRequest, @PathVariable Long id) {
        try {
            UserRequest updatedUser = userService.updateUser(userRequest, id);
            return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
