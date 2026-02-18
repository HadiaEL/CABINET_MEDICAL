package com.cabinetmedical.repository;

import com.cabinetmedical.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité Speciality
 */
@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

    /**
     * Récupère toutes les spécialités triées par nom
     */
    List<Speciality> findAllByOrderByNomAsc();
}

