package com.jeferson.msvc.workstaff.mapper;

import org.mapstruct.Mapper;
import com.jeferson.msvc.workstaff.dto.RoleRequestDto;
import com.jeferson.msvc.workstaff.dto.RoleResponseDto;
import com.jeferson.msvc.workstaff.models.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toEntity(RoleRequestDto role);

    RoleResponseDto toDto(Role role);

}
