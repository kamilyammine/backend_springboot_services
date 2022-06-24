package com.sirenanalytics.worldbank_common.exception;

public class ConflictException extends Exception
{
    private ErrorCode errorCode;

    public ConflictException(String message)
    {
        super(message);
    }

    public ConflictException(String message, ErrorCode errorCode)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public ConflictException(ErrorCode errorCode)
    {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode()
    {
        return errorCode;
    }
}
