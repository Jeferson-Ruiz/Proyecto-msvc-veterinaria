package com.jeferson.msvc.workstaff.services;

import com.jeferson.msvc.workstaff.dto.EmployeeRequestDto;
import com.jeferson.msvc.workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc.workstaff.mapper.EmployeeMapper;
import com.jeferson.msvc.workstaff.models.Employee;
import com.jeferson.msvc.workstaff.models.Role;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.ActionInformation;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.WorkArea;
import com.jeferson.msvc.workstaff.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final CodeService codeService;
    private final RoleValidation roleValidation;

    public EmployeeServiceImpl(
        EmployeeRepository employeeRespository,
        EmployeeMapper employeeMapper,
        CodeService codeService,
        RoleValidation roleValidation) {
        this.employeeRepository = employeeRespository;
        this.employeeMapper = employeeMapper;
        this.codeService = codeService;
        this.roleValidation = roleValidation;
    }

    @Override
    public EmployeeResponseDto save(EmployeeRequestDto employeeDto){
        
        //Validacion informacion repetida
        validateEmployeeInfo(employeeDto.getDocumentNumber(), employeeDto.getPhoneNumber(), employeeDto.getEmail());

        Employee employee = employeeMapper.toEntity(employeeDto);
        //Generacion de codigo
        employee.setEmployeeCode(codeService.generateEmployeeCode(employee));

        //validacion de roles en Area
        roleValidation.roleValidation(employee.getWorkArea(), employeeDto.getRole());

        //mapeo
        employee.setName(employeeDto.getName().toLowerCase());
        employee.setStatus(EmployeeStatus.PROCESS);
        employee.setRegistrationDate(LocalDate.now());
        employeeRepository.save(employee);
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
    public EmployeeResponseDto findByEmployeeCode(String code){
        Employee employee = employeeRepository.findByEmployeeCode(code).
            orElseThrow(() -> new EntityNotFoundException("No se encontro empleado asociado al codigo "+ code));
            return employeeMapper.toDto(employee);
    }

    @Override
    public List<EmployeeResponseDto> findAllByAreaRolAndStatus(WorkArea area, String role, EmployeeStatus status) {
        List<Employee> employees = employeeRepository.findByAreaRoleAndStatus(area, role, status);
        if (employees.isEmpty()) throw new EntityNotFoundException("No se encontro empleados asociados a los parametos de busqueda");
        return employees.stream()
            .map(employeeMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transient
    public void updateEmail(String employeeCode, String newEmail){
        Employee employee = findEmployeeCode(employeeCode);
        validateStatus(employee.getStatus());
        employeeRepository.existsByEmail(newEmail);
        employee.setEmail(newEmail);
        employeeRepository.save(employee);
    }

    @Override
    @Transient
    public void updatePhone(String employeeCode, String newPhone){
        Employee employee = findEmployeeCode(employeeCode);
        validateStatus(employee.getStatus());
        employeeRepository.existsByPhone(newPhone);
        employee.setPhoneNumber(newPhone);
        employeeRepository.save(employee);
    }

    @Override
    @Transient
    public void updateRole(String employeeCode, Set<Role> role){
        Employee employee = findEmployeeCode(employeeCode);
        validateStatus(employee.getStatus());
        roleValidation.roleValidation(employee.getWorkArea(), role);
        employee.setRoles(role);
        employeeRepository.save(employee);
    }

    @Override
    @Transient
    public void updateContractType(String employeeCode ,ContractType newContract){
        Employee employee = findEmployeeCode(employeeCode);
        employee.setContractType(newContract);
        employeeRepository.save(employee);
    }

    @Override
    @Transient
    public void updateEmployeeStatus(String employeeCode, EmployeeStatus employeeStatus){
        Employee employee = findEmployeeCode(employeeCode);
        validateUpdateStatus(employeeStatus, employee);
        employee.setStatus(employeeStatus);
        employeeRepository.save(employee);
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

    //helpers
    private void validateEmployeeInfo(String documentNumber, String phoneNumber, String email){

        if (employeeRepository.existsByDocument(documentNumber)) {
         throw new IllegalArgumentException("Error, documento ya registrado");
        } else if (employeeRepository.existsByPhone(phoneNumber)) {
            throw new IllegalArgumentException("El numero de telefono ya se encuentra resgistrado en el sistema");
        }else if(employeeRepository.existsByEmail(email)){
            throw new IllegalArgumentException("El email ya se encuentra registrado en el sistema");
        }
    }

    private void validateStatus(EmployeeStatus status){
        if (status.equals(EmployeeStatus.DELETED)) {
            throw new IllegalArgumentException("El empleado se encientra eliminado, no se puede actualizar");
        }
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