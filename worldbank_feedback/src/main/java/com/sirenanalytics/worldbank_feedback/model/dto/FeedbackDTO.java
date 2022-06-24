package com.sirenanalytics.worldbank_feedback.model.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class FeedbackDTO
{
    private Long id;
    private Double latitude;
    private Double longitude;
    private Long projectId;
    @NotEmpty(message = "A categoryItemKey is required")
    private String categoryItemKey;
    @NotEmpty(message = "A comment is required")
    private String comment;
    private String gender;
    private String ageRange;

    private String userType;
    private String userTypeOther;

    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String email;

    private List<MultipartFile> fileList;
    //this is a comma separate string in the format filename,GPSLatitude,GPSLatitudeRef,GPSLongitude,GPSLongitudeRef
    private List<String> imageLatLongData;
    private List<BlobDTO> blobDTOList;

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

    public List<MultipartFile> getFileList()
    {
        return fileList;
    }

    public void setFileList(List<MultipartFile> fileList)
    {
        this.fileList = fileList;
    }

    public List<String> getImageLatLongData()
    {
        return imageLatLongData;
    }

    public void setImageLatLongData(List<String> imageLatLongData)
    {
        this.imageLatLongData = imageLatLongData;
    }

    public List<BlobDTO> getBlobDTOList()
    {
        return blobDTOList;
    }

    public void setBlobDTOList(List<BlobDTO> blobDTOList)
    {
        this.blobDTOList = blobDTOList;
    }
}
