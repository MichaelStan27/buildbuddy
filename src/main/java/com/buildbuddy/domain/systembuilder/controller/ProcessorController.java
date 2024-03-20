package com.buildbuddy.domain.systembuilder.controller;

import com.buildbuddy.domain.systembuilder.dto.param.ProcessorRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.ProcessorRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.processor.ProcessorResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.processor.ProcessorResponseSchema;
import com.buildbuddy.domain.systembuilder.service.ProcessorService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/systemBuilder/processor")
public class ProcessorController {

    @Autowired
    private ProcessorService processorService;

    @GetMapping("/get")
    public ResponseEntity<Object> getProcessor(ProcessorRequestParam param, HttpServletRequest request){

        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", param);

        DataResponse<ProcessorResponseSchema> response = processorService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveProcessor(@RequestBody ProcessorRequestDto body,
                                              HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<ProcessorResponseDto> response = processorService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteProcessor(@RequestParam("processorId") Integer processorId,
                                                HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = processorService.delete(processorId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}

