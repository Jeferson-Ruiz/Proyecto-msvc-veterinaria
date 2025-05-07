package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.jr.sav_mvsc_medicalcontrol.models.Vaccine;
import com.jr.sav_mvsc_medicalcontrol.repositories.VaccineRepository;

@Service
public class VaccineServiceImpl implements VaccineService {

    private final VaccineRepository vaccineRepository;

    public VaccineServiceImpl(VaccineRepository vaccineRepository) {
        this.vaccineRepository = vaccineRepository;
    }

    @Override
    public List<Vaccine> findAllVaccines(){
        return (List<Vaccine>) vaccineRepository.findAll();
    }

    @Override
    public Vaccine saveVaccine(Vaccine vaccine){
        return vaccineRepository.save(vaccine);
    }
    
    @Override
    public Optional<Vaccine> findVaccineById(Long idVaccine){
        return vaccineRepository.findById(idVaccine);
    }

    @Override
    public void deleteVaccine(Long idVaccine){
        vaccineRepository.deleteById(idVaccine);
    }
}