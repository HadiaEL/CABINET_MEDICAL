package com.cabinetmedical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * DTO représentant une réponse paginée
 * Utilise les records de Java pour l'immutabilité
 */
@Schema(description = "Réponse paginée contenant une liste d'éléments")
public record PageResponse<T>(
        @Schema(description = "Liste des éléments de la page actuelle")
        List<T> content,

        @Schema(description = "Numéro de la page actuelle (commence à 0)", example = "0")
        int pageNumber,

        @Schema(description = "Taille de la page", example = "9")
        int pageSize,

        @Schema(description = "Nombre total d'éléments", example = "50")
        long totalElements,

        @Schema(description = "Nombre total de pages", example = "5")
        int totalPages,

        @Schema(description = "Indique si c'est la première page", example = "true")
        boolean first,

        @Schema(description = "Indique si c'est la dernière page", example = "false")
        boolean last,

        @Schema(description = "Indique si la page est vide", example = "false")
        boolean empty
) {
    /**
     * Constructeur à partir d'une Page Spring Data
     */
    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty()
        );
    }
}

