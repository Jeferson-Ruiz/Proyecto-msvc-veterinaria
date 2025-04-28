package com.jr.sav_msvc_pet.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.jr.sav_msvc_pet.models.Pet;
import com.jr.sav_msvc_pet.repositories.PetsRepository;

@Service
public class PetServiceImpl implements PetService{

    private final PetsRepository petsRepository;

    public PetServiceImpl(PetsRepository petsRepository) {
        this.petsRepository = petsRepository;
    }

    @Override
    public Pet savePet(Pet pet){
        return petsRepository.save(pet);
    }

    @Override
    public List<Pet> findAllPets(){
        return (List<Pet>) petsRepository.findAll();
    }

    @Override
    public Optional<Pet> findPetById(Long idPet){
        return petsRepository.findById(idPet);
    }

    @Override 
    public Optional<Pet> findByNameAndOwerId(String name, Long owerId){
        return petsRepository.findByNameAndOwerId(name, owerId);
    }

    @Override
    public void deletePetById(Long idPet){
        petsRepository.deleteById(idPet);
    }
}
