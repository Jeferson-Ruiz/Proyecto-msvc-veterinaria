package com.jr.msvc.medicalcontrol.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.msvc.medicalcontrol.models.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long>{
    
    Optional<Owner> findByDocumentNumber(String documentNumber);

    @Modifying
    @Query("update Owner set phoneNumber=:phoneNumber where idOwner=:idOwner")
    void updatePhoneNumber(@Param("idOwner") Long idOwner, @Param("phoneNumber") String phoneNumber);

    @Modifying
    @Query("update Owner set email=:email where idOwner=:idOwner")
    void updateEmail(@Param("idOwner") Long idOwner, @Param("email") String email);

    @Query("SELECT o FROM Owner o Where o.active = true")
    public List<Owner> findAllActiveOwners();

    @Query("SELECT o FROM Owner o Where o.active = false")
    public List<Owner> findAllDisableOwners();

}