package com.udc.gestionEquipos.controllers;

import com.udc.gestionEquipos.models.Checklist;
import com.udc.gestionEquipos.models.enums.EquipmentType;
import com.udc.gestionEquipos.services.ChecklistService;
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
@RequestMapping("/api/checklists")
@Tag(name = "Checklists", description = "Endpoints for managing equipment checklists")
public class ChecklistController {

    private final ChecklistService checklistService;

    public ChecklistController(ChecklistService checklistService) {
        this.checklistService = checklistService;
    }

    @Operation(summary = "Create a new checklist", description = "Registers a new checklist in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Checklist created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<Checklist> createChecklist(@RequestBody Checklist checklist) {
        return ResponseEntity.ok(checklistService.createChecklist(checklist));
    }

    @Operation(summary = "Get all checklists", description = "Retrieves all checklists from database")
    @ApiResponse(responseCode = "200", description = "List of all checklists")
    @GetMapping
    public ResponseEntity<List<Checklist>> getAllChecklists() {
        return ResponseEntity.ok(checklistService.getAllChecklists());
    }

    @Operation(summary = "Get checklist by ID", description = "Retrieve a checklist by its unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Checklist found"),
            @ApiResponse(responseCode = "404", description = "Checklist not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Checklist> getChecklistById(
            @Parameter(description = "UUID of the checklist to be retrieved")
            @PathVariable UUID id) {
        return checklistService.getChecklistById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get checklist by service ID", description = "Retrieve checklist linked to a maintenance service")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Checklist found"),
            @ApiResponse(responseCode = "404", description = "Checklist not found")
    })
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<Checklist> getChecklistByServiceId(
            @Parameter(description = "UUID of the maintenance service")
            @PathVariable UUID serviceId) {
        return checklistService.getChecklistByServiceId(serviceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get checklists by equipment type", description = "Retrieve all checklists filtered by equipment type")
    @ApiResponse(responseCode = "200", description = "List of checklists by type")
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Checklist>> getChecklistsByType(
            @Parameter(description = "Type of equipment (GENERATOR, PUMP, COMPRESSOR)")
            @PathVariable EquipmentType type) {
        return ResponseEntity.ok(checklistService.getChecklistsByEquipmentType(type));
    }

    @Operation(summary = "Update checklist", description = "Updates checklist information by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Checklist updated successfully"),
            @ApiResponse(responseCode = "404", description = "Checklist not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Checklist> updateChecklist(
            @Parameter(description = "UUID of the checklist to update")
            @PathVariable UUID id,
            @RequestBody Checklist checklist) {
        return ResponseEntity.ok(checklistService.updateChecklist(id, checklist));
    }

    @Operation(summary = "Delete checklist", description = "Deletes a checklist by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Checklist deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Checklist not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChecklist(
            @Parameter(description = "UUID of the checklist to delete")
            @PathVariable UUID id) {
        checklistService.deleteChecklist(id);
        return ResponseEntity.noContent().build();
    }
}

