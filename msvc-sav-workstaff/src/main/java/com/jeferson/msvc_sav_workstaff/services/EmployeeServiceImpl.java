package com.jeferson.msvc_sav_workstaff.services;

import com.jeferson.msvc_sav_workstaff.dto.EmployeeDto;
import com.jeferson.msvc_sav_workstaff.mapper.EmployeeMapper;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.JobPosition;
import com.jeferson.msvc_sav_workstaff.repositories.EmployeeRespository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRespository employeeRespository;
    private  final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRespository employeeRespository, EmployeeMapper employeeMapper) {
        this.employeeRespository = employeeRespository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<EmployeeDto> findAll() {
        return employeeRespository.findAll().stream()
                .map(employeeMapper::toDto).toList();
    }

    @Override
    public Optional<EmployeeDto> findById(Long idEmployee) {
        return employeeRespository.findById(idEmployee)
                .map(employeeMapper::toDto);
    }

    @Override
    public Optional<EmployeeDto> findByDocumentNumber(Long documentNumber) {
        return employeeRespository.findByDocumentNumber(documentNumber)
                .map(employeeMapper::toDto);
    }

    @Override
    public List<EmployeeDto> findAllByJobPosition(JobPosition jobPosition) {
        return employeeRespository.findByJobPosition(jobPosition).stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long idEmployee) {
        employeeRespository.deleteById(idEmployee);
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email) {
        employeeRespository.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(Long idEmployee, Long phoneNumber) {
        employeeRespository.updatePhoneNumber(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType) {
        employeeRespository.updateContractType(idEmployee, contractType);
    }
}