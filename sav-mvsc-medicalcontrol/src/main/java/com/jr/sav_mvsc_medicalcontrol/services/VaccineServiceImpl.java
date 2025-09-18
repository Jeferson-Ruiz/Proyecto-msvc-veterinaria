package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineResponseDto;
import com.jr.sav_mvsc_medicalcontrol.mapper.VaccineMapper;
import com.jr.sav_mvsc_medicalcontrol.models.Pet;
import com.jr.sav_mvsc_medicalcontrol.models.Vaccine;
import com.jr.sav_mvsc_medicalcontrol.repositories.PetRepository;
import com.jr.sav_mvsc_medicalcontrol.repositories.VaccineRepository;

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
        Pet pet = petRepository.findById(vaccineDto.getIdPet())
            .orElseThrow(() -> new RuntimeException("No se encontro paciente asociado al id "+ vaccineDto.getIdPet()));

        if (!pet.getActive()) {
            throw new RuntimeException("No se pueden registarar vacunas para un paciente, paciente "+vaccineDto.getIdPet()+" deshabilitado");
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
    public List<VaccineResponseDto> findVaccinesIdPet(Long idPet){
        List<Vaccine> vaccines = vaccineRepository.findAllByIdPet(idPet);
        if (vaccines.isEmpty()) {
            throw new RuntimeException("No se encontraron vacunas asociadas al paciente "+ idPet);
        }
        return vaccines.stream().map(vaccineMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateNextApplicationDate(Long idVaccine, LocalDate newDate){
        Vaccine vaccine = vaccineRepository.findById(idVaccine)
            .orElseThrow( () -> new RuntimeException ("No se encontro vacunaca asociciada al id: "+ idVaccine));
        if (vaccine.getApplicationData().isAfter(newDate)) {
            throw new RuntimeCryptoException("Error en el cronograma de vacunas, fecha inferior a la de registro");
        }
        vaccineRepository.updateNextApplicationDate(idVaccine, newDate);
    }

    @Override
    @Transactional
    public void updateName(Long idVaccine, String name){
        if (vaccineRepository.findById(idVaccine).isEmpty()) {
            throw new RuntimeException("No se encontro vacuna asociada al id "+ idVaccine);
        }
        vaccineRepository.updateName(idVaccine, name);
    }

}