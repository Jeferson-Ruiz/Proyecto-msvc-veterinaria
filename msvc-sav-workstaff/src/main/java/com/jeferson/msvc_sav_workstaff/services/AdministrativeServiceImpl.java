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
        if (administrativeRepository.existsByProfessionalCard(entity.getProfessionalCard())) {
            throw new IllegalArgumentException("La tarjeta profesional " + administrativeDto.getProfessionalCard()
                    + " ya se encuentra vinculado en el sistema");
        }
        entity.setRegistrationDate(LocalDate.now());
        entity.setActive(true);
        Administrative saved = employeeRepo.save(entity);
        return administrativeMapper.toDto(saved);
    }

    @Override
    public List<AdmistrativeResponseDto> findAllAdmin() {
        List<Administrative> admistratives = administrativeRepository.findAllActiveAdministrators();
        if (admistratives.isEmpty()) {
            throw new EntityNotFoundException("No existen empleados asociadoas al cargo de administrativos");
        }
        return admistratives.stream()
                .map(administrativeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdmistrativeResponseDto findAdminById(Long idEmployee) {
        Administrative administrative = validateInfo(idEmployee);
        return administrativeMapper.toDto(administrative);
    }

    @Override
    public AdmistrativeResponseDto findAdminByDocumentNumber(String documentNumber) {
        Administrative administrative = administrativeRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro administrativo asociado al numero de documento " + documentNumber));
        if (!administrative.getActive()) {
            throw new IllegalArgumentException(
                    "El empleado asignado al documento " + documentNumber + " se encientra desahabilitado del sistema");
        }
        return administrativeMapper.toDto(administrative);
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email) {
        Administrative administrative = validateInfo(idEmployee);
        if (administrative.getEmail().equals(email)) {
            throw new IllegalArgumentException(
                    "El email " + email + " ya se encuentra vinculado al administrativo " + idEmployee);
        }
        employeeServi.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(long idEmployee, String phoneNumber) {
        Administrative administrative = validateInfo(idEmployee);
        if (administrative.getPhoneNumber().equals(phoneNumber)) {
            throw new IllegalArgumentException(
                    "El telefono " + phoneNumber + " ya se encuentra vinculado al administrativo " + idEmployee);
        }
        employeeServi.updateNumberPhone(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType) {
        Administrative administrative = validateInfo(idEmployee);
        if (administrative.getContractType().equals(contractType)) {
            throw new IllegalArgumentException(
                    "El contrato " + contractType + " ya se encuentra vinculado al administrativo " + idEmployee);
        }
        employeeServi.updateContractType(idEmployee, contractType);
    }

    @Override
    @Transactional
    public void updateWorkArea(Long idEmployee, WorkArea workArea) {
        Administrative administrative = validateInfo(idEmployee);
        if (administrative.getWorkArea().equals(workArea)) {
            throw new IllegalArgumentException(
                    "El area de trabajo " + workArea + " ya se encuentra vinculado al administrativo " + idEmployee);
        }
        employeeServi.updateWorkArea(idEmployee, workArea);
    }

    @Override
    public void delete(Long idEmployee) {
        Administrative administrative = validateInfo(idEmployee);
        administrative.setActive(false);
        employeeRepo.save(administrative);
    }

    private Administrative validateInfo(Long idEmployee) {
        Administrative administrative = administrativeRepository.findById(idEmployee)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro administrativo asociado al id " + idEmployee));

        if (!administrative.getActive()) {
            throw new IllegalArgumentException("El empleado " + idEmployee + " se encuentra desactivado del sistema");
        }
        return administrative;
    }

}
