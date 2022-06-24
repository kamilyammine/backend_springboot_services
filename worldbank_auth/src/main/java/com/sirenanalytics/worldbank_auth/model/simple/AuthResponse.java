package com.sirenanalytics.worldbank_auth.model.simple;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class AuthResponse
{
    private Map<String, String> responseMap = new HashMap<>();
    private HttpStatus httpStatus;

    public Map<String, String> getResponseMap()
    {
        return responseMap;
    }

    public void setResponseMap(Map<String, String> responseMap)
    {
        this.responseMap = responseMap;
    }

    public HttpStatus getHttpStatus()
    {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus)
    {
        this.httpStatus = httpStatus;
    }
}
