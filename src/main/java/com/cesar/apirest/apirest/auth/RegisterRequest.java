package com.cesar.apirest.apirest.user2.auth;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String userName;
    String password;
    String firstName;
    String lastName;
    String country;
}
