package com.udc.gestionEquipos.models.dto.notificationService;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostEmail {
    String technicianId;
    String serviceNum;
    String clientId;
    String equipType;
    String description;
    String date;
    String priority;
}
