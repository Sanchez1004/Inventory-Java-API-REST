package com.cesar.apirest.apirest.user2.auth.service;

import com.cesar.apirest.apirest.user2.auth.AuthResponse;
import com.cesar.apirest.apirest.user2.auth.LoginRequest;
import com.cesar.apirest.apirest.user2.auth.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest registerRequest);
    AuthResponse register(RegisterRequest registerRequest);
}
