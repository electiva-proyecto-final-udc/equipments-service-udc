package com.udc.gestionEquipos.repositories;

import com.udc.gestionEquipos.models.Checklist;
import com.udc.gestionEquipos.models.enums.EquipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, UUID> {
    Optional<Checklist> findByServiceId(UUID serviceId);

    List<Checklist> findByEquipmentType(EquipmentType equipmentType);
}
