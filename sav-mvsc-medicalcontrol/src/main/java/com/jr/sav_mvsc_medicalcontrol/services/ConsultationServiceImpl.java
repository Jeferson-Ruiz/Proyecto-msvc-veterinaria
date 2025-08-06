package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.jr.sav_mvsc_medicalcontrol.dto.ConsultationDto;
import com.jr.sav_mvsc_medicalcontrol.mapper.ConsultationMapper;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;
import com.jr.sav_mvsc_medicalcontrol.models.Pet;
import com.jr.sav_mvsc_medicalcontrol.repositories.ConsultationRepository;
import com.jr.sav_mvsc_medicalcontrol.repositories.PetRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final PetRepository petRepository;
    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;

    public ConsultationServiceImpl(PetRepository petRepository, ConsultationRepository consultationRepository,
            ConsultationMapper consultationMapper) {
        this.petRepository = petRepository;
        this.consultationRepository = consultationRepository;
        this.consultationMapper = consultationMapper;
    }

    @Override
    public List<ConsultationDto> findAllConsultations() {
        return consultationRepository.findAll().stream()
                .map(consultationMapper::toDto).toList();
    }

    @Override
    public ConsultationDto findConsultionById(Long idConsultation) {
        Consultation consultation = consultationRepository.findById(idConsultation)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro consulta asociada al paciente " + idConsultation));
        return consultationMapper.toDto(consultation);
    }

    @Override
    public ConsultationDto saveConsultation(ConsultationDto consultationDto) {
        Pet pet = petRepository.findById(consultationDto.getIdPet())
            .orElseThrow(() -> new RuntimeException("No se puede generar una consulta para la mascota "+ consultationDto.getIdPet()));

        Consultation consultation = consultationMapper.toEntity(consultationDto);
        consultation.setRegistrationDate(LocalDateTime.now());
        Consultation savedConsultation = consultationRepository.save(consultation);
        return consultationMapper.toDto(savedConsultation);
    }

    @Override
    public void deleteConsultation(Long idConsultation) {
        Consultation consultation = consultationRepository.findById(idConsultation)
                .orElseThrow(() -> new EntityNotFoundException("No existe consulta asociada al Id " + idConsultation));
        consultationRepository.delete(consultation);
    }

    @Override
    public ConsultationDto findConsultationByIdPet(Long idPet) {
        Consultation consultatio = consultationRepository.findByIdPet(idPet)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe consulta asociada al paciente con el Id: " + idPet +" en el sistema"));

        return consultationMapper.toDto(consultatio);
    }
}