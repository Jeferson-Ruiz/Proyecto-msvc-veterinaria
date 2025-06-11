package com.jr.sav_msvc_pet.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    private String name;
    private String lastName;
    private String documentType;
    private Long documentNumber;
    private String email;
    private Long phoneNumber;
    private LocalDate dateOfRecording;
    
}