package com.sirenanalytics.worldbank_auth.controller;

import com.sirenanalytics.worldbank_auth.service.ResetService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/reset")
public class ResetController
{
    @Resource
    ResetService resetService;

    @Operation(summary = "POST an email address that needs to be reset")
    @PostMapping("/password")
    ResponseEntity<Object> resetPassword(@RequestBody String email)
    {
        if (EmailValidator.getInstance().isValid(email))
        {
            resetService.resetPassword(email);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Input must be a valid email address", HttpStatus.BAD_REQUEST);
    }
}
