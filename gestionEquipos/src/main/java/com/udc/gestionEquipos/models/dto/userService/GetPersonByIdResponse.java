package com.udc.gestionEquipos.models.dto.userService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPersonByIdResponse {

    private String message;
    private UserData data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserData {
        private String id;
        private String document;
        private String email;
        private String name;
        private String surname;
    }
}
