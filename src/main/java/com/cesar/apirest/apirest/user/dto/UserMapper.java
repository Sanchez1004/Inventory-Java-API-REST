package com.cesar.apirest.apirest.user.dto;

import com.cesar.apirest.apirest.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserRequest toUserDTO(UserEntity userEntity) {
        return UserRequest
                .builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .country(userEntity.getCountry())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .build();
    }

    public UserEntity toUserEntity(UserRequest userRequest) {
        return UserEntity
                .builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .country(userRequest.getCountry())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .role(userRequest.getRole())
                .build();
    }
}
