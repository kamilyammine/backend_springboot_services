package com.sirenanalytics.worldbank_common.exception;

public class InvalidKeyException extends RuntimeException
{
    String message;

    public InvalidKeyException(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
