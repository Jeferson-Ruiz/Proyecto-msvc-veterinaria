package com.jeferson.msvc_sav_workstaff.services;

import com.jeferson.msvc_sav_workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc_sav_workstaff.mapper.EmployeeMapper;
import com.jeferson.msvc_sav_workstaff.models.Administrative;
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.Employee;
import com.jeferson.msvc_sav_workstaff.models.Intern;
import com.jeferson.msvc_sav_workstaff.models.Vet;
import com.jeferson.msvc_sav_workstaff.models.WorkArea;
import com.jeferson.msvc_sav_workstaff.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRespository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRespository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<EmployeeResponseDto> findAll() {
        List<Employee> employees = employeeRepository.findAllActive();
        if (employees.isEmpty()) {
            throw new EntityNotFoundException("No se encuetran empleados registrados en el sistema");
        }
        return employees.stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public List<EmployeeResponseDto> findAllDisabled(){
        List<Employee> employees = employeeRepository.findAllDisabled();
        if (employees.isEmpty()) {
            throw new EntityNotFoundException("No se encuetran empleados desahabilitados en el sistema");
        }
        return employees.stream()
            .map(employeeMapper::toDto)
            .toList();
    }

    @Override
    public List<EmployeeResponseDto> getEmployeesByType(WorkArea workArea) {
        Class<? extends Employee> clazz = switch (workArea) {
            case ADMINISTRATIVE -> Administrative.class;
            case AUXILIARY -> Auxiliary.class;
            case INTERN -> Intern.class;
            case VET -> Vet.class;
        };

        return employeeRepository.findByType(clazz)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }


    @Override
    public EmployeeResponseDto findById(Long idEmployee) {
        Employee employee = employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro empleado asociado al Id " + idEmployee));
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeResponseDto findByDocumentNumber(String documentNumber) {
        Employee employee = employeeRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro empleado asociado al N° identificacion " + documentNumber));
        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional
    public void delete(Long idEmployee) {
        Employee employee = employeeRepository.getReferenceById(idEmployee);

        if (!employee.getActive()) {
            throw new IllegalArgumentException(
                    "El empleado " + idEmployee + " ya se encuentra desahabilitado del sistema");
        }
        employee.setActive(false);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email) {
        employeeRepository.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(Long idEmployee, String phoneNumber) {
        employeeRepository.updatePhoneNumber(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType) {
        employeeRepository.updateContractType(idEmployee, contractType);
    }

}