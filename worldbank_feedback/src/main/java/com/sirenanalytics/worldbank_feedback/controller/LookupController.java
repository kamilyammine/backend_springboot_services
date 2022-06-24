package com.sirenanalytics.worldbank_feedback.controller;

import com.sirenanalytics.worldbank_feedback.model.simple.LookupItem;
import com.sirenanalytics.worldbank_feedback.model.simple.LookupItemPhoto;
import com.sirenanalytics.worldbank_feedback.service.LookupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@ApiOperation(value = "Operations for lookups")
@RestController
@RequestMapping("/lookup")
public class LookupController
{
    @Resource
    LookupService lookupService;

    @ApiOperation(value = "GET all the lookup keys available in the system")
    @GetMapping("/types")
    ResponseEntity<List<LookupItem>> getKeys()
    {
        return new ResponseEntity<>(lookupService.getCategoryTypes(), HttpStatus.OK);
    }

    @ApiOperation(value = "GET a set of lookups for a given key set, or all if no parameter")
    @GetMapping({"/items", "/items/{keyList}"})
    ResponseEntity<Map<String, List<LookupItemPhoto>>> getLookupValues(@PathVariable(required = false) List<String> keyList)
    {
        return new ResponseEntity<>(lookupService.getCategoryItems(keyList), HttpStatus.OK);
    }

    @ApiOperation(value = "GET a set of lookups for a given key set, or all if no parameter")
    @GetMapping({"/kadaa"})
    ResponseEntity<List<LookupItem>> getKadaaList()
    {
        return new ResponseEntity<>(lookupService.getKadaaList(), HttpStatus.OK);
    }
}
