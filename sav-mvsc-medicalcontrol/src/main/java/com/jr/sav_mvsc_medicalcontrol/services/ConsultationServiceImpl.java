package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;
import com.jr.sav_mvsc_medicalcontrol.repositories.ConsultationRepository;

@Service
public class ConsultationServiceImpl implements ConsultationService{

    public ConsultationServiceImpl(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    private final ConsultationRepository consultationRepository;

    @Override
    public List<Consultation> findAllConsultations(){
        return (List<Consultation>) consultationRepository.findAll();
    }

}
