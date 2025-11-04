package com.jeferson.msvc.workstaff.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.jeferson.msvc.workstaff.dto.AuxiliaryRequestDto;
import com.jeferson.msvc.workstaff.dto.AuxiliaryResponseDto;
import com.jeferson.msvc.workstaff.mapper.AuxiliaryMapper;
import com.jeferson.msvc.workstaff.models.ActionInformation;
import com.jeferson.msvc.workstaff.models.Auxiliary;
import com.jeferson.msvc.workstaff.models.AuxiliaryRoles;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.repositories.AuxiliaryRepository;
import com.jeferson.msvc.workstaff.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AuxiliaryServiceImpl extends CodeEmployeeService implements AuxiliaryService {

    private final AuxiliaryRepository auxRepository;
    private final AuxiliaryMapper auxMapper;

    public AuxiliaryServiceImpl(AuxiliaryRepository auxRepository,
            AuxiliaryMapper auxMapper,
            EmployeeRepository employeeRepo) {
        super(employeeRepo);
        this.auxRepository = auxRepository;
        this.auxMapper = auxMapper;
    }

    @Override
    public AuxiliaryResponseDto saveAuxiliary(AuxiliaryRequestDto auxiliaryDto) {
        Boolean employee = employeeRepo.findByDocumentNumber(auxiliaryDto.getDocumentNumber()).isPresent();
        if (employee) {
            throw new RuntimeException(
                    "El documento " + auxiliaryDto.getDocumentNumber() + " ya se encuentra vinculado a un empleado en el sistema");
        }
        Auxiliary entity = auxMapper.toEntity(auxiliaryDto);

        String code = generateEmployeeCode("AX", entity);
        entity.setEmployeeCode(code);
        entity.setRegistrationDate(LocalDate.now());
        entity.setStatus(EmployeeStatus.PROCESS);
        auxRepository.save(entity);
        return auxMapper.toDto(entity);
    }

    @Override
    public List<AuxiliaryResponseDto> findAllByStatus(EmployeeStatus status) {
        validateStatus(status);
        List<Auxiliary> auxiliaries = auxRepository.findAllByStatus(status);
        if (auxiliaries.isEmpty()) {
            throw new EntityNotFoundException(" No existen auxiares asociadoas al estado de "+ status +" en el sistem");
        }
        return mapAuxiliariesByStatus(status, auxiliaries);
    }

    @Override
    public List<AuxiliaryResponseDto> findAllByRoles(AuxiliaryRoles auxiliaryRole, EmployeeStatus status){
        validateStatus(status);
        List<Auxiliary> auxiliaries = auxRepository.findByRoles(auxiliaryRole, status);
        if (auxiliaries.isEmpty()) {
            throw new EntityNotFoundException("No se encontro registro de " + auxiliaryRole + " asociados al estado de "+ status);
        }
        return mapAuxiliariesByStatus(status, auxiliaries);
    }

    @Override
    public AuxiliaryResponseDto findAdminByDocumentNumber(String documentNumber) {
        Auxiliary auxiliary = auxRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro auxiliar asociado al numero de documento " + documentNumber));
        return mapAuxiliaryByStatus(auxiliary);
    }

    @Override
    public AuxiliaryResponseDto findAdminByCode(String employeeCode) {
        Auxiliary auxiliary = auxRepository.findByCode(employeeCode)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro auxiliar asociado al codigo " + employeeCode));
        return mapAuxiliaryByStatus(auxiliary);
    }

    @Override
    @Transactional
    public void updateEmail(String employeeCode, String email) {
        Auxiliary auxiliary = validateInfo(employeeCode);
        if (auxiliary.getEmail().equals(email)) {
            throw new IllegalArgumentException(
                    "El email " + email + " ya se encuentra vinculado al auxiliar " + auxiliary.getName());
        }
        auxiliary.setEmail(email);
        auxRepository.save(auxiliary);
    }

    @Override
    @Transactional
    public void updatePhoneNumber(String employeeCode, String phoneNumber) {
        Auxiliary auxiliary = validateInfo(employeeCode);
        if (auxiliary.getPhoneNumber().equals(phoneNumber)) {
            throw new IllegalArgumentException(
                    "El telefono " + phoneNumber + " ya se encuentra vinculado al auxiliar " + auxiliary.getName());
        }
        auxiliary.setPhoneNumber(phoneNumber);
        auxRepository.save(auxiliary);
    }

    @Override
    @Transactional
    public void updateContractType(String employeeCode, ContractType contractType) {
        Auxiliary auxiliary = validateInfo(employeeCode);
        if (auxiliary.getContractType().equals(contractType)) {
            throw new IllegalArgumentException(
                    "El contrato " + contractType + " ya se encuentra vinculado al auxiliar " + auxiliary.getName());
        }
        auxiliary.setContractType(contractType);
        auxRepository.save(auxiliary);
    }

    @Override
    @Transactional
    public void updateRole(String employeeCode, AuxiliaryRoles auxiliaryRoles) {
        Auxiliary auxiliary = validateInfo(employeeCode);
        if (auxiliary.getAuxiliaryRoles().equals(auxiliaryRoles)) {
            throw new IllegalArgumentException(
                    "El area de trabajo " + auxiliaryRoles + " ya se encuentra vinculado al auxiliar " + auxiliary.getName());
        }
        auxRepository.updateRole(employeeCode, auxiliaryRoles);
    }

    @Override
    public void delete(String employeeCode, String deleteBy, String reason) {
        Auxiliary auxiliary = validateInfo(employeeCode);
        if (auxiliary.getStatus() == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("El auxiliar "+ auxiliary.getName() + " ya está eliminado del sistema");
        }
        auxiliary.setStatus(EmployeeStatus.DELETED);
        applyStatusChange(auxiliary, deleteBy, reason);
    }

    @Override
    public void suspended(String employeeCode, String deleteBy, String reason){
        Auxiliary auxiliary = validateInfo(employeeCode);
        if (auxiliary.getStatus() == EmployeeStatus.SUSPENDED) {
            throw new IllegalArgumentException("El auxiliar "+ auxiliary.getName() + "ya se encuentra suspendido del sistema");
        }
        auxiliary.setStatus(EmployeeStatus.SUSPENDED);
        applyStatusChange(auxiliary, deleteBy, reason);
    }

    @Override
    public void updateEmployeeStatus(String employeeCode, EmployeeStatus employeeStatus){
        Auxiliary auxiliary = validateInfo(employeeCode);
        validateUpdateStatus(employeeStatus, auxiliary);
        auxiliary.setStatus(employeeStatus);
        auxRepository.save(auxiliary);
    }

    // helpers
    private Auxiliary validateInfo(String employeeCode) {
        Auxiliary auxiliary = auxRepository.findByCode(employeeCode)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro auxiliar asociado al codigo "+ employeeCode));
        if (auxiliary.getStatus() == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("El auxiliar " + employeeCode + " se encuentra deshabilitado permanentemente del sistema");
        }
        return auxiliary;
    }

    private List<AuxiliaryResponseDto> mapAuxiliariesByStatus(EmployeeStatus status, List<Auxiliary> auxiliaries) {
        return switch (status) {
            case ACTIVE, PROCESS -> auxiliaries.stream()
                .map(auxMapper::toDto)
                .toList();
            case SUSPENDED, DELETED -> auxiliaries.stream()
                .map(auxMapper::toDisabledDto)
                .map(dto -> (AuxiliaryResponseDto)dto)
                .toList();
        };
    }

    private AuxiliaryResponseDto mapAuxiliaryByStatus(Auxiliary auxiliary){
        EmployeeStatus status = auxiliary.getStatus();
        return switch (status) {
            case ACTIVE, PROCESS -> auxMapper.toDto(auxiliary);
            case SUSPENDED, DELETED -> auxMapper.toDisabledDto(auxiliary);
        };
    }

    private void applyStatusChange(Auxiliary auxiliary, String deleteBy, String reason) {
        ActionInformation actionInf = new ActionInformation();
        actionInf.setDeletedAt(LocalDateTime.now());
        actionInf.setDeletedBy(deleteBy);
        actionInf.setReason(reason);
        actionInf.setEmployee(auxiliary);
        auxiliary.getActionInformations().add(actionInf);
        auxRepository.save(auxiliary);
    }

    private void validateUpdateStatus(EmployeeStatus status, Auxiliary auxiliary){
        if (status == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("No se puede eliminar un usuario desde el panel de actualizacion de estado");}
        if (status == auxiliary.getStatus()) {
            throw new IllegalArgumentException("El empleado ya se encuentra vinculado al estado de "+ status);}
    }

    private EmployeeStatus validateStatus(EmployeeStatus status){
        return status != null ? status : EmployeeStatus.ACTIVE;
    }
}