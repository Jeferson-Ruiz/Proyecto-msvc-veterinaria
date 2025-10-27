package com.jeferson.msvc.workstaff.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionInformationsRequestDto {
    @NotBlank
    private String deletedBy;
    @NotBlank
    private String reason;
}
