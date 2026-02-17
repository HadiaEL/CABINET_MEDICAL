package com.cabinetmedical.repository;

import com.cabinetmedical.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour la gestion des patients
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Trouve un patient par son email et son téléphone
     * @param email email du patient
     * @param telephone téléphone du patient
     * @return Optional contenant le patient s'il existe
     */
    Optional<Patient> findByEmailAndTelephone(String email, String telephone);

    /**
     * Trouve un patient par son email
     * @param email email du patient
     * @return Optional contenant le patient s'il existe
     */
    Optional<Patient> findByEmail(String email);
}

