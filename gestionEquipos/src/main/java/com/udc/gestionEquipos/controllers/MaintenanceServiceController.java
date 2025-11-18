package com.udc.gestionEquipos.controllers;

import com.udc.gestionEquipos.models.MaintenanceService;
import com.udc.gestionEquipos.models.enums.ServiceStatus;
import com.udc.gestionEquipos.services.MaintenanceServiceService;
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
@RequestMapping("/api/services")
@Tag(name = "Maintenance Services", description = "Endpoints for managing maintenance services")
public class MaintenanceServiceController {

    private final MaintenanceServiceService service;

    public MaintenanceServiceController(MaintenanceServiceService service) {
        this.service = service;
    }

    @Operation(summary = "Create maintenance service", description = "Registers a new maintenance service for equipment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance service created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<MaintenanceService> create(@RequestBody MaintenanceService maintenanceService,
                                                     @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.createService(maintenanceService, token));
    }

    @Operation(summary = "Get all services", description = "Retrieves a list of all maintenance services")
    @ApiResponse(responseCode = "200", description = "List of maintenance services")
    @GetMapping
    public ResponseEntity<List<MaintenanceService>> getAll() {
        return ResponseEntity.ok(service.getAllServices());
    }

    @Operation(summary = "Get service by ID", description = "Retrieve details of a specific maintenance service by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance service found"),
            @ApiResponse(responseCode = "404", description = "Maintenance service not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceService> getById(
            @Parameter(description = "UUID of the maintenance service")
            @PathVariable UUID id) {
        return service.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get services by equipment", description = "Retrieve all maintenance services linked to a specific equipment")
    @ApiResponse(responseCode = "200", description = "List of maintenance services for the equipment")
    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<List<MaintenanceService>> getByEquipment(
            @Parameter(description = "UUID of the equipment")
            @PathVariable UUID equipmentId) {
        return ResponseEntity.ok(service.getServicesByEquipment(equipmentId));
    }

    @Operation(summary = "Get services by technician", description = "Retrieve all maintenance services assigned to a technician")
    @ApiResponse(responseCode = "200", description = "List of maintenance services for the technician")
    @GetMapping("/technician/{technicianId}")
    public ResponseEntity<List<MaintenanceService>> getByTechnician(
            @Parameter(description = "UUID of the technician")
            @PathVariable UUID technicianId) {
        return ResponseEntity.ok(service.getServicesByTechnician(technicianId));
    }

    @Operation(summary = "Get services by status", description = "Retrieve maintenance services filtered by their current status")
    @ApiResponse(responseCode = "200", description = "List of maintenance services with given status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<MaintenanceService>> getByStatus(
            @Parameter(description = "Service status (CREATED, ASSIGNED, IN_PROGRESS, PAUSED, COMPLETED, CANCELED)")
            @PathVariable ServiceStatus status) {
        return ResponseEntity.ok(service.getServicesByStatus(status));
    }

    @Operation(summary = "Update maintenance service", description = "Updates the details of an existing maintenance service by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance service updated successfully"),
            @ApiResponse(responseCode = "404", description = "Maintenance service not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceService> update(
            @Parameter(description = "UUID of the maintenance service to update")
            @PathVariable UUID id,
            @RequestBody MaintenanceService maintenanceService) {
        return ResponseEntity.ok(service.updateService(id, maintenanceService));
    }

    @Operation(summary = "Delete maintenance service", description = "Deletes a maintenance service by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Maintenance service deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Maintenance service not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "UUID of the maintenance service to delete")
            @PathVariable UUID id) {
        service.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
