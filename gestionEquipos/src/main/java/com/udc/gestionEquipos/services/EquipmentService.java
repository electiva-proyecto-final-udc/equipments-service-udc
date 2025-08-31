package com.udc.gestionEquipos.services;

import com.udc.gestionEquipos.models.Equipment;
import com.udc.gestionEquipos.models.enums.EquipmentStatus;
import com.udc.gestionEquipos.models.enums.EquipmentType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EquipmentService {
    Equipment createEquipment(Equipment equipment);

    List<Equipment> getAllEquipment();

    Optional<Equipment> getEquipmentById(UUID id);

    List<Equipment> getEquipmentByType(EquipmentType type);

    List<Equipment> getEquipmentByStatus(EquipmentStatus status);

    List<Equipment> getEquipmentByClientId(UUID clientId);

    Equipment updateEquipment(UUID id, Equipment equipment);

    void deleteEquipment(UUID id);
}
