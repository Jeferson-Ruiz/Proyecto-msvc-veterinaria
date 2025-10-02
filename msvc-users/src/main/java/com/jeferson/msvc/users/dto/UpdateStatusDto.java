package com.jeferson.msvc.users.dto;

import com.jeferson.msvc.users.entities.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateStatusDto {
    
    @NotNull
    private UserStatus status;

    private String reason;
}
