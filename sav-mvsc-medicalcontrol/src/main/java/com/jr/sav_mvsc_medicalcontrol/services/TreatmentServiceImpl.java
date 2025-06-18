package com.jr.sav_mvsc_medicalcontrol.services;

import com.jr.sav_mvsc_medicalcontrol.dto.TreatmentDto;
import com.jr.sav_mvsc_medicalcontrol.mapper.TreatmentMapper;
import com.jr.sav_mvsc_medicalcontrol.models.Treatment;
import com.jr.sav_mvsc_medicalcontrol.repositories.TreatmentRespository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRespository treatmentRespository;
    private final ConsultationService consultationService;
    private final TreatmentMapper treatmentMapper;


    public TreatmentServiceImpl(TreatmentRespository treatmentRespository, TreatmentMapper treatmentMapper, ConsultationService consultationService) {
        this.treatmentRespository = treatmentRespository;
        this.treatmentMapper = treatmentMapper;
        this.consultationService = consultationService;
    }

    @Override
    public List<TreatmentDto> findAlltreatments() {
        return treatmentRespository.findAll().stream()
                .map(treatmentMapper::toDto).toList();
    }

    @Override
    public Optional<TreatmentDto> findTreatmentById(Long idTreatment){
        Optional<Treatment> optTreatment = treatmentRespository.findById(idTreatment);
        if (optTreatment.isEmpty()){
            return Optional.empty();
        }
        TreatmentDto dto = treatmentMapper.toDto(optTreatment.get());
        return  Optional.of(dto);
    }

    @Override
    public Optional<TreatmentDto> saveTreatment(TreatmentDto treatmentDto){
        if (consultationService.finConsultionById(treatmentDto.getIdConsultation()).isEmpty()){
            return Optional.empty();
        }
        Treatment entity = treatmentMapper.toEntiry(treatmentDto);
        return Optional.of(treatmentMapper.toDto(treatmentRespository.save(entity)));
    }

    @Override
    public void deleteTreatment(Long idTreatment){
        if (treatmentRespository.findById(idTreatment).isEmpty()){
            throw new EntityNotFoundException();
        }
        treatmentRespository.deleteById(idTreatment);
    }

}