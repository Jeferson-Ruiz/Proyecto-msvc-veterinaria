package com.jeferson.msvc.users.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.msvc.users.dto.EmailDto;
import com.jeferson.msvc.users.dto.PasswordDto;
import com.jeferson.msvc.users.dto.PasswordUpdateRequest;
import com.jeferson.msvc.users.dto.UpdateStatusDto;
import com.jeferson.msvc.users.dto.UserDeleteRequestDto;
import com.jeferson.msvc.users.dto.UpdateRolesDto;
import com.jeferson.msvc.users.dto.UserRequestDto;
import com.jeferson.msvc.users.dto.UserRespondeDto;
import com.jeferson.msvc.users.entities.Roles;
import com.jeferson.msvc.users.entities.UserStatus;
import com.jeferson.msvc.users.services.UserService;
import jakarta.validation.Valid;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto user) {
        UserRespondeDto savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<UserRespondeDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<UserRespondeDto>> getAllUsersByStatus(@PathVariable UserStatus status){
        return ResponseEntity.ok(userService.findAllByStatus(status));
    }

    @GetMapping("/{userCode}")
    public ResponseEntity<?> getUserById(@PathVariable String userCode) {
        return ResponseEntity.ok(userService.findByCode(userCode));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }


    @PatchMapping("/update/email/{userCode}")
    public ResponseEntity<?> updateEmail(@PathVariable String userCode, @Valid @RequestBody EmailDto request){
        userService.updateEmail(userCode, request.getEmail());
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/update/password/{userCode}")
    public ResponseEntity<?> updatePasswordByCode(@PathVariable String userCode, @Valid @RequestBody PasswordDto request ){
        userService.updatePasswordByUserCode(userCode, request.getOldPassword(), request.getNewPasswor());
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePasswordByEmail(@PathVariable String userCode, @Valid @RequestBody PasswordUpdateRequest request ){
        userService.updatePasswordByEmail(request.getEmail(), request.getNewPassword());
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/update/roles/{userCode}")
    public ResponseEntity<?> updateRoles(@PathVariable String userCode, @RequestBody UpdateRolesDto request){
        Set<Roles> roles = request.getRoles()
                              .stream()
                              .map(Roles::valueOf)
                              .collect(Collectors.toSet());
        userService.updateRoles(userCode, roles);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/update/status/{userCode}")
    public ResponseEntity<?> updateStatus(@PathVariable String userCode, @Valid @RequestBody UpdateStatusDto request){
        userService.updateStatus(userCode, request.getStatus(), request.getReason());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userCode}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userCode, @Valid @RequestBody UserDeleteRequestDto request) {
        userService.delete(userCode, request.getReason());
        return ResponseEntity.noContent().build();
    }

}
