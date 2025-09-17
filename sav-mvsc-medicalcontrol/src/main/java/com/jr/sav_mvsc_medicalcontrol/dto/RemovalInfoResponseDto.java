package com.jr.sav_mvsc_medicalcontrol.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemovalInfoResponseDto {

    private String deletedBy;
    private String reason;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime deletedAt;

}
