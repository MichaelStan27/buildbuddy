package com.buildbuddy.domain.consult.repository;

import com.buildbuddy.domain.consult.entity.RoomMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoomMasterRepository extends JpaRepository<RoomMaster, String>, JpaSpecificationExecutor<RoomMaster> {
}
