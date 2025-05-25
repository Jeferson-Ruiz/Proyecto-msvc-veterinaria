package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryDto;
import com.jeferson.msvc_sav_workstaff.mapper.AuxiliaryMapper;
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;
import com.jeferson.msvc_sav_workstaff.repositories.AuxiliaryRepository;
import com.jeferson.msvc_sav_workstaff.repositories.EmployeeRespository;

@Service
public class AuxiliaryServiceImpl implements AuxiliaryService {

    private final AuxiliaryRepository auxRepository;
    private final AuxiliaryMapper auxMapper;
    private final EmployeeRespository empRepositorio;

    public AuxiliaryServiceImpl(AuxiliaryRepository auxRepository,
            AuxiliaryMapper auxMapper,
            EmployeeRespository empRepositorio) {
        this.auxRepository = auxRepository;
        this.auxMapper = auxMapper;
        this.empRepositorio = empRepositorio;
    }

    public Optional<Auxiliary> saveAuxiliary(AuxiliaryDto auxiliaryDto) {
        if (empRepositorio.findByDocumentNumber(auxiliaryDto.getDocumentNumber()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(auxRepository.save(auxMapper.toEntity(auxiliaryDto)));
    }

}