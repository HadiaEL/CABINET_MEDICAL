package com.cabinetmedical.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité représentant un rendez-vous médical
 * Contient toutes les informations nécessaires : patient, médecin, date/heure
 *
 * Approche simplifiée :
 * - Une seule table pour les rendez-vous
 * - Date et heure complètes (jour/mois/année + heure)
 * - Durée fixe d'1 heure par défaut
 */
@Entity
@Table(name = "rendez_vous",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_medecin_date_heure",
            columnNames = {"medecin_id", "date_heure_debut"}
        )
    },
    indexes = {
        @Index(name = "idx_patient_date", columnList = "patient_id, date_heure_debut"),
        @Index(name = "idx_medecin_date", columnList = "medecin_id, date_heure_debut"),
        @Index(name = "idx_statut", columnList = "statut")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medecin_id", nullable = false)
    private Medecin medecin;

    @Column(name = "date_heure_debut", nullable = false)
    private LocalDateTime dateHeureDebut;

    @Column(name = "date_heure_fin", nullable = false)
    private LocalDateTime dateHeureFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutRendezVous statut = StatutRendezVous.CONFIRME;

    @Column(length = 1000)
    private String motif;

    @Column(length = 2000)
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        // Si heure de fin non définie, ajouter 1 heure par défaut
        if (dateHeureFin == null && dateHeureDebut != null) {
            dateHeureFin = dateHeureDebut.plusHours(1);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Vérifie si ce rendez-vous chevauche un autre rendez-vous
     */
    public boolean chevauche(RendezVous autre) {
        if (autre == null || !this.medecin.getId().equals(autre.getMedecin().getId())) {
            return false;
        }

        return this.dateHeureDebut.isBefore(autre.getDateHeureFin())
            && autre.getDateHeureDebut().isBefore(this.dateHeureFin);
    }

    /**
     * Énumération des statuts possibles d'un rendez-vous
     */
    public enum StatutRendezVous {
        CONFIRME,
        EN_ATTENTE,
        ANNULE,
        TERMINE,
        ABSENT
    }
}

