package com.udc.gestionEquipos.services.externalServices;

import com.udc.gestionEquipos.models.dto.notificationService.NotificationResponse;
import com.udc.gestionEquipos.models.dto.notificationService.PostEmail;
import com.udc.gestionEquipos.models.dto.notificationService.TechnicianAssignedEmailPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceClient {

    private final RestTemplate restTemplate;

    @Value("${notifications.service.url}")
    private String notificationServiceUrl;

    public NotificationServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public NotificationResponse sendFinishMaintenance(String id, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        NotificationResponse response = restTemplate.exchange(
                notificationServiceUrl + "/notification-service/reparations/finishReparation",
                HttpMethod.POST,
                entity,
                NotificationResponse.class
        ).getBody();
        System.out.println(response);
        return response;
    }

    public NotificationResponse sendTechnicianAssigned(
            PostEmail payload,
            String token
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.set("Content-Type", "application/json");

        HttpEntity<PostEmail> entity =
                new HttpEntity<>(payload, headers);

        return restTemplate.exchange(
                notificationServiceUrl + "/notification-service/equipments/register",
                HttpMethod.POST,
                entity,
                NotificationResponse.class
        ).getBody();
    }

    public NotificationResponse sendFinishReparationEmail(
            PostEmail payload,
            String token
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.set("Content-Type", "application/json");

        HttpEntity<PostEmail> entity =
                new HttpEntity<>(payload, headers);

        return restTemplate.exchange(
                notificationServiceUrl + "/notification-service/reparations/finishReparation",
                HttpMethod.POST,
                entity,
                NotificationResponse.class
        ).getBody();
    }

}
