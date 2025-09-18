package com.jr.sav_mvsc_medicalcontrol.services;

import com.jr.sav_mvsc_medicalcontrol.dto.treatment.TreatmentRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.treatment.TreatmentResponseDto;
import com.jr.sav_mvsc_medicalcontrol.mapper.TreatmentMapper;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;
import com.jr.sav_mvsc_medicalcontrol.models.Treatment;
import com.jr.sav_mvsc_medicalcontrol.repositories.ConsultationRepository;
import com.jr.sav_mvsc_medicalcontrol.repositories.TreatmentRespository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRespository treatmentRespository;
    private final ConsultationRepository consultationRepository;
    private final TreatmentMapper treatmentMapper;

    public TreatmentServiceImpl(TreatmentRespository treatmentRespository, TreatmentMapper treatmentMapper,
            ConsultationRepository consultationRepository) {
        this.treatmentRespository = treatmentRespository;
        this.treatmentMapper = treatmentMapper;
        this.consultationRepository = consultationRepository;
    }

    @Override
    public List<TreatmentResponseDto> findAlltreatments() {
        List<Treatment> treatments = treatmentRespository.findAll();
        if (treatments.isEmpty()) {
            throw new EntityNotFoundException("No se encuentran tratamientos registrados");
        }
        return treatments.stream().map(treatmentMapper::toDto).toList();
    }

    @Override
    public TreatmentResponseDto findTreatmentById(Long idTreatment) {
        Treatment treatment = treatmentRespository.findById(idTreatment)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro tratamiento asociado al id:" + idTreatment));
        return treatmentMapper.toDto(treatment);
    }

    @Override
    public TreatmentResponseDto saveTreatment(TreatmentRequestDto treatmentDto) {
        Consultation consultation = consultationRepository.findById(treatmentDto.getIdConsultation())
                .orElseThrow(() -> new EntityNotFoundException(
                    "No se encontro la consulta "+treatmentDto.getIdConsultation()+" No se puede registrar un tratamiento sin previa consulta"));

        if (treatmentDto.getStartDate().isAfter(treatmentDto.getEndDate())) {
            throw new IllegalArgumentException("Error en el cronograma de fechas del tratamiento");
        }

        if (treatmentDto.getStartDate().isBefore(consultation.getCitationDate().toLocalDate())) {
            throw new IllegalArgumentException(
                    "La fecha de inicio del tratamiento no puede ser posterior a la fecha de la cita");
        }

        Treatment entity = treatmentMapper.toEntiry(treatmentDto);
        entity.setRegistrationDate(LocalDate.now());
        entity.setPetName(consultation.getPetName());
        entity.setConsultation(consultation);
        Treatment save = treatmentRespository.save(entity);
        return treatmentMapper.toDto(save);
    }

    @Override
    public List<TreatmentResponseDto> findTreatmentByIdPet(Long idPet) {
        List<Treatment> treatments = treatmentRespository.findAllTreatmentsByIdpet(idPet);
        if (treatments.isEmpty()) {
            throw new EntityNotFoundException("No se encontro tratamiento asociado al paciente id: " + idPet);
        }
        return treatments.stream().map(treatmentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TreatmentResponseDto> findTreatmentByIdConsultation(Long idConsultation) {
        List<Treatment> treatments = treatmentRespository.findAllTreatmentByIdcosultation(idConsultation);
        if (treatments.isEmpty()) {
            throw new EntityNotFoundException("No se encontro tratamiento asociado a la consutla id: " + idConsultation);
        }
        return treatments.stream().map(treatmentMapper::toDto).collect(Collectors.toList());
    }

}