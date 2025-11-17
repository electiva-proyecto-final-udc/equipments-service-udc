package com.udc.gestionEquipos.services;

import com.udc.gestionEquipos.models.CompressorSpecs;
import com.udc.gestionEquipos.models.Equipment;
import com.udc.gestionEquipos.models.GeneratorSpecs;
import com.udc.gestionEquipos.models.PumpSpecs;
import com.udc.gestionEquipos.models.dto.EquipmentRequest;

import java.util.Map;
import java.util.UUID;

public interface EquipmentBuilderService {
    Equipment updateEquipment(UUID equipmentId, EquipmentRequest req);
    Equipment createEquipment(EquipmentRequest req);
}
