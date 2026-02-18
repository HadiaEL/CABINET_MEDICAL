package com.cabinetmedical.service;

import com.cabinetmedical.dto.SpecialityDTO;

import java.util.List;

/**
 * Interface du service de gestion des spécialités médicales
 */
public interface SpecialityService {

    /**
     * Récupère toutes les spécialités triées par nom
     *
     * @return Liste de toutes les spécialités
     */
    List<SpecialityDTO> getAllSpecialities();
}

