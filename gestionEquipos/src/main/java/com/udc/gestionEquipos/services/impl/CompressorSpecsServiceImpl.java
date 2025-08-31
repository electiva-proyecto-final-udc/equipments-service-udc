package com.udc.gestionEquipos.services.impl;

import com.udc.gestionEquipos.models.CompressorSpecs;
import com.udc.gestionEquipos.repositories.CompressorSpecsRepository;
import com.udc.gestionEquipos.services.CompressorSpecsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompressorSpecsServiceImpl implements CompressorSpecsService {

    private final CompressorSpecsRepository repository;

    public CompressorSpecsServiceImpl(CompressorSpecsRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompressorSpecs createSpecs(CompressorSpecs specs) {
        return repository.save(specs);
    }

    @Override
    public List<CompressorSpecs> getAllSpecs() {
        return repository.findAll();
    }

    @Override
    public Optional<CompressorSpecs> getSpecsById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<CompressorSpecs> getSpecsByEquipmentId(UUID equipmentId) {
        return repository.findByEquipmentId(equipmentId);
    }

    @Override
    public CompressorSpecs updateSpecs(UUID id, CompressorSpecs specs) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setBrand(specs.getBrand());
                    existing.setType(specs.getType());
                    existing.setVoltage(specs.getVoltage());
                    existing.setHp(specs.getHp());
                    existing.setQuantity(specs.getQuantity());
                    existing.setAmps(specs.getAmps());
                    existing.setRpm(specs.getRpm());
                    existing.setKw(specs.getKw());
                    existing.setHeadType(specs.getHeadType());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("CompressorSpecs not found with id: " + id));
    }

    @Override
    public void deleteSpecs(UUID id) {
        repository.deleteById(id);
    }
}