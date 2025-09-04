package com.udc.gestionEquipos.controllers;

import com.udc.gestionEquipos.models.GeneratorSpecs;
import com.udc.gestionEquipos.services.GeneratorSpecsService;
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
@RequestMapping("/api/specs/generators")
@Tag(name = "Generator Specifications", description = "Endpoints for managing generator specifications")
public class GeneratorSpecsController {

    private final GeneratorSpecsService service;

    public GeneratorSpecsController(GeneratorSpecsService service) {
        this.service = service;
    }

    @Operation(summary = "Create generator specs", description = "Registers new generator specifications linked to equipment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Generator specifications created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<GeneratorSpecs> create(@RequestBody GeneratorSpecs specs) {
        return ResponseEntity.ok(service.createSpecs(specs));
    }

    @Operation(summary = "Get all generator specs", description = "Retrieves all generator specifications from the system")
    @ApiResponse(responseCode = "200", description = "List of generator specifications")
    @GetMapping
    public ResponseEntity<List<GeneratorSpecs>> getAll() {
        return ResponseEntity.ok(service.getAllSpecs());
    }

    @Operation(summary = "Get generator specs by ID", description = "Retrieve details of generator specifications by their unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Generator specs found"),
            @ApiResponse(responseCode = "404", description = "Generator specs not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GeneratorSpecs> getById(
            @Parameter(description = "UUID of the generator specifications")
            @PathVariable UUID id) {
        return service.getSpecsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get generator specs by equipment", description = "Retrieve generator specifications linked to specific equipment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Generator specs found"),
            @ApiResponse(responseCode = "404", description = "No specs found for the given equipment")
    })
    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<GeneratorSpecs> getByEquipment(
            @Parameter(description = "UUID of the equipment")
            @PathVariable UUID equipmentId) {
        return service.getSpecsByEquipmentId(equipmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update generator specs", description = "Updates existing generator specifications by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Generator specs updated successfully"),
            @ApiResponse(responseCode = "404", description = "Generator specs not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<GeneratorSpecs> update(
            @Parameter(description = "UUID of the generator specifications to update")
            @PathVariable UUID id,
            @RequestBody GeneratorSpecs specs) {
        return ResponseEntity.ok(service.updateSpecs(id, specs));
    }

    @Operation(summary = "Delete generator specs", description = "Deletes generator specifications by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Generator specs deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Generator specs not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "UUID of the generator specifications to delete")
            @PathVariable UUID id) {
        service.deleteSpecs(id);
        return ResponseEntity.noContent().build();
    }
}
