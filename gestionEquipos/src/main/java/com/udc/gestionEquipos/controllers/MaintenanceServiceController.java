package com.udc.gestionEquipos.controllers;

import com.udc.gestionEquipos.models.MaintenanceService;
import com.udc.gestionEquipos.models.enums.ServiceStatus;
import com.udc.gestionEquipos.services.MaintenanceServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
public class MaintenanceServiceController {

    private final MaintenanceServiceService service;

    public MaintenanceServiceController(MaintenanceServiceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MaintenanceService> create(@RequestBody MaintenanceService maintenanceService) {
        return ResponseEntity.ok(service.createService(maintenanceService));
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceService>> getAll() {
        return ResponseEntity.ok(service.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceService> getById(@PathVariable UUID id) {
        return service.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<List<MaintenanceService>> getByEquipment(@PathVariable UUID equipmentId) {
        return ResponseEntity.ok(service.getServicesByEquipment(equipmentId));
    }

    @GetMapping("/technician/{technicianId}")
    public ResponseEntity<List<MaintenanceService>> getByTechnician(@PathVariable UUID technicianId) {
        return ResponseEntity.ok(service.getServicesByTechnician(technicianId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<MaintenanceService>> getByStatus(@PathVariable ServiceStatus status) {
        return ResponseEntity.ok(service.getServicesByStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceService> update(@PathVariable UUID id, @RequestBody MaintenanceService maintenanceService) {
        return ResponseEntity.ok(service.updateService(id, maintenanceService));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
