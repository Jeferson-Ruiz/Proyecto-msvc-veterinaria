package com.jeferson.msvc_sav_workstaff.mapper;

import org.mapstruct.Mapper;
import com.jeferson.msvc_sav_workstaff.dto.EmployeeDto;
import com.jeferson.msvc_sav_workstaff.models.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(Employee entity);
    
}
