package com.buildbuddy.domain.forum.repository;

import com.buildbuddy.domain.forum.entity.ThreadLikeEntity;
import com.buildbuddy.domain.forum.entity.pk.ThreadLikePk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThreadLikeRepository extends JpaRepository<ThreadLikeEntity, ThreadLikePk> {

    Optional<ThreadLikeEntity> findByThreadIdAndUserId(Integer threadId, Integer userId);
}
