package com.sirenanalytics.worldbank_common.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "x_shared_secret")
public class XSharedSecret
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key", nullable = false, length = 36)
    private String key;

    @Column(name = "create_datetime", nullable = false)
    private LocalDateTime createDateTime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public LocalDateTime getCreateDateTime()
    {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime)
    {
        this.createDateTime = createDateTime;
    }
}
