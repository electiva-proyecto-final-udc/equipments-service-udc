package com.udc.gestionEquipos.controllers;

import com.udc.gestionEquipos.models.CompressorSpecs;
import com.udc.gestionEquipos.services.CompressorSpecsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/specs/compressors")
public class CompressorSpecsController {

    private final CompressorSpecsService service;

    public CompressorSpecsController(CompressorSpecsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CompressorSpecs> create(@RequestBody CompressorSpecs specs) {
        return ResponseEntity.ok(service.createSpecs(specs));
    }

    @GetMapping
    public ResponseEntity<List<CompressorSpecs>> getAll() {
        return ResponseEntity.ok(service.getAllSpecs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompressorSpecs> getById(@PathVariable UUID id) {
        return service.getSpecsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<CompressorSpecs> getByEquipment(@PathVariable UUID equipmentId) {
        return service.getSpecsByEquipmentId(equipmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompressorSpecs> update(@PathVariable UUID id, @RequestBody CompressorSpecs specs) {
        return ResponseEntity.ok(service.updateSpecs(id, specs));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteSpecs(id);
        return ResponseEntity.noContent().build();
    }
}
