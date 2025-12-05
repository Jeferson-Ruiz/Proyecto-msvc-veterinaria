package com.jeferson.msvc.workstaff.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleNameRequestDto {

    @NotBlank
    private String roleName;

}
