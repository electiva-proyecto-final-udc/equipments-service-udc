package com.udc.gestionEquipos.repositories;

import com.udc.gestionEquipos.models.GeneratorSpecs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GeneratorSpecsRepository extends JpaRepository<GeneratorSpecs, UUID> {}

