package com.jr.sav_mvsc_medicalcontrol.dto.consultatio;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConsultationDateUpdate {

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime newDate;

}
