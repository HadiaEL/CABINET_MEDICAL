package com.cabinetmedical.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO représentant un médecin dans les réponses API
 */
@Schema(description = "Informations complètes d'un médecin")
public record MedecinDTO(
        @Schema(description = "Identifiant unique du médecin", example = "1")
        Long id,

        @Schema(description = "Nom du médecin", example = "Dupont")
        String nom,

        @Schema(description = "Prénom du médecin", example = "Jean")
        String prenom,

        @Schema(description = "Email du médecin", example = "jean.dupont@cabinet.fr")
        String email,

        @Schema(description = "Téléphone du médecin", example = "0123456789")
        String telephone,

        @Schema(description = "Numéro d'ordre du médecin", example = "ORD-12345")
        String numeroOrdre,

        @Schema(description = "Spécialité du médecin")
        SpecialiteDTO specialite
) {
}

