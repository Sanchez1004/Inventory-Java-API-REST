package com.cesar.apirest.apirest.user2.service;

import com.cesar.apirest.apirest.user2.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> getAllUsers();
    UserEntity getUserById(Long id);
    UserEntity createUser(UserEntity user);
    UserEntity updateUser(Long id, UserEntity user);
    boolean deleteUserById(Long id);
}
