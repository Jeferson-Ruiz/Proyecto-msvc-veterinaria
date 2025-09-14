package com.jr.sav_mvsc_medicalcontrol.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VaccineResponseDto {
    private Long idPet;
    private Long idVaccine;
    private String name;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate applicationData;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate nextApplicationDate;
    private String observations;

}
