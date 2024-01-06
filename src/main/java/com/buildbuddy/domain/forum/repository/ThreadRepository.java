package com.buildbuddy.domain.forum.repository;

import com.buildbuddy.domain.forum.entity.ThreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ThreadRepository extends JpaRepository<ThreadEntity, Integer> {

    @Query(nativeQuery = true, value = "select * from thread where thread_id = :threadId")
    Optional<ThreadEntity> findByThreadId(@Param(value = "threadId") Integer threadId);

}
