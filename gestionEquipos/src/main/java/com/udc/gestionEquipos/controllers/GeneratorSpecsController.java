package com.udc.gestionEquipos.controllers;

import com.udc.gestionEquipos.models.GeneratorSpecs;
import com.udc.gestionEquipos.services.GeneratorSpecsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/specs/generators")
public class GeneratorSpecsController {

    private final GeneratorSpecsService service;

    public GeneratorSpecsController(GeneratorSpecsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GeneratorSpecs> create(@RequestBody GeneratorSpecs specs) {
        return ResponseEntity.ok(service.createSpecs(specs));
    }

    @GetMapping
    public ResponseEntity<List<GeneratorSpecs>> getAll() {
        return ResponseEntity.ok(service.getAllSpecs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneratorSpecs> getById(@PathVariable UUID id) {
        return service.getSpecsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<GeneratorSpecs> getByEquipment(@PathVariable UUID equipmentId) {
        return service.getSpecsByEquipmentId(equipmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneratorSpecs> update(@PathVariable UUID id, @RequestBody GeneratorSpecs specs) {
        return ResponseEntity.ok(service.updateSpecs(id, specs));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteSpecs(id);
        return ResponseEntity.noContent().build();
    }
}
