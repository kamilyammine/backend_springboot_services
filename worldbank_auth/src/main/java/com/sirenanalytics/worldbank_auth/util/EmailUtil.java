package com.sirenanalytics.worldbank_auth.util;

import com.sirenanalytics.worldbank_auth.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
public class EmailUtil
{
    private static final Logger log = LoggerFactory.getLogger(EmailUtil.class);
    private static InternetAddress fromInternetAddress;
    @Resource
    AppProperties appProperties;
    @Autowired
    private JavaMailSender emailSender;

    @EventListener(ApplicationReadyEvent.class)
    public void loadConfig()
    {
        try
        {
            fromInternetAddress = new InternetAddress(appProperties.getSpringMailUsername(), "Ministry of Justice Automated Account");
        }
        catch (UnsupportedEncodingException e)
        {
            log.error("Unable to create fromAddress!", e);
        }
    }

    public void sendMessageWithAttachment(InternetAddress toInternetAddress, String subject, String body)
    {
        try
        {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromInternetAddress);
            helper.setTo(toInternetAddress);
            helper.setSubject(subject);
            helper.setText(body);
            emailSender.send(message);
        }
        catch (Exception e)
        {
            log.error("", e);
        }

    }
}
