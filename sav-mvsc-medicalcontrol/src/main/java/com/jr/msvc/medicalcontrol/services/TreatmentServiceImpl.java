package com.jr.msvc.medicalcontrol.services;

import com.jr.msvc.medicalcontrol.mapper.TreatmentMapper;
import com.jr.msvc.medicalcontrol.dto.treatment.TreatmentRequestDto;
import com.jr.msvc.medicalcontrol.dto.treatment.TreatmentResponseDto;
import com.jr.msvc.medicalcontrol.models.Consultation;
import com.jr.msvc.medicalcontrol.models.Treatment;
import com.jr.msvc.medicalcontrol.repositories.ConsultationRepository;
import com.jr.msvc.medicalcontrol.repositories.TreatmentRespository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRespository treatmentRespository;
    private final ConsultationRepository consultationRepository;
    private final TreatmentMapper treatmentMapper;
    private final CodeMedicalControlService codeMedical;


    public TreatmentServiceImpl(TreatmentRespository treatmentRespository, TreatmentMapper treatmentMapper,
            ConsultationRepository consultationRepository, CodeMedicalControlService codeMedical) {
        this.treatmentRespository = treatmentRespository;
        this.treatmentMapper = treatmentMapper;
        this.consultationRepository = consultationRepository;
        this.codeMedical = codeMedical;
    }

    @Override
    public TreatmentResponseDto saveTreatment(TreatmentRequestDto treatmentDto) {
        Consultation consultation = findTreatmentByConsultation(treatmentDto.getConsultationCode());

        //Validar Datos
        validataDates(treatmentDto.getStartDate(), treatmentDto.getEndDate(), consultation.getCitationDate());

        Treatment entity = treatmentMapper.toEntiry(treatmentDto);
        entity.setConsultation(consultation);
        entity.setRegistrationDate(LocalDateTime.now());

        //Agreganado codigo dinamico a la mascota
        String petCode = consultation.getPet().getPetCode();
        int count = treatmentRespository.countTreatmentByPetCode(petCode);
        String code = codeMedical.generateMedicalCode("TR", petCode,entity.getRegistrationDate(), count);
        entity.setTreatmentCode(code);

        entity.setPetName(consultation.getPetName());
        Treatment save = treatmentRespository.save(entity);
        return treatmentMapper.toDto(save);
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
    public TreatmentResponseDto findTreatmentByCode(String treatmentCode) {
        Treatment treatment = treatmentRespository.findByTreatmentCode(treatmentCode)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro tratamiento asociado al codigo: "+ treatmentCode));
        return treatmentMapper.toDto(treatment);
    }

    @Override
    public List<TreatmentResponseDto> findTreatmentByPetCode(String petCode) {
        List<Treatment> treatments = treatmentRespository.findAllTreatmentsByPetCode(petCode);
        if (treatments.isEmpty()) {
            throw new EntityNotFoundException("No se encontro tratamiento asociado al paciente");
        }
        return treatments.stream().map(treatmentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TreatmentResponseDto> findTreatmentByConsultationCode(String consultationCode) {
        List<Treatment> treatments = treatmentRespository.findAllTreatmentByIdcosultation(consultationCode);
        if (treatments.isEmpty()) {
            throw new EntityNotFoundException("No se encontro tratamiento asociado a la consulta: " + consultationCode);
        }
        return treatments.stream().map(treatmentMapper::toDto).collect(Collectors.toList());
    }

    //helpers
    private Consultation findTreatmentByConsultation(String consultatioCode){
         Consultation consultation = consultationRepository.findByConsultationCode(consultatioCode)
                .orElseThrow(() -> new EntityNotFoundException(
                    "No se encontro la consulta "+consultatioCode +", No se puede registrar un tratamiento sin previa consulta"));
        return consultation;
    }

    private void validataDates(LocalDate startDate, LocalDate endDate, LocalDateTime citationDate){
        if (startDate.isAfter(endDate) ) {
            throw new IllegalArgumentException("Error en el cronograma de fechas del tratamiento");
        }

        if (startDate.isBefore(citationDate.toLocalDate())) {
            throw new IllegalArgumentException(
                    "La fecha de inicio del tratamiento no puede ser anterior a la fecha de la cita");
        }
    }
}