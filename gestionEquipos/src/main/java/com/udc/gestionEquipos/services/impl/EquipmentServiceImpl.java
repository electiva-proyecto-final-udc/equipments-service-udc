package com.udc.gestionEquipos.services.impl;

import com.udc.gestionEquipos.models.Equipment;
import com.udc.gestionEquipos.models.enums.EquipmentStatus;
import com.udc.gestionEquipos.models.enums.EquipmentType;
import com.udc.gestionEquipos.repositories.EquipmentRepository;
import com.udc.gestionEquipos.services.EquipmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Equipment createEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    @Override
    public Optional<Equipment> getEquipmentById(UUID id) {
        return equipmentRepository.findById(id);
    }

    @Override
    public List<Equipment> getEquipmentByType(EquipmentType type) {
        return equipmentRepository.findByType(type);
    }

    @Override
    public List<Equipment> getEquipmentByStatus(EquipmentStatus status) {
        return equipmentRepository.findByStatus(status);
    }

    @Override
    public List<Equipment> getEquipmentByClientId(UUID clientId) {
        return equipmentRepository.findByClientId(clientId);
    }

    @Override
    public Equipment updateEquipment(UUID id, Equipment equipment) {
        return equipmentRepository.findById(id)
                .map(existing -> {
                    existing.setCode(equipment.getCode());
                    existing.setLocation(equipment.getLocation());
                    existing.setStatus(equipment.getStatus());
                    existing.setType(equipment.getType());
                    return equipmentRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Equipment not found with id: " + id));
    }

    @Override
    public void deleteEquipment(UUID id) {
        equipmentRepository.deleteById(id);
    }
}