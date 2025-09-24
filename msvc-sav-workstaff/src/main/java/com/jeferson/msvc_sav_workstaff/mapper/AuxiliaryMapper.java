package com.jeferson.msvc_sav_workstaff.mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jeferson.msvc_sav_workstaff.dto.ActionInformationsResponseDto;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryResponseDisabledDto;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryResponseDto;
import com.jeferson.msvc_sav_workstaff.models.ActionInformation;
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;

@Mapper(componentModel = "spring")
public interface AuxiliaryMapper {

    Auxiliary toEntity(AuxiliaryRequestDto dto);

    @Mapping(target = "fullName", expression = "java(auxiliary.getName() + \" \" + auxiliary.getLastName())")
    @Mapping(target = "age", expression = "java(calculateAge(auxiliary.getDateOfBirth()))")
    AuxiliaryResponseDto toDto(Auxiliary auxiliary);

    @Mapping(target = "fullName", expression = "java(auxiliary.getName() + \" \" + auxiliary.getLastName())")
    @Mapping(target = "age", expression = "java(calculateAge(auxiliary.getDateOfBirth()))")
    @Mapping(target = "actionInformations", source = "actionInformations")
    AuxiliaryResponseDisabledDto toDisabledDto(Auxiliary auxiliary);

    List<ActionInformationsResponseDto> toActionInfoResponseDtos(List<ActionInformation> actionInformations);

    default Byte calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return null;
        }
        return (byte) Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
