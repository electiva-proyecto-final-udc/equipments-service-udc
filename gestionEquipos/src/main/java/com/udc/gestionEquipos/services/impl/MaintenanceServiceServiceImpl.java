package com.udc.gestionEquipos.services.impl;

import com.udc.gestionEquipos.models.Equipment;
import com.udc.gestionEquipos.models.MaintenanceService;
import com.udc.gestionEquipos.models.dto.notificationService.NotificationResponse;
import com.udc.gestionEquipos.models.dto.notificationService.PostEmail;
import com.udc.gestionEquipos.models.dto.notificationService.TechnicianAssignedEmailPayload;
import com.udc.gestionEquipos.models.dto.userService.GetPersonByIdResponse;
import com.udc.gestionEquipos.models.enums.ServiceStatus;
import com.udc.gestionEquipos.repositories.MaintenanceServiceRepository;
import com.udc.gestionEquipos.services.EquipmentService;
import com.udc.gestionEquipos.services.MaintenanceServiceService;
import com.udc.gestionEquipos.services.externalServices.NotificationServiceClient;
import com.udc.gestionEquipos.services.externalServices.UserServiceClient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaintenanceServiceServiceImpl implements MaintenanceServiceService {

    private final MaintenanceServiceRepository repository;
    private final NotificationServiceClient notificationClient;
    private final EquipmentService equipmentService;

    public MaintenanceServiceServiceImpl(MaintenanceServiceRepository repository, NotificationServiceClient notificationClient, EquipmentService equipmentService) {
        this.repository = repository;
        this.notificationClient = notificationClient;
        this.equipmentService = equipmentService;
    }

    @Override
    public MaintenanceService createService(MaintenanceService service, String token) {
        NotificationResponse response = sendEmailTechnicianAssigned(service, token);
        if (response != null){
            System.out.println(response);
        }
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
    public MaintenanceService updateService(UUID id, MaintenanceService service, String token) {

        MaintenanceService existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));

        if (service.getType() != null) {
            existing.setType(service.getType());
        }

        if (service.getReportedProblem() != null) {
            existing.setReportedProblem(service.getReportedProblem());
        }

        if (service.getPriority() != null) {
            existing.setPriority(service.getPriority());
        }

        if (service.getTechnicianId() != null) {
            existing.setTechnicianId(service.getTechnicianId());
        }

        if (service.getExecutedActivity() != null) {
            existing.setExecutedActivity(service.getExecutedActivity());
        }

        if (service.getDescription() != null) {
            existing.setDescription(service.getDescription());
        }

        if (service.getRecommendations() != null) {
            existing.setRecommendations(service.getRecommendations());
        }

        if (service.getStatus() != null) {
            if (service.getStatus() == ServiceStatus.COMPLETED){
                try {
                    NotificationResponse response = sendEmailFinishReparation(id, token);
                    System.out.println(response);
                } catch (Exception e) {
                    System.out.println("No se pudo enviar el correo");
                    System.out.println(e.getMessage());
                }
            }
            existing.setStatus(service.getStatus());
        }

        if (service.getDeliveryCommitment() != null) {
            existing.setDeliveryCommitment(service.getDeliveryCommitment());
        }
        existing.setUpdatedAt(Instant.now());
        return repository.save(existing);
    }

    private NotificationResponse sendEmailFinishReparation(UUID serviceId, String token){
        NotificationResponse response = null;
        try {
            Optional<MaintenanceService> ser = getServiceById(serviceId);
            MaintenanceService service = ser
                    .orElseThrow(() -> new RuntimeException("Equipment not found"));
            Optional<Equipment> equipment = equipmentService.getEquipmentById(service.getEquipment().getId());
            Equipment eq = equipment
                    .orElseThrow(() -> new RuntimeException("Equipment not found"));

            PostEmail payload = new PostEmail(
                    service.getTechnicianId().toString(),
                    service.getCode(),
                    eq.getClientId().toString(),
                    eq.getType().toString(),
                    service.getDescription(),
                    Instant.now().toString(),
                    service.getPriority().toString()
            );

            response = notificationClient.sendFinishReparationEmail(payload, token);
        }catch (Exception e){
            System.out.println("No se pudo enviar el correo");
            System.out.println(e.getMessage());
        }
        return response;
    }

    private NotificationResponse sendEmailTechnicianAssigned(MaintenanceService service, String token) {
        Optional<Equipment> equipment = equipmentService.getEquipmentById(service.getEquipmentId());
        Equipment eq = equipment
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        PostEmail payload = new PostEmail(
                service.getTechnicianId().toString(),
                service.getCode(),
                eq.getClientId().toString(),
                eq.getType().toString(),
                service.getDescription(),
                service.getDeliveryCommitment().toString(),
                service.getPriority().toString()
        );

        // Enviar notificación al micro de correos
        return notificationClient.sendTechnicianAssigned(payload, token);
    }


    @Override
    public void deleteService(UUID id) {
        repository.deleteById(id);
    }

    private String translateEquipmentType(String type) {
        if (type == null) return "";

        return switch (type.toUpperCase()) {
            case "GENERATOR" -> "Generador";
            case "PUMP" -> "Bomba";
            case "COMPRESSOR" -> "Compresor";
            default -> type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
        };
    }

    private String translatePriority(String priority) {
        if (priority == null) return "";

        return switch (priority.toUpperCase()) {
            case "LOW" -> "Baja";
            case "MEDIUM" -> "Media";
            case "HIGH" -> "Alta";
            default -> priority.substring(0, 1).toUpperCase() + priority.substring(1).toLowerCase();
        };
    }


}
