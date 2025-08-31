package com.udc.gestionEquipos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "generator_specs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneratorSpecs extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "equipment_id", nullable = false, unique = true)
    private Equipment equipment;

    private String plantBrand;
    private String plantModel;
    private String plantSerial;

    private String engineBrand;
    private String engineModel;
    private String engineSerial;

    private String generatorBrand;
    private String generatorModel;
    private String generatorSerial;

    private String power;
    private Integer workingHours;
    private String cpl;
}

