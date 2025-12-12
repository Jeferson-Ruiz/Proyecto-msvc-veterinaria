package com.jeferson.msvc.workstaff.mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import com.jeferson.msvc.workstaff.dto.EmployeeRequestDto;
import com.jeferson.msvc.workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc.workstaff.models.Employee;
import com.jeferson.msvc.workstaff.models.Role;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequestDto employee);

    @Mapping(target = "fullName", expression = "java(employee.getName() + \" \" + employee.getLastName())")
    @Mapping(target = "roles", source = "employee.roles", qualifiedByName = "mapRolesToCodes")
    EmployeeResponseDto toDto(Employee employee);

    @Named("mapRolesToCodes")
    default List<String> mapRolesToCodes(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
            .map(Role::getRoleCode)
            .collect(Collectors.toList());
    }

    default Byte calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return null;
        }
        return (byte) Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
