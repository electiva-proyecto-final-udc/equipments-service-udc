package com.udc.gestionEquipos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.udc.gestionEquipos.models.enums.ChecklistItemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "checklist_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistItem {

    @Id
    @GeneratedValue
    private UUID id;  // ← ESTA es la verdadera PK

    @Column(name = "name", nullable = false)
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChecklistItemStatus status;

    @Column(length = 500)
    private String notes;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    private Checklist checklist;   // relación real
}
