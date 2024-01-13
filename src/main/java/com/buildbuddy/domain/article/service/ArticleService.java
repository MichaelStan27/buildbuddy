package com.buildbuddy.domain.article.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.article.dto.request.ArticleRequestDto;
import com.buildbuddy.domain.article.dto.response.article.ArticleResponseDto;
import com.buildbuddy.domain.article.entity.ArticleEntity;
import com.buildbuddy.domain.article.repository.ArticleRepository;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.exception.BadRequestException;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    public DataResponse<ArticleResponseDto> save(ArticleRequestDto articleDto){
        log.info("article: {}", articleDto);

        Integer id = articleDto.getId();
        ArticleEntity article = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() -> new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}",currentUser.getUsername());

            article = articleRepository.findByIdAndUserId(id, currentUser.getId()).orElseThrow(() -> new BadRequestException("Article Not Found"));
            article.setPost(articleDto.getPost());
        }
        else {
            article = ArticleRequestDto.convertToEntity(articleDto);
        }

        log.info("saving...");
        article = articleRepository.saveAndFlush(article);
        log.info("saved...");

        ArticleResponseDto data = ArticleResponseDto.convertToDto(article);

        return DataResponse.<ArticleResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving Article")
                .data(data)
                .build();

    }

}
