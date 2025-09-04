package com.udc.gestionEquipos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GestionEquiposApplication {
    public static void main(String[] args) {
        SpringApplication.run(GestionEquiposApplication.class, args);
    }
}

