package com.udc.gestionEquipos.controllers;

import com.udc.gestionEquipos.models.Checklist;
import com.udc.gestionEquipos.models.enums.EquipmentType;
import com.udc.gestionEquipos.services.ChecklistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checklists")
public class ChecklistController {

    private final ChecklistService checklistService;

    public ChecklistController(ChecklistService checklistService) {
        this.checklistService = checklistService;
    }

    @PostMapping
    public ResponseEntity<Checklist> createChecklist(@RequestBody Checklist checklist) {
        return ResponseEntity.ok(checklistService.createChecklist(checklist));
    }

    @GetMapping
    public ResponseEntity<List<Checklist>> getAllChecklists() {
        return ResponseEntity.ok(checklistService.getAllChecklists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Checklist> getChecklistById(@PathVariable UUID id) {
        return checklistService.getChecklistById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<Checklist> getChecklistByServiceId(@PathVariable UUID serviceId) {
        return checklistService.getChecklistByServiceId(serviceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Checklist>> getChecklistsByType(@PathVariable EquipmentType type) {
        return ResponseEntity.ok(checklistService.getChecklistsByEquipmentType(type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Checklist> updateChecklist(
            @PathVariable UUID id,
            @RequestBody Checklist checklist) {
        return ResponseEntity.ok(checklistService.updateChecklist(id, checklist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChecklist(@PathVariable UUID id) {
        checklistService.deleteChecklist(id);
        return ResponseEntity.noContent().build();
    }
}
