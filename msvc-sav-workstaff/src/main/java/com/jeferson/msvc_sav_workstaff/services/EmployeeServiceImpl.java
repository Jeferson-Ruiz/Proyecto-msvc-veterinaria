package com.jeferson.msvc_sav_workstaff.services;

import com.jeferson.msvc_sav_workstaff.models.Employee;
import com.jeferson.msvc_sav_workstaff.repositories.EmployeeRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRespository employeeRespository;

    public EmployeeServiceImpl(EmployeeRespository employeeRespository){
        this.employeeRespository = employeeRespository;
    }

    public List<Employee> findAllEmployee(){
        return (List<Employee>)  employeeRespository.findAll();
    }

    public Optional<Employee> findEmployeeById(Long idEmployee){
        return  employeeRespository.findById(idEmployee);
    }

    public List<Employee> FindAllByJobPosition(String jobPosition){
        return employeeRespository.findByJobPosition(jobPosition);
    }

    public Employee saveEmployee(Employee employee){
        return employeeRespository.save(employee);
    }

    public void deleteEmployee(Long idEmployee){
        employeeRespository.deleteById(idEmployee);
    }

    public void updateEmail(Long idEmployee, String email){
    }
}
