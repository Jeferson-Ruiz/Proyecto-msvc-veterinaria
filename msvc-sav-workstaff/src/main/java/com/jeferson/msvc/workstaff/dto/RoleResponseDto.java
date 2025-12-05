package com.jeferson.msvc.workstaff.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponseDto {
    private String roleCode;
    private String roleName;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "dd/MM/yyyy")
    private LocalDate registrationDate;

}
