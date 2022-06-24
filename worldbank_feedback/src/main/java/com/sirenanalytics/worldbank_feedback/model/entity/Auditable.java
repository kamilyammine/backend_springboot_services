package com.sirenanalytics.worldbank_feedback.model.entity;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
public class Auditable
{
    @Column(name = "update_user_id")
    private Long updateUserId;

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "create_timestamp")
    private LocalDateTime createTimestamp;

    public Long getUpdateUserId()
    {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId)
    {
        this.updateUserId = updateUserId;
    }

    public LocalDateTime getUpdateTimestamp()
    {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp)
    {
        this.updateTimestamp = updateTimestamp;
    }

    public Long getCreateUserId()
    {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId)
    {
        this.createUserId = createUserId;
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
        long modifyingUserId = 0L;
        LocalDateTime now = LocalDateTime.now();

        updateUserId = modifyingUserId;
        updateTimestamp = now;

        if (includeCreatedColumns)
        {
            createUserId = modifyingUserId;
            createTimestamp = now;
        }
    }
}
