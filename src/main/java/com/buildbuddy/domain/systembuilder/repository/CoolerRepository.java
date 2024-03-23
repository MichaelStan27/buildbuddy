package com.buildbuddy.domain.systembuilder.repository;

import com.buildbuddy.domain.systembuilder.entity.CoolerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CoolerRepository extends JpaRepository<CoolerEntity, Integer>, JpaSpecificationExecutor<CoolerEntity> {

    @Override
    Optional<CoolerEntity> findById(Integer id);
}
