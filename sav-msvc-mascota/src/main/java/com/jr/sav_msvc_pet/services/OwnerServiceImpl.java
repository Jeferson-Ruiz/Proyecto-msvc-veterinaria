package com.jr.sav_msvc_pet.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jr.sav_msvc_pet.dto.OwnerDto;
import com.jr.sav_msvc_pet.mapper.OwnerMapper;
import com.jr.sav_msvc_pet.models.Owner;
import com.jr.sav_msvc_pet.repositories.OwnerRepository;
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
        Optional<Owner> optOwner = ownerRepository.findById(idOwner);
        if (optOwner.isEmpty()) {
            return Optional.empty();
        }
        return ownerRepository.findById(idOwner).map(ownerMapper::toDto);
    }

    @Override
    public Optional<OwnerDto> findOwnerByDocumentNumber(Long documentNumber){
        if (ownerRepository.findByDocumentNumber(documentNumber).isEmpty()) {
            return Optional.empty();
        }
        return ownerRepository.findByDocumentNumber(documentNumber).map(ownerMapper::toDto);
    }

    @Override
    public Optional<Owner> saveOwner(OwnerDto ownerDto){
        if (ownerRepository.findByDocumentNumber(ownerDto.getDocumentNumber()).isPresent()) {
            return Optional.empty();
        }
        Owner entity = ownerMapper.toEntity(ownerDto);
        entity.setDateOfRecording(LocalDate.now());
        return Optional.of(ownerRepository.save(entity));
    }

    @Override
    public void deleteOwnerById(Long idOwner){
        if (ownerRepository.findById(idOwner).isEmpty()) {
            throw new EntityNotFoundException();
        }
        ownerRepository.deleteById(idOwner);
    }

    @Override
    @Transactional
    public void updatePhoneNumber(Long idOwner, Long phoneNumber){
        if (ownerRepository.findById(idOwner).isEmpty()) {
            throw new EntityNotFoundException();
        }
        ownerRepository.updatePhoneNumber(idOwner, phoneNumber);        
    }

    @Override
    @Transactional
    public void updateEmail(Long idOwner, String email){
        if (ownerRepository.findById(idOwner).isEmpty()) {
            throw new EntityNotFoundException();
        }
        ownerRepository.updateEmail(idOwner, email);
    }
}