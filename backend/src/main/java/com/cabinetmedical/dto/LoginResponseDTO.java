package com.cabinetmedical.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO pour la réponse d'authentification d'un patient
 */
@Schema(description = "Réponse d'authentification contenant les informations du patient")
public record LoginResponseDTO(
        @Schema(description = "Identifiant unique du patient", example = "1")
        Long id,

        @Schema(description = "Nom du patient", example = "Durand")
        String nom,

        @Schema(description = "Prénom du patient", example = "Marie")
        String prenom,

        @Schema(description = "Email du patient", example = "marie.durand@email.fr")
        String email,

        @Schema(description = "Rôle de l'utilisateur", example = "PATIENT")
        String role
) {
}

