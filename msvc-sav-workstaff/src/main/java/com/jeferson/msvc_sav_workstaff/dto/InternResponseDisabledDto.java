package com.jeferson.msvc_sav_workstaff.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternResponseDisabledDto extends InternResponseDto{
    List<ActionInformationsResponseDto> actionInformations;

}
