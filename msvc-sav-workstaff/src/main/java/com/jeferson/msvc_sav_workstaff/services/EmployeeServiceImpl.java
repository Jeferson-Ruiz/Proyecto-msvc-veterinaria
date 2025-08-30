package com.jeferson.msvc_sav_workstaff.services;

import com.jeferson.msvc_sav_workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc_sav_workstaff.mapper.EmployeeMapper;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.Employee;
import com.jeferson.msvc_sav_workstaff.models.WorkArea;
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
    public List<EmployeeResponseDto> findAllByJobPosition(WorkArea workArea) {
        return employeeRespository.findByWorkArea(workArea).stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long idEmployee) {
        Employee employee = employeeRespository.getReferenceById(idEmployee);
        employee.setActive(false);
        employeeRespository.save(employee);
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email) {
        employeeRespository.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(Long idEmployee, String phoneNumber) {
        employeeRespository.updatePhoneNumber(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType) {
        employeeRespository.updateContractType(idEmployee, contractType);
    }

    @Override
    @Transactional
    public void updateWorkArea(Long idEmployee, WorkArea workArea){
        employeeRespository.updateWorkArea(idEmployee, workArea);
    }

}