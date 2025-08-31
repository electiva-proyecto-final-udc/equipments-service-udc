package com.udc.gestionEquipos.services;

import com.udc.gestionEquipos.models.GeneratorSpecs;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GeneratorSpecsService {
    GeneratorSpecs createSpecs(GeneratorSpecs specs);
    List<GeneratorSpecs> getAllSpecs();
    Optional<GeneratorSpecs> getSpecsById(UUID id);
    Optional<GeneratorSpecs> getSpecsByEquipmentId(UUID equipmentId);
    GeneratorSpecs updateSpecs(UUID id, GeneratorSpecs specs);
    void deleteSpecs(UUID id);
}
