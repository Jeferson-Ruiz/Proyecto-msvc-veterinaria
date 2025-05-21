package com.jeferson.msvc_sav_workstaff.services;

import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.Employee;
import com.jeferson.msvc_sav_workstaff.models.JobPosition;
import com.jeferson.msvc_sav_workstaff.repositories.EmployeeRespository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRespository employeeRespository;

    public EmployeeServiceImpl(EmployeeRespository employeeRespository){
        this.employeeRespository = employeeRespository;
    }

    @Override
    public List<Employee> findAllEmployee(){
        return (List<Employee>)  employeeRespository.findAll();
    }

    @Override
    public Optional<Employee> findEmployeeById(Long idEmployee){
        return  employeeRespository.findById(idEmployee);
    }

    @Override
    public List<Employee> FindAllByJobPosition(JobPosition jobPosition){
        return employeeRespository.findByJobPosition(jobPosition);
    }

    @Override
    public Employee saveEmployee(Employee employee){
        return employeeRespository.save(employee);
    }

    @Override
    public void deleteEmployee(Long idEmployee){
        employeeRespository.deleteById(idEmployee);
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email){
        employeeRespository.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(Long idEmployee, Long phoneNumber){
        employeeRespository.updatePhoneNumber(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType){
        employeeRespository.updateContractType(idEmployee, contractType);
    }
}