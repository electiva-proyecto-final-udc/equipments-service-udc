package com.udc.gestionEquipos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.udc.gestionEquipos.models.BaseEntity;
import com.udc.gestionEquipos.models.enums.EquipmentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "checklists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Checklist extends BaseEntity {

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "service_id", nullable = false, unique = true)
    private MaintenanceService service;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentType equipmentType;

    @JsonManagedReference
    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChecklistItem> items = new ArrayList<>();
}
