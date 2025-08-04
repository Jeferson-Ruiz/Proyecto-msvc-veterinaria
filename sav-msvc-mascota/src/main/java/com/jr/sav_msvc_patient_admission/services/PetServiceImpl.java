package com.jr.sav_msvc_patient_admission.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.jr.sav_msvc_patient_admission.dto.PetDto;
import com.jr.sav_msvc_patient_admission.dto.PetOwnerResponseDto;
import com.jr.sav_msvc_patient_admission.dto.PetResponseDto;
import com.jr.sav_msvc_patient_admission.mapper.PetMapper;
import com.jr.sav_msvc_patient_admission.models.Owner;
import com.jr.sav_msvc_patient_admission.models.Pet;
import com.jr.sav_msvc_patient_admission.repositories.OwnerRepository;
import com.jr.sav_msvc_patient_admission.repositories.PetsRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PetServiceImpl implements PetService{

    private final PetsRepository petsRepository;
    private final PetMapper petMapper;
    private final OwnerRepository ownerRepository;

    public PetServiceImpl(PetsRepository petsRepository, PetMapper petMapper, OwnerRepository ownerRepository) {
        this.petsRepository = petsRepository;
        this.petMapper = petMapper;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public PetOwnerResponseDto savePet(PetDto petDto){

        Owner owner = ownerRepository.findByDocumentNumber(petDto.getDocumentNumber())
        .orElseThrow(() -> new EntityNotFoundException("No se puede registrar la mascota por que no se encontró el propietario con el N° identificacion " + petDto.getDocumentNumber()));

        Pet entity = petMapper.toEntity(petDto);
        entity.setOwner(owner);
        entity.setDateOfRecording(LocalDate.now());

        Pet savedPet = petsRepository.save(entity);
        return petMapper.toResponseDto(savedPet);
    }

    @Override
    public List<PetResponseDto> findAllPets(){
        List<Pet> pets = petsRepository.findAll();
        return pets.stream().map(petMapper::toResponsePetDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<PetOwnerResponseDto> findAllPetsWithOwners(){
        List<Pet> pets = petsRepository.findAll();
        return pets.stream().map(petMapper::toResponseDto)
            .collect(Collectors.toList());
    }

    @Override
    public PetResponseDto findPetById(Long idPet){
        Pet pet = petsRepository.findById(idPet)
            .orElseThrow(() -> new EntityNotFoundException("La mascota con ID " + idPet + " no fue encontrado"));
        return petMapper.toResponsePetDto(pet);
    }

    @Override
    public PetOwnerResponseDto findByNameAndOwnerNumber(String name, Long ownerNumber){
        Pet pet = petsRepository.findByNameAndOwnerNumber(name, ownerNumber)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro la mascota " + name + " asociado al propipeatio "+ ownerNumber));
        
        return (petMapper.toResponseDto(pet));
    }

    @Override
    public List<PetResponseDto> findPetsByOwner(Long documentNumber){
        List<Pet> pets = petsRepository.findPetsByOwnerDocument(documentNumber);
        if (pets.isEmpty()) {
            throw new EntityNotFoundException("No existen mascotas registradas al propietario "+ documentNumber); 
        }
        return pets.stream().map(petMapper::toResponsePetDto).toList();
    }

    @Override
    public void deletePetById(Long idPet){
        Pet pet = petsRepository.findById(idPet)
            .orElseThrow(() -> new EntityNotFoundException("El propietario con ID " + idPet + " no fue encontrado"));
        petsRepository.delete(pet);
    }
}