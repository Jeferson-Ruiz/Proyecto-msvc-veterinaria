package com.jeferson.msvc_sav_workstaff.repositories;

import com.jeferson.msvc_sav_workstaff.models.Vet;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VetRepository extends CrudRepository<Vet, Long > {

    @Query("SELECT a FROM a Vet WHERE a.documentNumber =:documentNumber")
    Optional<Vet> findByDocumentNumber(@Param("documentNumber") String documentNumber);

}
