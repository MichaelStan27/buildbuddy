package com.buildbuddy.domain.systembuilder.controller;

import com.buildbuddy.domain.systembuilder.dto.param.RamRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.RamRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.ram.RamResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.ram.RamResponseSchema;
import com.buildbuddy.domain.systembuilder.service.RamService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/systemBuilder/ram")
public class RamController {

    @Autowired
    private RamService ramService;

    @GetMapping("/get")
    public ResponseEntity<Object> getRam(RamRequestParam param, HttpServletRequest request){

        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", param);

        DataResponse<RamResponseSchema> response = ramService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveRam(@RequestBody RamRequestDto body,
                                              HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<RamResponseDto> response = ramService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteRam(@RequestParam("ramId") Integer ramId,
                                                HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = ramService.delete(ramId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}

