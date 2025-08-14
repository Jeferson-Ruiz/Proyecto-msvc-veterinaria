package com.jr.sav_mvsc_medicalcontrol.mapper;

import java.time.LocalDate;
import java.time.Period;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetWithOwnerResponseDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetResponseDto;
import com.jr.sav_mvsc_medicalcontrol.models.Pet;

@Mapper(componentModel = "spring")
public interface PetMapper {

    Pet toEntity(PetRequestDto dto);

    PetRequestDto toDto(Pet entity);

    @Mapping(target = "age", expression = "java(calculateAge(entity.getDateOfBirth()))")
    PetResponseDto toResponsePetDto(Pet entity);

    @Mappings({
            @Mapping(source = "owner.name", target = "ownerName"),
            @Mapping(source = "owner.phoneNumber", target = "ownerPhone"),
            @Mapping(target = "age", expression = "java(calculateAge(entity.getDateOfBirth()))")
    })
    PetWithOwnerResponseDto toResponseDto(Pet entity);


    // Metodo para caulcular la edad de la mascota
    default byte calculateAge(LocalDate birthDate) {
        if (birthDate == null)
            return 0;
        return (byte) Period.between(birthDate, LocalDate.now()).getYears();
    }

}
