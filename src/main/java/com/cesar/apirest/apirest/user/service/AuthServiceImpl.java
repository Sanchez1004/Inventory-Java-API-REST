package com.cesar.apirest.apirest.user.service;

import com.cesar.apirest.apirest.user.dto.AuthResponse;
import com.cesar.apirest.apirest.user.dto.LoginRequest;
import com.cesar.apirest.apirest.user.dto.RegisterRequest;
import com.cesar.apirest.apirest.user.jwt.JwtService;
import com.cesar.apirest.apirest.utils.Role;
import com.cesar.apirest.apirest.user.entity.UserEntity;
import com.cesar.apirest.apirest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for authentication-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Logs in a user based on the provided login request.
     *
     * @param loginRequest the login request containing user credentials
     * @return an authentication response containing a JWT token
     */
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    /**
     * Registers a new user based on the provided registration request.
     *
     * @param registerRequest the registration request containing user details
     * @return an authentication response containing a JWT token
     */
    public AuthResponse register(RegisterRequest registerRequest) {
        UserEntity user = UserEntity.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .country(registerRequest.getCountry())
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
