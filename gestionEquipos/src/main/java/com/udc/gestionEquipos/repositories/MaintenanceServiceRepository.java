package com.udc.gestionEquipos.repositories;

import com.udc.gestionEquipos.models.MaintenanceService;
import com.udc.gestionEquipos.models.enums.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MaintenanceServiceRepository extends JpaRepository<MaintenanceService, UUID> {

    List<MaintenanceService> findByEquipment_Id(UUID equipmentId);

    List<MaintenanceService> findByStatus(ServiceStatus status);

    List<MaintenanceService> findByTechnicianId(UUID technicianId);
}
