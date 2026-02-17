package com.cabinetmedical.mapper;

import com.cabinetmedical.dto.DoctorDTO;
import com.cabinetmedical.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper pour convertir entre Doctor et DoctorDTO
 * Utilise MapStruct pour la génération automatique
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = SpecialityMapper.class)
public interface DoctorMapper {

    /**
     * Convertit une entité Doctor en DoctorDTO
     * La spécialité est automatiquement mappée grâce au SpecialityMapper
     * Mapping explicite: speciality (entity) -> specialite (DTO)
     */
    @Mapping(source = "speciality", target = "specialite")
    DoctorDTO toDTO(Doctor doctor);

    /**
     * Convertit un DoctorDTO en entité Doctor
     * Mapping explicite: specialite (DTO) -> speciality (entity)
     */
    @Mapping(source = "specialite", target = "speciality")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Doctor toEntity(DoctorDTO doctorDTO);
}

