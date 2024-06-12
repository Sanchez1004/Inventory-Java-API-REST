package com.cesar.apirest.apirest.user.auth.service;

import com.cesar.apirest.apirest.user.auth.AuthResponse;
import com.cesar.apirest.apirest.user.auth.LoginRequest;
import com.cesar.apirest.apirest.user.auth.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest registerRequest);
    AuthResponse register(RegisterRequest registerRequest);
}
