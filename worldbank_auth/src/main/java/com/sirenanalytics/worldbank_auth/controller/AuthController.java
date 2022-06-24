package com.sirenanalytics.worldbank_auth.controller;

import com.sirenanalytics.worldbank_auth.model.simple.AuthRequest;
import com.sirenanalytics.worldbank_auth.model.simple.AuthResponse;
import com.sirenanalytics.worldbank_auth.model.simple.RefreshRequest;
import com.sirenanalytics.worldbank_auth.model.simple.ResetRequest;
import com.sirenanalytics.worldbank_auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController
{
    @Resource
    AuthService authService;

    @Operation(summary = "POST username and password to get an auth token")
    @PostMapping(path = "")
    ResponseEntity<Map<String, String>> authenticate(@Valid @RequestBody AuthRequest authRequest)
    {
        AuthResponse authResponse = authService.validateUser(authRequest);
        return new ResponseEntity<>(authResponse.getResponseMap(), authResponse.getHttpStatus());
    }

    @Operation(summary = "POST username and refresh_token to get new auth_token")
    @PostMapping(path = "/refresh")
    ResponseEntity<Map<String, String>> refresh(@Valid @RequestBody RefreshRequest refreshRequest)
    {
        AuthResponse authResponse = authService.refreshUserToken(refreshRequest);
        return new ResponseEntity<>(authResponse.getResponseMap(), authResponse.getHttpStatus());
    }

    @Operation(summary = "POST username and reset key to set change_pw_required to true and log user in")
    @PostMapping(path = "/reset")
    ResponseEntity<Map<String, String>> reset(@Valid @RequestBody ResetRequest resetRequest)
    {
        AuthResponse authResponse = authService.resetPassword(resetRequest);
        return new ResponseEntity<>(authResponse.getResponseMap(), authResponse.getHttpStatus());
    }
}
