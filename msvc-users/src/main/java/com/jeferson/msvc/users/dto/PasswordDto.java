package com.jeferson.msvc.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordDto {
    
    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPasswor;
}
