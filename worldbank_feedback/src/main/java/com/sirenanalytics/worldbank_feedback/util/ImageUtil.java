package com.sirenanalytics.worldbank_feedback.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.sirenanalytics.worldbank_feedback.model.entity.Blob;
import com.sirenanalytics.worldbank_feedback.model.simple.ClientParsedImageLatLongData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class ImageUtil
{
    private final Logger log = LoggerFactory.getLogger(ImageUtil.class);

    public void populateLatLongFromImageMetadata(Blob blob, ClientParsedImageLatLongData imageLatLongData)
    {
        /*----------------------------------------------------------+
        |   If we have a ClientParsedImageLatLongData, it means the |
        |   FE passed in the image's lat and long.  That always     |
        |   takes precedence.  If this is missing, then look for    |
        |   the coordinate in the image metadata.                   |
        +----------------------------------------------------------*/
        if (imageLatLongData != null)
        {
            blob.setLatitude(convertHMSDataToDecimalDegrees(
                    imageLatLongData.getLatitudeDegrees(),
                    imageLatLongData.getLatitudeMinutes(),
                    imageLatLongData.getLatitudeSeconds(),
                    imageLatLongData.getLatitudeDirectional())
            );

            blob.setLongitude(convertHMSDataToDecimalDegrees(
                    imageLatLongData.getLongitudeDegrees(),
                    imageLatLongData.getLongitudeMinutes(),
                    imageLatLongData.getLongitudeSeconds(),
                    imageLatLongData.getLongitudeDirectional())
            );
        }
        /*----------------------------------------------------------+
        |   No FE parsing was found.  We'll use the Drew Noakes     |
        |   java client to parse the image and extract geo-data.    |
        |   If the image has geo-tagging on it, it should be under  |
        |   a directory called gps.  Under that, we need to look    |
        |   for free text matching the lat and long.  I'm not sure  |
        |   if this is universal across all devices.  If all 4      |
        |   fields are found, convert the degree/minute/second      |
        |   format to degree decimal format.                        |
        +----------------------------------------------------------*/
        else
        {
            var drewMetaData = new Object()
            {
                String latitudeDirectional = null;
                String latitude = null;
                String longitudeDirectional = null;
                String longitude = null;

                boolean valuesArePopulated()
                {
                    return latitudeDirectional != null && latitude != null &&
                            longitudeDirectional != null && longitude != null;
                }
            };

            try
            {
                Metadata metadata = ImageMetadataReader.readMetadata(
                        new ByteArrayInputStream(blob.getBlobBytes().getBytes()));

                metadata.getDirectories().forEach(directory ->
                {
                    if ("gps".equalsIgnoreCase(directory.getName()))
                    {
                        directory.getTags().forEach(tag ->
                        {
                            if (drewMetaData.latitudeDirectional == null)
                                drewMetaData.latitudeDirectional = extractTagNameIfMatch(tag, "GPS Latitude Ref");

                            if (drewMetaData.latitude == null)
                                drewMetaData.latitude = extractTagNameIfMatch(tag, "GPS Latitude");

                            if (drewMetaData.longitudeDirectional == null)
                                drewMetaData.longitudeDirectional = extractTagNameIfMatch(tag, "GPS Longitude Ref");

                            if (drewMetaData.longitude == null)
                                drewMetaData.longitude = extractTagNameIfMatch(tag, "GPS Longitude");
                        });

                        if (drewMetaData.valuesArePopulated())
                        {
                            blob.setLatitude(parseDrewDMSFormatToDecimalDegrees(drewMetaData.latitude, drewMetaData.latitudeDirectional));
                            blob.setLongitude(parseDrewDMSFormatToDecimalDegrees(drewMetaData.longitude, drewMetaData.longitudeDirectional));
                        }
                    }
                });
            }
            catch (ImageProcessingException | IOException e)
            {
                log.error("Error extracting GPS info from image", e);
            }
        }
    }

    private String extractTagNameIfMatch(Tag tag, String nameToExtract)
    {
        if (nameToExtract.equalsIgnoreCase(tag.getTagName()))
            return tag.getDescription();

        return null;
    }

    private double parseDrewDMSFormatToDecimalDegrees(String dms, String cardinalDirection)
    {
        /*----------------------------------------------------------+
        |   DMS format will be something like N 33° 53' 24.79"      |
        |   or E 35° 31' 15.58".  We want to convert these to       |
        |   something like 33.890219, called decimal degrees.       |
        +----------------------------------------------------------*/

        String hours = dms.substring(0, dms.indexOf('°')).trim();
        String minutes = dms.substring(dms.indexOf('°') + 1, dms.indexOf('\'')).trim();
        String seconds = dms.substring(dms.indexOf('\'') + 1, dms.indexOf('"')).trim();

        /*----------------------------------------------------------+
        |   There are 60 minutes in each hour (or degree) and 60    |
        |   seconds in each minute, for a total of 3600 seconds in  |
        |   each degree.  Calculate minutes and seconds to come up  |
        |   with the decimal degree                                 |
        +----------------------------------------------------------*/

        double hoursDbl = Double.parseDouble(hours);
        double minutesDbl = Double.parseDouble(minutes);
        double secondsDbl = Double.parseDouble(seconds);

        return convertHMSDataToDecimalDegrees(hoursDbl, minutesDbl, secondsDbl, cardinalDirection);
    }

    private double convertHMSDataToDecimalDegrees(double hoursDbl, double minutesDbl, double secondsDbl, String cardinalDirection)
    {
        double decimalDegree = hoursDbl + ((minutesDbl * 60 + secondsDbl) / 3600);

        /*----------------------------------------------------------+
        |   Decimal Degree format doesn't have cardinal directions  |
        |   like N, S, E, W.  West and South are negative values    |
        +----------------------------------------------------------*/
        if ("W".equalsIgnoreCase(cardinalDirection) || "S".equalsIgnoreCase(cardinalDirection))
            decimalDegree = -decimalDegree;

        return decimalDegree;
    }
}
