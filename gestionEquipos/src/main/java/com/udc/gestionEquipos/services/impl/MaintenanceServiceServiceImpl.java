package com.udc.gestionEquipos.services.impl;

import com.udc.gestionEquipos.models.Equipment;
import com.udc.gestionEquipos.models.MaintenanceService;
import com.udc.gestionEquipos.models.dto.notificationService.NotificationResponse;
import com.udc.gestionEquipos.models.dto.notificationService.TechnicianAssignedEmailPayload;
import com.udc.gestionEquipos.models.dto.userService.GetPersonByIdResponse;
import com.udc.gestionEquipos.models.enums.ServiceStatus;
import com.udc.gestionEquipos.repositories.MaintenanceServiceRepository;
import com.udc.gestionEquipos.services.EquipmentService;
import com.udc.gestionEquipos.services.MaintenanceServiceService;
import com.udc.gestionEquipos.services.externalServices.NotificationServiceClient;
import com.udc.gestionEquipos.services.externalServices.UserServiceClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaintenanceServiceServiceImpl implements MaintenanceServiceService {

    private final MaintenanceServiceRepository repository;
    private final NotificationServiceClient notificationClient;
    private final UserServiceClient userClient;
    private final EquipmentService equipmentService;

    public MaintenanceServiceServiceImpl(MaintenanceServiceRepository repository, NotificationServiceClient notificationClient, UserServiceClient userClient, EquipmentService equipmentService) {
        this.repository = repository;
        this.notificationClient = notificationClient;
        this.userClient = userClient;
        this.equipmentService = equipmentService;
    }

    @Override
    public MaintenanceService createService(MaintenanceService service, String token) {
        Optional<Equipment> equipment = equipmentService.getEquipmentById(service.getEquipmentId());
        Equipment eq = equipment
                .orElseThrow(() -> new RuntimeException("Equipment not found"));


        GetPersonByIdResponse.UserData technician =
                userClient.getTechnicianById(service.getTechnicianId().toString(), token);

        GetPersonByIdResponse.UserData client = userClient.getUserById(eq.getClientId().toString(), token);
        // Construir el payload del correo
        TechnicianAssignedEmailPayload payload = new TechnicianAssignedEmailPayload(
                technician.getEmail(),
                technician.getName() + " " + technician.getSurname(),
                service.getCode(),
                eq.getType().toString(),
                client.getName() + " " + client.getSurname(),
                service.getDescription(),
                service.getDeliveryCommitment().toString(),
                service.getPriority().toString()
        );

        // Enviar notificación al micro de correos
        NotificationResponse notification =
                notificationClient.sendTechnicianAssigned(payload, token);

        // Guardar y devolver el servicio
        return repository.save(service);
    }


    @Override
    public List<MaintenanceService> getAllServices() {
        return repository.findAll();
    }

    @Override
    public Optional<MaintenanceService> getServiceById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<MaintenanceService> getServicesByEquipment(UUID equipmentId) {
        return repository.findByEquipment_Id(equipmentId);
    }

    @Override
    public List<MaintenanceService> getServicesByTechnician(UUID technicianId) {
        return repository.findByTechnicianId(technicianId);
    }

    @Override
    public List<MaintenanceService> getServicesByStatus(ServiceStatus status) {
        return repository.findByStatus(status);
    }

    @Override
    public MaintenanceService updateService(UUID id, MaintenanceService service) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setType(service.getType());
                    existing.setReportedProblem(service.getReportedProblem());
                    existing.setPriority(service.getPriority());
                    existing.setTechnicianId(service.getTechnicianId());
                    existing.setExecutedActivity(service.getExecutedActivity());
                    existing.setDescription(service.getDescription());
                    existing.setRecommendations(service.getRecommendations());
                    existing.setStatus(service.getStatus());
                    existing.setDeliveryCommitment(service.getDeliveryCommitment());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
    }

    @Override
    public void deleteService(UUID id) {
        repository.deleteById(id);
    }
}
