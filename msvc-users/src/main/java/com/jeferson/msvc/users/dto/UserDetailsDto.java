package com.jeferson.msvc.users.dto;

import java.time.LocalDateTime;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeferson.msvc.users.entities.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsDto {
    private String username;
    private String email;
    protected String password;
    private String userCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime registrationDate;
    
    private UserStatus status;
    
    private Set<RoleDto> roles;
}
