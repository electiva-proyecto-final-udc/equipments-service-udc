package com.udc.gestionEquipos.services.impl;

import com.udc.gestionEquipos.models.CompressorSpecs;
import com.udc.gestionEquipos.models.Equipment;
import com.udc.gestionEquipos.models.GeneratorSpecs;
import com.udc.gestionEquipos.models.PumpSpecs;
import com.udc.gestionEquipos.models.dto.EquipmentRequest;
import com.udc.gestionEquipos.models.enums.EquipmentStatus;
import com.udc.gestionEquipos.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EquipmentBuilderServiceImpl implements EquipmentBuilderService {

    private final EquipmentService equipmentService;
    private final GeneratorSpecsService generatorSpecsService;
    private final PumpSpecsService pumpSpecsService;
    private final CompressorSpecsService compressorSpecsService;

    @Override
    public Equipment createEquipment(EquipmentRequest req) {

        // Crear equipo base
        Equipment equipment = equipmentService.createEquipment(
                Equipment.builder()
                        .code(req.getCode())
                        .location(req.getLocation())
                        .clientId(req.getClientId())
                        .status(EquipmentStatus.AVAILABLE)
                        .type(req.getType())
                        .build()
        );

        // Dependiendo del tipo, crear specs correctas
        switch (req.getType()) {

            case GENERATOR:
                GeneratorSpecs generator = mapGeneratorSpecs(req.getSpecs());
                generator.setEquipment(equipment);
                generatorSpecsService.createSpecs(generator);
                equipment.setGeneratorSpecs(generator);
                break;

            case PUMP:
                PumpSpecs pump = mapPumpSpecs(req.getSpecs());
                pump.setEquipment(equipment);
                pumpSpecsService.createSpecs(pump);
                equipment.setPumpSpecs(pump);
                break;

            case COMPRESSOR:
                CompressorSpecs comp = mapCompressorSpecs(req.getSpecs());
                comp.setEquipment(equipment);
                compressorSpecsService.createSpecs(comp);
                equipment.setCompressorSpecs(comp);
                break;
        }

        return equipment;
    }

    @Override
    public Equipment updateEquipment(UUID equipmentId, EquipmentRequest req) {

        Equipment equipment = equipmentService.getEquipmentById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        equipment.setLocation(req.getLocation());
        equipment.setClientId(req.getClientId());
        UUID specsId = UUID.fromString((String) req.getSpecs().get("id"));
        switch (req.getType()) {

            case GENERATOR:
                GeneratorSpecs generator = mapGeneratorSpecs(req.getSpecs());
                generator.setEquipment(equipment);
                generatorSpecsService.updateSpecs(specsId, generator);
                equipment.setGeneratorSpecs(generator);
                break;

            case PUMP:
                PumpSpecs pump = mapPumpSpecs(req.getSpecs());
                pump.setEquipment(equipment);
                pumpSpecsService.updateSpecs(specsId, pump);
                equipment.setPumpSpecs(pump);
                break;

            case COMPRESSOR:
                CompressorSpecs comp = mapCompressorSpecs(req.getSpecs());
                comp.setEquipment(equipment);
                compressorSpecsService.updateSpecs(specsId, comp);
                equipment.setCompressorSpecs(comp);
                break;
        }

        return equipmentService.updateEquipment(equipmentId, equipment);
    }


    private GeneratorSpecs mapGeneratorSpecs(Map<String, Object> m) {
        return GeneratorSpecs.builder()
                .plantBrand((String) m.get("plantBrand"))
                .plantModel((String) m.get("plantModel"))
                .plantSerial((String) m.get("plantSerial"))
                .engineBrand((String) m.get("engineBrand"))
                .engineModel((String) m.get("engineModel"))
                .engineSerial((String) m.get("engineSerial"))
                .generatorBrand((String) m.get("generatorBrand"))
                .generatorModel((String) m.get("generatorModel"))
                .generatorSerial((String) m.get("generatorSerial"))
                .power((String) m.get("power"))
                .workingHours((Integer) m.get("workingHours"))
                .cpl((String) m.get("cpl"))
                .build();
    }

    private PumpSpecs mapPumpSpecs(Map<String, Object> m) {
        return PumpSpecs.builder()
                .brand((String) m.get("brand"))
                .hp(toDouble(m.get("hp")))
                .rpm(toInt(m.get("rpm")))
                .kw(toDouble(m.get("kw")))
                .voltage(toInt(m.get("voltage")))
                .amps(toInt(m.get("amps")))
                .build();
    }

    private CompressorSpecs mapCompressorSpecs(Map<String, Object> m) {
        return CompressorSpecs.builder()
                .brand((String) m.get("brand"))
                .type(CompressorSpecs.CompressorType.valueOf((String)m.get("type")))
                .voltage(toInt(m.get("voltage")))
                .hp(toDouble(m.get("hp")))
                .quantity(toInt(m.get("quantity")))
                .amps(toInt(m.get("amps")))
                .rpm(toInt(m.get("rpm")))
                .kw(toDouble(m.get("kw")))
                .headType(CompressorSpecs.HeadType.valueOf((String)m.get("headType")))
                .build();
    }

    private Integer toInt(Object o) { return o == null ? null : ( (Number) o ).intValue(); }
    private Double toDouble(Object o) { return o == null ? null : ( (Number) o ).doubleValue(); }
}
