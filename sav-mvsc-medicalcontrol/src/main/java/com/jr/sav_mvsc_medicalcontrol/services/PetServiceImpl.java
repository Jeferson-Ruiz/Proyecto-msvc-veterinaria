package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetResponseDisableDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetWithOwnerResponseDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetResponseDto;
import com.jr.sav_mvsc_medicalcontrol.mapper.PetMapper;
import com.jr.sav_mvsc_medicalcontrol.models.Owner;
import com.jr.sav_mvsc_medicalcontrol.models.Pet;
import com.jr.sav_mvsc_medicalcontrol.models.RemovalInformation;
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
    public PetWithOwnerResponseDto savePet(PetRequestDto petDto) {

        Owner owner = ownerRepository.findByDocumentNumber(petDto.getDocumentNumber())
            .orElseThrow(() -> new EntityNotFoundException(
                "No se puede registrar la mascota porque no se encontró el propietario con el N° de identificación "
                + petDto.getDocumentNumber()));

        if (!owner.getActive()) {
            throw new IllegalArgumentException(
                "No se puede registrar una mascota porque el propietario con N° "
                + petDto.getDocumentNumber() + " se encuentra desactivado en el sistema");
        }

        // Validación de duplicados
        if (petRepository.existByNameAndOwnerId(petDto.getName(), owner.getIdOwner())) {
            throw new IllegalArgumentException(
                "El propietario con N° " + petDto.getDocumentNumber()
                + " ya tiene registrada una mascota activa con el nombre: " + petDto.getName());
        }

        Pet entity = petMapper.toEntity(petDto);
        entity.setOwner(owner);
        entity.setDateOfRecording(LocalDate.now());
        entity.setActive(true);

        Pet savedPet = petRepository.save(entity);
        return petMapper.toResponseDto(savedPet);
    }
    
    @Override
    public List<PetResponseDisableDto> findAllDisablePets(){
        List<Pet> pets = petRepository.findAllDisablePets();
        if (pets.isEmpty()) {
            throw new EntityNotFoundException("No se encuentran mascotas descativadas en el sistema");
        }
        return pets.stream().map(petMapper::toResponseDisableDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<PetWithOwnerResponseDto> findAllActivesPets(){
        List<Pet> pets = petRepository.findAllActivePets();
        if (pets.isEmpty()) {
            throw new EntityNotFoundException("No se encuentran mascotas registradas en el sistema");
        }
        return pets.stream().map(petMapper::toResponseDto)
            .collect(Collectors.toList());
    }
    
    @Override
    public PetResponseDto findPetById(Long idPet){
        Pet pet = petRepository.findById(idPet)
            .orElseThrow(() -> new EntityNotFoundException("La mascota con ID " + idPet + " no fue encontrado en el sistema"));
        return petMapper.toResponsePetDto(pet);
    }

    @Override
    public PetWithOwnerResponseDto findByNameAndOwnerNumber(String name, String ownerNumber){
        Pet pet = petRepository.findByNameAndOwnerNumber(name, ownerNumber)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro la mascota " + name + " asociado al propipeatio "+ ownerNumber));
        
        return (petMapper.toResponseDto(pet));
    }

    @Override
    public List<PetResponseDto> findPetsByOwnerDocument(String documentNumber){
        List<Pet> pets = petRepository.findPetsByOwnerDocument(documentNumber);
        if (pets.isEmpty()) {
            throw new EntityNotFoundException("No existen mascotas registradas al documento del propietario "+ documentNumber); 
        }
        return pets.stream().map(petMapper::toResponsePetDto).toList();
    }

    @Override
    public List<PetWithOwnerResponseDto> findAllBySpecie(String specie){
        List<Pet> pets = petRepository.findBySpecie(specie);
        if (pets.isEmpty()) {
            throw new EntityNotFoundException("No se existen mascotas asociada a la especie de "+ specie);
        }
        return pets.stream()
            .map(petMapper::toResponseDto)
            .toList();
    }

    @Override
    @Transactional
    public void disablePetById(Long idPet, String deleteBy, String reason){
        Pet pet = petRepository.findById(idPet)
            .orElseThrow(() -> new EntityNotFoundException("No existe mascota registrada con el id " + idPet));
        if (!pet.getActive()) {
            throw new IllegalArgumentException("La mascota asociada al Id "+ idPet +" ya se encuentra desactivada del sistema");
        }
        pet.setActive(false);

        RemovalInformation removalInfo = new RemovalInformation();
        removalInfo.setPet(pet);
        removalInfo.setDeletedAt(LocalDateTime.now());
        removalInfo.setDeletedBy(deleteBy);
        removalInfo.setReason(reason);
        pet.getRemovalInformation().add(removalInfo);
        petRepository.save(pet);
    }
}
