package com.sirenanalytics.worldbank_feedback.model.simple;

public class LookupItemPhoto
{
    private String key;
    private String nameEnUs;
    private String nameArLb;
    private String descriptionEnUs;
    private String descriptionArLb;
    private boolean photoRequired;

    public LookupItemPhoto(String key, String nameEnUs, String nameArLb, String descriptionEnUs,
                           String descriptionArLb, boolean photoRequired)
    {
        this.key = key;
        this.nameEnUs = nameEnUs;
        this.nameArLb = nameArLb;
        this.descriptionEnUs = descriptionEnUs;
        this.descriptionArLb = descriptionArLb;
        this.photoRequired = photoRequired;
    }

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

    public boolean isPhotoRequired()
    {
        return photoRequired;
    }

    public void setPhotoRequired(boolean photoRequired)
    {
        this.photoRequired = photoRequired;
    }
}
