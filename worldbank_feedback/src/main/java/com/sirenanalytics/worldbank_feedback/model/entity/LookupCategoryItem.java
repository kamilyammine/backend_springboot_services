package com.sirenanalytics.worldbank_feedback.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "category_item")
public class LookupCategoryItem extends BaseLookup
{
    @Column(name = "category_type_key", nullable = false)
    private String categoryTypeKey;

    @Column(name = "photo_required", nullable = false)
    private Boolean photoRequired;

    public String getCategoryTypeKey()
    {
        return categoryTypeKey;
    }

    public void setCategoryTypeKey(String categoryTypeKey)
    {
        this.categoryTypeKey = categoryTypeKey;
    }

    public Boolean getPhotoRequired()
    {
        return photoRequired;
    }

    public void setPhotoRequired(Boolean photoRequired)
    {
        this.photoRequired = photoRequired;
    }
}
