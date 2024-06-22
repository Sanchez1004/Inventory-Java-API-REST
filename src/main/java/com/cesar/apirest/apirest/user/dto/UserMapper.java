package com.cesar.apirest.apirest.user.dto;

import com.cesar.apirest.apirest.user.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between UserRequest DTOs and UserEntity entities.
 */
@Component
public class UserMapper {

    /**
     * Converts a UserEntity entity to a UserRequest DTO.
     *
     * @param userEntity the UserEntity entity to convert
     * @return the corresponding UserRequest DTO
     */
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

    /**
     * Converts a UserRequest DTO to a UserEntity entity.
     *
     * @param userRequest the UserRequest DTO to convert
     * @return the corresponding UserEntity entity
     */
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
