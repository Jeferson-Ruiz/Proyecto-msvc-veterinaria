package com.jeferson.springcloud.msvc.oauth.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {

    private final Map<String, String> tokens = new HashMap<>(); 

    @Override
    public String generateRecoveryToken(String email) {
        String token = UUID.randomUUID().toString();
        tokens.put(token, email);
        return token;
    }

    @Override
    public String validateToken(String token) {
        return tokens.get(token);
    }

    @Override
    public void removeToken(String token) {
        tokens.remove(token);
    }

    // Solicitud de recuperación de contraseña
    @Override
    public String forgotPassword(String email) {
        String token = generateRecoveryToken(email);
        String link = "http://localhost:8081/auth/reset-password?token=" + token;
        System.out.println("Enlace de recuperación: " + link);
        return "Se ha enviado un enlace de recuperación (ver consola).";
    }

    // Validar token
    @Override
    public String resetPassword(String token) {
        String email = validateToken(token);
        if (email == null) {
            return "Token inválido o expirado.";
        }
        return "Token válido. Ahora puede actualizar la contraseña.";
    }
}
