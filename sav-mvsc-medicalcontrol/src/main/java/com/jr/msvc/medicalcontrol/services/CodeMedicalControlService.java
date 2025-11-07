package com.jr.msvc.medicalcontrol.services;

import java.time.LocalDateTime;

public interface CodeMedicalControlService {

    String generatePetCode(String ownerDocumentNumber);
    
    String generateMedicalCode(String prefix, String petCode, LocalDateTime date, long count);
}
