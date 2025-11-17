package com.jr.sav.msvc.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrefixRequestDto {

    @NotBlank
    @Pattern(
    regexp = "^(?=.*[\\p{L}])[\\p{L},\\sáéíóúÁÉÍÓÚñÑ]+$",
        message = "la descripcion no puede ser nula ni contener numeos")
    private String prefix;

}