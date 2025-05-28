package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.jeferson.msvc_sav_workstaff.dto.VetDto;
import com.jeferson.msvc_sav_workstaff.mapper.VetMapper;
import com.jeferson.msvc_sav_workstaff.models.Vet;
import com.jeferson.msvc_sav_workstaff.repositories.VetRepository;

@Service
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final EmployeeService employeeService;
    private final VetMapper vetMapper;

    public VetServiceImpl(VetRepository vetRepository, EmployeeService empRespository, VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.employeeService = empRespository;
        this.vetMapper = vetMapper;
    }

    public Optional<Vet> saveVet(VetDto vetDto) {
        if (employeeService.findByDocumentNumber(vetDto.getDocumentNumber()).isPresent()) {
            return Optional.empty();
        }

        return Optional.of(vetRepository.save(vetMapper.toEntity(vetDto)));
    }
}