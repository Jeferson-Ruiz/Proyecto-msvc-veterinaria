package com.msvc.invoice.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.msvc.invoice.entities.Comparison;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateRequestDto {

    private Comparison comparison;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    private LocalDate date;
}
