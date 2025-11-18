package com.udc.gestionEquipos.services;

import com.udc.gestionEquipos.models.MaintenanceService;
import com.udc.gestionEquipos.models.enums.ServiceStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MaintenanceServiceService {

    MaintenanceService createService(MaintenanceService service, String token);

    List<MaintenanceService> getAllServices();

    Optional<MaintenanceService> getServiceById(UUID id);

    List<MaintenanceService> getServicesByEquipment(UUID equipmentId);

    List<MaintenanceService> getServicesByTechnician(UUID technicianId);

    List<MaintenanceService> getServicesByStatus(ServiceStatus status);

    MaintenanceService updateService(UUID id, MaintenanceService service);

    void deleteService(UUID id);
}