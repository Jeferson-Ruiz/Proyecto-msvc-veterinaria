package com.jr.sav_mvsc_medicalcontrol.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.sav_mvsc_medicalcontrol.models.Pet;

public interface PetRepository extends JpaRepository<Pet, Long>{

    @Query("SELECT p FROM Pet p WHERE p.name =:name AND p.owner.documentNumber =:documentNumber")
    Optional<Pet> findByNameAndOwnerNumber(@Param("name") String name, @Param("documentNumber") String documentNumber);

    @Query("SELECT p FROM Pet p WHERE p.owner.documentNumber =:documentNumber")
    List<Pet> findPetsByOwnerDocument(@Param("documentNumber") String documentNumber);

    @Query("SELECT p FROM Pet p WHERE p.active = true")
    List<Pet> findAllActivePets();

    @Query("SELECT p FROM Pet p WHERE p.active = false")
    List<Pet> findAllDisablePets();

    @Query("""
    SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
    FROM Pet p WHERE p.name = :name AND p.owner.idOwner = :idOwner AND p.active = true""")
    boolean existByNameAndOwnerId(@Param("name") String name, @Param("idOwner") Long idOwner);

}
