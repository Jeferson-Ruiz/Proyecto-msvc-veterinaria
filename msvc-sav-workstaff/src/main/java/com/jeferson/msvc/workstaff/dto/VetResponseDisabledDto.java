package com.jeferson.msvc.workstaff.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VetResponseDisabledDto extends VetResponseDto {
    List<ActionInformationsResponseDto> actionInformations;

}
