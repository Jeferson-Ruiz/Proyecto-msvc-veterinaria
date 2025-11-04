package com.jeferson.msvc.workstaff.services;

import com.jeferson.msvc.workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc.workstaff.mapper.EmployeeMapper;
import com.jeferson.msvc.workstaff.models.Administrative;
import com.jeferson.msvc.workstaff.models.Auxiliary;
import com.jeferson.msvc.workstaff.models.Employee;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.Intern;
import com.jeferson.msvc.workstaff.models.ActionInformation;
import com.jeferson.msvc.workstaff.models.Vet;
import com.jeferson.msvc.workstaff.models.WorkArea;
import com.jeferson.msvc.workstaff.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
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
    public List<EmployeeResponseDto> findAllByStatus(EmployeeStatus status) {
        List<Employee> employees = employeeRepository.findAllByStatus(status);
        if (employees.isEmpty()) {
            throw new EntityNotFoundException("No se encuetran empleados registrados en el sistema");
        }
        return employees.stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public List<EmployeeResponseDto> getEmployeesByType(WorkArea workArea, EmployeeStatus status) {
        validateStatus(status);

        Class<? extends Employee> clazz = switch (workArea) {
            case ADMINISTRATIVE -> Administrative.class;
            case AUXILIARY -> Auxiliary.class;
            case INTERN -> Intern.class;
            case VET -> Vet.class;
        };

        return employeeRepository.findByType(clazz, status)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public EmployeeResponseDto findByEmployeeCode(String code){
        Employee employee = employeeRepository.findByEmployeeCode(code).
            orElseThrow(() -> new EntityNotFoundException("No se encontro empleado asociado al codigo "+ code));
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
    public void delete(String employeeCode, String deleteBy, String reason) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode).orElseThrow(() -> 
            new EntityNotFoundException("No se encontro empleado asociado al Id"));

        if (employee.getStatus() == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException(
                    "El empleado " + employee.getName() + " ya se encuentra desahabilitado del sistema");
        }
        employee.setStatus(EmployeeStatus.DELETED);

        ActionInformation removal = new ActionInformation();
        removal.setEmployee(employee);
        removal.setDeletedAt(LocalDateTime.now());
        removal.setDeletedBy(deleteBy);
        removal.setReason(reason);
        employee.getActionInformations().add(removal);
        employeeRepository.save(employee);
    }

    @Override
    public void updateEmployeeStatus(String employeeCode, EmployeeStatus employeeStatus){
        Employee employee = findEmployeeCode(employeeCode);
        validateUpdateStatus(employeeStatus, employee);
        employee.setStatus(employeeStatus);
        employeeRepository.save(employee);
    }

    private EmployeeStatus validateStatus(EmployeeStatus status){
        return status != null ? status : EmployeeStatus.ACTIVE;
    }


    private Employee findEmployeeCode(String code){
        Employee employee = employeeRepository.findByEmployeeCode(code)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro empleado asociado al codigo "+ code));
        return employee;
    }

    private void validateUpdateStatus(EmployeeStatus status, Employee employee){
        if (status == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("No se puede eliminar un usuario desde el panel de actualizacion de estado");}
        if (status == employee.getStatus()) {
            throw new IllegalArgumentException("El empleado ya se encuentra vinculado al estado de "+ status);}
    }

}