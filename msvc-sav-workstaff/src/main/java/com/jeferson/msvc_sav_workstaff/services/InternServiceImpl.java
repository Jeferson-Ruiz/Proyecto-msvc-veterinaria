package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.jeferson.msvc_sav_workstaff.dto.InternDto;
import com.jeferson.msvc_sav_workstaff.mapper.InternMapper;
import com.jeferson.msvc_sav_workstaff.models.Intern;
import com.jeferson.msvc_sav_workstaff.repositories.InternRepository;

@Service
public class InternServiceImpl implements InternService {

    private final InternRepository intRepository;
    private final InternMapper intMapper;
    private final EmployeeService employeeService;

    public InternServiceImpl(InternRepository intRepository, InternMapper intMapper,
            EmployeeService employeeService) {
        this.intRepository = intRepository;
        this.intMapper = intMapper;
        this.employeeService = employeeService;
    }

    public Optional<Intern> saveIntern(InternDto internDto) {
        if (employeeService.findByDocumentNumber(internDto.getDocumentNumber()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(intRepository.save(intMapper.toEntity(internDto)));
    }
}