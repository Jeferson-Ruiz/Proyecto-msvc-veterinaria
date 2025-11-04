package com.jeferson.msvc.workstaff.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.jeferson.msvc.workstaff.models.ActionInformation;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.jeferson.msvc.workstaff.dto.InternResponseDto;
import com.jeferson.msvc.workstaff.dto.InternRequestDto;
import com.jeferson.msvc.workstaff.mapper.InternMapper;
import com.jeferson.msvc.workstaff.models.Intern;
import com.jeferson.msvc.workstaff.models.InternRoles;
import com.jeferson.msvc.workstaff.repositories.EmployeeRepository;
import com.jeferson.msvc.workstaff.repositories.InternRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InternServiceImpl extends CodeEmployeeService implements InternService {

    private final InternRepository intRepository;
    private final InternMapper intMapper;

    public InternServiceImpl(InternRepository intRepository, InternMapper intMapper,
            EmployeeRepository employeeRepo) {
        super(employeeRepo);
        this.intRepository = intRepository;
        this.intMapper = intMapper;
    }

    @Override
    public InternResponseDto saveIntern(InternRequestDto internDto) {
        if (employeeRepo.findByDocumentNumber(internDto.getDocumentNumber()).isPresent()) {
            throw new RuntimeException(
                    "El documento " + internDto.getDocumentNumber() + " ya se encuentra vinculado a un empleado");
        }
        Intern entity = intMapper.toEntity(internDto);

        String code = generateEmployeeCode("IN", entity);
        entity.setEmployeeCode(code);
        
        entity.setRegistrationDate(LocalDate.now());
        entity.setStatus(EmployeeStatus.PROCESS);
        Intern saved = intRepository.save(entity);
        return intMapper.toDto(saved);
    }

    @Override
    public List<InternResponseDto> findAllByStatus(EmployeeStatus status){
        validateStatus(status);
        List<Intern> interns = intRepository.findAllByStatus(status);
        if (interns.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron pasante asociados al estado laboral de "+ status );
        }
        return mapInternsByStatus(status, interns);
    }

        @Override
    public List<InternResponseDto> findAllByRoleAndStatus(InternRoles internRole, EmployeeStatus status){
        validateStatus(status);
        List<Intern> interns = intRepository.findAllByRole(internRole, status);
        if (interns.isEmpty()) {
            throw new EntityNotFoundException("No se encontro registro de " + internRole + " asociados al estado de "+ status);
        }
        return mapInternsByStatus(status, interns);
    }

    @Override
    public InternResponseDto findAdminByDocumentNumber(String documentNumber) {
        Intern intern = intRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro veterianio asociado al numero de documento " + documentNumber));
        return mapInternByStatus(intern);
    }

    @Override
    public InternResponseDto findByCode(String employeeCode) {
        Intern intern = intRepository.findByCode(employeeCode)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro pasante asociado al codigo "+ employeeCode));
        return mapInternByStatus(intern);
    }

    @Override
    @Transactional
    public void updateEmail(String employeeCode, String email) {
        Intern intern = validateInfo(employeeCode);
        if (intern.getEmail().equals(email)) {
            throw new IllegalArgumentException(
                    "El email " + email + " ya se encuentra asociado al pasante " + intern.getName());
        }
        intern.setEmail(email);
        intRepository.save(intern);
    }

    @Override
    @Transactional
    public void updateNumberPhone(String employeeCode, String phoneNumber) {
        Intern intern = validateInfo(employeeCode);
        if (intern.getPhoneNumber().equals(phoneNumber)) {
            throw new IllegalArgumentException(
                    "El telefono " + phoneNumber + " ya se encuentra asociado al pasante " + intern.getName());
        }
        intern.setPhoneNumber(phoneNumber);
        intRepository.save(intern);
    }

    @Override
    @Transactional
    public void updateContractType(String employeeCode, ContractType contractType) {
        Intern intern = validateInfo(employeeCode);
        if (intern.getContractType().equals(contractType)) {
            throw new IllegalArgumentException(
                    "El contrato " + contractType + " ya se encuentra asociado al pasante " + intern.getName());
        }
        intern.setContractType(contractType);
        intRepository.save(intern);
    }

    @Override
    @Transactional
    public void updateRoles(String employeeCode, InternRoles internRoles) {
        Intern intern = validateInfo(employeeCode);
        if (intern.getInternRoles().equals(internRoles)) {
            throw new IllegalArgumentException(
                    "El area de trabajo " + internRoles + " ya se encuentra asociado al pasante " + intern.getName());
        }
        intRepository.updateRole(employeeCode, internRoles);
    }

    @Override
    public void updateEmployeeStatus(String employeeCode, EmployeeStatus status){
        Intern intern = validateInfo(employeeCode);
        validateUpdateStatus(status, intern);
        intern.setStatus(status);
        intRepository.save(intern);
    }

    @Override
    public void suspended(String employeeCode, String deleteBy, String reason) {
        Intern intern = validateInfo(employeeCode);
        if (intern.getStatus() == EmployeeStatus.SUSPENDED) {
            throw new IllegalArgumentException("El practicante "+ intern.getName()+" ya se encuentra suspendido del sistema");
        }
        intern.setStatus(EmployeeStatus.SUSPENDED);
        applyStatusChange(intern, deleteBy, reason);
    }

    @Override
    public void delete(String employeeCode, String deleteBy, String reason) {
        Intern intern = validateInfo(employeeCode);
        intern.setStatus(EmployeeStatus.DELETED);
        applyStatusChange(intern, deleteBy, reason);
    }


    //helper
    private Intern validateInfo(String employeeCode) {
        Intern intern = intRepository.findByCode(employeeCode)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro pasante asociado al codigo " + employeeCode));
        if (intern.getStatus() == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("El pasante " + employeeCode+ " se encuentra deshabilitado permanentemente del sistema");
        }
        return intern;
    }

    private List<InternResponseDto> mapInternsByStatus(EmployeeStatus status, List<Intern> interns){
        return switch (status) {
            case ACTIVE, PROCESS -> interns.stream()
                .map(intMapper::toDto) 
                .toList();
            case DELETED, SUSPENDED -> interns.stream()
                .map(intMapper::toDisabledDto)
                .map(dto -> (InternResponseDto) dto).toList();                
        };
    }

    private InternResponseDto mapInternByStatus(Intern intern){
        EmployeeStatus status = intern.getStatus();
        return switch (status) {
            case ACTIVE, PROCESS -> intMapper.toDto(intern);
            case DELETED, SUSPENDED -> intMapper.toDisabledDto(intern);
        };
    }

    private void applyStatusChange(Intern intern, String deleteBy, String reason) {
        ActionInformation actionInf = new ActionInformation();
        actionInf.setDeletedAt(LocalDateTime.now());
        actionInf.setDeletedBy(deleteBy);
        actionInf.setReason(reason);
        actionInf.setEmployee(intern);
        intern.getActionInformations().add(actionInf);
        intRepository.save(intern);
    }

    private void validateUpdateStatus(EmployeeStatus status, Intern intern){
        if (status == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("No se puede eliminar un usuario desde el panel de actualizacion de estado");}
        if (status == intern.getStatus()) {
            throw new IllegalArgumentException("El empleado ya se encuentra vinculado al estado de "+ status);}
    }

    private EmployeeStatus validateStatus(EmployeeStatus status){
        return status != null ? status : EmployeeStatus.ACTIVE;
    }

}