package com.jeferson.msvc.workstaff.dto;

import java.time.LocalDate;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.DocumentType;
import com.jeferson.msvc.workstaff.models.WorkArea;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotNull
    private DocumentType documentType;

    @NotBlank
    @Pattern(regexp = "^[0-9]+$", message = "El campo solo puede contener números sin espacios")
    private String documentNumber;

    @NotBlank @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]+$", message = "El campo solo puede contener números sin espacios")
    private String phoneNumber;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @NotNull
    private WorkArea workArea;

    @NotNull
    private Set<String> roleName;

    @NotNull
    private ContractType contractType;
}
