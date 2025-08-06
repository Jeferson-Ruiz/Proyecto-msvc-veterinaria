package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineDto;
import com.jr.sav_mvsc_medicalcontrol.mapper.VaccineMapper;
import com.jr.sav_mvsc_medicalcontrol.models.Pet;
import com.jr.sav_mvsc_medicalcontrol.models.Vaccine;
import com.jr.sav_mvsc_medicalcontrol.repositories.PetRepository;
import com.jr.sav_mvsc_medicalcontrol.repositories.VaccineRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class VaccineServiceImpl implements VaccineService {

    private final VaccineRepository vaccineRepository;
    private final VaccineMapper vaccineMapper;
    private final PetRepository petRepository;

    public VaccineServiceImpl(VaccineRepository vaccineRepository, VaccineMapper vaccineMapper, PetRepository petRepository) {
        this.vaccineRepository = vaccineRepository;
        this.vaccineMapper = vaccineMapper;
        this.petRepository = petRepository;
    }

    @Override
    public List<VaccineDto> findAllVaccines(){
        return vaccineRepository.findAll().stream()
            .map(vaccineMapper::toDto).toList();
    }

    @Override
    public VaccineDto saveVaccine(VaccineDto vaccineDto){
        Pet pet = petRepository.findById(vaccineDto.getIdPet())
            .orElseThrow(() -> new RuntimeException("No se puede registrar vacunas al paciente "+ vaccineDto.getIdPet()));
        
        Vaccine vaccine = vaccineMapper.toEntity(vaccineDto);
        vaccine.setApplicationData(LocalDate.now());
        Vaccine vaccineSaved = vaccineRepository.save(vaccine);
        return vaccineMapper.toDto(vaccineSaved);
    }
    
    @Override
    public VaccineDto findVaccineById(Long idVaccine){
        Vaccine vaccine = vaccineRepository.findById(idVaccine)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro una vacuna asociada al Id " + idVaccine));
        
        return vaccineMapper.toDto(vaccine);
    }

    @Override
    public List<VaccineDto> findVaccinesIdPet(Long idPet){
        List<Vaccine> vaccines = vaccineRepository.findByIdPet(idPet);

        if (vaccines.isEmpty()) {
            throw new RuntimeException("No se encontraron vacunas asociadas al paciente "+ idPet);
        }
        return vaccines.stream().map(vaccineMapper::toDto).toList();
    }

    @Override
    public void deleteVaccine(Long idVaccine){    
        Vaccine vaccine = vaccineRepository.findById(idVaccine)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro la vacuna "+ idVaccine + " en el sistema"));
        vaccineRepository.delete(vaccine);
    }
}