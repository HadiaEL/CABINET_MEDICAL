package com.cabinetmedical.service;

import com.cabinetmedical.dto.MedecinDTO;
import com.cabinetmedical.dto.PageResponse;
import com.cabinetmedical.entity.Medecin;
import com.cabinetmedical.mapper.MedecinMapper;
import com.cabinetmedical.repository.MedecinRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation simplifiée du service de gestion des médecins
 * Récupération paginée avec tri uniquement
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MedecinServiceImpl implements MedecinService {

    private final MedecinRepository medecinRepository;
    private final MedecinMapper medecinMapper;

    /**
     * Récupère tous les médecins avec pagination et tri
     */
    @Override
    public PageResponse<MedecinDTO> getAllMedecins(Pageable pageable) {
        log.debug("Récupération de tous les médecins - page: {}", pageable.getPageNumber());

        // Récupération avec FETCH JOIN pour optimiser (éviter N+1)
        Page<Medecin> medecinPage = medecinRepository.findAllWithSpecialite(pageable);

        // Conversion avec MapStruct
        Page<MedecinDTO> dtoPage = medecinPage.map(medecinMapper::toDTO);

        log.info("Trouvé {} médecins (page {}/{})",
                 dtoPage.getTotalElements(),
                 dtoPage.getNumber() + 1,
                 dtoPage.getTotalPages());

        return PageResponse.of(dtoPage);
    }
}

