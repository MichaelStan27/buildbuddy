package com.buildbuddy.domain.systembuilder.controller;

import com.buildbuddy.domain.systembuilder.dto.param.MotherboardRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.MotherboardRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.motherboard.MotherboardResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.motherboard.MotherboardResponseSchema;
import com.buildbuddy.domain.systembuilder.service.MotherboardService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/systemBuilder/motherboard")
public class MotherboardController {

    @Autowired
    private MotherboardService motherboardService;

    @GetMapping("/get")
    public ResponseEntity<Object> getMotherboard(MotherboardRequestParam param, HttpServletRequest request){

        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", param);

        DataResponse<MotherboardResponseSchema> response = motherboardService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveMotherboard(@RequestBody MotherboardRequestDto body,
                                           HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<MotherboardResponseDto> response = motherboardService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteMotherboard(@RequestParam("motherboardId") Integer motherboardId,
                                             HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = motherboardService.delete(motherboardId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}

