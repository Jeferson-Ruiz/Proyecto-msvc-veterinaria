package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationsWithVetDto;
import com.jr.sav_mvsc_medicalcontrol.client.VetClient;
import com.jr.sav_mvsc_medicalcontrol.dto.VetResponseDto;
import com.jr.sav_mvsc_medicalcontrol.dto.VetWithConsultationsDto;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationReponseDto;
import com.jr.sav_mvsc_medicalcontrol.mapper.ConsultationMapper;
import com.jr.sav_mvsc_medicalcontrol.models.AttendanceStatus;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;
import com.jr.sav_mvsc_medicalcontrol.models.Pet;
import com.jr.sav_mvsc_medicalcontrol.repositories.ConsultationRepository;
import com.jr.sav_mvsc_medicalcontrol.repositories.PetRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final VetClient vetClient;
    private final PetRepository petRepository;
    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;

    // Horario laboral
    LocalTime startTime = LocalTime.of(8, 0); // 8:00 AM
    LocalTime endTime = LocalTime.of(17, 30); // 6:00 PM

    public ConsultationServiceImpl(PetRepository petRepository, ConsultationRepository consultationRepository,
            ConsultationMapper consultationMapper, VetClient vetClient) {
        this.petRepository = petRepository;
        this.consultationRepository = consultationRepository;
        this.consultationMapper = consultationMapper;
        this.vetClient = vetClient;
    }

    @Override
    public List<ConsultationReponseDto> findAllConsultations() {
        List<Consultation> consultations = consultationRepository.findAll();
        if (consultations.isEmpty()) {
            throw new EntityNotFoundException("No se encuentran consultas asociadas en el sistema");
        }
        return consultations.stream()
            .map(this::mapToDtoWithVetName)
            .toList();
    }

    @Override
    public ConsultationReponseDto findConsultionById(Long idConsultation) {
        Consultation consultation = findById(idConsultation);
        return mapToDtoWithVetName(consultation);
    }

    @Override
    public ConsultationReponseDto saveConsultation(ConsultationRequestDto consultationDto) {
        try{
        Pet pet = validPet(consultationDto.getIdPet());
        VetResponseDto vetDto = validVet(consultationDto.getVetId());
        
        validDate(consultationDto.getCitationDate());
        checkAvailability(consultationDto.getVetId(), consultationDto.getCitationDate());
    
        Consultation consultation = consultationMapper.toEntity(consultationDto);
        consultation.setPet(pet);
        consultation.setPetName(pet.getName());
        consultation.setStatus(AttendanceStatus.PENDING);
        consultation.setRegistrationDate(LocalDateTime.now());
        Consultation saved = consultationRepository.save(consultation);
        return mapToDtoWithVetName(saved);
        }catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException("El veterinario ya tiene una cita asignada en ese horario");
        }
    }

    @Override
    public List<ConsultationReponseDto> findAllConsultationByIdPet(Long idPet) {
        List<Consultation> consultations = consultationRepository.findAllByIdPet(idPet);
        if (consultations.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron consultas asociadas al paciente " + idPet);
        }
        return consultations.stream().map(this::mapToDtoWithVetName).toList();
    }

    @Override
    public List<ConsultationReponseDto> findAllByStatus(AttendanceStatus status){
        List<Consultation> consultations = consultationRepository.findAllByStatus(status);
        if (consultations.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron consultas asociadas al estado " + status);
        }
        return consultations.stream().map(this::mapToDtoWithVetName).toList();
    }

    @Override
    public List<ConsultationReponseDto> findByDate(LocalDate date){
        if (date == null) {
            date = LocalDate.now();
        }
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        List<Consultation>consultations = consultationRepository.findByCitationDateBetween(start, end);
        if (consultations.isEmpty()) {
            throw new EntityNotFoundException("No se encontro consultas registradas para el dia "+ date);
        }
        return consultations.stream().map(this::mapToDtoWithVetName).toList();
    }

    @Override
    public VetWithConsultationsDto findConsultationsByIdVet(Long vetId){
        VetResponseDto vet;
        try {
            vet = vetClient.getVetById(vetId); 
        } catch (feign.FeignException.NotFound e) {
            throw new EntityNotFoundException("No existe el veterinario " + vetId + " en el sistema");
        }

        List<ConsultationsWithVetDto> consultations = consultationRepository.findByVetId(vetId) 
            .stream()
            .map(consultationMapper::toVetDto)
            .toList();
        return new VetWithConsultationsDto(vet, consultations);
    }

    @Override
    @Transactional
    public void updateConsultationDate(Long idConsultation, LocalDateTime newDate) {
        Consultation consultation = findById(idConsultation);
        validDate(newDate);
        consultationRepository.updateCitationDate(idConsultation, newDate);
    }    

    // helpers
    private Consultation findById(Long id){
        Consultation consultation = consultationRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("No se encontro consulta asociado al id "+ id + " en el sistema"));
        return consultation;
    }


    private void validHour(LocalTime date) {
        if (date.isBefore(startTime) || date.isAfter(endTime)) {
            throw new RuntimeException("Las cita deben ser programardas entre las " + startTime + " y las " + endTime);
        }
    }

    private void validDate(LocalDateTime date) {
        if (date.toLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de la cita no puede ser menor a la actual");
        }
        validHour(date.toLocalTime());
    }

    private Pet validPet (Long idPet) {
        Pet pet = petRepository.findById(idPet)
            .orElseThrow(() -> new EntityNotFoundException("No existe la mascota " + idPet));
        if (!pet.getActive()) {
            throw new IllegalArgumentException("La mascota " + idPet + " está deshabilitada");
        }
        return pet;
    }

    private VetResponseDto validVet(Long idVet) {
        try {
            VetResponseDto vet = vetClient.getVetById(idVet);
            if (!"ACTIVE".equalsIgnoreCase(vet.getStatus())) {
                throw new IllegalArgumentException("El veterinario " + idVet + " está deshabilitado del sistema");
            }
            return vet;
        } catch (feign.FeignException.NotFound e) {
            throw new EntityNotFoundException("No existe el veterinario " + idVet + " en el sistema");
        }
    }


    private ConsultationReponseDto mapToDtoWithVetName(Consultation consultation) {
    ConsultationReponseDto dto = consultationMapper.toDto(consultation);
    try {
        VetResponseDto vet = vetClient.getVetById(consultation.getVetId());
        dto.setFullName(vet.getFullName());
    } catch (feign.FeignException.NotFound e) {
        dto.setFullName("Veterinario no encontrado");
    }
    return dto;
    }

    private void checkAvailability(Long idVet, LocalDateTime appointmentDate){
        LocalDateTime start = appointmentDate.minusMinutes(30);
        LocalDateTime end = appointmentDate.plusMinutes(30);

        if (consultationRepository.existsByVetAndTimeRange(idVet, start, end)) {
            throw new IllegalArgumentException("El veterinario "+ idVet + " ya cuenta cita registrada para el dia " + appointmentDate);
        }
    }

}