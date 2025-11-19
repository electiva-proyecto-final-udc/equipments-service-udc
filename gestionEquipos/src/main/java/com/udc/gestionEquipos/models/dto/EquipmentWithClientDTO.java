package com.udc.gestionEquipos.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentWithClientDTO {
    private UUID id;
    private String code;
    private String type;
    private UUID clientId;
    private String clientName;
    private String clientSurname;
}

