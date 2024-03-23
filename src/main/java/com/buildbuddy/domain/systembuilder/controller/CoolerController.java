package com.buildbuddy.domain.systembuilder.controller;

import com.buildbuddy.domain.systembuilder.dto.param.CoolerRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.CoolerRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.cooler.CoolerResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.cooler.CoolerResponseSchema;
import com.buildbuddy.domain.systembuilder.service.CoolerService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/systemBuilder/cooler")
public class CoolerController {

    @Autowired
    private CoolerService coolerService;

    @GetMapping("/get")
    public ResponseEntity<Object> getCooler(CoolerRequestParam param, HttpServletRequest request){

        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", param);

        DataResponse<CoolerResponseSchema> response = coolerService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveCooler(@RequestBody CoolerRequestDto body,
                                           HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<CoolerResponseDto> response = coolerService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteCooler(@RequestParam("coolerId") Integer coolerId,
                                             HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = coolerService.delete(coolerId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}

