package com.buildbuddy.domain.forum.repository;

import com.buildbuddy.domain.forum.entity.CommentEntity;
import com.buildbuddy.domain.forum.entity.CommentModel;
import com.buildbuddy.util.spesification.SpecificationCreator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer>, JpaSpecificationExecutor<CommentEntity> {

    Optional<CommentEntity> findByIdAndUserIdAndThreadId(Integer id, Integer userId, Integer threadId);

    @Query(nativeQuery = true, value = "select c.comment_id as commentId, u.username as username, u.user_id as userId, u.profile_picture as userProfile, c.message as message, c.thread_id as threadId, c.created_time as createdTime, c.last_update_time as lastUpdateTime " +
            "from comment c join user u on c.user_id = u.user_id " +
            "where c.thread_id = (case when :threadId is null then c.thread_id else :threadId end) " +
            "and (u.username like (case when :search is null then u.username else :search end) " +
            "or c.message like (case when :search is null then c.message else :search end)" +
            ")")
    Page<CommentModel> getByCustomParam(@Param("threadId") Integer threadId, @Param("search") String search, Pageable pageable);

}
