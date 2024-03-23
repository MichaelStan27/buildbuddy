package com.buildbuddy.domain.systembuilder.repository;

import com.buildbuddy.domain.systembuilder.entity.GraphicsCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface GraphicsCardRepository extends JpaRepository<GraphicsCardEntity, Integer>, JpaSpecificationExecutor<GraphicsCardEntity>
{
        Optional<GraphicsCardEntity> findById(Integer id);
}
