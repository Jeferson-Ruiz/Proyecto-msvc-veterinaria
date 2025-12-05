package com.jeferson.msvc.workstaff.dto;

import com.jeferson.msvc.workstaff.models.WorkArea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequestDto {
    
    @NotBlank
    private String roleName;

    @NotNull
    private WorkArea area;

    private String description;
}
