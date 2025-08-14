package com.jr.sav_mvsc_medicalcontrol.dto.pet;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetResponseDto {
    private Long idPet;
    private String name;
    private String specie;
    private String breed;
    private String sex;
    private Byte age;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfRecording;
    private Boolean active;
}

