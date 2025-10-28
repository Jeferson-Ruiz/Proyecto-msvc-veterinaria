package com.jr.msvc.medicalcontrol.mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import com.jr.msvc.medicalcontrol.dto.pet.PetRequestDto;
import com.jr.msvc.medicalcontrol.dto.pet.PetResponseDisableDto;
import com.jr.msvc.medicalcontrol.dto.pet.PetResponseDto;
import com.jr.msvc.medicalcontrol.dto.pet.PetWithOwnerResponseDto;
import com.jr.msvc.medicalcontrol.dto.removal.RemovalInfoResponseDto;
import com.jr.msvc.medicalcontrol.models.Pet;
import com.jr.msvc.medicalcontrol.models.RemovalInformation;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PetMapper {

    Pet toEntity(PetRequestDto dto);

    PetRequestDto toDto(Pet entity);

    @Mapping(target = "age", expression = "java(calculateAge(entity.getDateOfBirth()))")
    PetResponseDto toResponsePetDto(Pet entity);

    @Mappings({
            @Mapping(target = "fullName", expression = "java(entity.getOwner().getName() + \" \" + entity.getOwner().getLastName())"),
            @Mapping(source = "owner.phoneNumber", target = "ownerPhone"),
            @Mapping(target = "age", expression = "java(calculateAge(entity.getDateOfBirth()))"),})
    PetWithOwnerResponseDto toResponseDto(Pet entity);

    @Mappings({
            @Mapping(target = "fullName", expression = "java(entity.getOwner().getName() + \" \" + entity.getOwner().getLastName())"),
            @Mapping(source = "owner.phoneNumber", target = "ownerPhone"),
            @Mapping(target = "age", expression = "java(calculateAge(entity.getDateOfBirth()))"),
            @Mapping(target = "removalInformation", source = "removalInformation")})
    PetResponseDisableDto toResponseDisableDto(Pet entity);

    List<RemovalInfoResponseDto> toRemovalInfoResponseDto(List<RemovalInformation> infoList);


    // Metodo para caulcular la edad de la mascota
    default byte calculateAge(LocalDate birthDate) {
        if (birthDate == null)
            return 0;
        return (byte) Period.between(birthDate, LocalDate.now()).getYears();
    }

}
