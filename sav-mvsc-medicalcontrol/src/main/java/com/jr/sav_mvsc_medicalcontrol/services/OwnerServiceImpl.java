package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jr.sav_mvsc_medicalcontrol.dto.OwnerRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.OwnerResponseDto;
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
    public List<OwnerResponseDto> findAllDisabeOwners(){
        List<Owner> owners = ownerRepository.findAllDisableOwners();
        if (owners.isEmpty()) {
            throw new EntityNotFoundException("No se encuentran propietarios desahabilitados en el sistema");
        }
        return owners.stream()
        .map(ownerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OwnerResponseDto> findAllActiveOwners(){
        List<Owner> owners =  ownerRepository.findAllActiveOwners();
        if (owners.isEmpty()) {
            throw new EntityNotFoundException("No se encuentran propietarios registrados en el sistema");            
        }
        return owners.stream()
        .map(ownerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public OwnerResponseDto findOwnerById(Long idOwner){
        Owner owner = ownerRepository.findById(idOwner)
            .orElseThrow(() -> new EntityNotFoundException("El propietario con ID " + idOwner + " no fue encontrado en el sistema"));
        return ownerMapper.toDto(owner);
    }

    @Override
    public OwnerResponseDto findOwnerByDocumentNumber(String documentNumber){
        Owner owner = ownerRepository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new EntityNotFoundException("El propietario con el N° documento " + documentNumber + " no fue encontrado en el sistema"));
        
        return ownerMapper.toDto(owner);
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
    @Transactional
    public void disableOwnerById(Long idOwner) throws IllegalAccessException{
        Owner owner = validInfo(idOwner);

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
    public void updatePhoneNumber(Long idOwner, String phoneNumber){
        Owner owner = validInfo(idOwner);
        if (owner.getPhoneNumber().equals(phoneNumber)) {
            throw new IllegalArgumentException("El numero "+ phoneNumber + " ya se encuentra asocioado al Id "+ idOwner);
        }
        ownerRepository.updatePhoneNumber(idOwner, phoneNumber);        
    }

    @Override
    @Transactional
    public void updateEmail(Long idOwner, String email){
        Owner owner = validInfo(idOwner);
        if (owner.getEmail().equals(email)) {
            throw new IllegalArgumentException("El email "+ email + " ya se encuentra asocioado al Id "+ idOwner);
        }

        ownerRepository.updateEmail(idOwner, email);
    }


    private Owner validInfo(Long id){
        Owner owner = ownerRepository.findById(id)
            .orElseThrow( () -> new EntityNotFoundException("No se encuentran propietarios registrados en el sistema"));

        if (!owner.getActive()) {
            throw new IllegalArgumentException("El propietario se encuentra desahabilitado del sistema");
        }
        return owner;
    }

}
