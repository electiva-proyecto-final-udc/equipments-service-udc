package com.udc.gestionEquipos.controllers;

import com.udc.gestionEquipos.models.Equipment;
import com.udc.gestionEquipos.models.enums.EquipmentStatus;
import com.udc.gestionEquipos.models.enums.EquipmentType;
import com.udc.gestionEquipos.services.EquipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService service;

    public EquipmentController(EquipmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Equipment> create(@RequestBody Equipment equipment) {
        return ResponseEntity.ok(service.createEquipment(equipment));
    }

    @GetMapping
    public ResponseEntity<List<Equipment>> getAll() {
        return ResponseEntity.ok(service.getAllEquipment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getById(@PathVariable UUID id) {
        return service.getEquipmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Equipment>> getByType(@PathVariable EquipmentType type) {
        return ResponseEntity.ok(service.getEquipmentByType(type));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Equipment>> getByStatus(@PathVariable EquipmentStatus status) {
        return ResponseEntity.ok(service.getEquipmentByStatus(status));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Equipment>> getByClient(@PathVariable UUID clientId) {
        return ResponseEntity.ok(service.getEquipmentByClientId(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipment> update(@PathVariable UUID id, @RequestBody Equipment equipment) {
        return ResponseEntity.ok(service.updateEquipment(id, equipment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}
