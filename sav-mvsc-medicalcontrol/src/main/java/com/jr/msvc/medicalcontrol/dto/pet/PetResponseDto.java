package com.jr.msvc.medicalcontrol.dto.pet;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetResponseDto {
    private String petCode;
    private String name;
    private String specie;
    private String breed;
    private String sex;
    private Byte age;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfRecording;
    private Boolean active;
}

