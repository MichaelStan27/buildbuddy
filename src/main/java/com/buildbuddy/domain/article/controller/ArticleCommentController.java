package com.buildbuddy.domain.article.controller;

import com.buildbuddy.domain.article.dto.param.ArticleCommentRequestParam;
import com.buildbuddy.domain.article.dto.request.ArticleCommentRequestDto;
import com.buildbuddy.domain.article.service.ArticleCommentService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/article/comment")
public class ArticleCommentController {

    @Autowired
    private ArticleCommentService commentService;

    @GetMapping("/get")
    public ResponseEntity<Object> getComment(ArticleCommentRequestParam requestParam,
                                             HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = commentService.get(requestParam);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveComment(@RequestBody ArticleCommentRequestDto body,
                                              HttpServletRequest request) {
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = commentService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteComment(@RequestParam("articleId") Integer articleId,
                                                @RequestParam("commentId") Integer commentId,
                                                HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = commentService.delete(articleId, commentId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
