package com.udc.gestionEquipos.services;

import com.udc.gestionEquipos.models.CompressorSpecs;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompressorSpecsService {
    CompressorSpecs createSpecs(CompressorSpecs specs);
    List<CompressorSpecs> getAllSpecs();
    Optional<CompressorSpecs> getSpecsById(UUID id);
    Optional<CompressorSpecs> getSpecsByEquipmentId(UUID equipmentId);
    CompressorSpecs updateSpecs(UUID id, CompressorSpecs specs);
    void deleteSpecs(UUID id);
}
