package com.sirenanalytics.worldbank_auth.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class FailedLogin
{
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "consecutive_fail_count", nullable = false, unique = true)
    private Integer consecutiveFailCount;

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    public FailedLogin()
    {
    }

    public FailedLogin(Long userId, Integer consecutiveFailCount)
    {
        this.userId = userId;
        this.consecutiveFailCount = consecutiveFailCount;
        updateTimestamp = LocalDateTime.now();
    }

    public void incrementConsecutiveFailedLoginCount()
    {
        consecutiveFailCount += 1;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Integer getConsecutiveFailCount()
    {
        return consecutiveFailCount;
    }

    public void setConsecutiveFailCount(Integer consecutiveFailCount)
    {
        this.consecutiveFailCount = consecutiveFailCount;
    }

    public LocalDateTime getUpdateTimestamp()
    {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp)
    {
        this.updateTimestamp = updateTimestamp;
    }
}
