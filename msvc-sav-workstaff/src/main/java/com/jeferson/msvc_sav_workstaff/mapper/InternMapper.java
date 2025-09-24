package com.jeferson.msvc_sav_workstaff.mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.jeferson.msvc_sav_workstaff.dto.InternResponseDto;
import com.jeferson.msvc_sav_workstaff.dto.ActionInformationsResponseDto;
import com.jeferson.msvc_sav_workstaff.dto.InternRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.InternResponseDisabledDto;
import com.jeferson.msvc_sav_workstaff.models.ActionInformation;
import com.jeferson.msvc_sav_workstaff.models.Intern;

@Mapper(componentModel = "spring")
public interface InternMapper {

    Intern toEntity(InternRequestDto dto);

    @Mapping(target = "fullName", expression = "java(entity.getName() + \" \" + entity.getLastName())")
    @Mapping(target = "age", expression = "java(calculateAge(entity.getDateOfBirth()))")
    InternResponseDto toDto(Intern entity);


    @Mapping(target = "fullName", expression = "java(entity.getName() + \" \" + entity.getLastName())")
    @Mapping(target = "age", expression = "java(calculateAge(entity.getDateOfBirth()))")
    @Mapping(target = "actionInformations", source = "actionInformations")
    InternResponseDisabledDto toDisabledDto(Intern entity);

    List<ActionInformationsResponseDto> toActionInformationsResponseDtos(List<ActionInformation> actionInformations);

    default Byte calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return null;
        }
        return (byte) Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
