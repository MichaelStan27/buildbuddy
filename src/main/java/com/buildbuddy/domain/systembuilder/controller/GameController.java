package com.buildbuddy.domain.systembuilder.controller;

import com.buildbuddy.domain.systembuilder.dto.param.GameRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.GameRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.game.GameResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.game.GameResponseSchema;
import com.buildbuddy.domain.systembuilder.service.GameService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/get")
    public ResponseEntity<Object> getGame(GameRequestParam param,
                                            HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", param);

        DataResponse<GameResponseSchema> response = gameService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveGame(@RequestBody GameRequestDto body,
                                             HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<GameResponseDto> response = gameService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteGame(@RequestParam("gameId") Integer gameId,
                                               HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = gameService.delete(gameId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
