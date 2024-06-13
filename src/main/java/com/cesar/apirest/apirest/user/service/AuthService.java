package com.cesar.apirest.apirest.user.service;

import com.cesar.apirest.apirest.user.DTO.AuthResponse;
import com.cesar.apirest.apirest.user.DTO.LoginRequest;
import com.cesar.apirest.apirest.user.DTO.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest registerRequest);
    AuthResponse register(RegisterRequest registerRequest);
}
