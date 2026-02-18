package com.cabinetmedical.controller;

import com.cabinetmedical.dto.DoctorDTO;
import com.cabinetmedical.dto.PageResponse;
import com.cabinetmedical.dto.SpecialityDTO;
import com.cabinetmedical.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests unitaires pour DoctorController - Endpoint getAllDoctors
 */
@WebMvcTest(DoctorController.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires - DoctorController.getAllDoctors()")
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private DoctorService doctorService;

    private List<DoctorDTO> mockDoctors;
    private PageResponse<DoctorDTO> mockPageResponse;

    @BeforeEach
    void setUp() {
        // Préparer les données de test
        SpecialityDTO cardiology = new SpecialityDTO(1L, "Cardiologie", "Spécialiste du cœur");
        SpecialityDTO dermatology = new SpecialityDTO(2L, "Dermatologie", "Spécialiste de la peau");

        mockDoctors = Arrays.asList(
                new DoctorDTO(1L, "Dupont", "Jean", "jean.dupont@cabinet.fr", "0123456789", "ORD-12345", cardiology),
                new DoctorDTO(2L, "Martin", "Marie", "marie.martin@cabinet.fr", "0987654321", "ORD-67890", dermatology),
                new DoctorDTO(3L, "Bernard", "Paul", "paul.bernard@cabinet.fr", "0147258369", "ORD-11111", cardiology)
        );

        mockPageResponse = new PageResponse<>(
                mockDoctors,
                0,      // pageNumber
                9,     // pageSize
                3L,     // totalElements
                1,      // totalPages
                true,   // first
                true,   // last
                false   // empty
        );
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait retourner la liste paginée des médecins avec succès")
    void getAllDoctors_ShouldReturnPagedDoctors_WhenSuccessful() throws Exception {
        // Given
        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(mockPageResponse);

        // When & Then
        mockMvc.perform(get("/doctor/allDoctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].nom").value("Dupont"))
                .andExpect(jsonPath("$.content[0].prenom").value("Jean"))
                .andExpect(jsonPath("$.content[0].email").value("jean.dupont@cabinet.fr"))
                .andExpect(jsonPath("$.content[0].telephone").value("0123456789"))
                .andExpect(jsonPath("$.content[0].numeroOrdre").value("ORD-12345"))
                .andExpect(jsonPath("$.content[0].specialite.nom").value("Cardiologie"))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(9))
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true))
                .andExpect(jsonPath("$.empty").value(false));

        verify(doctorService, times(1)).getAllDoctors(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait utiliser les paramètres de pagination par défaut")
    void getAllDoctors_ShouldUseDefaultPaginationParams_WhenNoParamsProvided() throws Exception {
        // Given
        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(mockPageResponse);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        // When
        mockMvc.perform(get("/doctor/allDoctors"))
                .andExpect(status().isOk());

        // Then
        verify(doctorService).getAllDoctors(pageableCaptor.capture());
        Pageable capturedPageable = pageableCaptor.getValue();

        assertThat(capturedPageable.getPageNumber()).isEqualTo(0);
        assertThat(capturedPageable.getPageSize()).isEqualTo(9);
        assertThat(capturedPageable.getSort().getOrderFor("nom")).isNotNull();
        assertThat(capturedPageable.getSort().getOrderFor("nom").getDirection()).isEqualTo(Sort.Direction.ASC);
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait accepter les paramètres de pagination personnalisés")
    void getAllDoctors_ShouldAcceptCustomPaginationParams() throws Exception {
        // Given
        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(mockPageResponse);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        // When
        mockMvc.perform(get("/doctor/allDoctors")
                        .param("page", "2")
                        .param("size", "20")
                        .param("sortBy", "prenom")
                        .param("sortDirection", "desc"))
                .andExpect(status().isOk());

        // Then
        verify(doctorService).getAllDoctors(pageableCaptor.capture());
        Pageable capturedPageable = pageableCaptor.getValue();

        assertThat(capturedPageable.getPageNumber()).isEqualTo(2);
        assertThat(capturedPageable.getPageSize()).isEqualTo(20);
        assertThat(capturedPageable.getSort().getOrderFor("prenom")).isNotNull();
        assertThat(capturedPageable.getSort().getOrderFor("prenom").getDirection()).isEqualTo(Sort.Direction.DESC);
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait trier par nom en ordre croissant")
    void getAllDoctors_ShouldSortByNameAscending() throws Exception {
        // Given
        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(mockPageResponse);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        // When
        mockMvc.perform(get("/doctor/allDoctors")
                        .param("sortBy", "nom")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk());

        // Then
        verify(doctorService).getAllDoctors(pageableCaptor.capture());
        Pageable capturedPageable = pageableCaptor.getValue();

        assertThat(capturedPageable.getSort().getOrderFor("nom")).isNotNull();
        assertThat(capturedPageable.getSort().getOrderFor("nom").getDirection()).isEqualTo(Sort.Direction.ASC);
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait trier par email en ordre décroissant")
    void getAllDoctors_ShouldSortByEmailDescending() throws Exception {
        // Given
        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(mockPageResponse);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        // When
        mockMvc.perform(get("/doctor/allDoctors")
                        .param("sortBy", "email")
                        .param("sortDirection", "desc"))
                .andExpect(status().isOk());

        // Then
        verify(doctorService).getAllDoctors(pageableCaptor.capture());
        Pageable capturedPageable = pageableCaptor.getValue();

        assertThat(capturedPageable.getSort().getOrderFor("email")).isNotNull();
        assertThat(capturedPageable.getSort().getOrderFor("email").getDirection()).isEqualTo(Sort.Direction.DESC);
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait trier par spécialité")
    void getAllDoctors_ShouldSortBySpeciality() throws Exception {
        // Given
        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(mockPageResponse);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        // When
        mockMvc.perform(get("/doctor/allDoctors")
                        .param("sortBy", "specialite")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk());

        // Then
        verify(doctorService).getAllDoctors(pageableCaptor.capture());
        Pageable capturedPageable = pageableCaptor.getValue();

        // Le tri par spécialité devrait être fait sur "specialite.nom"
        assertThat(capturedPageable.getSort().getOrderFor("specialite.nom")).isNotNull();
        assertThat(capturedPageable.getSort().getOrderFor("specialite.nom").getDirection()).isEqualTo(Sort.Direction.ASC);
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait utiliser 'nom' par défaut pour un champ de tri invalide")
    void getAllDoctors_ShouldUseDefaultSortField_WhenInvalidSortByProvided() throws Exception {
        // Given
        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(mockPageResponse);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        // When
        mockMvc.perform(get("/doctor/allDoctors")
                        .param("sortBy", "champsInvalide")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk());

        // Then
        verify(doctorService).getAllDoctors(pageableCaptor.capture());
        Pageable capturedPageable = pageableCaptor.getValue();

        // Devrait utiliser "nom" comme champ par défaut
        assertThat(capturedPageable.getSort().getOrderFor("nom")).isNotNull();
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait retourner une erreur 400 pour un numéro de page négatif")
    void getAllDoctors_ShouldReturn400_WhenPageNumberIsNegative() throws Exception {
        // When & Then
        mockMvc.perform(get("/doctor/allDoctors")
                        .param("page", "-1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Le numéro de page ne peut pas être négatif"));

        verify(doctorService, never()).getAllDoctors(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait retourner une erreur 400 pour une taille de page égale à 0")
    void getAllDoctors_ShouldReturn400_WhenPageSizeIsZero() throws Exception {
        // When & Then
        mockMvc.perform(get("/doctor/allDoctors")
                        .param("size", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("La taille de la page doit être supérieure à 0"));

        verify(doctorService, never()).getAllDoctors(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait retourner une erreur 400 pour une taille de page négative")
    void getAllDoctors_ShouldReturn400_WhenPageSizeIsNegative() throws Exception {
        // When & Then
        mockMvc.perform(get("/doctor/allDoctors")
                        .param("size", "-5"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("La taille de la page doit être supérieure à 0"));

        verify(doctorService, never()).getAllDoctors(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait retourner une erreur 400 pour une taille de page supérieure à 100")
    void getAllDoctors_ShouldReturn400_WhenPageSizeExceeds100() throws Exception {
        // When & Then
        mockMvc.perform(get("/doctor/allDoctors")
                        .param("size", "101"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("La taille de la page ne peut pas dépasser 100"));

        verify(doctorService, never()).getAllDoctors(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait accepter une taille de page de 100 exactement")
    void getAllDoctors_ShouldAcceptPageSizeOf100() throws Exception {
        // Given
        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(mockPageResponse);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        // When
        mockMvc.perform(get("/doctor/allDoctors")
                        .param("size", "100"))
                .andExpect(status().isOk());

        // Then
        verify(doctorService).getAllDoctors(pageableCaptor.capture());
        Pageable capturedPageable = pageableCaptor.getValue();

        assertThat(capturedPageable.getPageSize()).isEqualTo(100);
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait retourner une page vide quand aucun médecin n'existe")
    void getAllDoctors_ShouldReturnEmptyPage_WhenNoDoctorsExist() throws Exception {
        // Given
        PageResponse<DoctorDTO> emptyPageResponse = new PageResponse<>(
                List.of(),
                0,
                9,
                0L,
                0,
                true,
                true,
                true
        );

        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(emptyPageResponse);

        // When & Then
        mockMvc.perform(get("/doctor/allDoctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(0))
                .andExpect(jsonPath("$.empty").value(true));

        verify(doctorService, times(1)).getAllDoctors(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait gérer correctement la pagination pour une grande liste")
    void getAllDoctors_ShouldHandlePaginationForLargeList() throws Exception {
        // Given
        PageResponse<DoctorDTO> secondPageResponse = new PageResponse<>(
                mockDoctors.subList(0, 2),  // Simuler la page 2 avec 2 éléments
                1,      // pageNumber = 1 (deuxième page)
                9,     // pageSize
                25L,    // totalElements
                3,      // totalPages
                false,  // first = false (pas la première page)
                false,  // last = false (pas la dernière page)
                false   // empty
        );

        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(secondPageResponse);

        // When & Then
        mockMvc.perform(get("/doctor/allDoctors")
                        .param("page", "1")
                        .param("size", "9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber").value(1))
                .andExpect(jsonPath("$.totalElements").value(25))
                .andExpect(jsonPath("$.totalPages").value(3))
                .andExpect(jsonPath("$.first").value(false))
                .andExpect(jsonPath("$.last").value(false));

        verify(doctorService, times(1)).getAllDoctors(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait trier avec des majuscules/minuscules dans sortDirection")
    void getAllDoctors_ShouldHandleCaseInsensitiveSortDirection() throws Exception {
        // Given
        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(mockPageResponse);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        // When - Test avec "DESC" en majuscules
        mockMvc.perform(get("/doctor/allDoctors")
                        .param("sortDirection", "DESC"))
                .andExpect(status().isOk());

        // Then
        verify(doctorService).getAllDoctors(pageableCaptor.capture());
        Pageable capturedPageable = pageableCaptor.getValue();

        assertThat(capturedPageable.getSort().getOrderFor("nom").getDirection()).isEqualTo(Sort.Direction.DESC);
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait utiliser ASC par défaut pour sortDirection invalide")
    void getAllDoctors_ShouldUseAscendingSort_WhenSortDirectionIsInvalid() throws Exception {
        // Given
        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(mockPageResponse);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        // When
        mockMvc.perform(get("/doctor/allDoctors")
                        .param("sortDirection", "invalide"))
                .andExpect(status().isOk());

        // Then
        verify(doctorService).getAllDoctors(pageableCaptor.capture());
        Pageable capturedPageable = pageableCaptor.getValue();

        assertThat(capturedPageable.getSort().getOrderFor("nom").getDirection()).isEqualTo(Sort.Direction.ASC);
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait retourner tous les champs du médecin correctement")
    void getAllDoctors_ShouldReturnAllDoctorFieldsCorrectly() throws Exception {
        // Given
        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(mockPageResponse);

        // When & Then
        mockMvc.perform(get("/doctor/allDoctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].nom").value("Martin"))
                .andExpect(jsonPath("$.content[1].prenom").value("Marie"))
                .andExpect(jsonPath("$.content[1].email").value("marie.martin@cabinet.fr"))
                .andExpect(jsonPath("$.content[1].telephone").value("0987654321"))
                .andExpect(jsonPath("$.content[1].numeroOrdre").value("ORD-67890"))
                .andExpect(jsonPath("$.content[1].specialite.id").value(2))
                .andExpect(jsonPath("$.content[1].specialite.nom").value("Dermatologie"))
                .andExpect(jsonPath("$.content[1].specialite.description").value("Spécialiste de la peau"));
    }

    @Test
    @DisplayName("GET /doctor/allDoctors - Devrait trier par tous les champs valides")
    void getAllDoctors_ShouldSortByAllValidFields() throws Exception {
        // Given
        when(doctorService.getAllDoctors(any(Pageable.class))).thenReturn(mockPageResponse);

        String[] validSortFields = {"nom", "prenom", "numeroordre", "email", "telephone", "specialite"};

        for (String sortField : validSortFields) {
            // When & Then
            mockMvc.perform(get("/doctor/allDoctors")
                            .param("sortBy", sortField))
                    .andExpect(status().isOk());
        }

        // Vérifier que le service a été appelé pour chaque champ
        verify(doctorService, times(validSortFields.length)).getAllDoctors(any(Pageable.class));
    }
}

