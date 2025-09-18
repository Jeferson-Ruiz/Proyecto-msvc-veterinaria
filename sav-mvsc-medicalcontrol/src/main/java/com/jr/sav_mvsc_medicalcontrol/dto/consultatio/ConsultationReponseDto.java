package com.jr.sav_mvsc_medicalcontrol.dto.consultatio;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jr.sav_mvsc_medicalcontrol.models.AttendanceStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConsultationReponseDto {
    private Long idConsultation;
    private Long idPet;
    private String petName;
    private String reason;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime registrationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime citationDate;

    private String observations;
    private Long veterinaryId;

    private AttendanceStatus status;
}