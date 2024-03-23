package com.buildbuddy.domain.systembuilder.controller;

import com.buildbuddy.domain.systembuilder.dto.param.ComputerSetupRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.ComputerSetupRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.computerSetup.ComputerSetupResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.computerSetup.ComputerSetupResponseSchema;
import com.buildbuddy.domain.systembuilder.service.SystemBuilderService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/systemBuilder/computerSetup")
public class SystemBuilderController {

    @Autowired
    private SystemBuilderService systemBuilderService;

    @GetMapping("/get")
    public ResponseEntity<Object> getComputerSetup(ComputerSetupRequestParam param, HttpServletRequest request){

        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", param);

        DataResponse<ComputerSetupResponseSchema> response = systemBuilderService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveComputerSetup(@RequestBody ComputerSetupRequestDto body,
                                              HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<ComputerSetupResponseDto> response = systemBuilderService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteComputerSetup(@RequestParam("computerSetupId") Integer computerSetupId,
                                                HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = systemBuilderService.delete(computerSetupId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}

