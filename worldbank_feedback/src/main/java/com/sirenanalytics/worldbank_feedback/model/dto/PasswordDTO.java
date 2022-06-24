package com.sirenanalytics.worldbank_feedback.model.dto;

public class PasswordDTO
{
    private String rawPassword;
    private String encryptedPassword;

    public String getRawPassword()
    {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword)
    {
        this.rawPassword = rawPassword;
    }

    public String getEncryptedPassword()
    {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword)
    {
        this.encryptedPassword = encryptedPassword;
    }
}
