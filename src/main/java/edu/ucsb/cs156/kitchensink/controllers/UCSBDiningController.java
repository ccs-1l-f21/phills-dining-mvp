package edu.ucsb.cs156.spring.backenddemo.controllers;

import org.springframework.web.bind.annotation.RestController;

import edu.ucsb.cs156.spring.backenddemo.services.EarthquakeQueryService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description="Dining Commons Info from UCSB")
@Slf4j
@RestController
@RequestMapping("/api/dining")
public class UCSBDiningController {
    
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    UCSBDiningService ucsbDiningService;

    @ApiOperation(value = "Get list of dining commons serving meals on given date.", 
    notes = "JSON return format documented here: https://developer.ucsb.edu/apis/dining/dining-menu#/")
    @GetMapping("/commons")
    public ResponseEntity<String> getDiningCommons(
        @ApiParam("date in ISO format, e.g. 2021-10-04") @RequestParam String date
    ) throws JsonProcessingException {
        String result = ucsbDiningService.getDiningCommonsJSON(date);
        return ResponseEntity.ok().body(result);
    }

}