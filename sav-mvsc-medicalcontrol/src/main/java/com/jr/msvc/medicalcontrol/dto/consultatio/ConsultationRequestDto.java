package com.jr.msvc.medicalcontrol.dto.consultatio;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class ConsultationRequestDto {
    @NotNull
    private Long idPet;

    @NotBlank
    private String reason;
    
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime citationDate;

    private String observations;

    @NotNull
    private Long vetId;
}
