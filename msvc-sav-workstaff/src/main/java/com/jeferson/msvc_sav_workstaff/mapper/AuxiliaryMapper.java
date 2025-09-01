package com.jeferson.msvc_sav_workstaff.mapper;

import java.time.LocalDate;
import java.time.Period;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryResponseDto;
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;

@Mapper(componentModel = "spring")
public interface AuxiliaryMapper {

    Auxiliary toEntity(AuxiliaryRequestDto dto);

    @Mapping(target = "fullName", expression = "java(entity.getName() + \" \" + entity.getLastName())")
    @Mapping(target = "age", expression = "java(calculateAge(entity.getDateOfBirth()))")
    AuxiliaryResponseDto toDto(Auxiliary entity);

    default Byte calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return null;
        }
        return (byte) Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
