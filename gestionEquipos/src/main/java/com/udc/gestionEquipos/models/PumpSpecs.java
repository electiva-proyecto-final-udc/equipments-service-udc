package com.udc.gestionEquipos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "pump_specs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PumpSpecs extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "equipment_id", nullable = false, unique = true)
    private Equipment equipment;

    private String brand;
    private Double hp;
    private Integer rpm;
    private Double kw;
    private Integer voltage;
    private Integer amps;
}