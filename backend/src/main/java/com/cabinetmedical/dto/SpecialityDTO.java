package com.cabinetmedical.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO représentant une spécialité médicale dans les réponses API
 */
@Schema(description = "Informations d'une spécialité médicale")
public record SpecialityDTO(
        @Schema(description = "Identifiant unique de la spécialité", example = "1")
        Long id,

        @Schema(description = "Nom de la spécialité", example = "Cardiologie")
        String nom,

        @Schema(description = "Description de la spécialité", example = "Spécialiste des maladies cardiovasculaires")
        String description
) {
}

