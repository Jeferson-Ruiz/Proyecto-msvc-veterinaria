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
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.msvc.users.dto.EmailDto;
import com.jeferson.msvc.users.dto.PasswordDto;
import com.jeferson.msvc.users.dto.UpdateStatusDto;
import com.jeferson.msvc.users.dto.UserDeleteRequestDto;
import com.jeferson.msvc.users.dto.UpdateRolesDto;
import com.jeferson.msvc.users.dto.UserRequestDto;
import com.jeferson.msvc.users.dto.UserRespondeDto;
import com.jeferson.msvc.users.entities.Roles;
import com.jeferson.msvc.users.entities.UserStatus;
import com.jeferson.msvc.users.services.UserService;
import jakarta.validation.Valid;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }


    @PatchMapping("/update/email/{id}")
    public ResponseEntity<?> updateEmail(@PathVariable Long id, @Valid @RequestBody EmailDto request){
        userService.updateEmail(id, request.getEmail());
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/update/password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @Valid @RequestBody PasswordDto request ){
        userService.updatePassword(id, request.getOldPassword(), request.getNewPasswor());
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/update/roles/{id}")
    public ResponseEntity<?> updateRoles(@PathVariable Long id, @RequestBody UpdateRolesDto request){
        Set<Roles> roles = request.getRoles()
                              .stream()
                              .map(Roles::valueOf)
                              .collect(Collectors.toSet());
        userService.updateRoles(id, roles);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/update/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatusDto request){
        userService.updateStatus(id, request.getStatus(), request.getReason());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @Valid @RequestBody UserDeleteRequestDto request) {
        userService.delete(id, request.getReason());
        return ResponseEntity.noContent().build();
    }

}
