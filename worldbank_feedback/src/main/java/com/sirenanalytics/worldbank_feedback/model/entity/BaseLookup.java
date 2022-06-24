package com.sirenanalytics.worldbank_feedback.model.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseLookup
{
    @Id
    @Column(name = "key", nullable = false, length = 10)
    private String key;

    @Column(name = "name_en_us", nullable = false)
    private String nameEnUs;

    @Column(name = "name_ar_lb", nullable = false)
    private String nameArLb;

    @Column(name = "description_en_us", nullable = false)
    private String descriptionEnUs;

    @Column(name = "description_ar_lb", nullable = false)
    private String descriptionArLb;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "create_user_id", nullable = true)
    private Integer createUserId;

    @Column(name = "create_timestamp", nullable = true)
    private LocalDateTime createTimestamp;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getNameEnUs()
    {
        return nameEnUs;
    }

    public void setNameEnUs(String nameEnUs)
    {
        this.nameEnUs = nameEnUs;
    }

    public String getNameArLb()
    {
        return nameArLb;
    }

    public void setNameArLb(String nameArLb)
    {
        this.nameArLb = nameArLb;
    }

    public String getDescriptionEnUs()
    {
        return descriptionEnUs;
    }

    public void setDescriptionEnUs(String descriptionEnUs)
    {
        this.descriptionEnUs = descriptionEnUs;
    }

    public String getDescriptionArLb()
    {
        return descriptionArLb;
    }

    public void setDescriptionArLb(String descriptionArLb)
    {
        this.descriptionArLb = descriptionArLb;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Integer getCreateUserId()
    {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId)
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
}
