package com.udc.gestionEquipos.controllers;

import com.udc.gestionEquipos.models.PumpSpecs;
import com.udc.gestionEquipos.services.PumpSpecsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/specs/pumps")
@Tag(name = "Pump Specifications", description = "Endpoints for managing pump specifications")
public class PumpSpecsController {

    private final PumpSpecsService service;

    public PumpSpecsController(PumpSpecsService service) {
        this.service = service;
    }

    @Operation(summary = "Create pump specs", description = "Registers new pump specifications linked to equipment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pump specifications created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<PumpSpecs> create(@RequestBody PumpSpecs specs) {
        return ResponseEntity.ok(service.createSpecs(specs));
    }

    @Operation(summary = "Get all pump specs", description = "Retrieves all pump specifications from the system")
    @ApiResponse(responseCode = "200", description = "List of pump specifications")
    @GetMapping
    public ResponseEntity<List<PumpSpecs>> getAll() {
        return ResponseEntity.ok(service.getAllSpecs());
    }

    @Operation(summary = "Get pump specs by ID", description = "Retrieve details of pump specifications by their unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pump specs found"),
            @ApiResponse(responseCode = "404", description = "Pump specs not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PumpSpecs> getById(
            @Parameter(description = "UUID of the pump specifications")
            @PathVariable UUID id) {
        return service.getSpecsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get pump specs by equipment", description = "Retrieve pump specifications linked to specific equipment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pump specs found"),
            @ApiResponse(responseCode = "404", description = "No specs found for the given equipment")
    })
    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<PumpSpecs> getByEquipment(
            @Parameter(description = "UUID of the equipment")
            @PathVariable UUID equipmentId) {
        return service.getSpecsByEquipmentId(equipmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update pump specs", description = "Updates existing pump specifications by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pump specs updated successfully"),
            @ApiResponse(responseCode = "404", description = "Pump specs not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PumpSpecs> update(
            @Parameter(description = "UUID of the pump specifications to update")
            @PathVariable UUID id,
            @RequestBody PumpSpecs specs) {
        return ResponseEntity.ok(service.updateSpecs(id, specs));
    }

    @Operation(summary = "Delete pump specs", description = "Deletes pump specifications by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pump specs deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pump specs not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "UUID of the pump specifications to delete")
            @PathVariable UUID id) {
        service.deleteSpecs(id);
        return ResponseEntity.noContent().build();
    }
}
