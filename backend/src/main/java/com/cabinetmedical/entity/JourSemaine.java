package com.cabinetmedical.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité représentant un jour de la semaine
 */
@Entity
@Table(name = "jours_semaine")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JourSemaine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String nom;

    @Column(name = "numero_jour", nullable = false, unique = true)
    private Integer numeroJour; // 1=Lundi, 2=Mardi, ..., 7=Dimanche

    @Column(nullable = false)
    private Boolean ouvrable = true;

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

