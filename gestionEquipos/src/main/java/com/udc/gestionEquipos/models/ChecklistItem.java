package com.udc.gestionEquipos.models;

import com.udc.gestionEquipos.models.enums.ChecklistItemStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistItem {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChecklistItemStatus status;

    @Column(length = 500)
    private String notes;
}