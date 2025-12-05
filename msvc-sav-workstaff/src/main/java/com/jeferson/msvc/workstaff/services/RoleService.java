package com.jeferson.msvc.workstaff.services;

import java.util.List;

import com.jeferson.msvc.workstaff.dto.RoleRequestDto;
import com.jeferson.msvc.workstaff.dto.RoleResponseDto;

public interface RoleService {

    RoleResponseDto saveRole(RoleRequestDto roleDto);
    
    List<RoleResponseDto> findAllRole();

    RoleResponseDto findByCode(String code);

    void updateDescription(String roleCode ,String description);

    void updateName(String roleCode, String newRole);

}
