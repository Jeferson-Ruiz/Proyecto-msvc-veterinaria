package com.jeferson.msvc.workstaff.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.jeferson.msvc.workstaff.models.ActionInformation;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.jeferson.msvc.workstaff.dto.VetRequestDto;
import com.jeferson.msvc.workstaff.dto.VetResponseDto;
import com.jeferson.msvc.workstaff.mapper.VetMapper;
import com.jeferson.msvc.workstaff.models.Vet;
import com.jeferson.msvc.workstaff.models.VetRoles;
import com.jeferson.msvc.workstaff.repositories.EmployeeRepository;
import com.jeferson.msvc.workstaff.repositories.VetRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VetServiceImpl extends CodeEmployeeService implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    public VetServiceImpl(VetRepository vetRepository, VetMapper vetMapper, EmployeeRepository employeeRepo) {
        super(employeeRepo);
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
    }

    @Override
    public VetResponseDto saveVet(VetRequestDto vetDto) {
        if (employeeRepo.findByDocumentNumber(vetDto.getDocumentNumber()).isPresent()) {
            throw new RuntimeException("El documento "+ vetDto.getDocumentNumber()+" ya se encuentra vinculado a un empleado");
        }
        Vet entity = vetMapper.toEntity(vetDto);
        if (vetRepository.existsByProfessionalCard(entity.getProfessionalCard()) && !entity.getProfessionalCard().isEmpty()) {
            throw new IllegalArgumentException("La tarjeta profesional "+ entity.getProfessionalCard() +" ya se encuentra asociado a un veterinario");
        }

        String code = generateEmployeeCode("VT", entity);
        entity.setEmployeeCode(code);

        entity.setRegistrationDate(LocalDate.now());
        entity.setStatus(EmployeeStatus.PROCESS);
        Vet saved = vetRepository.save(entity);
        return vetMapper.toDto(saved);
    }

    @Override
    public List<VetResponseDto> findAllByStatus(EmployeeStatus status){
        validateStatus(status);
        List<Vet> vets = vetRepository.findAllByStatus(status);
        if (vets.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron empleados vinculado al cargo de veterinarios");
        }
        return mapVetsByStatus(status, vets);
    }

    @Override
    public List<VetResponseDto> findAllByRoleAndStatus(VetRoles vetRole, EmployeeStatus status){
        validateStatus(status);
        List<Vet> vets = vetRepository.findAllByRole(vetRole, status);
        if (vets.isEmpty()) {
            throw new EntityNotFoundException("No se encontro registro de " + vetRole + " asociados al estado de "+ status);
        }
        return mapVetsByStatus(status, vets);
    }

    @Override
    public VetResponseDto findByCode(String employeeCode){
        Vet vet = vetRepository.findByCode(employeeCode)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro veterinario asociado al codigo "+ employeeCode+ " en el sistema"));
        return mapVetByStatus(vet);
    }

    @Override
    public VetResponseDto findAdminByDocumentNumber(String documentNumber){
        Vet vet = vetRepository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro veterianio asociado al numero de documento " + documentNumber));
        return mapVetByStatus(vet);
    }

    @Override
    @Transactional
    public void updateEmail (String employeeCode, String email){
        Vet vet = validateInfo(employeeCode);
        if (vet.getEmail().equals(email)) {
            throw new IllegalArgumentException("El email "+ email+ " ya se encuentra asociado al veterinario "+ vet.getName());
        }
        vet.setEmail(email);
        vetRepository.save(vet);
    }

    @Override
    @Transactional
    public void updateNumberPhone(String employeeCode, String phoneNumber){
        Vet vet = validateInfo(employeeCode);
        if (vet.getPhoneNumber().equals(phoneNumber)) {
            throw new IllegalArgumentException("El telefono "+ phoneNumber + " ya se encuentra asociado al veterinario "+ vet.getName());
        }
        vet.setPhoneNumber(phoneNumber);
        vetRepository.save(vet);
    }

    @Override
    @Transactional
    public void updateContractType(String employeeCode, ContractType contractType){
        Vet vet = validateInfo(employeeCode);
        if (vet.getContractType().equals(contractType)) {
            throw new IllegalArgumentException("El contrato "+ contractType + " ya se encuentra asociado al veterinario "+ vet.getName());
        }        
        vet.setContractType(contractType);
        vetRepository.save(vet);
    }

    @Override
    @Transactional
    public void updateRole(String employeeCode, VetRoles vetRoles){
        Vet vet = validateInfo(employeeCode);
        if (vet.getVetRoles().equals(vetRoles)) {
            throw new IllegalArgumentException("El area de trabajo "+ vetRoles + " ya se encuentra asociado al veterinario "+ vet.getName());
        }
        vet.setVetRoles(vetRoles);
        vetRepository.save(vet);
    }

    @Override
    public void updateEmployeeStatus(String employeeCode, EmployeeStatus status){
        Vet vet = validateInfo(employeeCode);
        validateUpdateStatus(status, vet);
        vet.setStatus(status);
        vetRepository.save(vet);
    }

    @Override
    public void suspended(String employeeCode, String deleteBy, String reason){
        Vet vet = validateInfo(employeeCode);
        if (vet.getStatus() == EmployeeStatus.SUSPENDED) {
            throw new EntityNotFoundException("El veterinario "+ vet.getName() + " ya se encuentra suspendido del sistema");
        }
        vet.setStatus(EmployeeStatus.SUSPENDED);
        applyStatusChange(vet, deleteBy, reason);
    }

    @Override
    public void delete(String employeeCode, String deleteBy, String reason){
        Vet vet = validateInfo(employeeCode);
        vet.setStatus(EmployeeStatus.DELETED);
        applyStatusChange(vet, deleteBy, reason);
    }

    //helpers
    private Vet validateInfo(String employeeCode) {
        Vet vet = vetRepository.findByCode(employeeCode)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro veterinario asociado al codigo " + employeeCode));
        if (vet.getStatus() == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("El veterinario " + employeeCode + " se encuentra desactivado permanentemente del sistema");
        }
        return vet;
    }

    private List<VetResponseDto> mapVetsByStatus(EmployeeStatus status, List<Vet> vets){
        return switch (status) {
            case ACTIVE, PROCESS -> vets.stream()
                .map(vetMapper::toDto)
                .toList();
            case DELETED, SUSPENDED -> vets.stream()
                .map(vetMapper::toDisabledDto)
                .map(dto -> (VetResponseDto) dto)
                .toList(); 
        };
    }

    private VetResponseDto mapVetByStatus(Vet vet){
        EmployeeStatus status = vet.getStatus();
        return switch (status) {
            case ACTIVE, PROCESS -> vetMapper.toDto(vet);
            case SUSPENDED, DELETED -> vetMapper.toDisabledDto(vet);
        };
    }

    private void applyStatusChange(Vet vet, String deleteBy, String reason) {
        ActionInformation actionInf = new ActionInformation();
        actionInf.setDeletedAt(LocalDateTime.now());
        actionInf.setDeletedBy(deleteBy);
        actionInf.setReason(reason);
        actionInf.setEmployee(vet);
        vet.getActionInformations().add(actionInf);
        vetRepository.save(vet);
    }

    private EmployeeStatus validateStatus(EmployeeStatus status){
        return status != null ? status : EmployeeStatus.ACTIVE;
    }

    private void validateUpdateStatus(EmployeeStatus status, Vet vet){
        if (status == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("No se puede eliminar un usuario desde el panel de actualizacion de estado");}
        if (status == vet.getStatus()) {
            throw new IllegalArgumentException("El empleado ya se encuentra vinculado al estado de "+ status);}
    }

}