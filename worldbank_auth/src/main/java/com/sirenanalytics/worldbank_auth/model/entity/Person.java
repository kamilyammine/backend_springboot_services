package com.sirenanalytics.worldbank_auth.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Person
{
    @Id
    private Long id = null;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "father_name", nullable = true)
    private String fatherName;

    @Column(name = "mother_name", nullable = true)
    private String motherName;

    @Column(name = "register_number", nullable = true)
    private String registerNumber;

    @Column(name = "id_number", nullable = true)
    private String idNumber;

    @Column(name = "gender", nullable = true, length = 1)
    private String gender;

    @Column(name = "current_address", nullable = true)
    private String currentAddress;

    @Column(name = "dob", nullable = true)
    private LocalDate dob;

    @Column(name = "phone_home", nullable = true)
    private String phoneHome;

    @Column(name = "phone_mobile", nullable = true)
    private String phoneMobile;

    @Column(name = "judicial_training_admission_date", nullable = true)
    private LocalDate judicialTrainingAdmissionDate;

    @Column(name = "accredited_judge_commencement_date", nullable = true)
    private LocalDate accreditedJudgeCommencementDate;

    @Column(name = "has_children", nullable = true)
    private String hasChildren;

    @Column(name = "hr_ready", nullable = false)
    private Boolean hrReady;

    @Column(name = "registration_municipality_id", nullable = true)
    private Long registrationMunicipalityId;

    @Column(name = "marital_status_key", nullable = true, length = 50)
    private String maritalStatusKey;

    public String getFullName()
    {
        return firstName + " " + lastName;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getFatherName()
    {
        return fatherName;
    }

    public void setFatherName(String fatherName)
    {
        this.fatherName = fatherName;
    }

    public String getMotherName()
    {
        return motherName;
    }

    public void setMotherName(String motherName)
    {
        this.motherName = motherName;
    }

    public String getRegisterNumber()
    {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber)
    {
        this.registerNumber = registerNumber;
    }

    public String getIdNumber()
    {
        return idNumber;
    }

    public void setIdNumber(String idNumber)
    {
        this.idNumber = idNumber;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getCurrentAddress()
    {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress)
    {
        this.currentAddress = currentAddress;
    }

    public LocalDate getDob()
    {
        return dob;
    }

    public void setDob(LocalDate dob)
    {
        this.dob = dob;
    }

    public String getPhoneHome()
    {
        return phoneHome;
    }

    public void setPhoneHome(String phoneHome)
    {
        this.phoneHome = phoneHome;
    }

    public String getPhoneMobile()
    {
        return phoneMobile;
    }

    public void setPhoneMobile(String phoneMobile)
    {
        this.phoneMobile = phoneMobile;
    }

    public LocalDate getJudicialTrainingAdmissionDate()
    {
        return judicialTrainingAdmissionDate;
    }

    public void setJudicialTrainingAdmissionDate(LocalDate judicialTrainingAdmissionDate)
    {
        this.judicialTrainingAdmissionDate = judicialTrainingAdmissionDate;
    }

    public LocalDate getAccreditedJudgeCommencementDate()
    {
        return accreditedJudgeCommencementDate;
    }

    public void setAccreditedJudgeCommencementDate(LocalDate accreditedJudgeCommencementDate)
    {
        this.accreditedJudgeCommencementDate = accreditedJudgeCommencementDate;
    }

    public String getHasChildren()
    {
        return hasChildren;
    }

    public void setHasChildren(String hasChildren)
    {
        this.hasChildren = hasChildren;
    }

    public Boolean getHrReady()
    {
        return hrReady;
    }

    public void setHrReady(Boolean hrReady)
    {
        this.hrReady = hrReady;
    }

    public Long getRegistrationMunicipalityId()
    {
        return registrationMunicipalityId;
    }

    public void setRegistrationMunicipalityId(Long registrationMunicipalityId)
    {
        this.registrationMunicipalityId = registrationMunicipalityId;
    }

    public String getMaritalStatusKey()
    {
        return maritalStatusKey;
    }

    public void setMaritalStatusKey(String maritalStatusKey)
    {
        this.maritalStatusKey = maritalStatusKey;
    }
}