package com.sirenanalytics.worldbank_feedback.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "feedback")
public class Feedback extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "category_item_key", nullable = false)
    private String categoryItemKey;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "gender", nullable = true, length = 1)
    private String gender;

    @Column(name = "age_range", nullable = true)
    private String ageRange;

    @Column(name = "user_type", nullable = true)
    private String userType;

    @Column(name = "user_type_other", nullable = true)
    private String userTypeOther;

    @Column(name = "first_name", nullable = true)
    private String firstName;

    @Column(name = "last_name", nullable = true)
    private String lastName;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "email", nullable = true)
    private String email;

    @Transient
    private List<Blob> blobList;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public Long getProjectId()
    {
        return projectId;
    }

    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }

    public String getCategoryItemKey()
    {
        return categoryItemKey;
    }

    public void setCategoryItemKey(String categoryItemKey)
    {
        this.categoryItemKey = categoryItemKey;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getAgeRange()
    {
        return ageRange;
    }

    public void setAgeRange(String ageRange)
    {
        this.ageRange = ageRange;
    }

    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }

    public String getUserTypeOther()
    {
        return userTypeOther;
    }

    public void setUserTypeOther(String userTypeOther)
    {
        this.userTypeOther = userTypeOther;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public List<Blob> getBlobList()
    {
        return blobList;
    }

    public void setBlobList(List<Blob> blobList)
    {
        this.blobList = blobList;
    }
}
