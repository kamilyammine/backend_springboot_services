package com.sirenanalytics.worldbank_feedback.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kadaa_key", nullable = false)
    private String kadaaKey;

    @Column(name = "name_en_us", nullable = false)
    private String nameEnUs;

    @Column(name = "name_ar_lb", nullable = false)
    private String nameArLb;

    @Column(name = "description_en_us", nullable = false)
    private String descriptionEnUs;

    @Column(name = "description_ar_lb", nullable = false)
    private String descriptionArLb;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getKadaaKey()
    {
        return kadaaKey;
    }

    public void setKadaaKey(String kadaaKey)
    {
        this.kadaaKey = kadaaKey;
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
}
