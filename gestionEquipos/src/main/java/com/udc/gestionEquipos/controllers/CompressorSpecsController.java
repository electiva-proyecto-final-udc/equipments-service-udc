package com.udc.gestionEquipos.controllers;

import com.udc.gestionEquipos.models.CompressorSpecs;
import com.udc.gestionEquipos.services.CompressorSpecsService;
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
@RequestMapping("/api/specs/compressors")
@Tag(name = "Compressor Specifications", description = "Endpoints for managing compressor specifications")
public class CompressorSpecsController {

    private final CompressorSpecsService service;

    public CompressorSpecsController(CompressorSpecsService service) {
        this.service = service;
    }

    @Operation(summary = "Create compressor specs", description = "Registers specifications for a compressor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Compressor specs created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<CompressorSpecs> create(@RequestBody CompressorSpecs specs) {
        return ResponseEntity.ok(service.createSpecs(specs));
    }

    @Operation(summary = "Get all compressor specs", description = "Retrieves all compressor specifications")
    @ApiResponse(responseCode = "200", description = "List of compressor specs")
    @GetMapping
    public ResponseEntity<List<CompressorSpecs>> getAll() {
        return ResponseEntity.ok(service.getAllSpecs());
    }

    @Operation(summary = "Get compressor specs by ID", description = "Retrieve a specific compressor specification by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Compressor specs found"),
            @ApiResponse(responseCode = "404", description = "Compressor specs not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CompressorSpecs> getById(
            @Parameter(description = "UUID of the compressor specs to retrieve")
            @PathVariable UUID id) {
        return service.getSpecsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get compressor specs by equipment ID", description = "Retrieve compressor specifications associated with an equipment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Compressor specs found"),
            @ApiResponse(responseCode = "404", description = "Compressor specs not found for equipment")
    })
    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<CompressorSpecs> getByEquipment(
            @Parameter(description = "UUID of the equipment linked to compressor specs")
            @PathVariable UUID equipmentId) {
        return service.getSpecsByEquipmentId(equipmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update compressor specs", description = "Updates specifications of a compressor by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Compressor specs updated successfully"),
            @ApiResponse(responseCode = "404", description = "Compressor specs not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CompressorSpecs> update(
            @Parameter(description = "UUID of the compressor specs to update")
            @PathVariable UUID id,
            @RequestBody CompressorSpecs specs) {
        return ResponseEntity.ok(service.updateSpecs(id, specs));
    }

    @Operation(summary = "Delete compressor specs", description = "Deletes compressor specifications by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Compressor specs deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Compressor specs not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "UUID of the compressor specs to delete")
            @PathVariable UUID id) {
        service.deleteSpecs(id);
        return ResponseEntity.noContent().build();
    }
}
