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
    import java.util.function.BiConsumer;

    @Service
    public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;
        private final UserMapper userMapper;
        private final PasswordEncoder passwordEncoder;

        private final Map<UserField, BiConsumer<UserEntity, UserRequest>> updateFieldMap = new EnumMap<>(UserField.class);

        public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.userMapper = userMapper;
            this.passwordEncoder = passwordEncoder;

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

        private UserEntity getUserById(int id) {
            return userRepository.findById(id).orElseThrow(() -> new InventoryException("User not found with id: " + id));
        }

        @Override
        public List<UserRequest> getAllUsers() {
            List<UserEntity> userEntityList = userRepository.findAll();
            return userEntityList.stream().map(userMapper::toUserDTO).toList();
        }

        @Override
        public UserRequest searchUserById(int id) {
            return null;
        }

        @Override
        public UserRequest searchUserByEmail(String email) {
            return null;
        }

        @Override
        public UserRequest updateUser(UserRequest userRequest, int id) {
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
