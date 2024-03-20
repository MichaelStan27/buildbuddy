package com.buildbuddy.domain.systembuilder.controller;

import com.buildbuddy.domain.systembuilder.dto.param.GraphicsCardRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.GraphicsCardRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.graphicsCard.GraphicsCardResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.graphicsCard.GraphicsCardResponseSchema;
import com.buildbuddy.domain.systembuilder.service.GraphicsCardService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/systemBuilder/graphicsCard")
public class GraphicsCardController {

    @Autowired
    private GraphicsCardService graphicsCardService;

    @GetMapping("/get")
    public ResponseEntity<Object> getGraphicsCard(GraphicsCardRequestParam param, HttpServletRequest request){

        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", param);

        DataResponse<GraphicsCardResponseSchema> response = graphicsCardService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveGraphicsCard(@RequestBody GraphicsCardRequestDto body,
                                           HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<GraphicsCardResponseDto> response = graphicsCardService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteGraphicsCard(@RequestParam("graphicsCardId") Integer graphicsCardId,
                                             HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = graphicsCardService.delete(graphicsCardId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}

