package com.cesar.apirest.apirest.user2.auth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    String userName;
    String password;
}
