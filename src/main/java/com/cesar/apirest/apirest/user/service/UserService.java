package com.cesar.apirest.apirest.user.service;

import com.cesar.apirest.apirest.user.dto.UserRequest;
import com.cesar.apirest.apirest.user.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserRequest> getAllUsers();
    UserRequest searchUserById(int id);
    UserRequest searchUserByEmail(String email);
    UserRequest updateUser(UserRequest userRequest, int id);
}
