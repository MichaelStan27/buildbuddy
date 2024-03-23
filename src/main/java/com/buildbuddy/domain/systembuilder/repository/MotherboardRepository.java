package com.buildbuddy.domain.systembuilder.repository;

import com.buildbuddy.domain.systembuilder.entity.MotherboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MotherboardRepository extends JpaRepository<MotherboardEntity, Integer>, JpaSpecificationExecutor<MotherboardEntity> {

    Optional<MotherboardEntity> findById(Integer id);
}
