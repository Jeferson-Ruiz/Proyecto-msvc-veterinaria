package com.jeferson.msvc.workstaff.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.ActionInformation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeferson.msvc.workstaff.dto.AdministrativeRequestDto;
import com.jeferson.msvc.workstaff.dto.AdmistrativeResponseDto;
import com.jeferson.msvc.workstaff.mapper.AdministrativeMapper;
import com.jeferson.msvc.workstaff.models.Administrative;
import com.jeferson.msvc.workstaff.models.AdministrativeRoles;
import com.jeferson.msvc.workstaff.repositories.AdministrativeRepository;
import com.jeferson.msvc.workstaff.repositories.EmployeeRepository;

@Service
public class AdministrativeServiceImpl extends CodeEmployeeService implements AdministrativeService {

    private final AdministrativeRepository administrativeRepository;
    private final AdministrativeMapper administrativeMapper;

    public AdministrativeServiceImpl(AdministrativeRepository administrativeRepository,
            AdministrativeMapper administrativeMapper,
            EmployeeRepository employeeRepo) {
        super(employeeRepo);
        this.administrativeRepository = administrativeRepository;
        this.administrativeMapper = administrativeMapper;
    }

    @Override
    public AdmistrativeResponseDto saveAdministrative(AdministrativeRequestDto administrativeDto) {
        Boolean employee = employeeRepo.findByDocumentNumber(administrativeDto.getDocumentNumber()).isPresent();
        if (employee == true) {
            throw new RuntimeException("El documento " + administrativeDto.getDocumentNumber()
                    + " ya se encuentra vinculado a un empleado");
        }
        Administrative entity = administrativeMapper.toEntity(administrativeDto);
        if (administrativeRepository.existsByProfessionalCard(entity.getProfessionalCard()) && !entity.getProfessionalCard().isEmpty() ) {
            throw new IllegalArgumentException("La tarjeta profesional " + administrativeDto.getProfessionalCard()
                    + " ya se encuentra vinculado en el sistema");
        }
        if (entity.getProfessionalCard().isEmpty()) {
            entity.setProfessionalCard(null);
        }
        String codee = generateEmployeeCode("AD", entity);
        entity.setEmployeeCode(codee);
        entity.setRegistrationDate(LocalDate.now());
        entity.setStatus(EmployeeStatus.PROCESS);
        Administrative saved = employeeRepo.save(entity);
        return administrativeMapper.toDto(saved);
    }

    @Override
    public List<AdmistrativeResponseDto> findAllByStatus(EmployeeStatus status) {
        validateStatus(status);
        List<Administrative> administratives = administrativeRepository.findAllByStatus(status);
        if (administratives.isEmpty()) {
            throw new EntityNotFoundException("No existen administrativos asociadoas al estado de "+ status +" en el sistema");
        }
        return mapAdministrativesByStatus(status, administratives);
    }

    @Override
    public List<AdmistrativeResponseDto> findAllByRole(AdministrativeRoles administrativeRole, EmployeeStatus status){
        validateStatus(status);
        List<Administrative> administratives = administrativeRepository.findAllByRoles(administrativeRole, status);
        if (administratives.isEmpty()) {
            throw new EntityNotFoundException("No se encontro registro de " + administrativeRole + " asociados al estado de "+ status);    
        }
        return mapAdministrativesByStatus(status, administratives);
    }

    @Override
    public AdmistrativeResponseDto findAdminByDocumentNumber(String documentNumber) {
        Administrative administrative = administrativeRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro administrativo asociado al numero de documento " + documentNumber));
        return mapAdministrativeByStatus(administrative);
    }

    @Override
    public AdmistrativeResponseDto findAdminByCode(String employeeCode) {
        Administrative administrative = administrativeRepository.findByEmployeeCode(employeeCode)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro administrativo asociado al codigo " + employeeCode));
        return mapAdministrativeByStatus(administrative);
    }

    @Override
    @Transactional
    public void updateEmail(String employeeCode, String email) {
        Administrative administrative = validateInfo(employeeCode);
        if (administrative.getEmail().equals(email)) {
            throw new IllegalArgumentException(
                    "El email " + email + " ya se encuentra vinculado al administrativo " + administrative.getName());
        }
        administrative.setEmail(email);
        administrativeRepository.save(administrative);
    }

