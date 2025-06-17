package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.jr.sav_mvsc_medicalcontrol.client.PetClient;
import com.jr.sav_mvsc_medicalcontrol.dto.ConsultationDto;
import com.jr.sav_mvsc_medicalcontrol.dto.PetDto;
import com.jr.sav_mvsc_medicalcontrol.mapper.ConsultationMapper;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;
import com.jr.sav_mvsc_medicalcontrol.repositories.ConsultationRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final PetClient petClient;
    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;

    public ConsultationServiceImpl(PetClient petClient, ConsultationRepository consultationRepository,
            ConsultationMapper consultationMapper) {
        this.petClient = petClient;
        this.consultationRepository = consultationRepository;
        this.consultationMapper = consultationMapper;
    }

    @Override
    public List<ConsultationDto> findAllConsultations() {
        return consultationRepository.findAll().stream()
                .map(consultationMapper::toDto).toList();
    }

    @Override
    public Optional<ConsultationDto> finConsultionById(Long idConsultation) {
        Optional<Consultation> optConsultation = consultationRepository.findById(idConsultation);
        if (optConsultation.isEmpty()) {
            return Optional.empty();
        }
        ConsultationDto dto = consultationMapper.toDto(optConsultation.get());
        return Optional.of(dto);
    }

    @Override
    public Optional<ConsultationDto> saveConsultation(ConsultationDto consultationDto) {
        PetDto petDto = petClient.getPetById(consultationDto.getIdPet());
        if (petDto == null) {
            return Optional.empty();
        }
        Consultation entity = consultationMapper.toEntity(consultationDto);
        entity.setDate(LocalDate.now());
        return Optional.of(consultationMapper.toDto(consultationRepository.save(entity)));
    }

    @Override
    public void deleteConsultation(Long idConsultation) {
        if (consultationRepository.findById(idConsultation).isEmpty()) {
            throw new EntityNotFoundException();
        }
        consultationRepository.deleteById(idConsultation);
    }

    @Override
    public Optional<ConsultationDto> findConsultationByIdPet(Long idPet) {
        PetDto petDto = petClient.getPetById(idPet);
        if (petDto == null) {
            return Optional.empty();
        }
        return consultationRepository.findByIdPet(idPet)
                .map(consultationMapper::toDto);
    }
}