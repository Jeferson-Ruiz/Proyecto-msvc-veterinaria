package com.jeferson.msvc_sav_workstaff.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.WorkArea;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeferson.msvc_sav_workstaff.dto.AdministrativeRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AdmistrativeResponseDto;
import com.jeferson.msvc_sav_workstaff.mapper.AdministrativeMapper;
import com.jeferson.msvc_sav_workstaff.models.Administrative;
import com.jeferson.msvc_sav_workstaff.repositories.AdministrativeRepository;
import com.jeferson.msvc_sav_workstaff.repositories.EmployeeRepository;

@Service
public class AdministrativeServiceImpl implements AdministrativeService {

    private final AdministrativeRepository administrativeRepository;
    private final AdministrativeMapper administrativeMapper;
    private final EmployeeRepository employeeRepo;
    private final EmployeeService employeeServi;

    public AdministrativeServiceImpl(AdministrativeRepository administrativeRepository,
            AdministrativeMapper administrativeMapper,
            EmployeeRepository employeeRepo,
            EmployeeService employeeServi) {
        this.administrativeRepository = administrativeRepository;
        this.administrativeMapper = administrativeMapper;
        this.employeeRepo = employeeRepo;
        this.employeeServi = employeeServi;
    }

    @Override
    public AdmistrativeResponseDto saveAdministrative(AdministrativeRequestDto administrativeDto) {
        Boolean employee = employeeRepo.findByDocumentNumber(administrativeDto.getDocumentNumber()).isPresent();
        if (employee == true) {
            throw new RuntimeException("El documento " + administrativeDto.getDocumentNumber()
                    + " ya se encuentra vinculado a un empleado");
        }
        Administrative entity = administrativeMapper.toEntity(administrativeDto);
        entity.setRegistrationDate(LocalDate.now());
        entity.setActive(true);
        Administrative saved = employeeRepo.save(entity);
        return administrativeMapper.toDto(saved);
    }

    @Override
    public List<AdmistrativeResponseDto> findAllAdmin(){
        List<Administrative> admistratives = administrativeRepository.findAll();
        if (admistratives.isEmpty()) {
            throw new EntityNotFoundException("No existen empleados asociadoas al cargo de administrativos");
        }
        return admistratives.stream()
            .map(administrativeMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public AdmistrativeResponseDto findAdminById(Long idEmployee) {

        Administrative administrative = administrativeRepository.findById(idEmployee)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro administrativo asociado al Id " + idEmployee + " en el sistema"));
        return administrativeMapper.toDto(administrative);
    }

    @Override
    public AdmistrativeResponseDto findAdminByDocumentNumber(String documentNumber){
        Administrative administrative = administrativeRepository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro veterianio asociado al numero de documento " + documentNumber));
        return administrativeMapper.toDto(administrative);
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email) {
        employeeServi.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(long idEmployee, String phoneNumber) {
        employeeServi.updateNumberPhone(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType) {
        employeeServi.updateContractType(idEmployee, contractType);
    }

    @Override
    @Transactional
    public void updateWorkArea(Long idEmployee, WorkArea workArea){
        employeeServi.updateWorkArea(idEmployee, workArea);
    }

    @Override
    public void delete(Long idEmployee) {
        employeeServi.delete(idEmployee);
    }

}
