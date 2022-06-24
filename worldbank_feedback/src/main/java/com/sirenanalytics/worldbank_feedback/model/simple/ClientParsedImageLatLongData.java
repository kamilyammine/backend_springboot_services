package com.sirenanalytics.worldbank_feedback.model.simple;

public class ClientParsedImageLatLongData
{
    private String filename;
    private String latitudeDirectional;
    private Double latitudeDegrees;
    private Double latitudeMinutes;
    private Double latitudeSeconds;
    private String longitudeDirectional;
    private Double longitudeDegrees;
    private Double longitudeMinutes;
    private Double longitudeSeconds;

    public ClientParsedImageLatLongData()
    {
    }

    public ClientParsedImageLatLongData(String[] stringArray)
    {
        this.filename = stringArray[0];
        this.latitudeDirectional = stringArray[1];
        this.latitudeDegrees = Double.parseDouble(stringArray[2]);
        this.latitudeMinutes = Double.parseDouble(stringArray[3]);
        this.latitudeSeconds = Double.parseDouble(stringArray[4]);
        this.longitudeDirectional = stringArray[5];
        this.longitudeDegrees = Double.parseDouble(stringArray[6]);
        this.longitudeMinutes = Double.parseDouble(stringArray[7]);
        this.longitudeSeconds = Double.parseDouble(stringArray[8]);
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String getLatitudeDirectional()
    {
        return latitudeDirectional;
    }

    public void setLatitudeDirectional(String latitudeDirectional)
    {
        this.latitudeDirectional = latitudeDirectional;
    }

    public Double getLatitudeDegrees()
    {
        return latitudeDegrees;
    }

    public void setLatitudeDegrees(Double latitudeDegrees)
    {
        this.latitudeDegrees = latitudeDegrees;
    }

    public Double getLatitudeMinutes()
    {
        return latitudeMinutes;
    }

    public void setLatitudeMinutes(Double latitudeMinutes)
    {
        this.latitudeMinutes = latitudeMinutes;
    }

    public Double getLatitudeSeconds()
    {
        return latitudeSeconds;
    }

    public void setLatitudeSeconds(Double latitudeSeconds)
    {
        this.latitudeSeconds = latitudeSeconds;
    }

    public String getLongitudeDirectional()
    {
        return longitudeDirectional;
    }

    public void setLongitudeDirectional(String longitudeDirectional)
    {
        this.longitudeDirectional = longitudeDirectional;
    }

    public Double getLongitudeDegrees()
    {
        return longitudeDegrees;
    }

    public void setLongitudeDegrees(Double longitudeDegrees)
    {
        this.longitudeDegrees = longitudeDegrees;
    }

    public Double getLongitudeMinutes()
    {
        return longitudeMinutes;
    }

    public void setLongitudeMinutes(Double longitudeMinutes)
    {
        this.longitudeMinutes = longitudeMinutes;
    }

    public Double getLongitudeSeconds()
    {
        return longitudeSeconds;
    }

    public void setLongitudeSeconds(Double longitudeSeconds)
    {
        this.longitudeSeconds = longitudeSeconds;
    }
}
