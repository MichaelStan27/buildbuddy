package com.buildbuddy.domain.systembuilder.repository;

import com.buildbuddy.domain.systembuilder.entity.CaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CaseRepository extends JpaRepository<CaseEntity, Integer>, JpaSpecificationExecutor<CaseEntity> {

    Optional<CaseEntity> findById(Integer id);

}
