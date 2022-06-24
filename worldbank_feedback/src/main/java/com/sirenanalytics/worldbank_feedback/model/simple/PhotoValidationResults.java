package com.sirenanalytics.worldbank_feedback.model.simple;

import java.util.ArrayList;
import java.util.List;

public class PhotoValidationResults
{
    private final List<Photo> photoList = new ArrayList<>();
    private Long projectId;
    private Double feedbackLatitude;
    private Double feedbackLongitude;
    private Integer closestCoordinateId;
    private Double closestCoordinateDistanceInKm;
    private boolean atLeastOneImageIsGeotagged;
    private String errorCode;

    public Long getProjectId()
    {
        return projectId;
    }

    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }

    public Double getFeedbackLatitude()
    {
        return feedbackLatitude;
    }

    public void setFeedbackLatitude(Double feedbackLatitude)
    {
        this.feedbackLatitude = feedbackLatitude;
    }

    public Double getFeedbackLongitude()
    {
        return feedbackLongitude;
    }

    public void setFeedbackLongitude(Double feedbackLongitude)
    {
        this.feedbackLongitude = feedbackLongitude;
    }

    public Integer getClosestCoordinateId()
    {
        return closestCoordinateId;
    }

    public void setClosestCoordinateId(Integer closestCoordinateId)
    {
        this.closestCoordinateId = closestCoordinateId;
    }

    public Double getClosestCoordinateDistanceInKm()
    {
        return closestCoordinateDistanceInKm;
    }

    public void setClosestCoordinateDistanceInKm(Double closestCoordinateDistanceInKm)
    {
        this.closestCoordinateDistanceInKm = closestCoordinateDistanceInKm;
    }

    public boolean isAtLeastOneImageIsGeotagged()
    {
        return atLeastOneImageIsGeotagged;
    }

    public void setAtLeastOneImageIsGeotagged(boolean atLeastOneImageIsGeotagged)
    {
        this.atLeastOneImageIsGeotagged = atLeastOneImageIsGeotagged;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public List<Photo> getPhotoList()
    {
        return photoList;
    }

    public void addPhoto(Photo photo)
    {
        photoList.add(photo);
    }

    public static class Photo
    {
        private String fileName;
        private Double latitude;
        private Double longitude;
        private Integer closestCoordinateId;
        private Double closestCoordinateDistanceInKm;

        public String getFileName()
        {
            return fileName;
        }

        public void setFileName(String fileName)
        {
            this.fileName = fileName;
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

        public Integer getClosestCoordinateId()
        {
            return closestCoordinateId;
        }

        public void setClosestCoordinateId(Integer closestCoordinateId)
        {
            this.closestCoordinateId = closestCoordinateId;
        }

        public Double getClosestCoordinateDistanceInKm()
        {
            return closestCoordinateDistanceInKm;
        }

        public void setClosestCoordinateDistanceInKm(Double closestCoordinateDistanceInKm)
        {
            this.closestCoordinateDistanceInKm = closestCoordinateDistanceInKm;
        }
    }
}
