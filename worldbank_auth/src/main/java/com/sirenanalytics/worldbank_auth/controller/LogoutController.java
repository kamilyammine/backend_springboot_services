package com.sirenanalytics.worldbank_auth.controller;

import com.sirenanalytics.worldbank_auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class LogoutController
{
    @Resource
    AuthService authService;

    @Operation(summary = "POST to log the user out")
    @PostMapping(path = "")
    ResponseEntity<String> logout()
    {
        authService.logUserOut();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
