package com.udc.gestionEquipos.models.dto;

import com.udc.gestionEquipos.models.dto.userService.GetPersonByIdResponse;
import lombok.*;

import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentDetailsDTO {

    private UUID id;
    private String code;
    private String type;
    private String status;
    private String location;

    private GetPersonByIdResponse.UserData client;
}

