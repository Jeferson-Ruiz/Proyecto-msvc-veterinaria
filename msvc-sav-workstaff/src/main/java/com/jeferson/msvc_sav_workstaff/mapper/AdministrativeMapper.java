package com.jeferson.msvc_sav_workstaff.mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.jeferson.msvc_sav_workstaff.dto.AdministrativeRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AdministrativeResponseDisabledDto;
import com.jeferson.msvc_sav_workstaff.dto.AdmistrativeResponseDto;
import com.jeferson.msvc_sav_workstaff.dto.ActionInformationsResponseDto;
import com.jeferson.msvc_sav_workstaff.models.Administrative;
import com.jeferson.msvc_sav_workstaff.models.ActionInformation;

@Mapper(componentModel = "spring")
public interface AdministrativeMapper {

    Administrative toEntity(AdministrativeRequestDto dto);
    
    @Mapping(target = "fullName", expression = "java(entity.getName() + \" \" + entity.getLastName())")
    @Mapping(target = "age", expression = "java(calculateAge(entity.getDateOfBirth()))")
    AdmistrativeResponseDto toDto(Administrative entity);

    @Mapping(target = "fullName", expression = "java(entity.getName() + \" \" + entity.getLastName())")
    @Mapping(target = "age", expression = "java(calculateAge(entity.getDateOfBirth()))")
    @Mapping(target = "actionInformations", source = "actionInformations")
    AdministrativeResponseDisabledDto toDisabledDto(Administrative entity);
    
    List<ActionInformationsResponseDto> toRemovalInformationDtos(List<ActionInformation> removalInformations);


    default Byte calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return null;
        }
        return (byte) Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

}
