package com.jeferson.msvc.users.services;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class UserCodeGeneratorImpl implements UserCodeGenerator {

    private static final String PREFIX = "USR";
    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String generateUserCode() {

        // Fecha actual en formato YYYYMMDD
        String datePart = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        // Parte aleatoria de 6 caracteres
        StringBuilder randomPart = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = RANDOM.nextInt(ALPHANUM.length());
            randomPart.append(ALPHANUM.charAt(index));
        }

        return String.format("%s-%s-%s", PREFIX, datePart, randomPart);
    }

}
