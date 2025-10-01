package com.jeferson.msvc.users.dto;

import java.util.Set;
import com.jeferson.msvc.users.entities.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    private Set<Roles> roles;
}
