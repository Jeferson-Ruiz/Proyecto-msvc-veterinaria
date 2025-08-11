package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetOwnerResponseDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetResponseDto;
import com.jr.sav_mvsc_medicalcontrol.mapper.PetMapper;
import com.jr.sav_mvsc_medicalcontrol.models.Owner;
import com.jr.sav_mvsc_medicalcontrol.models.Pet;
import com.jr.sav_mvsc_medicalcontrol.repositories.OwnerRepository;
import com.jr.sav_mvsc_medicalcontrol.repositories.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final OwnerRepository ownerRepository;

    public PetServiceImpl(PetRepository petRepository, PetMapper petMapper, OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public PetOwnerResponseDto savePet(PetDto petDto){

        Owner owner = ownerRepository.findByDocumentNumber(petDto.getDocumentNumber())
        .orElseThrow(() -> new EntityNotFoundException(
            "No se puede registrar la mascota por que no se encontró el propietario con el N° identificacion " + petDto.getDocumentNumber()));

        if (owner.getActive() == false) {
            throw new RuntimeException(
            "No se puede registrar una mascota por el propietario "+ petDto.getDocumentNumber()+" se encuentra desactivado en el sistema");
        }
        Pet entity = petMapper.toEntity(petDto);
        entity.setOwner(owner);
        entity.setDateOfRecording(LocalDate.now());
        entity.setActive(true);

        Pet savedPet = petRepository.save(entity);
        return petMapper.toResponseDto(savedPet);
    }

    @Override
    public List<PetResponseDto> findAllPets(){
        List<Pet> pets = petRepository.findAll();
        return pets.stream().map(petMapper::toResponsePetDto)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<PetOwnerResponseDto> findAllDisablePets(){
        List<Pet> pets = petRepository.findAllDisablePets();
        return pets.stream().map(petMapper::toResponseDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<PetOwnerResponseDto> findAllActivesPets(){
        List<Pet> pets = petRepository.findAllActivePets();
        return pets.stream().map(petMapper::toResponseDto)
            .collect(Collectors.toList());
    }
    
    @Override
    public PetResponseDto findPetById(Long idPet){
        Pet pet = petRepository.findById(idPet)
            .orElseThrow(() -> new EntityNotFoundException("La mascota con ID " + idPet + " no fue encontrado"));
        return petMapper.toResponsePetDto(pet);
    }

    @Override
    public PetOwnerResponseDto findByNameAndOwnerNumber(String name, Long ownerNumber){
        Pet pet = petRepository.findByNameAndOwnerNumber(name, ownerNumber)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro la mascota " + name + " asociado al propipeatio "+ ownerNumber));
        
        return (petMapper.toResponseDto(pet));
    }

    @Override
    public List<PetResponseDto> findPetsByOwner(Long documentNumber){
        List<Pet> pets = petRepository.findPetsByOwnerDocument(documentNumber);
        if (pets.isEmpty()) {
            throw new EntityNotFoundException("No existen mascotas registradas al propietario "+ documentNumber); 
        }
        return pets.stream().map(petMapper::toResponsePetDto).toList();
    }

    @Override
    @Transactional
    public void disablePetById(Long idPet){
        Pet pet = petRepository.findById(idPet)
            .orElseThrow(() -> new EntityNotFoundException("No existe mascota registrada con el id " + idPet));
        pet.setActive(false);
        
        petRepository.save(pet);
    }


}
