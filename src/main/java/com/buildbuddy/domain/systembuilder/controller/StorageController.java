package com.buildbuddy.domain.systembuilder.controller;

import com.buildbuddy.domain.systembuilder.dto.param.StorageRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.StorageRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.storage.StorageResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.storage.StorageResponseSchema;
import com.buildbuddy.domain.systembuilder.service.StorageService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/systemBuilder/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/get")
    public ResponseEntity<Object> getStorage(StorageRequestParam param, HttpServletRequest request){

        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", param);

        DataResponse<StorageResponseSchema> response = storageService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());

        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveStorage(@RequestBody StorageRequestDto body,
                                              HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<StorageResponseDto> response = storageService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteStorage(@RequestParam("storageId") Integer storageId,
                                                HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = storageService.delete(storageId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}

