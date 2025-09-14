package com.jr.sav_mvsc_medicalcontrol.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class VaccineRequestDto {

    @NotNull
    private Long idPet;

    @NotBlank
    private String name;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate nextApplicationDate;
    private String observations;
}
