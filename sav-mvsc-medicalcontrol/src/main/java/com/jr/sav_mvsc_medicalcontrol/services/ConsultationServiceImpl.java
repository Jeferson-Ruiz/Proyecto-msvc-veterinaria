package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.jr.sav_mvsc_medicalcontrol.client.PetClient;
import com.jr.sav_mvsc_medicalcontrol.dto.PetDto;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;
import com.jr.sav_mvsc_medicalcontrol.repositories.ConsultationRepository;
import feign.FeignException.FeignClientException;

@Service
public class ConsultationServiceImpl implements ConsultationService{

    private final PetClient petClient;
    private final ConsultationRepository consultationRepository;


    public ConsultationServiceImpl(PetClient petClient, ConsultationRepository consultationRepository) {
        this.petClient = petClient;
        this.consultationRepository = consultationRepository;
    }

    @Override
    public List<Consultation> findAllConsultations() {
        return (List<Consultation>) consultationRepository.findAll();
    }

    @Override
    public Optional<Consultation> finConsultionById(Long idConsultation) {
        return consultationRepository.findById(idConsultation);
    }

    @Override
    public Consultation saveConsultation(Consultation consultation){
        consultation.setDate(LocalDate.now());
        return consultationRepository.save(consultation);
    }

    @Override
    public void deleteConsultation(Long idConsultation){
        consultationRepository.deleteById(idConsultation);
    }

    @Override
    public Optional<PetDto> findConsultationByIdPet(Long idPet){
        try {
             PetDto pet = petClient.getPetById(idPet);
             return Optional.of(pet);
        } catch (FeignClientException e) {
        return Optional.empty();
        }
    }
}