package com.jr.sav_mvsc_medicalcontrol.dto;

import java.util.List;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationsWithVetDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VetWithConsultationsDto {
    private VetResponseDto vet;
    private List<ConsultationsWithVetDto> consultations;

}
