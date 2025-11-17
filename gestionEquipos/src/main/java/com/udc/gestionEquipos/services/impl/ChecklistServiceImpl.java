package com.udc.gestionEquipos.services.impl;

import com.udc.gestionEquipos.models.Checklist;
import com.udc.gestionEquipos.models.enums.EquipmentType;
import com.udc.gestionEquipos.repositories.ChecklistRepository;
import com.udc.gestionEquipos.services.ChecklistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChecklistServiceImpl implements ChecklistService {
    private final ChecklistRepository checklistRepository;

    public ChecklistServiceImpl(ChecklistRepository checklistRepository) {
        this.checklistRepository = checklistRepository;
    }

    @Override
    public Checklist createChecklist(Checklist checklist) {

        // Asegurar relación bidireccional
        if (checklist.getItems() != null) {
            checklist.getItems().forEach(item -> item.setChecklist(checklist));
        }

        return checklistRepository.save(checklist);
    }


    @Override
    public List<Checklist> getAllChecklists() {
        return checklistRepository.findAll();
    }

    @Override
    public Optional<Checklist> getChecklistById(UUID id) {
        return checklistRepository.findById(id);
    }

    @Override
    public Optional<Checklist> getChecklistByServiceId(UUID serviceId) {
        return checklistRepository.findByServiceId(serviceId);
    }

    @Override
    public List<Checklist> getChecklistsByEquipmentType(EquipmentType type) {
        return checklistRepository.findByEquipmentType(type);
    }

    @Override
    public Checklist updateChecklist(UUID id, Checklist checklist) {
        return checklistRepository.findById(id)
                .map(existing -> {
                    existing.setEquipmentType(checklist.getEquipmentType());
                    existing.setItems(checklist.getItems());
                    return checklistRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Checklist not found with id: " + id));
    }

    @Override
    public void deleteChecklist(UUID id) {
        checklistRepository.deleteById(id);
    }
}
