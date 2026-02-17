package com.cabinetmedical.service;

import com.cabinetmedical.dto.DoctorDTO;
import com.cabinetmedical.dto.PageResponse;
import com.cabinetmedical.entity.Doctor;
import com.cabinetmedical.mapper.DoctorMapper;
import com.cabinetmedical.repository.DoctorRepository;
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
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    /**
     * Récupère tous les médecins avec pagination et tri
     */
    @Override
    public PageResponse<DoctorDTO> getAllDoctors(Pageable pageable) {
        log.debug("Récupération de tous les médecins - page: {}", pageable.getPageNumber());

        // Récupération avec FETCH JOIN pour optimiser (éviter N+1)
        Page<Doctor> doctorPage = doctorRepository.findAllWithSpecialite(pageable);

        // Conversion avec MapStruct
        Page<DoctorDTO> dtoPage = doctorPage.map(doctorMapper::toDTO);

        log.info("Trouvé {} médecins (page {}/{})",
                 dtoPage.getTotalElements(),
                 dtoPage.getNumber() + 1,
                 dtoPage.getTotalPages());

        return PageResponse.of(dtoPage);
    }
}

