package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeferson.msvc_sav_workstaff.models.Administrative;
import com.jeferson.msvc_sav_workstaff.repositories.AdministrativeRepository;

@Service
public class AdministrativeServiceImpl implements AdministrativeService {

    private final AdministrativeRepository administrativeRepository;

    public AdministrativeServiceImpl(AdministrativeRepository administrativeRepository){
        this.administrativeRepository = administrativeRepository;
    }

    @Override
    public Optional<Administrative> findAdmistrativeById(Long idEmployee){
        return administrativeRepository.findById(idEmployee);
    }

    @Override
    public Administrative saveAdministrative(Administrative administrative){
        return administrativeRepository.save(administrative);
    }

    @Override
    @Transactional
    public void updateAdministrativeWorkArea(Long idEmployee, String workArea){
        administrativeRepository.updateWorkArea(idEmployee, workArea);
    }

}