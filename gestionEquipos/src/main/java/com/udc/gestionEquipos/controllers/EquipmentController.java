package com.udc.gestionEquipos.controllers;

import com.udc.gestionEquipos.models.Equipment;
import com.udc.gestionEquipos.models.enums.EquipmentStatus;
import com.udc.gestionEquipos.models.enums.EquipmentType;
import com.udc.gestionEquipos.services.EquipmentService;
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
@RequestMapping("/api/equipment")
@Tag(name = "Equipment", description = "Endpoints for managing equipment")
public class EquipmentController {

    private final EquipmentService service;

    public EquipmentController(EquipmentService service) {
        this.service = service;
    }

    @Operation(summary = "Create equipment", description = "Registers new equipment in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<Equipment> create(@RequestBody Equipment equipment) {
        return ResponseEntity.ok(service.createEquipment(equipment));
    }

    @Operation(summary = "Get all equipment", description = "Retrieves a list of all registered equipment")
    @ApiResponse(responseCode = "200", description = "List of equipment")
    @GetMapping
    public ResponseEntity<List<Equipment>> getAll() {
        return ResponseEntity.ok(service.getAllEquipment());
    }

    @Operation(summary = "Get equipment by ID", description = "Retrieve details of a specific equipment by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipment found"),
            @ApiResponse(responseCode = "404", description = "Equipment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getById(
            @Parameter(description = "UUID of the equipment to retrieve")
            @PathVariable UUID id) {
        return service.getEquipmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get equipment by type", description = "Retrieve equipment filtered by type")
    @ApiResponse(responseCode = "200", description = "List of equipment by type")
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Equipment>> getByType(
            @Parameter(description = "Equipment type (GENERATOR, PUMP, COMPRESSOR)")
            @PathVariable EquipmentType type) {
        return ResponseEntity.ok(service.getEquipmentByType(type));
    }

    @Operation(summary = "Get equipment by status", description = "Retrieve equipment filtered by status")
    @ApiResponse(responseCode = "200", description = "List of equipment by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Equipment>> getByStatus(
            @Parameter(description = "Equipment status (AVAILABLE, IN_REPAIR, OUT_OF_SERVICE)")
            @PathVariable EquipmentStatus status) {
        return ResponseEntity.ok(service.getEquipmentByStatus(status));
    }

    @Operation(summary = "Get equipment by client", description = "Retrieve equipment associated with a specific client")
    @ApiResponse(responseCode = "200", description = "List of equipment for client")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Equipment>> getByClient(
            @Parameter(description = "UUID of the client")
            @PathVariable UUID clientId) {
        return ResponseEntity.ok(service.getEquipmentByClientId(clientId));
    }

    @Operation(summary = "Update equipment", description = "Updates details of existing equipment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Equipment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Equipment> update(
            @Parameter(description = "UUID of the equipment to update")
            @PathVariable UUID id,
            @RequestBody Equipment equipment) {
        return ResponseEntity.ok(service.updateEquipment(id, equipment));
    }

    @Operation(summary = "Delete equipment", description = "Removes equipment from the system by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Equipment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Equipment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "UUID of the equipment to delete")
            @PathVariable UUID id) {
        service.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}
