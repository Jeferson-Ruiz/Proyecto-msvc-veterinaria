package com.jeferson.msvc.workstaff.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministrativeResponseDisabledDto extends AdmistrativeResponseDto {
    private List<ActionInformationsResponseDto> actionInformations;

}
