package com.cesar.apirest.apirest.user.service;

import com.cesar.apirest.apirest.user.dto.UserRequest;

import java.util.List;

public interface UserService {
    List<UserRequest> getAllUsers();
    UserRequest searchUserById(Long id);
    UserRequest searchUserByEmail(String email);
    UserRequest updateUser(UserRequest userRequest, Long id);
}
