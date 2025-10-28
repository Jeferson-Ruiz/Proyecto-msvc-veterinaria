package com.jr.msvc.medicalcontrol.dto.vaccine;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
public class VaccineDateUpdate {
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate newDate;
}
