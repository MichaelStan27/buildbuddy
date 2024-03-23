package com.buildbuddy.domain.systembuilder.controller;

import com.buildbuddy.domain.systembuilder.dto.param.MonitorRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.MonitorRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.monitor.MonitorResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.monitor.MonitorResponseSchema;
import com.buildbuddy.domain.systembuilder.service.MonitorService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/systemBuilder/monitor")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @GetMapping("/get")
    public ResponseEntity<Object> getMonitor(MonitorRequestParam param, HttpServletRequest request){

        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", param);

        DataResponse<MonitorResponseSchema> response = monitorService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveMonitor(@RequestBody MonitorRequestDto body,
                                           HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<MonitorResponseDto> response = monitorService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteMonitor(@RequestParam("monitorId") Integer monitorId,
                                             HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = monitorService.delete(monitorId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}

