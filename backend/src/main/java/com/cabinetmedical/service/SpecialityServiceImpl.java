package com.cabinetmedical.service;

import com.cabinetmedical.dto.SpecialityDTO;
import com.cabinetmedical.entity.Speciality;
import com.cabinetmedical.mapper.SpecialityMapper;
import com.cabinetmedical.repository.SpecialityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des spécialités médicales
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository specialityRepository;
    private final SpecialityMapper specialityMapper;

    /**
     * Récupère toutes les spécialités triées par nom
     */
    @Override
    public List<SpecialityDTO> getAllSpecialities() {
        log.info("Récupération de toutes les spécialités");

        List<Speciality> specialities = specialityRepository.findAllByOrderByNomAsc();

        log.info("Nombre de spécialités récupérées: {}", specialities.size());

        return specialities.stream()
                .map(specialityMapper::toDTO)
                .collect(Collectors.toList());
    }
}

