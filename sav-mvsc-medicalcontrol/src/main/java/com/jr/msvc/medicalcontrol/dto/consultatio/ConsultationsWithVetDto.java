package com.jr.msvc.medicalcontrol.dto.consultatio;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jr.msvc.medicalcontrol.models.AttendanceStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultationsWithVetDto {
    
    private String consultationCode;
    private String petCode;
    private String petName;
    private String reason;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime registrationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime citationDate;

    private String observations;

    private AttendanceStatus status;

}
