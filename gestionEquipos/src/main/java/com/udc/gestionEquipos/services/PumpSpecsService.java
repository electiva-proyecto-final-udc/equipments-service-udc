package com.udc.gestionEquipos.services;

import com.udc.gestionEquipos.models.PumpSpecs;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PumpSpecsService {
    PumpSpecs createSpecs(PumpSpecs specs);
    List<PumpSpecs> getAllSpecs();
    Optional<PumpSpecs> getSpecsById(UUID id);
    Optional<PumpSpecs> getSpecsByEquipmentId(UUID equipmentId);
    PumpSpecs updateSpecs(UUID id, PumpSpecs specs);
    void deleteSpecs(UUID id);
}
