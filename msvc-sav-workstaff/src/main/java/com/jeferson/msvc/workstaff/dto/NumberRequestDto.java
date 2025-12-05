package com.jeferson.msvc.workstaff.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class NumberRequestDto {

    @Pattern(regexp = "^[0-9]+$", message = "El campo solo puede contener números sin espacios")
    private String documentNumber;

}
