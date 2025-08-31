package com.udc.gestionEquipos.services;

import com.udc.gestionEquipos.models.Checklist;
import com.udc.gestionEquipos.models.enums.EquipmentType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChecklistService {
    Checklist createChecklist(Checklist checklist);

    List<Checklist> getAllChecklists();

    Optional<Checklist> getChecklistById(UUID id);

    Optional<Checklist> getChecklistByServiceId(UUID serviceId);

    List<Checklist> getChecklistsByEquipmentType(EquipmentType type);

    Checklist updateChecklist(UUID id, Checklist checklist);

    void deleteChecklist(UUID id);
}

