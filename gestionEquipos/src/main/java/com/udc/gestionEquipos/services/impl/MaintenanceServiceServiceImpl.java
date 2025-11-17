package com.udc.gestionEquipos.services.impl;

import com.udc.gestionEquipos.models.MaintenanceService;
import com.udc.gestionEquipos.models.enums.ServiceStatus;
import com.udc.gestionEquipos.repositories.MaintenanceServiceRepository;
import com.udc.gestionEquipos.services.MaintenanceServiceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaintenanceServiceServiceImpl implements MaintenanceServiceService {

    private final MaintenanceServiceRepository repository;

    public MaintenanceServiceServiceImpl(MaintenanceServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public MaintenanceService createService(MaintenanceService service) {
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
