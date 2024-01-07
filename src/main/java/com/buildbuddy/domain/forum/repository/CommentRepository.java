package com.buildbuddy.domain.forum.repository;

import com.buildbuddy.domain.forum.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    Optional<CommentEntity> findByIdAndUserIdAndThreadId(Integer id, Integer userId, Integer threadId);

}
