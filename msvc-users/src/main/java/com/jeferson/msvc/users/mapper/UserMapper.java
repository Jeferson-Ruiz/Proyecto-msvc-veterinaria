package com.jeferson.msvc.users.mapper;

import com.jeferson.msvc.users.dto.UserDisabledDto;
import com.jeferson.msvc.users.dto.UserRequestDto;
import com.jeferson.msvc.users.dto.UserResponseDto;
import com.jeferson.msvc.users.entities.User;

public interface UserMapper {
    User toEntity (UserRequestDto dto);

    UserResponseDto toDto(User user);

    UserDisabledDto toDisabledDto(User user);

    
}
