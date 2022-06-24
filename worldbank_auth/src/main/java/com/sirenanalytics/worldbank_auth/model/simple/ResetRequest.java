package com.sirenanalytics.worldbank_auth.model.simple;

import javax.validation.constraints.NotEmpty;

public class ResetRequest
{
    @NotEmpty(message = "The username is required")
    private String username;

    @NotEmpty(message = "The key is required")
    private String resetKey;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getResetKey()
    {
        return resetKey;
    }

    public void setResetKey(String resetKey)
    {
        this.resetKey = resetKey;
    }
}
