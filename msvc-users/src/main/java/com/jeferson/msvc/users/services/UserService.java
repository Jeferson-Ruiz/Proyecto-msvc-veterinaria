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

    UserRespondeDto findByCode(String code);

    UserDetailsDto findByUsername(String userName);

    UserDetailsDto findByEmail(String email);

    List<UserRespondeDto> findAll();

    List<UserRespondeDto> findAllByStatus(UserStatus status);
    
    void updateEmail(String userCode, String email);

    void updatePasswordByUserCode(String userCode, String oldPassword ,String newpassword);

    void updatePasswordByEmail(String email, String newPassword);

    void updateRoles(String userCode, Set<Roles> newRoles);

    void updateStatus(String userCode, UserStatus status, String reason);

    void delete(String userCode, String reason);
}