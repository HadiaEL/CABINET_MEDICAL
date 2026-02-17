package com.cabinetmedical.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Réponse d'erreur standardisée pour les APIs
 * Utilise un record Java pour l'immutabilité
 */
@Schema(description = "Détails d'une erreur API")
public record ErrorResponse(
        @Schema(description = "Timestamp de l'erreur")
        LocalDateTime timestamp,

        @Schema(description = "Code de statut HTTP", example = "404")
        int status,

        @Schema(description = "Message d'erreur", example = "Ressource non trouvée")
        String error,

        @Schema(description = "Message détaillé", example = "Médecin non trouvé avec id: '1'")
        String message,

        @Schema(description = "Chemin de la requête", example = "/medecin/allMedecins/1")
        String path
) {
    /**
     * Constructeur statique pour créer une ErrorResponse
     */
    public static ErrorResponse of(int status, String error, String message, String path) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status,
                error,
                message,
                path
        );
    }
}

