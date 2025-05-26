package com.jeferson.msvc_sav_workstaff.controllers;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.msvc_sav_workstaff.dto.InternDto;
import com.jeferson.msvc_sav_workstaff.mapper.InternMapper;
import com.jeferson.msvc_sav_workstaff.models.Intern;
import com.jeferson.msvc_sav_workstaff.services.InternService;

@RestController
@RequestMapping("api/sav/employee/intern")
public class InternController {

    private final InternService intService;
    private final InternMapper intMapper;

    public InternController(InternService intService, InternMapper intMapper) {
        this.intService = intService;
        this.intMapper = intMapper;
    }

    public ResponseEntity<?> saveInfoIntern(InternDto internDto) {
        Optional<Intern> optInter = intService.saveIntern(internDto);

        if (optInter.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Usario ya existe en el sistema");
        }
        Intern internResgister = optInter.get();
        InternDto internResponse = intMapper.toDto(internResgister);
        return ResponseEntity.status(HttpStatus.CREATED).body(internResponse);
    }
}
