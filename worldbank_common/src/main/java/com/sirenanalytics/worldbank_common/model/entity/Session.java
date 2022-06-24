package com.sirenanalytics.worldbank_common.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Session
{
    @Column(name = "create_datetime", nullable = false)
    protected LocalDateTime creationTime;
    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "consecutive_refreshes", nullable = false)
    private int consecutiveRefreshes;
    @Column(name = "access_token", nullable = false)
    private String accessToken;
    @Column(name = "refresh_token", length = 300, nullable = false)
    private String refreshToken;

    public void incrementConsecutiveRefreshes()
    {
        consecutiveRefreshes += 1;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public int getConsecutiveRefreshes()
    {
        return consecutiveRefreshes;
    }

    public void setConsecutiveRefreshes(int consecutiveRefreshes)
    {
        this.consecutiveRefreshes = consecutiveRefreshes;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getCreationTime()
    {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime)
    {
        this.creationTime = creationTime;
    }
}