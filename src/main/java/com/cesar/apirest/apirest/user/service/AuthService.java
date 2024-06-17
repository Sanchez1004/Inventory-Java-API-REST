package com.cesar.apirest.apirest.user.service;

import com.cesar.apirest.apirest.user.dto.AuthResponse;
import com.cesar.apirest.apirest.user.dto.LoginRequest;
import com.cesar.apirest.apirest.user.dto.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest registerRequest);
    AuthResponse register(RegisterRequest registerRequest);
}
