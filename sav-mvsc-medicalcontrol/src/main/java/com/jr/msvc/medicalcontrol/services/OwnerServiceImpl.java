package com.jr.msvc.medicalcontrol.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jr.msvc.medicalcontrol.dto.owner.OwnerRequestDto;
import com.jr.msvc.medicalcontrol.dto.owner.OwnerResponseDto;
import com.jr.msvc.medicalcontrol.mapper.OwnerMapper;
import com.jr.msvc.medicalcontrol.models.Owner;
import com.jr.msvc.medicalcontrol.models.Pet;
import com.jr.msvc.medicalcontrol.repositories.OwnerRepository;
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
    public OwnerResponseDto saveOwner(OwnerRequestDto ownerDto){
        boolean exists = ownerRepository.findByDocumentNumber(ownerDto.getDocumentNumber()).isPresent();

        if (exists) {
        throw new RuntimeException("Ya existe un propietario asociado al numero de documento " + ownerDto.getDocumentNumber());
        }
        Owner owner = ownerMapper.toEntity(ownerDto);
        owner.setDateOfRecording(LocalDate.now());
        owner.setActive(true);
        return ownerMapper.toDto(ownerRepository.save(owner));
    }

    @Override
    public List<OwnerResponseDto> findAllByStatus(boolean status){

        List<Owner> owners = ownerRepository.findAllOwnersByStatus(status);

        if (status == false && owners.isEmpty()) {
            throw new EntityNotFoundException("No se encuentran propietarios desahabilitados en el sistema");
        } else if (status == true && owners.isEmpty()) {
            throw new EntityNotFoundException("No se encuentran propietarios en el sistema");
        }

        return owners.stream()
        .map(ownerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public OwnerResponseDto findOwnerByDocumentNumber(String documentNumber){
        Owner owner = ownerRepository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new EntityNotFoundException("El propietario con el N° documento " + documentNumber + " no fue encontrado en el sistema"));
        
        return ownerMapper.toDto(owner);
    }

    @Override
    @Transactional
    public void disableOwnerByDocument(String document) throws IllegalAccessException{
        Owner owner = validInfo(document);

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
    public void updatePhoneNumber(String document, String phoneNumber){
        Owner owner = validInfo(document);
        if (owner.getPhoneNumber().equals(phoneNumber)) {
            throw new IllegalArgumentException("El numero "+ phoneNumber + " ya se encuentra asocioado al propietario "+ owner.getName() );
        }
        owner.setPhoneNumber(phoneNumber);
        ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public void updateEmail(String document, String email){
        Owner owner = validInfo(document);
        if (owner.getEmail().equals(email)) {
            throw new IllegalArgumentException("El email "+ email + " ya se encuentra asocioado al propietario "+ owner.getName());
        }
        owner.setEmail(email);
        ownerRepository.save(owner);
    }

    private Owner validInfo(String document){
        Owner owner = ownerRepository.findByDocumentNumber(document)
            .orElseThrow( () -> new EntityNotFoundException("No se encontro propietarias asociado al documento "+ document + " en el sistema"));

        if (!owner.getActive()) {
            throw new IllegalArgumentException("El propietario se encuentra desahabilitado del sistema");
        }
        return owner;
    }

}
