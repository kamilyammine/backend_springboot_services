package com.sirenanalytics.worldbank_feedback.model.simple;

public class ContactsNeedingEmail
{
    private Long feedbackId;
    private Long contactId;
    private String comment;
    private String gender;
    private String ageRange;
    private String userType;
    private String userTypeOther;
    private String feedbackFirstName;
    private String feedbackLastName;
    private String feedbackAddress;
    private String feedbackPhone;
    private String feedbackEmail;
    private String roadLabelEnUs;
    private String roadLabelArLb;
    private String roadNameEnUs;
    private String roadNameArLb;
    private String kadaaNameEnUs;
    private String kadaaNameArLb;
    private String categoryTypeNameEnUs;
    private String categoryTypeNameArlb;
    private String categoryItemNameEnUs;
    private String categoryItemNameArlb;

    private String contactFirstName;
    private String contactLastName;
    private String contactEmail;

    public String getContactFullName()
    {
        return contactFirstName + " " + contactLastName;
    }

    public Long getFeedbackId()
    {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId)
    {
        this.feedbackId = feedbackId;
    }

    public Long getContactId()
    {
        return contactId;
    }

    public void setContactId(Long contactId)
    {
        this.contactId = contactId;
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

    public String getFeedbackFirstName()
    {
        return feedbackFirstName;
    }

    public void setFeedbackFirstName(String feedbackFirstName)
    {
        this.feedbackFirstName = feedbackFirstName;
    }

    public String getFeedbackLastName()
    {
        return feedbackLastName;
    }

    public void setFeedbackLastName(String feedbackLastName)
    {
        this.feedbackLastName = feedbackLastName;
    }

    public String getFeedbackAddress()
    {
        return feedbackAddress;
    }

    public void setFeedbackAddress(String feedbackAddress)
    {
        this.feedbackAddress = feedbackAddress;
    }

    public String getFeedbackPhone()
    {
        return feedbackPhone;
    }

    public void setFeedbackPhone(String feedbackPhone)
    {
        this.feedbackPhone = feedbackPhone;
    }

    public String getFeedbackEmail()
    {
        return feedbackEmail;
    }

    public void setFeedbackEmail(String feedbackEmail)
    {
        this.feedbackEmail = feedbackEmail;
    }

    public String getRoadLabelEnUs()
    {
        return roadLabelEnUs;
    }

    public void setRoadLabelEnUs(String roadLabelEnUs)
    {
        this.roadLabelEnUs = roadLabelEnUs;
    }

    public String getRoadLabelArLb()
    {
        return roadLabelArLb;
    }

    public void setRoadLabelArLb(String roadLabelArLb)
    {
        this.roadLabelArLb = roadLabelArLb;
    }

    public String getRoadNameEnUs()
    {
        return roadNameEnUs;
    }

    public void setRoadNameEnUs(String roadNameEnUs)
    {
        this.roadNameEnUs = roadNameEnUs;
    }

    public String getRoadNameArLb()
    {
        return roadNameArLb;
    }

    public void setRoadNameArLb(String roadNameArLb)
    {
        this.roadNameArLb = roadNameArLb;
    }

    public String getKadaaNameEnUs()
    {
        return kadaaNameEnUs;
    }

    public void setKadaaNameEnUs(String kadaaNameEnUs)
    {
        this.kadaaNameEnUs = kadaaNameEnUs;
    }

    public String getKadaaNameArLb()
    {
        return kadaaNameArLb;
    }

    public void setKadaaNameArLb(String kadaaNameArLb)
    {
        this.kadaaNameArLb = kadaaNameArLb;
    }

    public String getContactFirstName()
    {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName)
    {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName()
    {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName)
    {
        this.contactLastName = contactLastName;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }

    public String getCategoryTypeNameEnUs()
    {
        return categoryTypeNameEnUs;
    }

    public void setCategoryTypeNameEnUs(String categoryTypeNameEnUs)
    {
        this.categoryTypeNameEnUs = categoryTypeNameEnUs;
    }

    public String getCategoryTypeNameArlb()
    {
        return categoryTypeNameArlb;
    }

    public void setCategoryTypeNameArlb(String categoryTypeNameArlb)
    {
        this.categoryTypeNameArlb = categoryTypeNameArlb;
    }

    public String getCategoryItemNameEnUs()
    {
        return categoryItemNameEnUs;
    }

    public void setCategoryItemNameEnUs(String categoryItemNameEnUs)
    {
        this.categoryItemNameEnUs = categoryItemNameEnUs;
    }

    public String getCategoryItemNameArlb()
    {
        return categoryItemNameArlb;
    }

    public void setCategoryItemNameArlb(String categoryItemNameArlb)
    {
        this.categoryItemNameArlb = categoryItemNameArlb;
    }
}
