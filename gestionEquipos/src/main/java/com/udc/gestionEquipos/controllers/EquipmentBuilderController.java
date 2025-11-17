package com.udc.gestionEquipos.controllers;

import com.udc.gestionEquipos.models.Equipment;
import com.udc.gestionEquipos.services.impl.EquipmentBuilderServiceImpl;
import com.udc.gestionEquipos.models.dto.EquipmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/equipmentBuilder")
@RequiredArgsConstructor
public class EquipmentBuilderController {

    private final EquipmentBuilderServiceImpl equipmentBuilderService;

    @PostMapping("/create")
    public ResponseEntity<Equipment> createEquipment(@RequestBody EquipmentRequest req) {
        return ResponseEntity.ok(equipmentBuilderService.createEquipment(req));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Equipment> updateEquipment(
            @PathVariable UUID id,
            @RequestBody EquipmentRequest req
    ) {
        return ResponseEntity.ok(equipmentBuilderService.updateEquipment(id, req));
    }

}

