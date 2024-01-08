package com.buildbuddy.domain.forum.repository;

import com.buildbuddy.domain.forum.entity.CommentEntity;
import com.buildbuddy.util.spesification.SpecificationCreator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer>, JpaSpecificationExecutor<CommentEntity> {

    Optional<CommentEntity> findByIdAndUserIdAndThreadId(Integer id, Integer userId, Integer threadId);

}
