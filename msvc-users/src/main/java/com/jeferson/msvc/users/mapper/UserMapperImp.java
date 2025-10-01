package com.jeferson.msvc.users.mapper;

import org.springframework.stereotype.Component;
import com.jeferson.msvc.users.dto.UserDisabledDto;
import com.jeferson.msvc.users.dto.UserRequestDto;
import com.jeferson.msvc.users.dto.UserResponseDto;
import com.jeferson.msvc.users.entities.User;
import com.jeferson.msvc.users.entities.UserStatusReason;

@Component
public class UserMapperImp implements UserMapper {

    @Override
    public User toEntity(UserRequestDto dto){
        if (dto == null) return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        return user;
    }

    @Override
    public UserResponseDto toDto(User user) {
        if (user == null) return null;

        UserResponseDto dto = new UserResponseDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());
        dto.setRegistrationDate(user.getRegistrationDate());

        return dto;
    }

    @Override
    public UserDisabledDto toDisabledDto(User user) {
        if (user == null) return null;

        UserDisabledDto dto = new UserDisabledDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());
        dto.setRegistrationDate(user.getRegistrationDate());

        String reason = user.getUserStatusReason().stream()
        .reduce((first, second) -> second) 
        .map(UserStatusReason::getReason)
        .orElse("No registrado");

        dto.setReason(reason);

        return dto;
    }

}
