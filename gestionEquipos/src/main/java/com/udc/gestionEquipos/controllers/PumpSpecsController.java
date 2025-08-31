package com.udc.gestionEquipos.controllers;

import com.udc.gestionEquipos.models.PumpSpecs;
import com.udc.gestionEquipos.services.PumpSpecsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/specs/pumps")
public class PumpSpecsController {

    private final PumpSpecsService service;

    public PumpSpecsController(PumpSpecsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PumpSpecs> create(@RequestBody PumpSpecs specs) {
        return ResponseEntity.ok(service.createSpecs(specs));
    }

    @GetMapping
    public ResponseEntity<List<PumpSpecs>> getAll() {
        return ResponseEntity.ok(service.getAllSpecs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PumpSpecs> getById(@PathVariable UUID id) {
        return service.getSpecsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<PumpSpecs> getByEquipment(@PathVariable UUID equipmentId) {
        return service.getSpecsByEquipmentId(equipmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PumpSpecs> update(@PathVariable UUID id, @RequestBody PumpSpecs specs) {
        return ResponseEntity.ok(service.updateSpecs(id, specs));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteSpecs(id);
        return ResponseEntity.noContent().build();
    }
}
