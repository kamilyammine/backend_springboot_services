package com.sirenanalytics.worldbank_auth.model.simple;

import javax.validation.constraints.NotEmpty;

public class AuthRequest
{
    @NotEmpty(message = "The username is required")
    private String username;

    @NotEmpty(message = "The password is required")
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
