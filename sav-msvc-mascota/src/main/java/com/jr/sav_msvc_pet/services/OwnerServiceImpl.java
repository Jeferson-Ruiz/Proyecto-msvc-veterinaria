package com.jr.sav_msvc_pet.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jr.sav_msvc_pet.models.Owner;
import com.jr.sav_msvc_pet.repositories.OwnerRepository;

@Service
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<Owner> findAllOwners(){
        return (List<Owner>) ownerRepository.findAll();
    }

    @Override
    public Optional<Owner> findOwnerById(Long idOwner){
        return ownerRepository.findById(idOwner);
    }

    @Override
    public Optional<Owner> findOwnerByDocumentNumber(Long documentNumber){
        return ownerRepository.findByDocumentNumber(documentNumber);
    }

    @Override
    public Owner saveOwner(Owner owner){
        return ownerRepository.save(owner);
    }

    @Override
    public void deleteOwnerById(Long idOwner){
        ownerRepository.deleteById(idOwner);
    }

    @Override
    @Transactional
    public void updatephoneNumber(Long idOwner, Long phoneNumber){
        ownerRepository.updatephoneNumber(idOwner, phoneNumber);
    }

    @Override
    @Transactional
    public void updateEmail(Long idOwner, String email){
        updateEmail(idOwner, email);
    }


    



}
