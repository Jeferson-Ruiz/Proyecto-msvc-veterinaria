package com.jeferson.msvc_sav_workstaff.repositories;

import com.jeferson.msvc_sav_workstaff.models.Vet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VetRepository extends JpaRepository<Vet, Long > {

    @Query("SELECT v FROM Vet v WHERE v.documentNumber =:documentNumber")
    Optional<Vet> findByDocumentNumber(@Param("documentNumber") String documentNumber);

}
