package com.cabinetmedical.controller;

import com.cabinetmedical.dto.SpecialityDTO;
import com.cabinetmedical.exception.ErrorResponse;
import com.cabinetmedical.service.SpecialityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST pour la gestion des spécialités médicales
 */
@RestController
@RequestMapping("/speciality")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"}, allowCredentials = "true")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Spécialités API", description = "API REST pour la gestion des spécialités médicales")
public class SpecialityController {

    private final SpecialityService specialityService;

    /**
     * Récupère la liste de toutes les spécialités
     */
    @Operation(
            summary = "Récupérer toutes les spécialités",
            description = "Endpoint REST API pour récupérer la liste complète des spécialités médicales triées par nom. " +
                         "Utilisé pour alimenter les listes déroulantes et les filtres."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des spécialités récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SpecialityDTO.class))
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
    @GetMapping("/allSpecialities")
    public ResponseEntity<List<SpecialityDTO>> getAllSpecialities() {
        log.info("GET /speciality/allSpecialities");

        List<SpecialityDTO> specialities = specialityService.getAllSpecialities();

        log.info("Nombre de spécialités retournées: {}", specialities.size());

        return ResponseEntity.ok(specialities);
    }
}

