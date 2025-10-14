package com.jeferson.msvc.users.mapper;

import com.jeferson.msvc.users.dto.UserDisabledDto;
import com.jeferson.msvc.users.dto.UserRequestDto;
import com.jeferson.msvc.users.dto.UserRespondeDto;
import com.jeferson.msvc.users.dto.UserDetailsDto;
import com.jeferson.msvc.users.entities.User;

public interface UserMapper {
    User toEntity (UserRequestDto dto);

    UserDetailsDto toDto(User user);

    UserRespondeDto toRespondeDto(User user);

    UserDisabledDto toDisabledDto(User user);

    
}
