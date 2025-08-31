package com.udc.gestionEquipos.services.impl;

import com.udc.gestionEquipos.models.PumpSpecs;
import com.udc.gestionEquipos.repositories.PumpSpecsRepository;
import com.udc.gestionEquipos.services.PumpSpecsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PumpSpecsServiceImpl implements PumpSpecsService {

    private final PumpSpecsRepository repository;

    public PumpSpecsServiceImpl(PumpSpecsRepository repository) {
        this.repository = repository;
    }

    @Override
    public PumpSpecs createSpecs(PumpSpecs specs) {
        return repository.save(specs);
    }

    @Override
    public List<PumpSpecs> getAllSpecs() {
        return repository.findAll();
    }

    @Override
    public Optional<PumpSpecs> getSpecsById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<PumpSpecs> getSpecsByEquipmentId(UUID equipmentId) {
        return repository.findByEquipmentId(equipmentId);
    }

    @Override
    public PumpSpecs updateSpecs(UUID id, PumpSpecs specs) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setBrand(specs.getBrand());
                    existing.setHp(specs.getHp());
                    existing.setRpm(specs.getRpm());
                    existing.setKw(specs.getKw());
                    existing.setVoltage(specs.getVoltage());
                    existing.setAmps(specs.getAmps());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("PumpSpecs not found with id: " + id));
    }

    @Override
    public void deleteSpecs(UUID id) {
        repository.deleteById(id);
    }
}
