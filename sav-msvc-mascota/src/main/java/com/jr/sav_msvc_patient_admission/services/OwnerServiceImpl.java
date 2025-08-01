package com.jr.sav_msvc_patient_admission.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jr.sav_msvc_patient_admission.dto.OwnerDto;
import com.jr.sav_msvc_patient_admission.mapper.OwnerMapper;
import com.jr.sav_msvc_patient_admission.models.Owner;
import com.jr.sav_msvc_patient_admission.repositories.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }

    @Override
    public List<OwnerDto> findAllOwners(){
        return ownerRepository.findAll().stream()
        .map(ownerMapper::toDto).toList();
    }

    @Override
    public Optional<OwnerDto> findOwnerById(Long idOwner){
        Owner owner = ownerRepository.findById(idOwner).orElseThrow(() -> new EntityNotFoundException("El propietario con ID " + idOwner + " no fue encontrado"));
        return Optional.of(ownerMapper.toDto(owner));
    }

    @Override
    public Optional<OwnerDto> findOwnerByDocumentNumber(Long documentNumber){
        Owner owner = ownerRepository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new EntityNotFoundException("El propietario con el N° documento " + documentNumber + " no fue encontrado"));
        
        return Optional.of(ownerMapper.toDto(owner));
    }

    @Override
    public Optional<OwnerDto> saveOwner(OwnerDto ownerDto){
        if (ownerRepository.findByDocumentNumber(ownerDto.getDocumentNumber()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un propietario con el número de documento " + ownerDto.getDocumentNumber());
        }

        Owner entity = ownerMapper.toEntity(ownerDto);
        entity.setDateOfRecording(LocalDate.now());
        Owner savedOwner = ownerRepository.save(entity);
        return Optional.of(ownerMapper.toDto(savedOwner));
    }

    @Override
    public void deleteOwnerById(Long idOwner){
        Owner owner = ownerRepository.findById(idOwner)
            .orElseThrow(() -> new EntityNotFoundException("El propietario con ID " + idOwner + " no fue encontrado"));
        
        ownerRepository.delete(owner);
    }

    @Override
    @Transactional
    public void updatePhoneNumber(Long idOwner, Long phoneNumber){
        if (ownerRepository.findById(idOwner).isEmpty()) {
            throw new EntityNotFoundException("No se puede actualizar. Propietario con ID " + idOwner + " no existe.");
        }
        ownerRepository.updatePhoneNumber(idOwner, phoneNumber);        
    }

    @Override
    @Transactional
    public void updateEmail(Long idOwner, String email){
        if (ownerRepository.findById(idOwner).isEmpty()) {
            throw new EntityNotFoundException("No se puede actualizar. Propietario con ID " + idOwner + " no existe.");
        }
        ownerRepository.updateEmail(idOwner, email);
    }
}