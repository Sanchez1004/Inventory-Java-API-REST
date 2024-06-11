package com.cesar.apirest.apirest.auth.service;

import com.cesar.apirest.apirest.auth.AuthResponse;
import com.cesar.apirest.apirest.auth.LoginRequest;
import com.cesar.apirest.apirest.auth.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest registerRequest);
    AuthResponse register(RegisterRequest registerRequest);
}
