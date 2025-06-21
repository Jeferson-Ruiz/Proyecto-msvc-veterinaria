package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.jr.sav_mvsc_medicalcontrol.client.PetClient;
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineDto;
import com.jr.sav_mvsc_medicalcontrol.mapper.VaccineMapper;
import com.jr.sav_mvsc_medicalcontrol.models.Vaccine;
import com.jr.sav_mvsc_medicalcontrol.repositories.VaccineRepository;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class VaccineServiceImpl implements VaccineService {

    private final VaccineRepository vaccineRepository;
    private final VaccineMapper vaccineMapper;
    private final PetClient petClient;

    public VaccineServiceImpl(VaccineRepository vaccineRepository, VaccineMapper vaccineMapper, PetClient petClient) {
        this.vaccineRepository = vaccineRepository;
        this.vaccineMapper = vaccineMapper;
        this.petClient = petClient;
    }

    @Override
    public List<VaccineDto> findAllVaccines(){
        return vaccineRepository.findAll().stream()
            .map(vaccineMapper::toDto).toList();
    }

    @Override
    public Optional<VaccineDto> saveVaccine(VaccineDto vaccineDto){
        if (petClient.getPetById(vaccineDto.getIdPet()) == null) {
            return Optional.empty();
        }
        Vaccine entity = vaccineMapper.toEntity(vaccineDto);
        entity.setApplicationData(LocalDate.now());
        return Optional.of(vaccineMapper.toDto(vaccineRepository.save(entity)));
    }
    
    @Override
    public Optional<VaccineDto> findVaccineById(Long idVaccine){
        Optional<Vaccine> optVaccine = vaccineRepository.findById(idVaccine);
        if (optVaccine.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(vaccineMapper.toDto(optVaccine.get()));
    }

    @Override
    public Optional<List<VaccineDto>> findVaccinesIdPet(Long idPet){
        try{
        petClient.getPetById(idPet);
        List<VaccineDto> vacunasDto = vaccineRepository
            .findByIdPet(idPet)
            .stream()
            .map(vaccineMapper::toDto)
            .toList(); 
        return Optional.of(vacunasDto);
        }catch(FeignException.NotFound e){
            return Optional.empty();
        }
    }

    @Override
    public void deleteVaccine(Long idVaccine){        
        if (vaccineRepository.findById(idVaccine).isEmpty()) {
            throw new EntityNotFoundException();
        }
        vaccineRepository.deleteById(idVaccine);
    }
}