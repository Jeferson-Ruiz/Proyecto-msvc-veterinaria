package com.jr.msvc.medicalcontrol.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jr.msvc.medicalcontrol.repositories.PetRepository;

@Service
public class CodeMedicalControlServiceImpl implements CodeMedicalControlService{

  @Autowired
  private PetRepository petRepository;

  @Override
  public String generatePetCode(String ownerDocumentNumber) {
      String lastDigits = ownerDocumentNumber.substring(ownerDocumentNumber.length() - 3);
        long count = petRepository.countPetsByOwnerDocument(ownerDocumentNumber) + 1;

      return String.format("PT%s-%02d", lastDigits, count);
  }

  @Override
  public String generateMedicalCode(String prefix, String petCode, LocalDateTime date, long count) {
      String lastDigits = petCode.substring(petCode.length() - 6);
      String datePart = date.format(DateTimeFormatter.ofPattern("ddMM"));
      return String.format("%s%s-%s-%02d", prefix, lastDigits, datePart, count + 1);
  }



}