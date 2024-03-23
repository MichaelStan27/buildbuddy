package com.buildbuddy.domain.systembuilder.controller;

import com.buildbuddy.domain.systembuilder.dto.param.PowerSupplyRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.PowerSupplyRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.powersupply.PowerSupplyResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.powersupply.PowerSupplyResponseSchema;
import com.buildbuddy.domain.systembuilder.service.PowerSupplyService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/systemBuilder/powerSupply")
public class PowerSupplyController {

    @Autowired
    private PowerSupplyService powerSupplyService;

    @GetMapping("/get")
    public ResponseEntity<Object> getPowerSupply(PowerSupplyRequestParam param, HttpServletRequest request){

        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", param);

        DataResponse<PowerSupplyResponseSchema> response = powerSupplyService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @PostMapping("/save")
    public ResponseEntity<Object> savePowerSupply(@RequestBody PowerSupplyRequestDto body,
                                           HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<PowerSupplyResponseDto> response = powerSupplyService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deletePowerSupply(@RequestParam("powerSupplyId") Integer powerSupplyId,
                                             HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = powerSupplyService.delete(powerSupplyId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}

