package com.jr.msvc.medicalcontrol.dto.vaccine;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class VaccineRequestDto {

    @NotBlank
    private String petCode;

    @NotBlank
    private String vaccineCode;

    @NotBlank
    private String name;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate nextApplicationDate;
    private String observations;
}
