package com.buildbuddy.domain.forum.repository;

import com.buildbuddy.domain.forum.entity.ThreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<ThreadEntity, Integer> {
}
