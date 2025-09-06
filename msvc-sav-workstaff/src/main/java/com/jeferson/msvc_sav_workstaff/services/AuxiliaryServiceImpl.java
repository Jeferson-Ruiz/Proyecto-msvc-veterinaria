package com.jeferson.msvc_sav_workstaff.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryResponseDto;
import com.jeferson.msvc_sav_workstaff.mapper.AuxiliaryMapper;
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;
import com.jeferson.msvc_sav_workstaff.models.AuxiliaryRoles;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.repositories.AuxiliaryRepository;
import com.jeferson.msvc_sav_workstaff.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AuxiliaryServiceImpl implements AuxiliaryService {

    private final AuxiliaryRepository auxRepository;
    private final AuxiliaryMapper auxMapper;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRespo;

    public AuxiliaryServiceImpl(AuxiliaryRepository auxRepository,
            AuxiliaryMapper auxMapper,
            EmployeeService employeeService,
            EmployeeRepository employeeRespo) {
        this.auxRepository = auxRepository;
        this.auxMapper = auxMapper;
        this.employeeService = employeeService;
        this.employeeRespo = employeeRespo;
    }

    @Override
    public AuxiliaryResponseDto saveAuxiliary(AuxiliaryRequestDto auxiliaryDto) {
        Boolean employee = employeeRespo.findByDocumentNumber(auxiliaryDto.getDocumentNumber()).isPresent();
        if (employee) {
            throw new RuntimeException(
                    "El documento " + auxiliaryDto.getDocumentNumber() + " ya se encuentra vinculado a un empleado");
        }
        Auxiliary entity = auxMapper.toEntity(auxiliaryDto);
        entity.setRegistrationDate(LocalDate.now());
        entity.setActive(true);
        auxRepository.save(entity);
        return auxMapper.toDto(entity);
    }

    @Override
    public List<AuxiliaryResponseDto> findAllAuxiliary() {
        List<Auxiliary> auxiliaries = auxRepository.findAllActiveAuxiliaries();
        if (auxiliaries.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron empleados asociados al cargo de auxiliar");
        }
        return auxiliaries.stream()
                .map(auxMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuxiliaryResponseDto> findAllDisabledAuxiliary() {
        List<Auxiliary> auxiliaries = auxRepository.findAllDisabledAuxiliaries();
        if (auxiliaries.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron auxiliares desahabilidatos en el sistema");
        }
        return auxiliaries.stream()
                .map(auxMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuxiliaryResponseDto findById(Long idEmployee) {
        Auxiliary auxiliary = auxRepository.findById(idEmployee)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro auxiliar asociado al id " + idEmployee));
        return auxMapper.toDto(auxiliary);
    }

    @Override
    public AuxiliaryResponseDto findAdminByDocumentNumber(String documentNumber) {
        Auxiliary auxiliary = auxRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro veterianio asociado al numero de documento " + documentNumber));
        return auxMapper.toDto(auxiliary);
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email) {
        Auxiliary auxiliary = validateInfo(idEmployee);
        if (auxiliary.getEmail().equals(email)) {
            throw new IllegalArgumentException(
                    "El email " + email + " ya se encuentra vinculado al auxiliar " + idEmployee);
        }
        employeeService.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updatePhoneNumber(Long idEmployee, String phoneNumber) {
        Auxiliary auxiliary = validateInfo(idEmployee);
        if (auxiliary.getPhoneNumber().equals(phoneNumber)) {
            throw new IllegalArgumentException(
                    "El telefono " + phoneNumber + " ya se encuentra vinculado al auxiliar " + idEmployee);
        }
        employeeService.updateNumberPhone(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType) {
        Auxiliary auxiliary = validateInfo(idEmployee);
        if (auxiliary.getContractType().equals(contractType)) {
            throw new IllegalArgumentException(
                    "El contrato " + contractType + " ya se encuentra vinculado al auxiliar " + idEmployee);
        }
        employeeRespo.updateContractType(idEmployee, contractType);
    }

    @Override
    @Transactional
    public void updateRole(Long idEmployee, AuxiliaryRoles auxiliaryRoles) {
        Auxiliary auxiliary = validateInfo(idEmployee);
        if (auxiliary.getAuxiliaryRoles().equals(auxiliaryRoles)) {
            throw new IllegalArgumentException(
                    "El area de trabajo " + auxiliaryRoles + " ya se encuentra vinculado al auxiliar " + idEmployee);
        }
        auxRepository.updateRole(idEmployee, auxiliaryRoles);
    }

    @Override
    public void delete(Long idEmployee) {
        Auxiliary auxiliary = validateInfo(idEmployee);
        auxiliary.setActive(false);
        employeeRespo.save(auxiliary);
    }

    private Auxiliary validateInfo(Long idEmployee) {
        Auxiliary auxiliary = auxRepository.findById(idEmployee)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro auxiliar asociado al id " + idEmployee));
        if (!auxiliary.getActive()) {
            throw new IllegalArgumentException("El empelado " + idEmployee + " se encuentra deshabilitado del sistema");
        }
        return auxiliary;
    }
}