package com.jeferson.msvc.users.mapper;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.jeferson.msvc.users.dto.UserDisabledDto;
import com.jeferson.msvc.users.dto.UserRequestDto;
import com.jeferson.msvc.users.dto.UserRespondeDto;
import com.jeferson.msvc.users.dto.RoleDto;
import com.jeferson.msvc.users.dto.UserDetailsDto;
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
    public UserDetailsDto toDto(User user) {
        if (user == null) return null;

        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRegistrationDate(user.getRegistrationDate());
        dto.setStatus(user.getStatus());

        if (user.getRoles() != null) {
            Set<RoleDto> roleDtos = user.getRoles().stream()
            .map(role -> {
                RoleDto r = new RoleDto();
                r.setName(role.getName().name());
                return r;
            })
            .collect(Collectors.toSet());
            dto.setRoles(roleDtos);
        }
        return dto;
    }
    
    @Override
    public UserRespondeDto toRespondeDto(User user){
        if (user == null) return null;

        UserRespondeDto dto = new UserRespondeDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRegistrationDate(user.getRegistrationDate());
        dto.setStatus(user.getStatus());

        dto.setRoles(
        user.getRoles().stream()
            .map(role -> {
                RoleDto r = new RoleDto();
                r.setName(role.getName().name());
                return r;
            })
            .collect(Collectors.toSet())
         );
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

        LocalDateTime deactivationDate = user.getUserStatusReason().stream()
            .reduce((first, second) -> second) 
            .map(UserStatusReason::getDeactivationDate)
            .orElse(null);


        dto.setReason(reason);
        dto.setDeactivationDate(deactivationDate);

        return dto;
    }

}
