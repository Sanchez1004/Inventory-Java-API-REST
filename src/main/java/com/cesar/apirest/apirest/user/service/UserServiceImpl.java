package com.cesar.apirest.apirest.user.service;

import com.cesar.apirest.apirest.exception.InventoryException;
import com.cesar.apirest.apirest.exception.UserException;
import com.cesar.apirest.apirest.user.dto.UserMapper;
import com.cesar.apirest.apirest.user.dto.UserRequest;
import com.cesar.apirest.apirest.user.entity.UserEntity;
import com.cesar.apirest.apirest.user.repository.UserRepository;
import com.cesar.apirest.apirest.utils.UserField;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * Service implementation for user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final Map<UserField, BiConsumer<UserEntity, UserRequest>> updateFieldMap = new EnumMap<>(UserField.class);

    /**
     * Constructor to initialize UserServiceImpl with dependencies.
     *
     * @param userRepository  the user repository
     * @param userMapper      the user mapper
     * @param passwordEncoder the password encoder
     */
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;

        initializeUpdateFieldMap();
    }

    /**
     * Initializes the updateFieldMap with field update logic.
     */
    private void initializeUpdateFieldMap() {
        updateFieldMap.put(UserField.FIRST_NAME, (entity, request) -> {
            if (request.getFirstName() != null) {
                entity.setFirstName(request.getFirstName());
            }
        });
        updateFieldMap.put(UserField.LAST_NAME, (entity, request) -> {
            if (request.getLastName() != null) {
                entity.setLastName(request.getLastName());
            }
        });
        updateFieldMap.put(UserField.EMAIL, (entity, request) -> {
            if (request.getEmail() != null) {
                entity.setEmail(request.getEmail());
            }
        });
        updateFieldMap.put(UserField.PASSWORD, (entity, request) -> {
            if (request.getPassword() != null) {
                entity.setPassword(passwordEncoder.encode(request.getPassword()));
            }
        });
        updateFieldMap.put(UserField.COUNTRY, (entity, request) -> {
            if (request.getCountry() != null) {
                entity.setCountry(request.getCountry());
            }
        });
        updateFieldMap.put(UserField.ROLE, (entity, request) -> {
            if (request.getRole() != null) {
                entity.setRole(request.getRole());
            }
        });
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the user ID
     * @return the user entity
     * @throws InventoryException if the user is not found
     */
    private UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new InventoryException("User not found with id: " + id));
    }

    /**
     * Retrieves all users.
     *
     * @return the list of user requests
     */
    @Override
    public List<UserRequest> getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        return userEntityList.stream().map(userMapper::toUserDTO).toList();
    }

    /**
     * Searches for a user by their ID.
     *
     * @param id the user ID
     * @return the user request
     * @throws UserException if the user is not found
     */
    @Override
    public UserRequest searchUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        Optional<UserRequest> userDTO = userEntity.map(userMapper::toUserDTO);
        return userDTO.orElseThrow(() -> new UserException("User not found with id: " + id));
    }

    /**
     * Searches for a user by their email.
     *
     * @param email the user email
     * @return the user request
     * @throws UserException if the user is not found
     */
    @Override
    public UserRequest searchUserByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        Optional<UserRequest> userDTO = userEntity.map(userMapper::toUserDTO);
        return userDTO.orElseThrow(() -> new UserException("User not found with email: " + email));
    }

    /**
     * Updates a user's information.
     *
     * @param userRequest the user request with updated information
     * @param id          the user ID
     * @return the updated user request
     * @throws UserException if the user request is null
     */
    @Override
    public UserRequest updateUser(UserRequest userRequest, Long id) {
        if (userRequest == null) {
            throw new UserException("User request cannot be null");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().stream().findFirst().orElseThrow().getAuthority();

        UserEntity userEntity = getUserById(id);

        for (Map.Entry<UserField, BiConsumer<UserEntity, UserRequest>> entry : updateFieldMap.entrySet()) {
            if (entry.getKey() == UserField.ROLE && !userRole.equals("ROLE_ADMIN")) {
                continue;
            }
            entry.getValue().accept(userEntity, userRequest);
        }

        userRepository.save(userEntity);
        return userMapper.toUserDTO(userEntity);
    }
}
