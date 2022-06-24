package com.sirenanalytics.worldbank_feedback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.sirenanalytics.worldbank_common", "com.sirenanalytics.worldbank_feedback"})
@EntityScan({"com.sirenanalytics.worldbank_common.model.entity", "com.sirenanalytics.worldbank_feedback.model.entity"})
@EnableJpaRepositories({"com.sirenanalytics.worldbank_common", "com.sirenanalytics.worldbank_feedback"})
@EnableScheduling
public class WorldBankFeedbackApplication extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        SpringApplication.run(WorldBankFeedbackApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(WorldBankFeedbackApplication.class);
    }
}
