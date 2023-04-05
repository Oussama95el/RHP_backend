package com.simplon.rhp.user;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String firstname;
    private String lastname;
    private String email;
    private Role role;
}
