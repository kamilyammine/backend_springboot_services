package com.sirenanalytics.worldbank_common.exception;

public enum ErrorCode
{
    NO_ERROR("no_error", "No Error"),
    BAD_REQUEST("bad_request", "Bad Request"),
    MISSING_DATA("missing_data", "Missing Data"),
    INVALID_DATA("invalid_data", "Invalid Data"),
    CONFLICT("conflict", "Data Conflict"),
    INVALID_USER("invalid_user", "Invalid User"),
    INVALID_PASSWORD("invalid_password", "Invalid Password"),
    PASSWORDS_DO_NOT_MATCH("passwords_do_not_match", "Passwords don't match"),
    PASSWORD_TOO_WEAK("password_too_weak", "Must be min 8, contain one upper, lower, number and special char.  No whitespace."),
    FORBIDDEN("forbidden", "Insufficient permission to complete this request"),
    PHOTO_NOT_GEOTAGGED("photo_not_geotagged", "Your photo needs to be taken on the site about which you are filing a complaint. The photo you are uploading does not have geotagging"),
    PHOTO_NOT_IN_PROXIMITY("photo_not_in_proximity", "Your photo needs to be taken on the site about which you are filing a complaint. The photo you are uploading was not taken on any CDR construction site"),
    MISSING_COORDINATE_DATA("missing_coordinate_data", "There appears to be 1 or no coordinates for this project"),
    INTERNAL_SERVER_ERROR("internal server error", "Internal Server Error");

    private final String code;
    private final String message;
    private final String info;

    ErrorCode(String code, String message)
    {
        this.code = code;
        this.message = message;
        this.info = "";
    }

    ErrorCode(String code, String message, String info)
    {
        this.code = code;
        this.message = message;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return message;
    }

    public String getInfo()
    {
        return info;
    }
}
