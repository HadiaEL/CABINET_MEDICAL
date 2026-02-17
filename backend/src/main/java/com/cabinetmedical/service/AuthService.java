package com.cabinetmedical.service;

import com.cabinetmedical.dto.LoginRequestDTO;
import com.cabinetmedical.dto.LoginResponseDTO;
import com.cabinetmedical.entity.Patient;
import com.cabinetmedical.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service pour l'authentification des patients
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthService {

    private final PatientRepository patientRepository;

    /**
     * Authentifie un patient avec son email et son téléphone
     * @param loginRequest requête d'authentification
     * @return les informations du patient authentifié
     * @throws RuntimeException si l'authentification échoue
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        log.info("Tentative d'authentification pour l'email: {}", loginRequest.email());

        Patient patient = patientRepository.findByEmailAndTelephone(
                loginRequest.email(),
                loginRequest.telephone()
        ).orElseThrow(() -> {
            log.warn("Échec de l'authentification pour l'email: {}", loginRequest.email());
            return new RuntimeException("Email ou mot de passe incorrect");
        });

        log.info("Authentification réussie pour le patient: {} {}", patient.getPrenom(), patient.getNom());

        return new LoginResponseDTO(
                patient.getId(),
                patient.getNom(),
                patient.getPrenom(),
                patient.getEmail(),
                "PATIENT"
        );
    }
}

