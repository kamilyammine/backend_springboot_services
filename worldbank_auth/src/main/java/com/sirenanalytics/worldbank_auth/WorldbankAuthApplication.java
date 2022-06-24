package com.sirenanalytics.worldbank_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.sirenanalytics.worldbank_common", "com.sirenanalytics.worldbank_auth"})
@EntityScan({"com.sirenanalytics.worldbank_common.model.entity", "com.sirenanalytics.worldbank_auth.model.entity"})
@EnableJpaRepositories({"com.sirenanalytics.worldbank_common", "com.sirenanalytics.worldbank_auth"})
public class WorldbankAuthApplication extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        SpringApplication.run(WorldbankAuthApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(WorldbankAuthApplication.class);
    }
}
