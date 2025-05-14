package com.jeferson.msvc_sav_workstaff.repositories;

import org.springframework.data.repository.CrudRepository;
import com.jeferson.msvc_sav_workstaff.models.Employee;

public interface EmployeeRespository extends CrudRepository<Employee, Long> {

}
