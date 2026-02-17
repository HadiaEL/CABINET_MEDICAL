package com.cabinetmedical.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité représentant les disponibilités/horaires de travail d'un médecin
 * Définit quand un médecin est disponible pour prendre des rendez-vous
 *
 * Utilise les tables de référence pour définir les plages horaires récurrentes
 * Ex: "Dr. Martin est disponible tous les lundis de 09:00 à 12:00"
 */
@Entity
@Table(name = "disponibilites_medecin",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_medecin_jour_heure",
            columnNames = {"medecin_id", "jour_semaine_id", "heure_debut_id"}
        )
    },
    indexes = {
        @Index(name = "idx_medecin_jour", columnList = "medecin_id, jour_semaine_id")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisponibiliteMedecin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medecin_id", nullable = false)
    private Medecin medecin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jour_semaine_id", nullable = false)
    private JourSemaine jourSemaine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heure_debut_id", nullable = false)
    private HeureJour heureDebut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heure_fin_id", nullable = false)
    private HeureJour heureFin;

    @Column(nullable = false)
    private Boolean actif = true;

    @Column(length = 500)
    private String note;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

