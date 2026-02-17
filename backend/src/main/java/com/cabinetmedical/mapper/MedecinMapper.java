package com.cabinetmedical.mapper;

import com.cabinetmedical.dto.MedecinDTO;
import com.cabinetmedical.entity.Medecin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper pour convertir entre Medecin et MedecinDTO
 * Utilise MapStruct pour la génération automatique
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = SpecialiteMapper.class)
public interface MedecinMapper {

    /**
     * Convertit une entité Medecin en MedecinDTO
     * La spécialité est automatiquement mappée grâce au SpecialiteMapper
     */
    MedecinDTO toDTO(Medecin medecin);

    /**
     * Convertit un MedecinDTO en entité Medecin
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Medecin toEntity(MedecinDTO medecinDTO);
}

