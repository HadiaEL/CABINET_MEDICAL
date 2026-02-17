package com.cabinetmedical.service;

import com.cabinetmedical.dto.MedecinDTO;
import com.cabinetmedical.dto.PageResponse;
import org.springframework.data.domain.Pageable;

/**
 * Interface du service de gestion des médecins
 * Version simplifiée: récupération paginée uniquement
 */
public interface MedecinService {

    /**
     * Récupère tous les médecins avec pagination et tri
     *
     * @param pageable Configuration de pagination et tri
     * @return Page de médecins avec leurs spécialités
     */
    PageResponse<MedecinDTO> getAllMedecins(Pageable pageable);
}

