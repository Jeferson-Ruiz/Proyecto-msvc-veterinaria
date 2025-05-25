package com.jeferson.msvc_sav_workstaff.controllers;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryDto;
import com.jeferson.msvc_sav_workstaff.mapper.AuxiliaryMapper;
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;
import com.jeferson.msvc_sav_workstaff.services.AuxiliaryService;

@RestController
@RequestMapping("api/sav/employee/aux")
public class AuxiliaryController {

    private final AuxiliaryService auxService;
    private final AuxiliaryMapper auxMapper;

    public AuxiliaryController(AuxiliaryService auxService, AuxiliaryMapper auxMapper) {
        this.auxService = auxService;
        this.auxMapper = auxMapper;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoAuxiliary(@RequestBody AuxiliaryDto auxiliaryDto) {
        Optional<Auxiliary> optAuxiliary = auxService.saveAuxiliary(auxiliaryDto);

        if (optAuxiliary.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El usuario ya existe en el sistema");
        }
        Auxiliary auxiRegist = optAuxiliary.get();
        AuxiliaryDto auxResponse = auxMapper.toDto(auxiRegist);
        return ResponseEntity.status(HttpStatus.CREATED).body(auxResponse);
    }

}
