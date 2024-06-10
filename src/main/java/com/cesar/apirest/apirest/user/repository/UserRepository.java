package com.cesar.apirest.apirest.user.repository;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import com.cesar.apirest.apirest.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<ItemEntity> findByEmail(String email);
}
