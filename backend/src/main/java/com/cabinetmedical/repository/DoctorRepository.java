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
}

