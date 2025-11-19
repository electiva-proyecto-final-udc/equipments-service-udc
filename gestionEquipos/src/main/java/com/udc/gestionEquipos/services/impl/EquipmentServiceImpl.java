package com.udc.gestionEquipos.services.impl;

import com.udc.gestionEquipos.models.Equipment;
import com.udc.gestionEquipos.models.dto.EquipmentDetailsDTO;
import com.udc.gestionEquipos.models.dto.EquipmentWithClientDTO;
import com.udc.gestionEquipos.models.dto.userService.GetPersonByIdResponse;
import com.udc.gestionEquipos.models.enums.EquipmentStatus;
import com.udc.gestionEquipos.models.enums.EquipmentType;
import com.udc.gestionEquipos.repositories.EquipmentRepository;
import com.udc.gestionEquipos.services.EquipmentService;
import com.udc.gestionEquipos.services.externalServices.UserServiceClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<EquipmentWithClientDTO> getEquipmentWithClientData(String token) {

        List<Equipment> equipments = equipmentRepository.findAll();

        // Obtener solo los IDs únicos
        Set<UUID> clientIds = equipments.stream()
                .map(Equipment::getClientId)
                .collect(Collectors.toSet());

        // Resolver clientes llamando al micro de usuarios
        Map<UUID, GetPersonByIdResponse.UserData> clientsById = new HashMap<>();

        for (UUID clientId : clientIds) {
            GetPersonByIdResponse.UserData client = userClient.getUserById(clientId.toString(), token);
            clientsById.put(clientId, client);
        }

        // Armar la respuesta final
        return equipments.stream()
                .map(e -> {
                    GetPersonByIdResponse.UserData c = clientsById.get(e.getClientId());
                    return new EquipmentWithClientDTO(
                            e.getId(),
                            e.getCode(),
                            e.getCreatedAt(),
                            e.getStatus().name(),
                            e.getType().name(),
                            e.getClientId(),
                            c != null ? c.getName() + " " + c.getSurname() : null
                    );
                })
                .collect(Collectors.toList());
    }


    @Override
    public void deleteEquipment(UUID id) {
        equipmentRepository.deleteById(id);
    }
}