package com.sirenanalytics.worldbank_auth.service;

import com.sirenanalytics.worldbank_auth.model.simple.AuthRequest;
import com.sirenanalytics.worldbank_auth.model.simple.AuthResponse;
import com.sirenanalytics.worldbank_auth.model.simple.RefreshRequest;
import com.sirenanalytics.worldbank_auth.model.simple.ResetRequest;

public interface AuthService
{
    AuthResponse validateUser(AuthRequest authRequest);

    AuthResponse refreshUserToken(RefreshRequest refreshRequest);

    void logUserOut();

    AuthResponse resetPassword(ResetRequest resetRequest);
}
