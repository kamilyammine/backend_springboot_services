package com.sirenanalytics.worldbank_feedback.util;

import com.sirenanalytics.worldbank_feedback.config.AppProperties;
import com.sirenanalytics.worldbank_feedback.model.entity.Blob;
import com.sirenanalytics.worldbank_feedback.model.entity.BlobBytes;
import com.sirenanalytics.worldbank_feedback.model.simple.ContactsNeedingEmail;
import com.sirenanalytics.worldbank_feedback.service.BlobService;
import com.sirenanalytics.worldbank_feedback.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailUtil
{
    private static final Logger log = LoggerFactory.getLogger(EmailUtil.class);
    private static InternetAddress fromAddress;

    @Resource
    FeedbackService feedbackService;

    @Resource
    BlobService blobService;
    @Resource
    AppProperties appProperties;
    @Autowired
    private JavaMailSender emailSender;

    @EventListener(ApplicationReadyEvent.class)
    public void loadConfig()
    {
        try
        {
            fromAddress = new InternetAddress(appProperties.getSpringMailUsername(), "Citizen Feedback");
        }
        catch (UnsupportedEncodingException e)
        {
            log.error("Unable to create fromAddress!", e);
        }
    }

    //Run once / day at 3am
    @Scheduled(cron = "0 0 3 ? * *")
    public void processSubmittedFeedbacks()
    {
        log.info("--------------------  -----  --------------------");
        log.info("           process submitted feedbacks           ");
        log.info("--------------------  -----  --------------------");

        try
        {
            List<ContactsNeedingEmail> cneList = feedbackService.findFeedbacksRequiringEmail();
            for (ContactsNeedingEmail cne : cneList)
            {
                List<Long> blobIdList = feedbackService.findBlobIdsForFeedbackId(cne.getFeedbackId());
                List<Blob> blobList = new ArrayList<>();

                for (Long blobId : blobIdList)
                {
                    Blob blob = blobService.findById(blobId).get();
                    BlobBytes blobBytes = blobService.findBytesById(blobId).get();
                    blob.setBlobBytes(blobBytes);
                    blobList.add(blob);
                }

                String subject = "Citizen feedback for " + cne.getRoadLabelEnUs();

                InternetAddress internetAddress = new InternetAddress(cne.getContactEmail(), cne.getContactFullName());

                String sbBody = "Greetings.  \n" +
                        "Feedback has arrived for a project you are affiliated with.\n" +
                        "Below is the information submitted by the user \n\n" +
                        "\nCategory Type: " + cne.getCategoryTypeNameEnUs() + " - " + cne.getCategoryTypeNameArlb() +
                        "\nCategory Item: " + cne.getCategoryItemNameEnUs() + " - " + cne.getCategoryItemNameArlb() +
                        "\nKadaa: " + cne.getKadaaNameEnUs() + " - " + cne.getKadaaNameArLb() +
                        "\nRoad Name: " + cne.getRoadNameEnUs() + " - " + cne.getRoadNameArLb() +
                        "\nComment: " + cne.getComment() +
                        "\nGender: " + cne.getGender() +
                        "\nAge Range: " + cne.getAgeRange() +
                        "\nUser Type: " + cne.getUserType() +
                        "\nOther User Types: " + cne.getUserTypeOther() +
                        "\nFirst Name: " + cne.getFeedbackFirstName() +
                        "\nLast Name: " + cne.getFeedbackLastName() +
                        "\nAddress: " + cne.getFeedbackAddress() +
                        "\nPhone: " + cne.getFeedbackPhone() +
                        "\nEmail: " + cne.getFeedbackEmail();


                sendMessageWithAttachment(internetAddress, subject, sbBody, blobList);
                feedbackService.insertFeedbackContact(cne.getFeedbackId(), cne.getContactId());
            }
            log.info("==>> Emails processed: " + cneList.size());
        }
        catch (Exception e)
        {
            log.error("Unable to process email", e);
        }
    }

    public void sendMessageWithAttachment(InternetAddress internetAddress, String subject, String body, List<Blob> blobList) throws MessagingException
    {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromAddress);
        helper.setTo(internetAddress);
        helper.setSubject(subject);
        helper.setText(body);

        for (Blob blob : blobList)
        {
            helper.addAttachment(blob.getFileName(), new ByteArrayResource(blob.getBlobBytes().getBytes()));
        }
        emailSender.send(message);
    }

    public void sendTestMessage(String emailTo)
    {
        try
        {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromAddress);
            helper.setTo(emailTo);
            helper.setSubject("Test from WorldBank App");
            helper.setText("This is a test message");

            emailSender.send(message);
        }
        catch (Exception e)
        {
            log.error("Unable to process email", e);
        }
    }
}
