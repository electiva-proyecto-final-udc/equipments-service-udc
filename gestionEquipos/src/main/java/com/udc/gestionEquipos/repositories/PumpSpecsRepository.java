package com.udc.gestionEquipos.repositories;

import com.udc.gestionEquipos.models.PumpSpecs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PumpSpecsRepository extends JpaRepository<PumpSpecs, UUID> {}
