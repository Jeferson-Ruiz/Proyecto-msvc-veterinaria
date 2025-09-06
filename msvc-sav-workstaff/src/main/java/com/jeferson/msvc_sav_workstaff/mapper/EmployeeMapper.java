package com.jeferson.msvc_sav_workstaff.mapper;

import java.time.LocalDate;
import java.time.Period;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.jeferson.msvc_sav_workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc_sav_workstaff.models.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "fullName", expression = "java(entity.getName() + \" \" + entity.getLastName())")
    @Mapping(target = "workArea", expression = "java(entity.getArea())")
    EmployeeResponseDto toDto(Employee entity);

    default Byte calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return null;
        }
        return (byte) Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
