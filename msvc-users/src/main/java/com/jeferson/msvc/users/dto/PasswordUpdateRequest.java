package com.jeferson.msvc.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String newPassword;
}
