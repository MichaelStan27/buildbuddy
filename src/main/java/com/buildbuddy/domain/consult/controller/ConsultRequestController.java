package com.buildbuddy.domain.consult.controller;

import com.buildbuddy.domain.consult.dto.param.ConsultantRequestReqParam;
import com.buildbuddy.domain.consult.dto.request.ConsultantRequestDto;
import com.buildbuddy.domain.consult.service.ConsultRequestService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/consult/request")
public class ConsultRequestController {

    @Autowired
    private ConsultRequestService service;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ConsultantRequestDto body, HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = service.create(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/approval")
    public ResponseEntity<Object> approval(@RequestBody ConsultantRequestDto body, HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = service.approval(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/get")
    public ResponseEntity<Object> get(ConsultantRequestReqParam param, HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = service.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
