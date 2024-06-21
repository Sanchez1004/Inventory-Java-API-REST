package com.cesar.apirest.apirest.user.dto;

import com.cesar.apirest.apirest.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String country;
    private Role role;
}
