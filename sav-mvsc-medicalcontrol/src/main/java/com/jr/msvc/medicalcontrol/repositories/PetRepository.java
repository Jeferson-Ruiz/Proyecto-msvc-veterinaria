package com.jr.msvc.medicalcontrol.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jr.msvc.medicalcontrol.models.Pet;

public interface PetRepository extends JpaRepository<Pet, Long>{

    @Query("SELECT p FROM Pet p WHERE p.name =:name AND p.owner.documentNumber =:documentNumber")
    Optional<Pet> findByNameAndOwnerNumber(@Param("name") String name, @Param("documentNumber") String documentNumber);

    @Query("SELECT p FROM Pet p WHERE p.petCode=:petCode")
    Optional<Pet> findByPetCode(@Param("petCode") String petCode);

    @Query("SELECT p FROM Pet p WHERE p.owner.documentNumber =:documentNumber")
    List<Pet> findPetsByOwnerDocument(@Param("documentNumber") String documentNumber);

    @Query("SELECT p FROM Pet p WHERE p.active=true")
    List<Pet> findAllPetsByActive();

    @Query("SELECT p FROM Pet p WHERE p.active=false")
    List<Pet> findAllPetsByDisabled();

    @Query("SELECT p FROM Pet p WHERE p.specie =:specie")
    List<Pet> findBySpecie(@Param("specie") String specie);

    @Query("SELECT COUNT(p) FROM Pet p WHERE p.owner.documentNumber=:documentNumber")
    int countPetsByOwnerDocument(@Param("documentNumber") String documentNumber);

    @Query("""
    SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
    FROM Pet p WHERE p.name =:name AND p.owner.documentNumber=:documentNumber AND p.active = true""")
    boolean existByNameAndOwnerDocument(@Param("name") String name, @Param("documentNumber") String documentNumber);

}
