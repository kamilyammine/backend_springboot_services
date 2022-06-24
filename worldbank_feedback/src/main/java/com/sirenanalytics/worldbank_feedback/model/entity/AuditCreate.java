package com.sirenanalytics.worldbank_feedback.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@MappedSuperclass
public class AuditCreate
{
    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "create_timestamp")
    private LocalDateTime createTimestamp;

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
    private void setAuditColumns()
    {
        createUserId = 0L;
        createTimestamp = LocalDateTime.now();
    }
}
