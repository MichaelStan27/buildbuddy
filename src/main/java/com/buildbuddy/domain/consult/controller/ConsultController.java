package com.buildbuddy.domain.consult.controller;

import com.buildbuddy.domain.consult.dto.request.ConsultantReqDto;
import com.buildbuddy.domain.consult.dto.response.ConsultantDetailDto;
import com.buildbuddy.domain.consult.service.ConsultService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/consult")
@Validated
public class ConsultController {

    @Autowired
    private ConsultService consultService;

    @PostMapping("/save-detail")
    public ResponseEntity<Object> save(@Valid @RequestBody ConsultantReqDto body, HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("body: {}", body);

        DataResponse<ConsultantDetailDto> response = consultService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
