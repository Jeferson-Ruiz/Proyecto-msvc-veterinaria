package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationReponseDto;
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

    // Horario laboral
    LocalTime startTime = LocalTime.of(8, 0); // 8:00 AM
    LocalTime endTime = LocalTime.of(17, 30); // 6:00 PM

    public ConsultationServiceImpl(PetRepository petRepository, ConsultationRepository consultationRepository,
            ConsultationMapper consultationMapper) {
        this.petRepository = petRepository;
        this.consultationRepository = consultationRepository;
        this.consultationMapper = consultationMapper;
    }

    @Override
    public List<ConsultationReponseDto> findAllConsultations() {
        List<Consultation> consultations = consultationRepository.findAll();
        if (consultations.isEmpty()) {
            throw new EntityNotFoundException("No se encuentran consultas asociadas en el sistema");
        }
        return consultations.stream()
                .map(consultationMapper::toDto).toList();
    }

    @Override
    public ConsultationReponseDto findConsultionById(Long idConsultation) {
        Consultation consultation = consultationRepository.findById(idConsultation)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro consulta asociada al id " + idConsultation));
        return consultationMapper.toDto(consultation);
    }

    @Override
    public ConsultationReponseDto saveConsultation(ConsultationRequestDto consultationDto) {
        Pet pet = petRepository.findById(consultationDto.getIdPet())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se puede generar una consulta para la mascota " + consultationDto.getIdPet()));

        if (!pet.getActive()) {
            throw new IllegalArgumentException("No se puede generar una consuta el paciente " + consultationDto.getIdPet()
                    + " se encuentra desactivado");
        }
        LocalTime dateTime = consultationDto.getCitationDate().toLocalTime();
        validarHora(dateTime);

        Consultation consultation = consultationMapper.toEntity(consultationDto);
        consultation.setPet(pet);
        consultation.setRegistrationDate(LocalDateTime.now());

        if (consultation.getCitationDate().toLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de la cita no puede ser inferior a la de registro");
        }
        return consultationMapper.toDto(consultationRepository.save(consultation));
    }

    @Override
    public List<ConsultationReponseDto> findAllConsultationByIdPet(Long idPet) {
        List<Consultation> consultations = consultationRepository.findAllByIdPet(idPet);
        if (consultations.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron consultas asociadas al paciente " + idPet);
        }
        return consultations.stream().map(consultationMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void updateConsultationDate(Long idConsultation, LocalDateTime newDate) {
        Consultation consultation = consultationRepository.findById(idConsultation)
            .orElseThrow(
                () -> new IllegalArgumentException("No se encontro consulta asocida al id" + idConsultation));

        if (!newDate.isAfter(consultation.getCitationDate())) {
            throw new IllegalArgumentException("La nueva fecha debe ser posterior a la actual");
        }

        LocalTime dateTime = newDate.toLocalTime();
        validarHora(dateTime);

        consultationRepository.updateCitationDate(idConsultation, newDate);
    }

    private void validarHora(LocalTime date) {
        if (date.isBefore(startTime) || date.isAfter(endTime)) {
            throw new RuntimeException("Las cita deben ser programardas entre las " + startTime + " y las " + endTime);
        }
    }
}