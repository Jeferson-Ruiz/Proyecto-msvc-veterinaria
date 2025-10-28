package com.jr.msvc.medicalcontrol.dto.pet;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetRequestDto {
    
    @NotBlank
    private String name;

    @NotBlank
    private String specie;

    @Pattern(regexp = "^$|^(?!\\s+$).+", message = "la raza no puede contener solo espacios en blanco")
    private String breed;

    @NotBlank
    private String sex;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @NotBlank
    private String documentNumber;
}