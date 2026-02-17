package com.cabinetmedical.repository;

import com.cabinetmedical.entity.Medecin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository simplifié pour l'entité Medecin
 * Récupération paginée avec FETCH JOIN optimisé
 */
@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {

    /**
     * Récupère tous les médecins avec leur spécialité (FETCH JOIN pour éviter N+1)
     */
    @Query("""
            SELECT DISTINCT m FROM Medecin m 
            LEFT JOIN FETCH m.specialite
            """)
    Page<Medecin> findAllWithSpecialite(Pageable pageable);
}

