package com.buildbuddy.domain.article.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.article.dto.param.ArticleRequestParam;
import com.buildbuddy.domain.article.dto.request.ArticleLikeDto;
import com.buildbuddy.domain.article.dto.request.ArticleRequestDto;
import com.buildbuddy.domain.article.dto.response.article.ArticleResponseDto;
import com.buildbuddy.domain.article.dto.response.article.ArticleResponseSchema;
import com.buildbuddy.domain.article.entity.ArticleEntity;
import com.buildbuddy.domain.article.entity.ArticleLikeEntity;
import com.buildbuddy.domain.article.entity.ArticleModel;
import com.buildbuddy.domain.article.repository.ArticleLikeRepository;
import com.buildbuddy.domain.article.repository.ArticleRepository;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.exception.BadRequestException;
import com.buildbuddy.jsonresponse.DataResponse;
import com.buildbuddy.util.PaginationCreator;
import com.buildbuddy.util.spesification.SpecificationCreator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    @Autowired
    private SpecificationCreator<ArticleEntity> specificationCreator;
    
    @Autowired
    private PaginationCreator paginationCreator;

    @Transactional
    public DataResponse<ArticleResponseSchema> get(ArticleRequestParam requestParam) {

        UserEntity user = audit.getCurrentAuditor().orElseThrow(() -> new RuntimeException("User Not Found"));

        String search = requestParam.getSearch() != null ? "%" + requestParam.getSearch().toUpperCase() + "%" : null;
        boolean isPaginated = requestParam.isPagination();
        Integer pageNo = requestParam.getPageNo();
        Integer pageSize = requestParam.getPageSize();
        String sortBy = requestParam.getSortBy();
        String sortDirection = requestParam.getSortDirection();

        Sort sort = paginationCreator.createAliasesSort(sortDirection, sortBy);

        Pageable pageable = paginationCreator.createPageable(isPaginated, sort, pageNo, pageSize);

        Page<ArticleModel> dataPage = articleRepository.getByCustomParam(user.getId(), requestParam.getArticleId(), search, pageable);
        List<ArticleModel> articleList = dataPage.getContent();

        List<ArticleResponseDto> articleResponseDtos = articleList.stream()
                .map(ArticleResponseDto::convertToDto)
                .toList();

        ArticleResponseSchema data = ArticleResponseSchema.builder()
                .articleList(articleResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<ArticleResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting article")
                .data(data)
                .build();
    }
    
    @Transactional
    public DataResponse<ArticleResponseDto> save(ArticleRequestDto articleDto){
        log.info("article: {}", articleDto);

        Integer id = articleDto.getId();
        ArticleEntity article = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() -> new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}",currentUser.getUsername());

            String title = articleDto.getTitle();
            String post = articleDto.getPost();
            String status = articleDto.getStatus();
            String image = articleDto.getImage();

            article = articleRepository.findByIdAndUserId(id, currentUser.getId()).orElseThrow(() -> new BadRequestException("Article Not Found"));
            article.setTitle(title != null ? title : article.getTitle());
            article.setPost(post != null ? post : article.getPost());
            article.setStatus((status != null? status : article.getStatus()));

            article.setImage(image != null ? Base64.getDecoder().decode(articleDto.getImage()) : article.getImage());
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

    @Transactional
    public DataResponse<String> delete(Integer articleId){
        log.info("deleting article with id: {}",articleId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() -> new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}",currentUser.getUsername());

        ArticleEntity article = articleRepository.findByIdAndUserId(articleId, currentUser.getId()).orElseThrow(() -> new BadRequestException("Article not Found"));

        log.info("deleting...");
        articleRepository.delete(article);
        log.info("deleted");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting Article")
                .data("Article with id: "+ articleId + " deleted.")
                .build();

    }

    public DataResponse<Object> like(ArticleLikeDto dto){
        UserEntity user = audit.getCurrentAuditor().orElseThrow(() -> new RuntimeException("User Not Found"));

        String message = "";

        articleRepository.findById(dto.getArticleId()).orElseThrow(() -> new RuntimeException("article not found"));
        Optional<ArticleLikeEntity> threadLikeOpt = articleLikeRepository.findByArticleIdAndUserId(dto.getArticleId(), user.getId());

        if(threadLikeOpt.isPresent()){
            log.info("Unliking article");

            articleLikeRepository.delete(threadLikeOpt.get());

            message = "Article Unliked.";
        }
        else{
            log.info("liking article");
            ArticleLikeEntity entity = ArticleLikeEntity.builder()
                    .articleId(dto.getArticleId())
                    .userId(user.getId())
                    .build();
            articleLikeRepository.saveAndFlush(entity);
            message = "Article liked.";
        }

        log.info("success....");

        return DataResponse.<Object>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message(message)
                .build();
    }

}
