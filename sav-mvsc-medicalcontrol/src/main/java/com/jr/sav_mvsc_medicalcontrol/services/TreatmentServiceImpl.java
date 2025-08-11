package com.jr.sav_mvsc_medicalcontrol.services;

import com.jr.sav_mvsc_medicalcontrol.dto.treatment.TreatmentDto;
import com.jr.sav_mvsc_medicalcontrol.dto.treatment.TreatmentResponseDto;
import com.jr.sav_mvsc_medicalcontrol.mapper.TreatmentMapper;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;
import com.jr.sav_mvsc_medicalcontrol.models.Treatment;
import com.jr.sav_mvsc_medicalcontrol.repositories.ConsultationRepository;
import com.jr.sav_mvsc_medicalcontrol.repositories.TreatmentRespository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
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
        return treatmentRespository.findAll().stream()
                .map(treatmentMapper::toDto).toList();
    }

    @Override
    public TreatmentResponseDto findTreatmentById(Long idTreatment) {
        Treatment treatment = treatmentRespository.findById(idTreatment)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro un tratamiento asociado al id:" + idTreatment));
        return treatmentMapper.toDto(treatment);
    }

    @Override
    public TreatmentResponseDto saveTreatment(TreatmentDto treatmentDto) {
        Consultation consultation = consultationRepository.findById(treatmentDto.getIdConsultation())
                .orElseThrow(
                        () -> new EntityNotFoundException("No se puede registrar un tratamiento sin previa consulta"));

        if (!treatmentDto.getStartDate().isAfter(treatmentDto.getEndDate()) ||
                treatmentDto.getStartDate().isBefore(consultation.getCitationDate().toLocalDate())) {

            throw new IllegalArgumentException("Error en el cronograma de fechas del tratamiento");
        }

        Treatment entity = treatmentMapper.toEntiry(treatmentDto);
        Treatment save = treatmentRespository.save(entity);
        return treatmentMapper.toDto(save);
    }

    @Override
    public List<TreatmentResponseDto> findTreatmentByIdPet(Long idPet) {
        return treatmentRespository.findAllTreatmentsByIdpet(idPet).stream()
                .map(treatmentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TreatmentResponseDto> findTreatmentByIdConsultation(Long idConsultation) {
        return treatmentRespository.findAllTreatmentByIdcosultation(idConsultation).stream()
                .map(treatmentMapper::toDto).collect(Collectors.toList());
    }

}