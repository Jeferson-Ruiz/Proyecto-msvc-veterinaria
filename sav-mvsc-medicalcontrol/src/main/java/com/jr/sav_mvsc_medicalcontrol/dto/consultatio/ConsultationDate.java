package com.jr.sav_mvsc_medicalcontrol.dto.consultatio;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultationDate {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
}
