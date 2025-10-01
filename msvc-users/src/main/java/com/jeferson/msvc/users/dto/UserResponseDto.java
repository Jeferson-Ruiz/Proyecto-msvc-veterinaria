package com.jeferson.msvc.users.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeferson.msvc.users.entities.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String username;
    private String email;
    private UserStatus status;
        
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime registrationDate;

}
