package com.jeferson.msvc_sav_workstaff.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.EmployeeStatus;
import com.jeferson.msvc_sav_workstaff.models.ActionInformation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeferson.msvc_sav_workstaff.dto.AdministrativeRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AdmistrativeResponseDto;
import com.jeferson.msvc_sav_workstaff.mapper.AdministrativeMapper;
import com.jeferson.msvc_sav_workstaff.models.Administrative;
import com.jeferson.msvc_sav_workstaff.models.AdministrativeRoles;
import com.jeferson.msvc_sav_workstaff.repositories.AdministrativeRepository;
import com.jeferson.msvc_sav_workstaff.repositories.EmployeeRepository;

@Service
public class AdministrativeServiceImpl implements AdministrativeService {

    private final AdministrativeRepository administrativeRepository;
    private final AdministrativeMapper administrativeMapper;
    private final EmployeeRepository employeeRepo;
    private final EmployeeService employeeServi;

    public AdministrativeServiceImpl(AdministrativeRepository administrativeRepository,
            AdministrativeMapper administrativeMapper,
            EmployeeRepository employeeRepo,
            EmployeeService employeeServi, EmployeeService employeeService) {
        this.administrativeRepository = administrativeRepository;
        this.administrativeMapper = administrativeMapper;
        this.employeeRepo = employeeRepo;
        this.employeeServi = employeeServi;
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
        entity.setRegistrationDate(LocalDate.now());
        entity.setStatus(EmployeeStatus.PROCESS);
        Administrative saved = employeeRepo.save(entity);
        return administrativeMapper.toDto(saved);
    }

    @Override
    public List<?> findAllByStatus(EmployeeStatus status) {
        employeeServi.validateStatus(status);
        List<Administrative> administratives = administrativeRepository.findAllByStatus(status);
        if (administratives.isEmpty()) {
            throw new EntityNotFoundException("No existen administrativos asociadoas al estado de "+ status +" en el sistema");
        }
        return mapAdministrativesByStatus(status, administratives);
    }

    @Override
    public List<?> findAllByRole(AdministrativeRoles administrativeRole, EmployeeStatus status){
        employeeServi.validateStatus(status);
        List<Administrative> administratives = administrativeRepository.findAllByRoles(administrativeRole, status);
        if (administratives.isEmpty()) {
            throw new EntityNotFoundException("No se encontro registro de " + administrativeRole + " asociados al estado de "+ status);    
        }
        return mapAdministrativesByStatus(status, administratives);
    }

    @Override
    public AdmistrativeResponseDto findAdminById(Long idEmployee) {
        Administrative administrative = administrativeRepository.findById(idEmployee)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro administrativo asociado al Id " + idEmployee + " en el sistema"));
        return administrativeMapper.toDto(administrative);
    }

    @Override
    public AdmistrativeResponseDto findAdminByDocumentNumber(String documentNumber) {
        Administrative administrative = administrativeRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro administrativo asociado al numero de documento " + documentNumber));
        return administrativeMapper.toDto(administrative);
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email) {
        Administrative administrative = validateInfo(idEmployee);
        if (administrative.getEmail().equals(email)) {
            throw new IllegalArgumentException(
                    "El email " + email + " ya se encuentra vinculado al administrativo " + idEmployee);
        }
        employeeServi.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(long idEmployee, String phoneNumber) {
        Administrative administrative = validateInfo(idEmployee);
        if (administrative.getPhoneNumber().equals(phoneNumber)) {
            throw new IllegalArgumentException(
                    "El telefono " + phoneNumber + " ya se encuentra vinculado al administrativo " + idEmployee);
        }
        employeeServi.updateNumberPhone(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType) {
        Administrative administrative = validateInfo(idEmployee);
        if (administrative.getContractType().equals(contractType)) {
            throw new IllegalArgumentException(
                    "El contrato " + contractType + " ya se encuentra vinculado al administrativo " + idEmployee);
        }
        employeeServi.updateContractType(idEmployee, contractType);
    }

    @Override
    @Transactional
    public void updateRole(Long idEmployee, AdministrativeRoles admiRoles) {
        Administrative administrative = validateInfo(idEmployee);
        if (administrative.getAdministrativeRoles().equals(admiRoles)) {
            throw new IllegalArgumentException(
                    "El area de trabajo " + admiRoles + " ya se encuentra vinculado al administrativo " + idEmployee);
        }
        administrativeRepository.updateRole(idEmployee, admiRoles);
    }

    @Override
    public void delete(Long idEmployee, String deleteAt, String reason) {
        Administrative administrative = validateInfo(idEmployee);
        if (administrative.getStatus() == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("El empleado administrativo "+ idEmployee + " ya está eliminado del sistema");
        }
        administrative.setStatus(EmployeeStatus.DELETED);
        ActionInformation removal = new ActionInformation();
        removal.setEmployee(administrative);
        removal.setDeletedAt(LocalDateTime.now());    
        removal.setDeletedBy(deleteAt);
        removal.setReason(reason);
        administrative.getActionInformations().add(removal);
        employeeRepo.save(administrative);
    }

    @Override
    public void suspended(Long idEmployee, String deleteBy, String reason){
        Administrative administrative = validateInfo(idEmployee);
        if (administrative.getStatus() == EmployeeStatus.SUSPENDED) {
            throw new IllegalArgumentException("El empleado administrativo"+ idEmployee + " ya se encuentra suspendido del sistema");
        }
        administrative.setStatus(EmployeeStatus.SUSPENDED);
        ActionInformation removal = new ActionInformation();
        removal.setEmployee(administrative);
        removal.setDeletedAt(LocalDateTime.now());
        removal.setDeletedBy(deleteBy);
        removal.setReason(reason);
        administrative.getActionInformations().add(removal);
        employeeRepo.save(administrative);
    }


    private Administrative validateInfo(Long idEmployee) {
        Administrative administrative = administrativeRepository.findById(idEmployee)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro administrativo asociado al id " + idEmployee));

        if (administrative.getStatus() == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("El administrativo " + idEmployee + " ya se encuentra desahabilitado permanentemente del sistema");
        }
        return administrative;
    }

    private List<?> mapAdministrativesByStatus(EmployeeStatus status, List<Administrative> admistratives) {
        return switch (status) {
            case ACTIVE, PROCESS -> admistratives.stream()
                .map(administrativeMapper::toDto)
                .toList();
            case SUSPENDED, DELETED -> admistratives.stream()
                .map(administrativeMapper::toDisabledDto)
                .toList();
        };
    }

}
