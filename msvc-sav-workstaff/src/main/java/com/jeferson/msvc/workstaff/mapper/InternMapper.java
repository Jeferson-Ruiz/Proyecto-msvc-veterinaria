package com.jeferson.msvc.workstaff.mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.jeferson.msvc.workstaff.dto.InternResponseDto;
import com.jeferson.msvc.workstaff.dto.ActionInformationsResponseDto;
import com.jeferson.msvc.workstaff.dto.InternRequestDto;
import com.jeferson.msvc.workstaff.dto.InternResponseDisabledDto;
import com.jeferson.msvc.workstaff.models.ActionInformation;
import com.jeferson.msvc.workstaff.models.Intern;

@Mapper(componentModel = "spring")
public interface InternMapper {

    Intern toEntity(InternRequestDto dto);

    @Mapping(target = "fullName", expression = "java(intern.getName() + \" \" + intern.getLastName())")
    @Mapping(target = "age", expression = "java(calculateAge(intern.getDateOfBirth()))")
    InternResponseDto toDto(Intern intern);


    @Mapping(target = "fullName", expression = "java(intern.getName() + \" \" + intern.getLastName())")
    @Mapping(target = "age", expression = "java(calculateAge(intern.getDateOfBirth()))")
    @Mapping(target = "actionInformations", source = "actionInformations")
    InternResponseDisabledDto toDisabledDto(Intern intern);

    List<ActionInformationsResponseDto> toActionInformationsResponseDtos(List<ActionInformation> actionInformations);

    default Byte calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return null;
        }
        return (byte) Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
