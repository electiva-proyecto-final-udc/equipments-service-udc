package com.udc.gestionEquipos.models.dto.notificationService;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    String status;
    String response;
}
