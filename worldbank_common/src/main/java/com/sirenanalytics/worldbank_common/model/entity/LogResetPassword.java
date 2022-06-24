package com.sirenanalytics.worldbank_common.model.entity;

import com.sirenanalytics.worldbank_common.util.ContextUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Entity
public class LogResetPassword
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "key")
    private String key;

    //status will be P for pending or C for complete
    @Column(name = "status")
    private String status;

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @Column(name = "create_timestamp")
    private LocalDateTime createTimestamp;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public LocalDateTime getUpdateTimestamp()
    {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp)
    {
        this.updateTimestamp = updateTimestamp;
    }

    public LocalDateTime getCreateTimestamp()
    {
        return createTimestamp;
    }

    public void setCreateTimestamp(LocalDateTime createTimestamp)
    {
        this.createTimestamp = createTimestamp;
    }

    @PrePersist
    public void onPrePersist()
    {
        setAuditColumns(true);
    }

    @PreUpdate
    public void onPreUpdate()
    {
        setAuditColumns(false);
    }

    private void setAuditColumns(boolean includeCreatedColumns)
    {
        Long modifyingUserId = ContextUtil.getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();

        updateTimestamp = now;

        if (includeCreatedColumns)
        {
            createTimestamp = now;
        }
    }
}
