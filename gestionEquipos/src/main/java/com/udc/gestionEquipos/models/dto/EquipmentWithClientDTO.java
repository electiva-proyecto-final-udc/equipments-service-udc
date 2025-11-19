package com.udc.gestionEquipos.models.dto;

import com.udc.gestionEquipos.models.enums.EquipmentStatus;
import com.udc.gestionEquipos.models.enums.EquipmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentWithClientDTO {
    private UUID id;
    private String code;
    private Instant createdAt;
    private String status;
    private String type;
    private UUID clientId;
    private String clientName;
}

