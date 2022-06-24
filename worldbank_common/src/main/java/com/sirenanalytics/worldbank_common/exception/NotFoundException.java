package com.sirenanalytics.worldbank_common.exception;

public class NotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 4_954_644_341_942_908_988L;

    public NotFoundException(Long id)
    {
        super("Item not found : " + id);
    }
}
