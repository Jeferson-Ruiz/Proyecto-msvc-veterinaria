package com.jeferson.msvc_sav_workstaff.services;

import java.time.LocalDate;
import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.jeferson.msvc_sav_workstaff.dto.InternDto;
import com.jeferson.msvc_sav_workstaff.mapper.InternMapper;
import com.jeferson.msvc_sav_workstaff.models.Intern;
import com.jeferson.msvc_sav_workstaff.repositories.InternRepository;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Optional<InternDto> findById(Long idEmployee){
        Optional<Intern> optInter = intRepository.findById(idEmployee);
        if (optInter.isEmpty()) {
            return Optional.empty();
        }
        return intRepository.findById(idEmployee).map(intMapper::toDto);
    }

    @Override
    public Optional<InternDto> saveIntern(InternDto internDto) {
        if (employeeService.findByDocumentNumber(internDto.getDocumentNumber()).isPresent()) {
            return Optional.empty();
        }
        Intern entity = intMapper.toEntity(internDto);
        entity.setRegistrationDate(LocalDate.now());
        return Optional.of(intMapper.toDto(intRepository.save(entity)));
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email){
        if (intRepository.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException();
        }
        employeeService.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(Long idEmployee, Long phoneNumber){

        if (intRepository.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException();
        }
        employeeService.updateNumberPhone(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType){
        if (intRepository.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException();
        }
        employeeService.updateContractType(idEmployee, contractType);
    }

    @Override
    @Transactional
    public void updateWorkStatus(Long idEmployee, Boolean workStatus){
        if (intRepository.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException();
        }
        employeeService.updateWorkStatus(idEmployee, workStatus);
    }

    @Override
    public void delete (Long idEmployee){
        if (intRepository.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException();
        }
        employeeService.delete(idEmployee);
    }

}