package com.jr.msvc.medicalcontrol.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jr.msvc.medicalcontrol.client.VetClient;
import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationReponseDto;
import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationRequestDto;
import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationsWithVetDto;
import com.jr.msvc.medicalcontrol.dto.vet.VetResponseDto;
import com.jr.msvc.medicalcontrol.dto.vet.VetWithConsultationsDto;
import com.jr.msvc.medicalcontrol.mapper.ConsultationMapper;
import com.jr.msvc.medicalcontrol.models.AttendanceStatus;
import com.jr.msvc.medicalcontrol.models.Consultation;
import com.jr.msvc.medicalcontrol.models.Pet;
import com.jr.msvc.medicalcontrol.repositories.ConsultationRepository;
import com.jr.msvc.medicalcontrol.repositories.PetRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final VetClient vetClient;
    private final PetRepository petRepository;
    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;
    private final CodeMedicalControlService codeMedical;

    // Horario laboral
    LocalTime startTime = LocalTime.of(8, 0); // 8:00 AM
    LocalTime endTime = LocalTime.of(17, 30); // 6:00 PM

    public ConsultationServiceImpl(PetRepository petRepository, ConsultationRepository consultationRepository,
            ConsultationMapper consultationMapper, VetClient vetClient, CodeMedicalControlService codeMedical) {
        this.petRepository = petRepository;
        this.consultationRepository = consultationRepository;
        this.consultationMapper = consultationMapper;
        this.vetClient = vetClient;
        this.codeMedical = codeMedical;
    }

    @Override
    public ConsultationReponseDto saveConsultation(ConsultationRequestDto consultationDto) {
        try{
            Pet pet = validPet(consultationDto.getPetCode());
            VetResponseDto vetDto = validVet(consultationDto.getVetCode());
            
            validDate(consultationDto.getCitationDate());
            checkAvailability(consultationDto.getVetCode(), consultationDto.getCitationDate());
        
            //Agreganado la caonsulta a la mascota
            Consultation consultation = consultationMapper.toEntity(consultationDto);
            consultation.setPet(pet);
            consultation.setPetName(pet.getName());

            //Creacion de codigo dinamico
            String petCode = consultation.getPet().getPetCode();
            int count = consultationRepository.countCitaByPetCode(petCode);
            String code = codeMedical.generateMedicalCode("CON", petCode, consultation.getCitationDate(), count);
            consultation.setConsultationCode(code);

            consultation.setStatus(AttendanceStatus.PENDING);
            consultation.setRegistrationDate(LocalDateTime.now());

            Consultation saved = consultationRepository.save(consultation);
            return mapToDtoWithVetName(saved);

        }catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException("El veterinario ya tiene una cita asignada en ese horario");
        }
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
    public ConsultationReponseDto findConsultionByCode(String consultationCode) {
        Consultation consultation = findByCode(consultationCode);
        return mapToDtoWithVetName(consultation);
    }

    @Override
    public List<ConsultationReponseDto> findAllConsultationByPetCode(String petCode) {
        List<Consultation> consultations = consultationRepository.findAllByPetCode(petCode);
        if (consultations.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron consultas asociadas al paciente "+ petCode);
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
    public VetWithConsultationsDto findConsultationsByVetCode(String vetCode){
        VetResponseDto vet;
        try {
            vet = vetClient.getVetByCode(vetCode); 
        } catch (feign.FeignException.NotFound e) {
            throw new EntityNotFoundException("No existe el veterinario " + vetCode + " en el sistema");
        }

        List<ConsultationsWithVetDto> consultations = consultationRepository.findByVetCode(vetCode) 
            .stream()
            .map(consultationMapper::toVetDto)
            .toList();
        return new VetWithConsultationsDto(vet, consultations);
    }

    @Override
    @Transactional
    public void updateConsultationDate(String consultatioCode, LocalDateTime newDate) {
        Consultation consultation = findByCode(consultatioCode);
        validDate(newDate);

        if (consultation.getCitationDate().equals(newDate)) {
            throw new IllegalArgumentException("La consulta ya cuando con la fecha, ingresar una nueva");
        }

        consultation.setCitationDate(newDate);
        consultationRepository.save(consultation);
    }    

    // helpers
    private Consultation findByCode(String code){
        Consultation consultation = consultationRepository.findByConsultationCode(code).orElseThrow(
            () -> new EntityNotFoundException("No se encontro consulta asociado al codigo "+ code + " en el sistema"));
        return consultation;
    }


    private void validHour(LocalTime date) {
        if (date.isBefore(startTime) || date.isAfter(endTime)) {
            throw new RuntimeException("Las cita deben ser programardas entre las " + startTime + " y las " + endTime);
        }
    }

    private void validDate(LocalDateTime date) {
        if (date.toLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de la cita no puede ser menor a la fecha actual");
        }
        validHour(date.toLocalTime());
    }

    private Pet validPet (String petCode) {
        Pet pet = petRepository.findByPetCode(petCode)
            .orElseThrow(() -> new EntityNotFoundException("No existe la mascota con el codigo" + petCode));
        if (!pet.getActive()) {
            throw new IllegalArgumentException("La mascota " + pet.getName() + " está deshabilitada");
        }
        return pet;
    }

    private VetResponseDto validVet(String vetCode) {
        try {
            VetResponseDto vet = vetClient.getVetByCode(vetCode);
            if (!"ACTIVE".equalsIgnoreCase(vet.getStatus())) {
                throw new IllegalArgumentException("El veterinario " + vet.getFullName() + " está deshabilitado del sistema");
            }
            return vet;
        } catch (feign.FeignException.NotFound e) {
            throw new EntityNotFoundException("No se encontro registro del veterinario " + vetCode + " en el sistema");
        }
    }


    private ConsultationReponseDto mapToDtoWithVetName(Consultation consultation) {
        ConsultationReponseDto dto = consultationMapper.toDto(consultation);
        try {
            VetResponseDto vet = vetClient.getVetByCode(consultation.getVetCode());
            dto.setFullName(vet.getFullName());
        } catch (feign.FeignException.NotFound e) {
            dto.setFullName("Veterinario no encontrado");
        }
        return dto;
    }

    private void checkAvailability(String vetCode, LocalDateTime appointmentDate){
        LocalDateTime start = appointmentDate.minusMinutes(30);
        LocalDateTime end = appointmentDate.plusMinutes(30);

        if (consultationRepository.existsByVetAndTimeRange(vetCode, start, end)) {
            throw new IllegalArgumentException("El veterinario ya cuenta cita registrada para el dia " + appointmentDate);
        }
    }

}