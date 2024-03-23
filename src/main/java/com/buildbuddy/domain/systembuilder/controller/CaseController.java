package com.buildbuddy.domain.systembuilder.controller;

import com.buildbuddy.domain.systembuilder.dto.param.CaseRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.CaseRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.computerCase.CaseResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.computerCase.CaseResponseSchema;
import com.buildbuddy.domain.systembuilder.service.CaseService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/systemBuilder/case")
public class CaseController {

    @Autowired
    private CaseService caseService;

    @GetMapping("/get")
    public ResponseEntity<Object> getCase(CaseRequestParam param, HttpServletRequest request){

        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", param);

        DataResponse<CaseResponseSchema> response = caseService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveCase(@RequestBody CaseRequestDto body,
                                             HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<CaseResponseDto> response = caseService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteCase(@RequestParam("caseId") Integer caseId,
                                               HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = caseService.delete(caseId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
