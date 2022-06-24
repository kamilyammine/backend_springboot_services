package com.sirenanalytics.worldbank_feedback.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*----------------------------------------------------------------------+
|   Autowired property values can be anywhere in the code and can get   |
|   out of control quickly.  Let's put them in a common location for    |
|   easy management.                                                    |
+----------------------------------------------------------------------*/
@Component
public class AppProperties
{
    @Value("${spring.mail.username}")
    private String springMailUsername;

    public String getSpringMailUsername()
    {
        return springMailUsername;
    }
}
