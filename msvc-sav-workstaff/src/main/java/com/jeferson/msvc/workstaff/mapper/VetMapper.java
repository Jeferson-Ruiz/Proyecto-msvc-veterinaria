package com.jeferson.msvc.workstaff.mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.jeferson.msvc.workstaff.dto.ActionInformationsResponseDto;
import com.jeferson.msvc.workstaff.dto.VetRequestDto;
import com.jeferson.msvc.workstaff.dto.VetResponseDisabledDto;
import com.jeferson.msvc.workstaff.dto.VetResponseDto;
import com.jeferson.msvc.workstaff.models.ActionInformation;
import com.jeferson.msvc.workstaff.models.Vet;

@Mapper(componentModel = "spring")
public interface VetMapper {

    Vet toEntity(VetRequestDto dto);
    
    @Mapping(target = "fullName", expression = "java(vet.getName() + \" \" + vet.getLastName())")
    @Mapping(target = "age", expression = "java(calculateAge(vet.getDateOfBirth()))")
    VetResponseDto toDto(Vet vet);

    @Mapping(target = "fullName", expression = "java(vet.getName() + \" \" + vet.getLastName())")
    @Mapping(target = "age", expression = "java(calculateAge(vet.getDateOfBirth()))")
    @Mapping(target = "actionInformations", source = "actionInformations")
    VetResponseDisabledDto toDisabledDto(Vet vet);

    List<ActionInformationsResponseDto> toActionInformationsResponseDtos(List<ActionInformation> actionInformations);

    default Byte calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return null;
        }
        return (byte) Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

}
