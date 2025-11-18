package com.udc.gestionEquipos.models.dto.notificationService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicianAssignedEmailPayload {
    String to;
    String technicianName;
    String serviceNum;
    String equipType;
    String clientName;
    String description;
    String date;
    String priority;

}
