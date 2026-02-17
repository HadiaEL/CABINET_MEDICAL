package com.cabinetmedical.mapper;

import com.cabinetmedical.dto.SpecialityDTO;
import com.cabinetmedical.entity.Speciality;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper pour convertir entre Speciality et SpecialityDTO
 * Utilise MapStruct pour la génération automatique
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialityMapper {

    /**
     * Convertit une entité Speciality en SpecialityDTO
     */
    SpecialityDTO toDTO(Speciality speciality);

    /**
     * Convertit un SpecialityDTO en entité Speciality
     */
    Speciality toEntity(SpecialityDTO specialityDTO);
}

