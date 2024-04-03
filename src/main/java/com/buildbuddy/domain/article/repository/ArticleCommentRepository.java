package com.buildbuddy.domain.article.repository;

import com.buildbuddy.domain.article.entity.ArticleCommentEntity;
import com.buildbuddy.domain.article.entity.ArticleCommentModel;
import com.buildbuddy.domain.forum.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleCommentRepository extends JpaRepository<ArticleCommentEntity, Integer> {


    Optional<ArticleCommentEntity> findByIdAndUserIdAndArticleId(Integer id, Integer userId, Integer articleId);

    @Query(nativeQuery = true, value = "select c.comment_id as commentId, u.username as username, u.profile_picture as userProfile, c.message as message, c.article_id as articleId, c.created_time as createdTime, c.last_update_time as lastUpdateTime " +
            "from article_comment c join user u on c.user_id = u.user_id " +
            "where c.article_id = (case when :articleId is null then c.article_id else :articleId end) " +
            "and (u.username like (case when :search is null then u.username else :search end) " +
            "or c.message like (case when :search is null then c.message else :search end)" +
            ")")
    Page<ArticleCommentModel> getByCustomParam(@Param("articleId") Integer articleId, @Param("search") String search, Pageable pageable);

}
