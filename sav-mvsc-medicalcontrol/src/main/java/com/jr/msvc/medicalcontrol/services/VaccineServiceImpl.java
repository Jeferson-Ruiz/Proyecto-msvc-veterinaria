package com.jr.msvc.medicalcontrol.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jr.msvc.medicalcontrol.dto.vaccine.VaccineRequestDto;
import com.jr.msvc.medicalcontrol.dto.vaccine.VaccineResponseDto;
import com.jr.msvc.medicalcontrol.mapper.VaccineMapper;
import com.jr.msvc.medicalcontrol.models.Pet;
import com.jr.msvc.medicalcontrol.models.Vaccine;
import com.jr.msvc.medicalcontrol.repositories.PetRepository;
import com.jr.msvc.medicalcontrol.repositories.VaccineRepository;
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
    public VaccineResponseDto saveVaccine(VaccineRequestDto vaccineDto){
        Pet pet = petRepository.findByPetCode(vaccineDto.getPetCode())
            .orElseThrow(() -> new RuntimeException("No se encontro paciente asociado al codigo "+ vaccineDto.getPetCode()));

        if (!pet.getActive()) {
            throw new RuntimeException("No se pueden registarar vacunas para un paciente, paciente "+pet.getName()+" deshabilitado");
        }
        Vaccine vaccine = vaccineMapper.toEntity(vaccineDto);
        vaccine.setApplicationData(LocalDate.now());

        if (vaccine.getApplicationData().isAfter(vaccine.getNextApplicationDate())) {
            throw new RuntimeException("Error en el cronograma de la vacunacion de la mascota");
        }
        vaccine.setPet(pet);
        Vaccine vaccineSaved = vaccineRepository.save(vaccine);
        return vaccineMapper.toDto(vaccineSaved);
    }

    @Override
    public List<VaccineResponseDto> findVaccinesPetCode(String petCode){
        List<Vaccine> vaccines = vaccineRepository.findAllVaccinesByPetCode(petCode);
        if (vaccines.isEmpty()) {
            throw new RuntimeException("No se encontraron vacunas asociadas al paciente "+ petCode);
        }
        return vaccines.stream().map(vaccineMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateNextApplicationDate(String vaccineCode, LocalDate newDate){
        Vaccine vaccine = vaccineRepository.findVaccineBycode(vaccineCode)
            .orElseThrow( () -> new RuntimeException ("No se encontro vacuna asociciada al codigo: "+ vaccineCode));
        if (vaccine.getApplicationData().isAfter(newDate)) {
            throw new IllegalArgumentException("Error en el cronograma de vacunas, fecha inferior a la de registro");
        }else if (vaccine.getNextApplicationDate().equals(newDate)) {
            throw new IllegalArgumentException("Error la fecha de la proxima aplicación ya se encuentra registrada");
        }
        vaccine.setApplicationData(newDate);
        vaccineRepository.save(vaccine);
    }

    @Override
    @Transactional
    public void updateName(String vaccineCode, String newName){
        Vaccine vaccine = vaccineRepository.findVaccineBycode(vaccineCode).orElseThrow(() -> new EntityNotFoundException
            ("No se encontro vacuna asociada al codigo"+ vaccineCode));
        
            if (vaccine.getName().equals(newName)) {
                throw new IllegalArgumentException("Error la vacuna ya se encuentra registrada con ese nombre");
            }
        
        vaccine.setName(newName);
        vaccineRepository.save(vaccine);
    }

}