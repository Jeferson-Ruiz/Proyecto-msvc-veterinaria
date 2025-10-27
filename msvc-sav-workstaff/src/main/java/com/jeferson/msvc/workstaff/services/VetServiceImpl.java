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
public class VetServiceImpl implements VetService {

    private final EmployeeRepository employeeRepo;
    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    public VetServiceImpl(VetRepository vetRepository, VetMapper vetMapper, EmployeeRepository employeeRepo) {
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
        this.employeeRepo = employeeRepo;
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
        entity.setRegistrationDate(LocalDate.now());
        entity.setStatus(EmployeeStatus.PROCESS);
        Vet saved = vetRepository.save(entity);
        return vetMapper.toDto(saved);
    }

    @Override
    public List<VetResponseDto> findAllByStatus(EmployeeStatus status){
        validateStatus(status);
        List<Vet> vets = vetRepository.findAllActiveVets(status);
        if (vets.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron empleados vinculado al cargo de veterinarios");
        }
        return mapVetsByStatus(status, vets);
    }

    @Override
    public List<VetResponseDto> findAllByRole(VetRoles vetRole, EmployeeStatus status){
        validateStatus(status);
        List<Vet> vets = vetRepository.findAllByRole(vetRole, status);
        if (vets.isEmpty()) {
            throw new EntityNotFoundException("No se encontro registro de " + vetRole + " asociados al estado de "+ status);
        }
        return mapVetsByStatus(status, vets);
    }

    @Override
    public VetResponseDto findById(Long idEmployee){
        Vet vet = vetRepository.findById(idEmployee)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro veterinario asociado al id "+ idEmployee + " en el sistema"));
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
    public void updateEmail (Long idEmployee, String email){
        Vet vet = validateInfo(idEmployee);
        if (vet.getEmail().equals(email)) {
            throw new IllegalArgumentException("El email "+ email+ " ya se encuentra asociado al veterinario "+ idEmployee);
        }
        employeeRepo.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(Long idEmployee, String phoneNumber){
        Vet vet = validateInfo(idEmployee);
        if (vet.getPhoneNumber().equals(phoneNumber)) {
            throw new IllegalArgumentException("El telefono "+ phoneNumber + " ya se encuentra asociado al veterinario "+ idEmployee);
        }
        employeeRepo.updatePhoneNumber(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType){
        Vet vet = validateInfo(idEmployee);
        if (vet.getContractType().equals(contractType)) {
            throw new IllegalArgumentException("El contrato "+ contractType + " ya se encuentra asociado al veterinario "+ idEmployee);
        }        
        employeeRepo.updateContractType(idEmployee, contractType);
    }

    @Override
    @Transactional
    public void updateRole(Long idEmployee, VetRoles vetRoles){
        Vet vet = validateInfo(idEmployee);
        if (vet.getVetRoles().equals(vetRoles)) {
            throw new IllegalArgumentException("El area de trabajo "+ vetRoles + " ya se encuentra asociado al veterinario "+ idEmployee);
        }
        vetRepository.updateRole(idEmployee, vetRoles);
    }

    @Override
    public void delete(Long idEmployee, String deleteBy, String reason){
        Vet vet = validateInfo(idEmployee);
        vet.setStatus(EmployeeStatus.DELETED);
        applyStatusChange(vet, deleteBy, reason);
    }


    @Override
    public void suspended(Long idEmployee, String deleteBy, String reason){
        Vet vet = validateInfo(idEmployee);
        if (vet.getStatus() == EmployeeStatus.SUSPENDED) {
            throw new EntityNotFoundException("El veterinario "+ vet.getName() + " ya se encuentra suspendido del sistema");
        }
        vet.setStatus(EmployeeStatus.SUSPENDED);
        applyStatusChange(vet, deleteBy, reason);
    }

    @Override
    public void updateEmployeeStatus(Long idEmployee, EmployeeStatus status){
        Vet vet = validateInfo(idEmployee);
        validateUpdateStatus(status, vet);
        vet.setStatus(status);
        vetRepository.save(vet);
    }

    //helpers
    private Vet validateInfo(Long idEmployee) {
        Vet vet = vetRepository.findById(idEmployee)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro veterinario asociado al id " + idEmployee));
        if (vet.getStatus() == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("El veterinario " + idEmployee + " se encuentra desactivado permanentemente del sistema");
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