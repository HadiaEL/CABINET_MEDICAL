package com.cabinetmedical.mapper;

import com.cabinetmedical.dto.SpecialiteDTO;
import com.cabinetmedical.entity.Specialite;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper pour convertir entre Specialite et SpecialiteDTO
 * Utilise MapStruct pour la génération automatique
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialiteMapper {

    /**
     * Convertit une entité Specialite en SpecialiteDTO
     */
    SpecialiteDTO toDTO(Specialite specialite);

    /**
     * Convertit un SpecialiteDTO en entité Specialite
     */
    Specialite toEntity(SpecialiteDTO specialiteDTO);
}

