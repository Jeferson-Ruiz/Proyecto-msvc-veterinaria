package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jr.sav_mvsc_medicalcontrol.dto.OwnerDto;
import com.jr.sav_mvsc_medicalcontrol.mapper.OwnerMapper;
import com.jr.sav_mvsc_medicalcontrol.models.Owner;
import com.jr.sav_mvsc_medicalcontrol.models.Pet;
import com.jr.sav_mvsc_medicalcontrol.repositories.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class OwnerServiceImpl implements OwnerService {

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
    public List<OwnerDto> findAllDisabeOwners(){
        return ownerRepository.findAllDisableOwners().stream()
        .map(ownerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OwnerDto> findAllActiveOwners(){
        return ownerRepository.findAllActiveOwners().stream()
        .map(ownerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public OwnerDto findOwnerById(Long idOwner){
        Owner owner = ownerRepository.findById(idOwner)
            .orElseThrow(() -> new EntityNotFoundException("El propietario con ID " + idOwner + " no fue encontrado"));
        return ownerMapper.toDto(owner);
    }

    @Override
    public OwnerDto findOwnerByDocumentNumber(Long documentNumber){
        Owner owner = ownerRepository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new EntityNotFoundException("El propietario con el N° documento " + documentNumber + " no fue encontrado"));
        
        return ownerMapper.toDto(owner);
    }

    @Override
    public OwnerDto saveOwner(OwnerDto ownerDto){
        boolean exists = ownerRepository.findByDocumentNumber(ownerDto.getDocumentNumber()).isPresent();

        if (exists) {
        throw new RuntimeException("Ya existe un propietario con el número de documento " + ownerDto.getDocumentNumber());
        }
        
        Owner owner = ownerMapper.toEntity(ownerDto);
        owner.setDateOfRecording(LocalDate.now());
        owner.setActive(true);
        Owner savedOwner = ownerRepository.save(owner);
        return ownerMapper.toDto(savedOwner);
    }

    @Override
    @Transactional
    public void disableOwnerById(Long idOwner){
        Owner owner = ownerRepository.findById(idOwner)
            .orElseThrow(() -> new EntityNotFoundException("El propietario con ID " + idOwner + " no fue encontrado"));

        owner.setActive(false);
        if (!owner.getPets().isEmpty()) {
            for(Pet pet : owner.getPets()){
                pet.setActive(false);
            }
        }
        ownerRepository.save(owner);
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
