package com.jr.sav_mvsc_medicalcontrol.services;

import com.jr.sav_mvsc_medicalcontrol.dto.TreatmentDto;
import com.jr.sav_mvsc_medicalcontrol.mapper.TreatmentMapper;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;
import com.jr.sav_mvsc_medicalcontrol.models.Treatment;
import com.jr.sav_mvsc_medicalcontrol.repositories.ConsultationRepository;
import com.jr.sav_mvsc_medicalcontrol.repositories.TreatmentRespository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRespository treatmentRespository;
    private final ConsultationRepository consultationRepository;
    private final TreatmentMapper treatmentMapper;


    public TreatmentServiceImpl(TreatmentRespository treatmentRespository, TreatmentMapper treatmentMapper, ConsultationRepository consultationRepository) {
        this.treatmentRespository = treatmentRespository;
        this.treatmentMapper = treatmentMapper;
        this.consultationRepository = consultationRepository;
    }

    @Override
    public List<TreatmentDto> findAlltreatments() {
        return treatmentRespository.findAll().stream()
                .map(treatmentMapper::toDto).toList();
    }

    @Override
    public TreatmentDto findTreatmentById(Long idTreatment){
        Treatment treatment = treatmentRespository.findById(idTreatment)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro un tratamiento asociado al id:" + idTreatment));
        return treatmentMapper.toDto(treatment);
    }

    @Override
    public TreatmentDto saveTreatment(TreatmentDto treatmentDto){
        Consultation consultation = consultationRepository.findById(treatmentDto.getIdConsultation())
            .orElseThrow(() -> new EntityNotFoundException("No se puede registrar un tratamiento sin previa consulta"));
        Treatment entity = treatmentMapper.toEntiry(treatmentDto);
        Treatment save = treatmentRespository.save(entity);
        return treatmentMapper.toDto(save);
        
    }

    @Override
    public List<TreatmentDto> findTreatmentByIdPet(Long idPet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findTreatmentByIdPet'");
    }

    
}