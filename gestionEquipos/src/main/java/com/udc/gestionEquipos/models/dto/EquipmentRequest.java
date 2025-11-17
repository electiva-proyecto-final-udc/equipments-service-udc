package com.udc.gestionEquipos.models.dto;

import com.udc.gestionEquipos.models.enums.EquipmentType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class EquipmentRequest {

    private String code;
    private String location;
    private UUID clientId;

    private EquipmentType type;  // GENERATOR, PUMP, COMPRESSOR

    // Contiene las specs específicas dependiendo del tipo
    private Map<String, Object> specs = new HashMap<>();
}
