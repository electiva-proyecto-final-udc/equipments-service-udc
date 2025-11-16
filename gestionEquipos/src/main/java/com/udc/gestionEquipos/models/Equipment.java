package com.udc.gestionEquipos.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.udc.gestionEquipos.models.enums.EquipmentStatus;
import com.udc.gestionEquipos.models.enums.EquipmentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity

@Table(name = "equipment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipment extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentStatus status;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private UUID clientId;  // Reference to Users microservice

    @JsonManagedReference
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenanceService> services;

    @JsonManagedReference
    @OneToOne(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private GeneratorSpecs generatorSpecs;

    @JsonManagedReference
    @OneToOne(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private PumpSpecs pumpSpecs;

    @JsonManagedReference
    @OneToOne(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private CompressorSpecs compressorSpecs;
}