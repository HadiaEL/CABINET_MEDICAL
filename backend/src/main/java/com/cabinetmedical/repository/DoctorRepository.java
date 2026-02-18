package com.cabinetmedical.repository;

import com.cabinetmedical.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository simplifié pour l'entité Doctor
 * Récupération paginée avec FETCH JOIN optimisé
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    /**
     * Récupère tous les médecins avec leur spécialité (FETCH JOIN pour éviter N+1)
     */
    @Query("""
            SELECT DISTINCT d FROM Doctor d
            LEFT JOIN FETCH d.speciality
            """)
    Page<Doctor> findAllWithSpecialite(Pageable pageable);

    /**
     * Récupère les médecins filtrés par ID de spécialité
     * @param specialityId ID de la spécialité à filtrer
     * @param pageable Configuration de pagination et tri
     * @return Page de médecins correspondant à la spécialité
     */
    @Query("""
            SELECT DISTINCT d FROM Doctor d
            LEFT JOIN FETCH d.speciality s
            WHERE s.id = :specialityId
            """)
    Page<Doctor> findBySpecialityId(Long specialityId, Pageable pageable);
}

