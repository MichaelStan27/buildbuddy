package com.buildbuddy.domain.forum.repository;

import com.buildbuddy.domain.forum.entity.ThreadEntity;
import com.buildbuddy.domain.forum.entity.ThreadModel;
import com.buildbuddy.domain.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ThreadRepository extends JpaRepository<ThreadEntity, Integer>, JpaSpecificationExecutor<ThreadEntity> {

    Optional<ThreadEntity> findByIdAndUserId(Integer id, Integer userId);

    @Query(nativeQuery = true, value = "select t.thread_id as threadId, t.post as post, u.username as username, " +
            "u.profile_picture as userProfile, (select count(*) from thread_like where thread_id = t.thread_id) AS totalLike, " +
            "(case when exists (select * from thread_like where thread_id = t.thread_id AND user_id = :userId) then true else false end) as isLikedByUser, t.created_time as createdTime, t.last_update_time as lastUpdateTime " +
            "from thread t join user u on t.user_id = u.user_id " +
            "where t.thread_id = (case when :threadId is null then t.thread_id else :threadId end) " +
            "and (u.username like (case when :search is null then u.username else :search end) " +
            "or t.post like (case when :search is null then t.post else :search end) " +
            ") ")
    Page<ThreadModel> getByCustomParam(@Param("userId") Integer userId,
                                       @Param("threadId") Integer threadId,
                                       @Param("search") String search, Pageable pageable);
}
