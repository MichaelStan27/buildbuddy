package com.buildbuddy.domain.article.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.article.dto.param.ArticleCommentRequestParam;
import com.buildbuddy.domain.article.dto.request.ArticleCommentRequestDto;
import com.buildbuddy.domain.article.dto.response.comment.ArticleCommentResponseDto;
import com.buildbuddy.domain.article.dto.response.comment.ArticleCommentSchema;
import com.buildbuddy.domain.article.entity.ArticleCommentEntity;
import com.buildbuddy.domain.article.entity.ArticleCommentModel;
import com.buildbuddy.domain.article.entity.ArticleEntity;
import com.buildbuddy.domain.article.repository.ArticleCommentRepository;
import com.buildbuddy.domain.article.repository.ArticleRepository;
import com.buildbuddy.domain.forum.dto.request.CommentRequestDto;
import com.buildbuddy.domain.forum.dto.response.comment.CommentResponseDto;
import com.buildbuddy.domain.forum.entity.CommentEntity;
import com.buildbuddy.domain.forum.entity.ThreadEntity;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.exception.BadRequestException;
import com.buildbuddy.jsonresponse.DataResponse;
import com.buildbuddy.util.PaginationCreator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ArticleCommentService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @Autowired
    private ArticleRepository articleRepository;


    @Autowired
    private PaginationCreator paginationCreator;

    public DataResponse<Object> get(ArticleCommentRequestParam requestParam){
        log.info("param: {}", requestParam);

        boolean isPaginated = requestParam.isPagination();
        Integer pageNo = requestParam.getPageNo();
        Integer pageSize = requestParam.getPageSize();
        String sortBy = requestParam.getSortBy();
        String sortDirection = requestParam.getSortDirection();
        String search = requestParam.getSearch() != null ? "%" + requestParam.getSearch() + "%" : null;

        Sort sort = paginationCreator.createAliasesSort(sortDirection, sortBy);

        Pageable pageable = paginationCreator.createPageable(isPaginated, sort, pageNo, pageSize);

        Page<ArticleCommentModel> dataPage = articleCommentRepository.getByCustomParam(requestParam.getArticleId(), search, pageable);
        List<ArticleCommentModel> commentList = dataPage.getContent();

        List<ArticleCommentResponseDto> dtoList = commentList.stream()
                .map(ArticleCommentResponseDto::convertToDto)
                .toList();

        ArticleCommentSchema data = ArticleCommentSchema.builder()
                .commentList(dtoList)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<Object>builder()
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .message("Success getting comment")
                .data(data)
                .build();
    }

    @Transactional
    public DataResponse<Object> save(ArticleCommentRequestDto dto){
        log.info("comment: {}", dto);

        ArticleEntity article = articleRepository.findById(dto.getArticleId()).orElseThrow(() -> new BadRequestException("article not found"));
        ArticleCommentEntity comment = null;

        if(dto.getCommentId() != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() -> new BadRequestException("Request not authenticated"));
            log.info("authenticated user: {}", currentUser.getUsername());
            comment = articleCommentRepository.findByIdAndUserIdAndArticleId(dto.getCommentId(), currentUser.getId(), article.getId()).orElseThrow(() -> new BadRequestException("Comment Not Found"));
            comment.setMessage(dto.getMessage());
        }
        else{
            comment = ArticleCommentRequestDto.convertToEntity(dto, article);
        }

        log.info("saving...");
        comment = articleCommentRepository.saveAndFlush(comment);
        log.info("saved.");

        ArticleCommentResponseDto data = ArticleCommentResponseDto.convertToDto(comment);

        return DataResponse.<Object>builder()
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .message("Success saving comment")
                .data(data)
                .build();
    }

    @Transactional
    public DataResponse<String> delete(Integer articleId, Integer commentId){
        log.info("Deleting comment by: comment id: {}, article id: {}", commentId, articleId);

        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() -> new BadRequestException("Request not authenticated"));

        ArticleCommentEntity comment = articleCommentRepository.findByIdAndUserIdAndArticleId(commentId, currentUser.getId(), articleId).orElseThrow(() -> new BadRequestException("Comment Not Found"));

        log.info("Deleting...");
        articleCommentRepository.delete(comment);
        log.info("Deleted.");

        return DataResponse.<String>builder()
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .message("Success Deleting Comment")
                .data("Comment with id: " + commentId + " at thread id: " + articleId + " deleted.")
                .build();
    }

}
