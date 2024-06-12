package com.cesar.apirest.apirest.user.auth.service;

import com.cesar.apirest.apirest.user.auth.AuthResponse;
import com.cesar.apirest.apirest.user.auth.LoginRequest;
import com.cesar.apirest.apirest.user.auth.RegisterRequest;
import com.cesar.apirest.apirest.user.jwt.JwtService;
import com.cesar.apirest.apirest.user.Role;
import com.cesar.apirest.apirest.user.entity.UserEntity;
import com.cesar.apirest.apirest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("AuthService")
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        UserDetails user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        UserEntity user = UserEntity.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .country(registerRequest.getCountry())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
