package com.buildbuddy.domain.article.controller;

import com.buildbuddy.domain.article.dto.param.ArticleRequestParam;
import com.buildbuddy.domain.article.dto.request.ArticleLikeDto;
import com.buildbuddy.domain.article.dto.request.ArticleRequestDto;
import com.buildbuddy.domain.article.dto.response.article.ArticleResponseDto;
import com.buildbuddy.domain.article.dto.response.article.ArticleResponseSchema;
import com.buildbuddy.domain.article.service.ArticleService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/get")
    public ResponseEntity<Object> getArticle(ArticleRequestParam param,
            HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", param);

        DataResponse<ArticleResponseSchema> response = articleService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveArticle(@RequestBody ArticleRequestDto body, HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<ArticleResponseDto> response = articleService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteArticle(@RequestParam("articleId") Integer articleId,
            HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = articleService.delete(articleId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("/like")
    public ResponseEntity<Object> like(@RequestBody ArticleLikeDto body, HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = articleService.like(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }


}
