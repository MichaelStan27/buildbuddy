package com.buildbuddy.domain.systembuilder.repository;

import com.buildbuddy.domain.systembuilder.entity.PowerSupplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PowerSupplyRepository extends JpaRepository<PowerSupplyEntity, Integer>, JpaSpecificationExecutor<PowerSupplyEntity> {

    Optional<PowerSupplyEntity> findById(Integer id);
}
