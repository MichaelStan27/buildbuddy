package com.buildbuddy.domain.systembuilder.repository;

import com.buildbuddy.domain.systembuilder.entity.ComputerSetupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ComputerSetupRepository extends JpaRepository<ComputerSetupEntity, Integer>, JpaSpecificationExecutor<ComputerSetupEntity> {
    Optional<ComputerSetupEntity> findById(Integer integer);

}
