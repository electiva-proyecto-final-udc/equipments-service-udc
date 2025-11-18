package com.udc.gestionEquipos.services.externalServices;

import com.udc.gestionEquipos.models.dto.userService.GetPersonByIdResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceClient {

    private final RestTemplate restTemplate;

    @Value("${users.service.url}")
    private String userServiceUrl;

    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GetPersonByIdResponse.UserData getUserById(String id, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        GetPersonByIdResponse response = restTemplate.exchange(
                userServiceUrl + "/user-service/v1/clients/" + id,
                HttpMethod.GET,
                entity,
                GetPersonByIdResponse.class
        ).getBody();

        return response.getData();
    }

    public GetPersonByIdResponse.UserData getTechnicianById(String id, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        GetPersonByIdResponse response = restTemplate.exchange(
                userServiceUrl + "/user-service/v1/technician/" + id,
                HttpMethod.GET,
                entity,
                GetPersonByIdResponse.class
        ).getBody();

        return response.getData();
    }
}
