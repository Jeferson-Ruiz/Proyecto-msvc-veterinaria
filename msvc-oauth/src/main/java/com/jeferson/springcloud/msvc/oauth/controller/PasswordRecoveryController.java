package com.jeferson.springcloud.msvc.oauth.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import com.jeferson.springcloud.msvc.oauth.services.PasswordRecoveryService;

@RestController
@RequestMapping("/oauth")
public class PasswordRecoveryController {

    private final PasswordRecoveryService recoveryService;
    private final WebClient.Builder client;

    public PasswordRecoveryController(PasswordRecoveryService recoveryService, WebClient.Builder client) {
        this.recoveryService = recoveryService;
        this.client = client;
    }

    // Solicitud de recuperación de contraseña
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        System.out.println(email);
        String message = recoveryService.forgotPassword(email);
        return ResponseEntity.ok(message);
    }

    // Validar token
    @GetMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token) {
        String message = recoveryService.resetPassword(token);
        return ResponseEntity.ok(message);
    }

    //Actualizar contraseña (llama al microservicio Users)
    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(
            @RequestParam String token,
            @RequestParam String newPassword) {

        String email = recoveryService.validateToken(token);
        if (email == null) {
            return ResponseEntity.badRequest().body("Token inválido o expirado.");
        }

        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("newPassword", newPassword);

        client.build().patch()
                .uri("http://msvc-users/user/update-password")
                .bodyValue(body)
                .retrieve()
                .toBodilessEntity()
                .block();

        recoveryService.removeToken(token);
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }

}
