package com.buildbuddy.domain.systembuilder.repository;

import com.buildbuddy.domain.systembuilder.entity.RamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RamRepository extends JpaRepository<RamEntity, Integer>, JpaSpecificationExecutor<RamEntity> {

    Optional<RamEntity> findById(Integer id);
}
