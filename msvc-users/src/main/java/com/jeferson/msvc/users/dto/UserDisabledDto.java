package com.jeferson.msvc.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDisabledDto extends UserResponseDto{
    private String reason;

}
