package com.cesar.apirest.apirest.user2.auth.service;

import com.cesar.apirest.apirest.user2.auth.AuthResponse;
import com.cesar.apirest.apirest.user2.auth.LoginRequest;
import com.cesar.apirest.apirest.user2.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("AuthService")
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    public AuthResponse login(LoginRequest loginRequest) {
        return null;
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        return null;
    }
}
