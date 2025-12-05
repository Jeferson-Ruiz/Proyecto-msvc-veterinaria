package com.jeferson.msvc.workstaff.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import com.jeferson.msvc.workstaff.dto.RoleRequestDto;
import com.jeferson.msvc.workstaff.dto.RoleResponseDto;
import com.jeferson.msvc.workstaff.mapper.RoleMapper;
import com.jeferson.msvc.workstaff.models.Role;
import com.jeferson.msvc.workstaff.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final CodeService codeService;

    public RoleServiceImpl(RoleRepository roleRepository,
        RoleMapper roleMapper,
        CodeService codeService) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.codeService = codeService;
    }
    @Override
    public RoleResponseDto saveRole(RoleRequestDto roleDto){
        String name = roleDto.getRoleName().strip().toUpperCase();

        //validar repetido
        validateRoleName(name);

        //Mapear
        Role role = roleMapper.toEntity(roleDto);
        role.setRoleCode(codeService.generateRoleCode(role));
        role.setRoleName(name);
        role.setArea(roleDto.getArea());
        role.setDescription(roleDto.getDescription().strip().toLowerCase());
        role.setRegistrationDate(LocalDate.now());

        //guardar en Bd
        roleRepository.save(role);
        return roleMapper.toDto(role);
    }

    @Override
    public List<RoleResponseDto> findAllRole(){
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                    .map(roleMapper::toDto)
                    .toList();
    }

    @Override
    public RoleResponseDto findByCode(String code){
        Role role = findRoleByCode(code);
        return roleMapper.toDto(role);
    }

    @Override
    public void updateName(String roleCode, String newRole){
        Role role = findRoleByCode(roleCode);
        validateRoleName(newRole);
        role.setRoleName(newRole.toUpperCase());
        roleRepository.save(role);
    }

    @Override
    public void updateDescription(String roleCode ,String description){
        Role role = findRoleByCode(roleCode);
        role.setDescription(description.strip().toLowerCase());
        roleRepository.save(role);
    }

    //helpers
    private void validateRoleName(String name){
        if (roleRepository.existByName(name)) {
            throw new IllegalArgumentException("El rol "+ name + " ya se encuentra creado");
        }
    }

    private Role findRoleByCode(String roleCode){
        String code = roleCode.strip().toLowerCase();
        Role role = roleRepository.findByCode(code)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro categoria asociada al codigo "+ roleCode));
        return role;
    }
}
