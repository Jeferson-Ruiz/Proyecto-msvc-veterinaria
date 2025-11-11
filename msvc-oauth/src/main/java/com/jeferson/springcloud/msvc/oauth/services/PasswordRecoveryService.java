package com.jeferson.springcloud.msvc.oauth.services;

public interface PasswordRecoveryService {
    String generateRecoveryToken(String email);

    String validateToken(String token);

    void removeToken(String token);

    public String forgotPassword(String email);

    String resetPassword(String token);
}
