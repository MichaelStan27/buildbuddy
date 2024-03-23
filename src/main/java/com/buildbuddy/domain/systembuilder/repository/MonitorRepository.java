package com.buildbuddy.domain.systembuilder.repository;

import com.buildbuddy.domain.systembuilder.entity.MonitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MonitorRepository extends JpaRepository<MonitorEntity, Integer>, JpaSpecificationExecutor<MonitorEntity> {

    Optional<MonitorEntity> findById(Integer id);

}
