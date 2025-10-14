package com.jeferson.springcloud.msvc.oauth.models;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private UserStatus status;
    private Set<Role> roles = new HashSet<>();
}