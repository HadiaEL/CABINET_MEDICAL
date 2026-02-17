package com.cabinetmedical.service;

import com.cabinetmedical.dto.DoctorDTO;
import com.cabinetmedical.dto.PageResponse;
import org.springframework.data.domain.Pageable;

/**
 * Interface du service de gestion des médecins
 * Version simplifiée: récupération paginée uniquement
 */
public interface DoctorService {

    /**
     * Récupère tous les médecins avec pagination et tri
     *
     * @param pageable Configuration de pagination et tri
     * @return Page de médecins avec leurs spécialités
     */
    PageResponse<DoctorDTO> getAllDoctors(Pageable pageable);
}

