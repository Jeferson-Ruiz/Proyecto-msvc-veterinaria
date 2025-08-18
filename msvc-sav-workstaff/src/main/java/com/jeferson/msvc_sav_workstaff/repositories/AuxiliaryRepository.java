package com.jeferson.msvc_sav_workstaff.repositories;

import com.jeferson.msvc_sav_workstaff.models.Auxiliary;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AuxiliaryRepository  extends CrudRepository<Auxiliary, Long> {

    @Query("SELECT a FROM a Auxiliary WHERE a.documentNumber =:documentNumber")
    Optional<Auxiliary> findByDocumentNumber(@Param("documentNumber") String documentNumber);
}
