package com.buildbuddy.domain.systembuilder.repository;

import com.buildbuddy.domain.systembuilder.entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<StorageEntity, Integer>, JpaSpecificationExecutor<StorageEntity> {

    Optional<StorageEntity> findById(Integer integer);
}
