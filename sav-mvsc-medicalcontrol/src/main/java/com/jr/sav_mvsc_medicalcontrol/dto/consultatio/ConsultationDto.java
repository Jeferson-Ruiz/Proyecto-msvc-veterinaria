package com.jr.sav_mvsc_medicalcontrol.dto.consultatio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDto {
    private Long idPet;
    private String reason;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime citationDate;

    private String observations;
    private Long veterinaryId;
}
