package com.jeferson.msvc_sav_workstaff.services;

import com.jeferson.msvc_sav_workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc_sav_workstaff.mapper.EmployeeMapper;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.Employee;
import com.jeferson.msvc_sav_workstaff.models.JobPosition;
import com.jeferson.msvc_sav_workstaff.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRespository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRespository, EmployeeMapper employeeMapper) {
        this.employeeRespository = employeeRespository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<EmployeeResponseDto> findAll() {
        return employeeRespository.findAll().stream()
                .map(employeeMapper::toDto).toList();
    }

    @Override
    public EmployeeResponseDto findById(Long idEmployee) {
        Employee employee = employeeRespository.findById(idEmployee)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro empleado asociado al Id "+ idEmployee));
        
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeResponseDto findByDocumentNumber(String documentNumber) {
        Employee employee = employeeRespository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro empleado asociado al N° identificacion "+ documentNumber));

        return employeeMapper.toDto(employee);
    }

    @Override
    public List<EmployeeResponseDto> findAllByJobPosition(JobPosition jobPosition) {
        return employeeRespository.findByJobPosition(jobPosition).stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long idEmployee) {
        Employee employee = employeeRespository.findById(idEmployee)
            .orElseThrow(() -> new RuntimeException("No se encontro empleado asociado al Id "+ idEmployee));
        employee.setActive(false);
        employeeRespository.save(employee);
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email) {
        Employee employee = employeeValidation(idEmployee);
        if (employee.getEmail().equals(email)) {
            throw new IllegalArgumentException("Email ya registrado en el sistema, ingrese un nuevo email");
        }
        employeeRespository.updateEmail(employee.getEmployeeId(), email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(Long idEmployee, String phoneNumber) {
        Employee employee = employeeValidation(idEmployee);
        if (employee.getPhoneNumber().equals(phoneNumber)) {
            throw new IllegalArgumentException("Telefono ya registrado en el sistema, ingrese un nuevo numero telefonico");
        }
        employeeRespository.updatePhoneNumber(employee.getEmployeeId(), phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType) {
        Employee employee = employeeValidation(idEmployee);

        if (employee.getContractType().equals(contractType)) {
            throw new IllegalArgumentException("El empleado ya cuenta con el contrato en el sistema, ingrese un nuevo contrato");
        }
        employeeRespository.updateContractType(employee.getEmployeeId(), contractType);
    }


    private Employee employeeValidation(Long id){
        Employee employee = employeeRespository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontro empleado asociado al Id "+ id + " en el sistema"));

        if (!employee.getActive()) {
            throw new RuntimeException("El empleado con el Id" + id +" se encuentra dehabilitado");
        }
        return employee;
    }
}