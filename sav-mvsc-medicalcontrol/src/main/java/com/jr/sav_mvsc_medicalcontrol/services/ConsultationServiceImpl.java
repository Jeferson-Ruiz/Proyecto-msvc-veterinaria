package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import java.util.Optional;
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
    public List<Consultation> findAllConsultations() {
        return (List<Consultation>) consultationRepository.findAll();
    }

    @Override
    public Optional<Consultation> finConsultionById(Long idConsultation) {
        return consultationRepository.findById(idConsultation);
    }

    @Override
    public Optional<Consultation> finConsultionByIdPet(Long idPet) {
        return consultationRepository.findByIdPet(idPet);
    }

    @Override
    public Consultation saveConsultation(Consultation consultation){
        return consultationRepository.save(consultation);
    }

    @Override
    public void deleteConsultation(Long idConsultation){
        consultationRepository.deleteById(idConsultation);
    }




}