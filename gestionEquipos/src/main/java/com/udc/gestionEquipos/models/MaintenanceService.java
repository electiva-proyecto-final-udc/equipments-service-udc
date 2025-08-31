package com.udc.gestionEquipos.models;

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
    @ManyToOne(optional = false) @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceType type;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    private LocalDate deliveryCommitment;

    @Column(length = 500)
    private String reportedProblem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriorityLevel priority;

    @Column(nullable = false)
    private UUID technicianId;   // Reference to Users microservice

    @Column(length = 1000)
    private String executedActivity;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String recommendations;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceStatus status;

    @OneToOne(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private Checklist checklist;
}