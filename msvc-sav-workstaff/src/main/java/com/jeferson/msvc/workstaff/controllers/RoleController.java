package com.jeferson.msvc.workstaff.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.msvc.workstaff.dto.RoleNameRequestDto;
import com.jeferson.msvc.workstaff.dto.RoleRequestDto;
import com.jeferson.msvc.workstaff.dto.RoleResponseDto;
import com.jeferson.msvc.workstaff.services.RoleService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<?> saveRole(@RequestBody @Valid RoleRequestDto roleDto){
        RoleResponseDto role = roleService.saveRole(roleDto);
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity<?> getAllRole(){
        return ResponseEntity.ok(roleService.findAllRole());
    }

    @GetMapping("/code/{roleCode}")
    public ResponseEntity<?> getByCode(@PathVariable String roleCode){
        RoleResponseDto role = roleService.findByCode(roleCode);
        return ResponseEntity.ok(role);
    }

    @PatchMapping("/update-name/code/{roleCode}")
    public ResponseEntity<?> updtRoleName(@PathVariable String roleCode, @RequestBody @Valid RoleNameRequestDto request){
        roleService.updateName(roleCode, request.getRoleName());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-description/code/{roleCode}")
    public ResponseEntity<?> updtRoleDescription(@PathVariable String roleCode, @RequestBody String description){
        roleService.updateDescription(roleCode, description);
        return ResponseEntity.noContent().build();
    }
}
