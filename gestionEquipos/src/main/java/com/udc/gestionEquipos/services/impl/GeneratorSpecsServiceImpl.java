package com.udc.gestionEquipos.services.impl;

import com.udc.gestionEquipos.models.GeneratorSpecs;
import com.udc.gestionEquipos.repositories.GeneratorSpecsRepository;
import com.udc.gestionEquipos.services.GeneratorSpecsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GeneratorSpecsServiceImpl implements GeneratorSpecsService {

    private final GeneratorSpecsRepository repository;

    public GeneratorSpecsServiceImpl(GeneratorSpecsRepository repository) {
        this.repository = repository;
    }

    @Override
    public GeneratorSpecs createSpecs(GeneratorSpecs specs) {
        return repository.save(specs);
    }

    @Override
    public List<GeneratorSpecs> getAllSpecs() {
        return repository.findAll();
    }

    @Override
    public Optional<GeneratorSpecs> getSpecsById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<GeneratorSpecs> getSpecsByEquipmentId(UUID equipmentId) {
        return repository.findByEquipmentId(equipmentId);
    }

    @Override
    public GeneratorSpecs updateSpecs(UUID id, GeneratorSpecs specs) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setPlantBrand(specs.getPlantBrand());
                    existing.setPlantModel(specs.getPlantModel());
                    existing.setPlantSerial(specs.getPlantSerial());
                    existing.setEngineBrand(specs.getEngineBrand());
                    existing.setEngineModel(specs.getEngineModel());
                    existing.setEngineSerial(specs.getEngineSerial());
                    existing.setGeneratorBrand(specs.getGeneratorBrand());
                    existing.setGeneratorModel(specs.getGeneratorModel());
                    existing.setGeneratorSerial(specs.getGeneratorSerial());
                    existing.setPower(specs.getPower());
                    existing.setWorkingHours(specs.getWorkingHours());
                    existing.setCpl(specs.getCpl());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("GeneratorSpecs not found with id: " + id));
    }

    @Override
    public void deleteSpecs(UUID id) {
        repository.deleteById(id);
    }
}
