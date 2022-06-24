package com.sirenanalytics.worldbank_feedback.model.dto;

public class ProjectDTO
{
    private Long id;
    private String kadaaKey;
    private String nameEnUs;
    private String nameArLb;
    private String descriptionEnUs;
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
