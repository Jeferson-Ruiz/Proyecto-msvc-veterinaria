package com.jr.sav_msvc_pet.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.jr.sav_msvc_pet.models.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Optional<Owner> findByDocumentNumber(Long documentNumber);

    @Modifying
    @Query("update Owner set phoneNumber=:phoneNumber where idOwner=:idOwner")
    void updatePhoneNumber(@Param("idOwner") Long idOwner, @Param("phoneNumber") Long phoneNumber);

    @Modifying
    @Query("update Owner set email=:email where idOwner=:idOwner")
    void updateEmail(@Param("idOwner") Long idOwner, @Param("email") String email);

}