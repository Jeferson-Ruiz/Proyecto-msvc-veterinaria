package com.jeferson.msvc.users.controller;

import java.util.List;
import java.util.Set;
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
import com.jeferson.msvc.users.dto.ReasonDto;
import com.jeferson.msvc.users.dto.UserRequestDto;
import com.jeferson.msvc.users.dto.UserResponseDto;
import com.jeferson.msvc.users.entities.Roles;
import com.jeferson.msvc.users.entities.UserStatus;
import com.jeferson.msvc.users.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto user) {
        UserResponseDto savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
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
    public ResponseEntity<?> updateRoles(@PathVariable Long id, @RequestBody Set<Roles> roles){
        userService.updateRoles(id, roles);
        return ResponseEntity.noContent().build();
    }



    @PatchMapping("/update/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody UserStatus status, @RequestBody ReasonDto request){
        userService.updateStatus(id, status, request.getReason());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody ReasonDto request) {
        userService.delete(id, request.getReason());
        return ResponseEntity.noContent().build();
    }

}
