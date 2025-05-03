package com.jr.sav_mvsc_medicalcontrol.services;

import com.jr.sav_mvsc_medicalcontrol.models.Consultation;
import com.jr.sav_mvsc_medicalcontrol.models.Treatment;
import com.jr.sav_mvsc_medicalcontrol.repositories.TreatmentRespository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRespository treatmentRespository;


    public TreatmentServiceImpl(TreatmentRespository treatmentRespository) {
        this.treatmentRespository = treatmentRespository;
    }

    @Override
    public List<Treatment> findAlltreatments() {
        return (List<Treatment>) treatmentRespository.findAll();
    }

    @Override
    public Optional<Treatment> findTreatmentById(Long idTreatment){
        return  treatmentRespository.findById(idTreatment);
    }

    @Override
    public Treatment saveTreatment(Treatment treatment){
        return treatmentRespository.save(treatment);
    }

    @Override
    public void deleteTreatment(Long idTreatment){
        treatmentRespository.deleteById(idTreatment);
    }

}