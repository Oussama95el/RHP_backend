package com.simplon.rhp.user;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
}
