package com.cabinetmedical.service;

import com.cabinetmedical.dto.DoctorDTO;
import com.cabinetmedical.dto.PageResponse;
import com.cabinetmedical.dto.SpecialityDTO;
import com.cabinetmedical.entity.Doctor;
import com.cabinetmedical.entity.Speciality;
import com.cabinetmedical.mapper.DoctorMapper;
import com.cabinetmedical.repository.DoctorRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour DoctorServiceImpl
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires - DoctorServiceImpl")
class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorMapper doctorMapper;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    private List<Doctor> mockDoctorEntities;
    private List<DoctorDTO> mockDoctorDTOs;
    private Pageable pageable;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        // Charger les spécialités depuis le fichier JSON
        List<Map<String, Object>> specialitiesData = loadJsonFile("specialityMock.json");
        List<Speciality> specialities = new ArrayList<>();

        for (Map<String, Object> specData : specialitiesData) {
            Speciality speciality = new Speciality();
            speciality.setId(((Number) specData.get("id")).longValue());
            speciality.setNom((String) specData.get("nom"));
            speciality.setDescription((String) specData.get("description"));
            specialities.add(speciality);
        }

        // Charger les médecins depuis le fichier JSON
        List<Map<String, Object>> doctorsData = loadJsonFile("doctorsMock.json");
        mockDoctorEntities = new ArrayList<>();
        mockDoctorDTOs = new ArrayList<>();

        for (Map<String, Object> docData : doctorsData) {
            // Créer l'entité Doctor
            Doctor doctor = new Doctor();
            doctor.setId(((Number) docData.get("id")).longValue());
            doctor.setNom((String) docData.get("nom"));
            doctor.setPrenom((String) docData.get("prenom"));
            doctor.setEmail((String) docData.get("email"));
            doctor.setTelephone((String) docData.get("telephone"));
            doctor.setNumeroOrdre((String) docData.get("numeroOrdre"));

            // Associer la spécialité
            Map<String, Object> specData = (Map<String, Object>) docData.get("specialite");
            Long specId = ((Number) specData.get("id")).longValue();
            Speciality speciality = specialities.stream()
                    .filter(s -> s.getId().equals(specId))
                    .findFirst()
                    .orElseThrow();
            doctor.setSpeciality(speciality);
            mockDoctorEntities.add(doctor);

            // Créer le DTO correspondant
            SpecialityDTO specialityDTO = new SpecialityDTO(
                    ((Number) specData.get("id")).longValue(),
                    (String) specData.get("nom"),
                    (String) specData.get("description")
            );

            DoctorDTO doctorDTO = new DoctorDTO(
                    doctor.getId(),
                    doctor.getNom(),
                    doctor.getPrenom(),
                    doctor.getEmail(),
                    doctor.getTelephone(),
                    doctor.getNumeroOrdre(),
                    specialityDTO
            );
            mockDoctorDTOs.add(doctorDTO);
        }

        pageable = PageRequest.of(0, 9, Sort.by("nom").ascending());
    }

    /**
     * Charge un fichier JSON depuis les ressources de test
     */
    private List<Map<String, Object>> loadJsonFile(String fileName) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IOException("Fichier non trouvé: " + fileName);
            }
            return objectMapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {});
        }
    }

    @Test
    @DisplayName("getAllDoctors - Devrait retourner une page de médecins avec succès")
    void getAllDoctors_ShouldReturnPageOfDoctors_WhenSuccessful() {
        // Given
        Page<Doctor> doctorPage = new PageImpl<>(mockDoctorEntities, pageable, mockDoctorEntities.size());
        when(doctorRepository.findAllWithSpecialite(pageable)).thenReturn(doctorPage);

        // Configurer le mapper pour chaque médecin
        when(doctorMapper.toDTO(mockDoctorEntities.get(0))).thenReturn(mockDoctorDTOs.get(0));
        when(doctorMapper.toDTO(mockDoctorEntities.get(1))).thenReturn(mockDoctorDTOs.get(1));
        when(doctorMapper.toDTO(mockDoctorEntities.get(2))).thenReturn(mockDoctorDTOs.get(2));

        // When
        PageResponse<DoctorDTO> result = doctorService.getAllDoctors(pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.content()).hasSize(3);
        assertThat(result.totalElements()).isEqualTo(3);
        assertThat(result.pageNumber()).isEqualTo(0);
        assertThat(result.pageSize()).isEqualTo(9);
        assertThat(result.totalPages()).isEqualTo(1);
        assertThat(result.first()).isTrue();
        assertThat(result.last()).isTrue();
        assertThat(result.empty()).isFalse();

        // Vérifier le premier médecin
        DoctorDTO firstDoctor = result.content().get(0);
        assertThat(firstDoctor.nom()).isEqualTo("Dupont");
        assertThat(firstDoctor.prenom()).isEqualTo("Jean");
        assertThat(firstDoctor.email()).isEqualTo("jean.dupont@cabinet.fr");
        assertThat(firstDoctor.specialite().nom()).isEqualTo("Cardiologie");

        // Vérifier les interactions
        verify(doctorRepository, times(1)).findAllWithSpecialite(pageable);
        verify(doctorMapper, times(3)).toDTO(any(Doctor.class));
    }

    @Test
    @DisplayName("getAllDoctors - Devrait retourner une page vide quand aucun médecin n'existe")
    void getAllDoctors_ShouldReturnEmptyPage_WhenNoDoctorsExist() {
        // Given
        Page<Doctor> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        when(doctorRepository.findAllWithSpecialite(pageable)).thenReturn(emptyPage);

        // When
        PageResponse<DoctorDTO> result = doctorService.getAllDoctors(pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.content()).isEmpty();
        assertThat(result.totalElements()).isEqualTo(0);
        assertThat(result.totalPages()).isEqualTo(0);
        assertThat(result.empty()).isTrue();

        verify(doctorRepository, times(1)).findAllWithSpecialite(pageable);
        verify(doctorMapper, never()).toDTO(any(Doctor.class));
    }

    @Test
    @DisplayName("getAllDoctors - Devrait gérer la pagination correctement")
    void getAllDoctors_ShouldHandlePaginationCorrectly() {
        // Given - Simuler la deuxième page avec seulement 2 médecins
        Pageable secondPageable = PageRequest.of(1, 9, Sort.by("nom").ascending());
        List<Doctor> secondPageDoctors = mockDoctorEntities.subList(0, 2);
        Page<Doctor> doctorPage = new PageImpl<>(secondPageDoctors, secondPageable, 25); // 25 éléments au total

        when(doctorRepository.findAllWithSpecialite(secondPageable)).thenReturn(doctorPage);
        when(doctorMapper.toDTO(mockDoctorEntities.get(0))).thenReturn(mockDoctorDTOs.get(0));
        when(doctorMapper.toDTO(mockDoctorEntities.get(1))).thenReturn(mockDoctorDTOs.get(1));

        // When
        PageResponse<DoctorDTO> result = doctorService.getAllDoctors(secondPageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.content()).hasSize(2);
        assertThat(result.totalElements()).isEqualTo(25);
        assertThat(result.pageNumber()).isEqualTo(1);
        assertThat(result.totalPages()).isEqualTo(3);
        assertThat(result.first()).isFalse();
        assertThat(result.last()).isFalse();

        verify(doctorRepository, times(1)).findAllWithSpecialite(secondPageable);
    }

    @Test
    @DisplayName("getAllDoctors - Devrait appliquer le tri correctement")
    void getAllDoctors_ShouldApplySortingCorrectly() {
        // Given - Tri par prénom en ordre décroissant
        Pageable sortedPageable = PageRequest.of(0, 9, Sort.by("prenom").descending());
        Page<Doctor> doctorPage = new PageImpl<>(mockDoctorEntities, sortedPageable, mockDoctorEntities.size());

        when(doctorRepository.findAllWithSpecialite(sortedPageable)).thenReturn(doctorPage);
        when(doctorMapper.toDTO(any(Doctor.class))).thenReturn(mockDoctorDTOs.get(0), mockDoctorDTOs.get(1), mockDoctorDTOs.get(2));

        // When
        PageResponse<DoctorDTO> result = doctorService.getAllDoctors(sortedPageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.content()).hasSize(3);

        verify(doctorRepository, times(1)).findAllWithSpecialite(sortedPageable);
    }

    @Test
    @DisplayName("getAllDoctors - Devrait gérer différentes tailles de page")
    void getAllDoctors_ShouldHandleDifferentPageSizes() {
        // Given - Taille de page = 2
        Pageable smallPageable = PageRequest.of(0, 2, Sort.by("nom").ascending());
        List<Doctor> twoDoctor = mockDoctorEntities.subList(0, 2);
        Page<Doctor> doctorPage = new PageImpl<>(twoDoctor, smallPageable, mockDoctorEntities.size());

        when(doctorRepository.findAllWithSpecialite(smallPageable)).thenReturn(doctorPage);
        when(doctorMapper.toDTO(mockDoctorEntities.get(0))).thenReturn(mockDoctorDTOs.get(0));
        when(doctorMapper.toDTO(mockDoctorEntities.get(1))).thenReturn(mockDoctorDTOs.get(1));

        // When
        PageResponse<DoctorDTO> result = doctorService.getAllDoctors(smallPageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.content()).hasSize(2);
        assertThat(result.pageSize()).isEqualTo(2);
        assertThat(result.totalElements()).isEqualTo(3);
        assertThat(result.totalPages()).isEqualTo(2);
        assertThat(result.first()).isTrue();
        assertThat(result.last()).isFalse();

        verify(doctorRepository, times(1)).findAllWithSpecialite(smallPageable);
    }

    @Test
    @DisplayName("getAllDoctors - Devrait mapper correctement tous les champs du médecin")
    void getAllDoctors_ShouldMapAllDoctorFieldsCorrectly() {
        // Given
        Page<Doctor> doctorPage = new PageImpl<>(mockDoctorEntities.subList(0, 1), pageable, 1);
        DoctorDTO expectedDTO = mockDoctorDTOs.get(0);

        when(doctorRepository.findAllWithSpecialite(pageable)).thenReturn(doctorPage);
        when(doctorMapper.toDTO(mockDoctorEntities.get(0))).thenReturn(expectedDTO);

        // When
        PageResponse<DoctorDTO> result = doctorService.getAllDoctors(pageable);

        // Then
        assertThat(result.content()).hasSize(1);
        DoctorDTO actualDTO = result.content().get(0);

        assertThat(actualDTO.id()).isEqualTo(expectedDTO.id());
        assertThat(actualDTO.nom()).isEqualTo(expectedDTO.nom());
        assertThat(actualDTO.prenom()).isEqualTo(expectedDTO.prenom());
        assertThat(actualDTO.email()).isEqualTo(expectedDTO.email());
        assertThat(actualDTO.telephone()).isEqualTo(expectedDTO.telephone());
        assertThat(actualDTO.numeroOrdre()).isEqualTo(expectedDTO.numeroOrdre());
        assertThat(actualDTO.specialite()).isNotNull();
        assertThat(actualDTO.specialite().id()).isEqualTo(expectedDTO.specialite().id());
        assertThat(actualDTO.specialite().nom()).isEqualTo(expectedDTO.specialite().nom());
        assertThat(actualDTO.specialite().description()).isEqualTo(expectedDTO.specialite().description());

        verify(doctorMapper, times(1)).toDTO(mockDoctorEntities.get(0));
    }

    @Test
    @DisplayName("getAllDoctors - Devrait appeler le repository avec le bon Pageable")
    void getAllDoctors_ShouldCallRepositoryWithCorrectPageable() {
        // Given
        Pageable customPageable = PageRequest.of(3, 25, Sort.by("email").descending());
        Page<Doctor> doctorPage = new PageImpl<>(List.of(), customPageable, 0);

        when(doctorRepository.findAllWithSpecialite(customPageable)).thenReturn(doctorPage);

        // When
        doctorService.getAllDoctors(customPageable);

        // Then
        verify(doctorRepository, times(1)).findAllWithSpecialite(customPageable);
    }

    @Test
    @DisplayName("getAllDoctors - Devrait gérer une grande liste de médecins")
    void getAllDoctors_ShouldHandleLargeListOfDoctors() {
        // Given - Simuler 50 médecins
        Pageable largePageable = PageRequest.of(0, 50);
        Page<Doctor> doctorPage = new PageImpl<>(mockDoctorEntities, largePageable, 150); // 150 au total

        when(doctorRepository.findAllWithSpecialite(largePageable)).thenReturn(doctorPage);
        when(doctorMapper.toDTO(any(Doctor.class))).thenReturn(mockDoctorDTOs.get(0), mockDoctorDTOs.get(1), mockDoctorDTOs.get(2));

        // When
        PageResponse<DoctorDTO> result = doctorService.getAllDoctors(largePageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.totalElements()).isEqualTo(150);
        assertThat(result.pageSize()).isEqualTo(50);
        assertThat(result.totalPages()).isEqualTo(3);

        verify(doctorRepository, times(1)).findAllWithSpecialite(largePageable);
    }

    @Test
    @DisplayName("getAllDoctors - Devrait retourner la dernière page correctement")
    void getAllDoctors_ShouldReturnLastPageCorrectly() {
        // Given - Dernière page avec un seul élément
        Pageable lastPageable = PageRequest.of(2, 9);
        List<Doctor> lastPageDoctors = mockDoctorEntities.subList(0, 1);
        Page<Doctor> doctorPage = new PageImpl<>(lastPageDoctors, lastPageable, 21); // 21 éléments au total

        when(doctorRepository.findAllWithSpecialite(lastPageable)).thenReturn(doctorPage);
        when(doctorMapper.toDTO(mockDoctorEntities.get(0))).thenReturn(mockDoctorDTOs.get(0));

        // When
        PageResponse<DoctorDTO> result = doctorService.getAllDoctors(lastPageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.content()).hasSize(1);
        assertThat(result.pageNumber()).isEqualTo(2);
        assertThat(result.totalPages()).isEqualTo(3);
        assertThat(result.first()).isFalse();
        assertThat(result.last()).isTrue();

        verify(doctorRepository, times(1)).findAllWithSpecialite(lastPageable);
    }
}

