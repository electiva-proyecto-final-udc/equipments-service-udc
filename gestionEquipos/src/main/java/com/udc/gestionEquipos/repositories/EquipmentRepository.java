package com.udc.gestionEquipos.repositories;

import com.udc.gestionEquipos.models.Equipment;
import com.udc.gestionEquipos.models.enums.EquipmentStatus;
import com.udc.gestionEquipos.models.enums.EquipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {
    List<Equipment> findByType(EquipmentType type);

    List<Equipment> findByStatus(EquipmentStatus status);

    List<Equipment> findByClientId(UUID clientId);
}
