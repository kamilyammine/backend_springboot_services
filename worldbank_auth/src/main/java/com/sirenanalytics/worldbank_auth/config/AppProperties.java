package com.sirenanalytics.worldbank_auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;

/*----------------------------------------------------------------------+
|   Autowired property values can be anywhere in the code and can get   |
|   out of control quickly.  Let's put them in a common location for    |
|   easy management.                                                    |
+----------------------------------------------------------------------*/
@Component
public class AppProperties
{
    private static InternetAddress fromAddress;

    @Value("${shared-secret-rotation-frequency-in-days:1}")
    public Integer sharedSecretRotationFrequencyInDays;

    @Value("${access-token-lifespan-in-minutes:10}")
    public Integer accessTokenLifespanInMinutes;

    @Value("${refresh-token-lifespan-in-minutes:30}")
    public Integer refreshTokenLifespanInMinutes;

    @Value("${max-consecutive-refreshes-allowed:6}")
    public Integer maxConsecutiveRefreshesAllowed;

    @Value("${spring.mail.username}")
    private String springMailUsername;

    @Value("${base-url}")
    private String baseUrl;

    public static InternetAddress getFromAddress()
    {
        return fromAddress;
    }

    public Integer getSharedSecretRotationFrequencyInDays()
    {
        return sharedSecretRotationFrequencyInDays;
    }

    public Integer getAccessTokenLifespanInMinutes()
    {
        return accessTokenLifespanInMinutes;
    }

    public Integer getRefreshTokenLifespanInMinutes()
    {
        return refreshTokenLifespanInMinutes;
    }

    public Integer getMaxConsecutiveRefreshesAllowed()
    {
        return maxConsecutiveRefreshesAllowed;
    }

    public String getSpringMailUsername()
    {
        return springMailUsername;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }
}
