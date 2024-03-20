package com.buildbuddy.domain.systembuilder.repository;

import com.buildbuddy.domain.systembuilder.entity.ProcessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProcessorRepository extends JpaRepository<ProcessorEntity, Integer>, JpaSpecificationExecutor<ProcessorEntity> {

    Optional<ProcessorEntity> findById(Integer id);
}
