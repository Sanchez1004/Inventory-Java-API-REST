package com.cesar.apirest.apirest.user.service;

import com.cesar.apirest.apirest.user.entity.UserEntity;
import com.cesar.apirest.apirest.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(Long id) {
        return null;
    }

    @Override
    public UserEntity updateUser(Long id, UserEntity user) {
        return null;
    }

    @Override
    public boolean deleteUserById(Long id) {
        return false;
    }
}
