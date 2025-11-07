package com.jr.msvc.medicalcontrol.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.msvc.medicalcontrol.models.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long>{
    
    Optional<Owner> findByDocumentNumber(String documentNumber);

    @Query("SELECT o FROM Owner o Where o.active=:status")
    public List<Owner> findAllOwnersByStatus(@Param("status") boolean status);

}