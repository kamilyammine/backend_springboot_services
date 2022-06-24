package com.sirenanalytics.worldbank_common.model.simple;

import javax.validation.constraints.NotEmpty;

public class ChangePasswordRequest
{
    @NotEmpty(message = "The current password is required")
    private String oldPassword;

    @NotEmpty(message = "The new password is required")
    private String newPassword;

    @NotEmpty(message = "The confirmed new password is required")
    private String confirmNewPassword;

    public String getOldPassword()
    {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword()
    {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword)
    {
        this.confirmNewPassword = confirmNewPassword;
    }
}
