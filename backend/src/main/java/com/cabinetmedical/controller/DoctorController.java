package com.cabinetmedical.controller;

import com.cabinetmedical.dto.DoctorDTO;
import com.cabinetmedical.dto.PageResponse;
import com.cabinetmedical.exception.ErrorResponse;
import com.cabinetmedical.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST pour la récupération de la liste des médecins
 * Endpoint simplifié: pagination et tri uniquement
 */
@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"}, allowCredentials = "true")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Médecins API", description = "API REST pour la gestion des médecins du cabinet médical - Microservice")
public class DoctorController {

    private final DoctorService doctorService;

    /**
     * Récupère la liste paginée de tous les médecins avec tri
     */
    @Operation(
            summary = "Récupérer tous les médecins avec pagination et tri",
            description = "Endpoint REST API pour récupérer la liste paginée de tous les médecins avec leurs spécialités. " +
                         "Retourne un objet paginé avec la liste des médecins, le nombre de pages, le nombre total d'éléments et les informations de navigation. " +
                         "Supporte le tri par différents champs de la table médecin."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des médecins récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Paramètres de requête invalides",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur interne du serveur",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/allDoctors")
    public ResponseEntity<PageResponse<DoctorDTO>> getAllDoctors(
            @Parameter(description = "Numéro de la page (commence à 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Nombre d'éléments par page (taille)", example = "9")
            @RequestParam(defaultValue = "9") int size,

            @Parameter(
                    description = "Champ pour le tri (nom, prenom, numeroOrdre, email, telephone, specialite)",
                    example = "nom"
            )
            @RequestParam(defaultValue = "nom") String sortBy,

            @Parameter(
                    description = "Direction du tri (asc ou desc)",
                    example = "asc"
            )
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        log.info("GET /doctor/allDoctors - page: {}, size: {}, sortBy: {}, sortDirection: {}",
                 page, size, sortBy, sortDirection);

        // Validation des paramètres
        validatePaginationParams(page, size);

        // Création du Pageable avec tri
        Sort sort = createSort(sortBy, sortDirection);
        Pageable pageable = PageRequest.of(page, size, sort);

        // Récupération des données
        PageResponse<DoctorDTO> response = doctorService.getAllDoctors(pageable);

        return ResponseEntity.ok(response);
    }

    /**
     * Valide les paramètres de pagination
     */
    private void validatePaginationParams(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Le numéro de page ne peut pas être négatif");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("La taille de la page doit être supérieure à 0");
        }
        if (size > 100) {
            throw new IllegalArgumentException("La taille de la page ne peut pas dépasser 100");
        }
    }

    /**
     * Crée l'objet Sort basé sur les paramètres
     */
    private Sort createSort(String sortBy, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        // Validation du champ de tri
        return switch (sortBy.toLowerCase()) {
            case "nom", "prenom", "numeroordre", "email", "telephone" ->
                    Sort.by(direction, sortBy);
            case "specialite" ->
                    Sort.by(direction, "specialite.nom");
            default -> {
                log.warn("Champ de tri invalide: {}. Utilisation de 'nom' par défaut", sortBy);
                yield Sort.by(direction, "nom");
            }
        };
    }
}

