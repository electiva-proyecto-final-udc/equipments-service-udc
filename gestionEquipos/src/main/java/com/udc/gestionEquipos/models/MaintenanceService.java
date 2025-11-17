package com.udc.gestionEquipos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.udc.gestionEquipos.models.enums.MaintenanceType;
import com.udc.gestionEquipos.models.enums.PriorityLevel;
import com.udc.gestionEquipos.models.enums.ServiceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "maintenance_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceService extends BaseEntity {

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceType type;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String equipmentCode;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    private LocalDate deliveryCommitment;

    @Column(length = 500)
    private String reportedProblem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriorityLevel priority;

    @Column(nullable = false)
    private UUID technicianId;

    @Column(length = 1000)
    private String executedActivity;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String recommendations;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceStatus status;

    @JsonManagedReference
    @OneToOne(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private Checklist checklist;

    // Campo virtual NO mapeado a la base de datos
    @Transient
    private UUID equipmentId;

    // Se expone equipmentId en el JSON
    @JsonProperty("equipmentId")
    public UUID getEquipmentId() {
        return (equipment != null) ? equipment.getId() : null;
    }
}
