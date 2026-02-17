package com.cabinetmedical.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO pour la requête d'authentification d'un patient
 */
@Schema(description = "Requête d'authentification pour un patient")
public record LoginRequestDTO(
        @Schema(description = "Email du patient", example = "marie.durand@email.fr", required = true)
        String email,

        @Schema(description = "Numéro de téléphone du patient (mot de passe)", example = "0601020304", required = true)
        String telephone
) {
}

