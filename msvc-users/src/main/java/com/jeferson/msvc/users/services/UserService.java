package com.jeferson.msvc.users.services;

import java.util.List;
import java.util.Set;
import com.jeferson.msvc.users.dto.UserRequestDto;
import com.jeferson.msvc.users.dto.UserRespondeDto;
import com.jeferson.msvc.users.dto.UserDetailsDto;
import com.jeferson.msvc.users.entities.Roles;
import com.jeferson.msvc.users.entities.UserStatus;

public interface UserService {

    UserRespondeDto save(UserRequestDto userDto);

    UserRespondeDto findById(Long id);

    UserDetailsDto findByUsername(String userName);

    List<UserRespondeDto> findAll();

    List<UserRespondeDto> findAllByStatus(UserStatus status);
    
    void updateEmail(Long id, String email);

    void updatePassword(Long id, String oldPassword ,String newpassword);

    void updateRoles(Long id, Set<Roles> newRoles);

    void updateStatus(Long id, UserStatus status, String reason);

    void delete(Long id, String reason);
}