    @Override
    @Transactional
    public void updateNumberPhone(String employeeCode, String phoneNumber) {
        Administrative administrative = validateInfo(employeeCode);
        if (administrative.getPhoneNumber().equals(phoneNumber)) {
            throw new IllegalArgumentException(
                    "El telefono " + phoneNumber + " ya se encuentra vinculado al administrativo " + administrative.getName());
        }
        administrative.setPhoneNumber(phoneNumber);
        administrativeRepository.save(administrative);
    }

    @Override
    @Transactional
    public void updateContractType(String employeeCode, ContractType contractType) {
        Administrative administrative = validateInfo(employeeCode);
        if (administrative.getContractType().equals(contractType)) {
            throw new IllegalArgumentException(
                    "El contrato " + contractType + " ya se encuentra vinculado al administrativo " + administrative.getName());
        }
        administrative.setContractType(contractType);
        administrativeRepository.save(administrative);
    }

    @Override
    @Transactional
    public void updateRole(String employeeCode, AdministrativeRoles admiRoles) {
        Administrative administrative = validateInfo(employeeCode);
        if (administrative.getAdministrativeRoles().equals(admiRoles)) {
            throw new IllegalArgumentException(
                    "El area de trabajo " + admiRoles + " ya se encuentra vinculado al administrativo " + administrative.getName());
        }
        administrative.setAdministrativeRoles(admiRoles);
        administrativeRepository.save(administrative);
    }

    @Override
    public void updateEmployeeStatus(String employeeCode, EmployeeStatus status){
        Administrative administrative = validateInfo(employeeCode);
        validateUpdateStatus(status, administrative);
        administrative.setStatus(status);
        administrativeRepository.save(administrative);
    }

    @Override
    public void suspended(String employeeCode, String deleteBy, String reason){
        Administrative administrative = validateInfo(employeeCode);
        if (administrative.getStatus() == EmployeeStatus.SUSPENDED) {
            throw new IllegalArgumentException("El empleado administrativo "+administrative.getName() +" ya se encuentra suspendido del sistema");
        }
        administrative.setStatus(EmployeeStatus.SUSPENDED);
        applyStatusChange(administrative, deleteBy, reason);
    }

    @Override
    public void delete(String employeeCode, String deleteAt, String reason) {
        Administrative administrative = validateInfo(employeeCode);
        if (administrative.getStatus() == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("El empleado administrativo "+ employeeCode + " ya está eliminado del sistema");
        }
        administrative.setStatus(EmployeeStatus.DELETED);
        applyStatusChange(administrative, deleteAt, reason);
    }


    //helpers
    private Administrative validateInfo(String employeeCode) {
        Administrative administrative = administrativeRepository.findByEmployeeCode(employeeCode)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro administrativo asociado al codigo" + employeeCode));

        if (administrative.getStatus() == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("El administrativo " +administrative.getName()+ " ya se encuentra Eliminado, no se puede efectuar ninguna actualizacion");
        }
        return administrative;
    }

    private List<AdmistrativeResponseDto> mapAdministrativesByStatus( EmployeeStatus status, List<Administrative> administratives) {
    return switch (status) {
        case ACTIVE, PROCESS -> administratives.stream()
            .map(administrativeMapper::toDto)
            .toList();
        case SUSPENDED, DELETED -> administratives.stream()
            .map(administrativeMapper::toDisabledDto)
            .map(dto -> (AdmistrativeResponseDto) dto) 
            .toList();
        };
    }

    private AdmistrativeResponseDto mapAdministrativeByStatus(Administrative administrative){
        EmployeeStatus status = administrative.getStatus();
        return switch (status) {
            case ACTIVE, PROCESS -> administrativeMapper.toDto(administrative);
            case DELETED, SUSPENDED -> administrativeMapper.toDisabledDto(administrative);
        };
    }

    private void applyStatusChange(Administrative administration, String deleteBy, String reason) {
        ActionInformation actionInf = new ActionInformation();
        actionInf.setDeletedAt(LocalDateTime.now());
        actionInf.setDeletedBy(deleteBy);
        actionInf.setReason(reason);
        actionInf.setEmployee(administration);
        administration.getActionInformations().add(actionInf);
        administrativeRepository.save(administration);
    }

    private void validateUpdateStatus(EmployeeStatus status, Administrative administrative){
        if (status == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("No se puede eliminar un usuario desde el panel de actualizacion de estado");}
        if (status == administrative.getStatus()) {
            throw new IllegalArgumentException("El empleado ya se encuentra vinculado al estado de "+ status);}
    }

    private EmployeeStatus validateStatus(EmployeeStatus status){
        return status != null ? status : EmployeeStatus.ACTIVE;
    }

}
