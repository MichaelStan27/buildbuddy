package com.buildbuddy.domain.forum.repository;

import com.buildbuddy.domain.forum.entity.ThreadEntity;
import com.buildbuddy.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ThreadRepository extends JpaRepository<ThreadEntity, Integer>, JpaSpecificationExecutor<ThreadEntity> {

    Optional<ThreadEntity> findByIdAndUserId(Integer id, Integer userId);


}
