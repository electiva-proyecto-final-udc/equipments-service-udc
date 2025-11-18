package com.udc.gestionEquipos.services.impl;

import com.udc.gestionEquipos.models.Equipment;
import com.udc.gestionEquipos.models.dto.EquipmentDetailsDTO;
import com.udc.gestionEquipos.models.dto.userService.GetPersonByIdResponse;
import com.udc.gestionEquipos.models.enums.EquipmentStatus;
import com.udc.gestionEquipos.models.enums.EquipmentType;
import com.udc.gestionEquipos.repositories.EquipmentRepository;
import com.udc.gestionEquipos.services.EquipmentService;
import com.udc.gestionEquipos.services.externalServices.UserServiceClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final UserServiceClient userClient;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, UserServiceClient userClient) {
        this.equipmentRepository = equipmentRepository;
        this.userClient = userClient;
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

    public EquipmentDetailsDTO getEquipmentDetails(UUID id, String token) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        GetPersonByIdResponse.UserData client = userClient.getUserById(equipment.getClientId().toString(), token);
        return EquipmentDetailsDTO.builder()
                .id(equipment.getId())
                .code(equipment.getCode())
                .type(equipment.getType().name())
                .status(equipment.getStatus().name())
                .location(equipment.getLocation())
                .client(client)
                .build();
    }

    @Override
    public void deleteEquipment(UUID id) {
        equipmentRepository.deleteById(id);
    }
}