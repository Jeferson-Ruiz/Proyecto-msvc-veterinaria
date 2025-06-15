package com.jr.sav_msvc_patient_admission.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.jr.sav_msvc_patient_admission.dto.PetDto;
import com.jr.sav_msvc_patient_admission.mapper.PetMapper;
import com.jr.sav_msvc_patient_admission.models.Pet;
import com.jr.sav_msvc_patient_admission.repositories.PetsRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PetServiceImpl implements PetService{

    private final PetsRepository petsRepository;
    private final PetMapper petMapper;

    public PetServiceImpl(PetsRepository petsRepository, PetMapper petMapper) {
        this.petsRepository = petsRepository;
        this.petMapper = petMapper;
    }

    @Override
    public Optional<PetDto> savePet(PetDto petDto){
        if (petsRepository.findByNameAndOwnerNumber(petDto.getName(), petDto.getOwnerNumber()).isPresent()) {
            return Optional.empty();
        }
        Pet entity = petMapper.toEntity(petDto);
        entity.setDateOfRecording(LocalDate.now());
        Pet savedPet = petsRepository.save(entity);
        return Optional.of(petMapper.toDto(savedPet));
    }

    @Override
    public List<PetDto> findAllPets(){
        return petsRepository.findAll().stream()
            .map(petMapper::toDto).toList();
    }

    @Override
    public Optional<PetDto> findPetById(Long idPet){
        Optional<Pet> optPet = petsRepository.findById(idPet);
        if (optPet.isEmpty()) {
            return Optional.empty();
        }
        PetDto dto = petMapper.toDto(optPet.get());
        return Optional.of(dto);
    }

    @Override 
    public Optional<PetDto> findByNameAndOwnerNumber(String name, Long ownerNumber){
        Optional<Pet> optPet = petsRepository.findByNameAndOwnerNumber(name, ownerNumber);
        if (optPet.isEmpty()) {
            return Optional.empty();
        }
        PetDto dto = petMapper.toDto(optPet.get());
        return Optional.of(dto);
    }

    @Override
    public void deletePetById(Long idPet){
        if (petsRepository.findById(idPet).isEmpty()) {
            throw new EntityNotFoundException();
        }
        petsRepository.deleteById(idPet);
    }
}