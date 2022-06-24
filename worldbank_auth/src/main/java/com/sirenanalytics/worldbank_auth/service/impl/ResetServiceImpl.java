package com.sirenanalytics.worldbank_auth.service.impl;

import com.sirenanalytics.worldbank_auth.config.AppProperties;
import com.sirenanalytics.worldbank_auth.model.entity.Person;
import com.sirenanalytics.worldbank_auth.repository.PersonRepository;
import com.sirenanalytics.worldbank_auth.service.ResetService;
import com.sirenanalytics.worldbank_auth.util.EmailUtil;
import com.sirenanalytics.worldbank_common.model.entity.LogResetPassword;
import com.sirenanalytics.worldbank_common.repository.CommonLogResetPasswordRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import java.util.Optional;

@Service
public class ResetServiceImpl implements ResetService
{
    private static final Logger log = LoggerFactory.getLogger(EmailUtil.class);

    @Resource
    AppProperties appProperties;

    @Resource
    PersonRepository personRepository;

    @Resource
    CommonLogResetPasswordRepository commonLogResetPasswordRepository;

    @Resource
    EmailUtil emailUtil;

    public void resetPassword(String email)
    {
        /*----------------------------------------------------------------------+
        |   Get the user by email, if one exists.  This is in the person table. |
        |   If the user exists, generate a link that we email to the user.  We  |
        |   will use the key if the user chooses to reset.                      |
        +----------------------------------------------------------------------*/

        try
        {
            Optional<Person> optionalPerson = personRepository.findByEmailAddress(email);
            InternetAddress toInternetAddress;
            String subject = "طلب إعادة تعيين كلمة مرور"; //"Ministry of Justice Password Reset Request"
            String body;

            if (optionalPerson.isEmpty())
            {
                toInternetAddress = new InternetAddress(email);
                body = "تم إدخال هذا البريد الإلكتروني لإعادة تعيين كلمة المرور الخاصة بك ، ولكن لا يوجد حساب لهذا البريد الإلكتروني. ربما تم تسجيل حسابك ببريد إلكتروني آخر." +
                        "\n\nThis email was entered to reset the password for Ministry of Justice, but no account exists with this email.  Perhaps your account is registered with a different email";
            }
            else
            {
                Person person = optionalPerson.get();
                String key = RandomStringUtils.randomAlphanumeric(128);
                String url = appProperties.getBaseUrl() + "/reset-password/" + key;

                LogResetPassword logResetPassword = new LogResetPassword();
                logResetPassword.setKey(key);
                logResetPassword.setStatus("P");
                logResetPassword.setUserId(person.getId()); //person id and user id are the same
                commonLogResetPasswordRepository.save(logResetPassword);

                toInternetAddress = new InternetAddress(email, person.getFullName());
                body = "تم إدخال هذا البريد الإلكتروني لإعادة تعيين كلمة المرور الخاصة بك ، ولكن لا يوجد حساب لهذا البريد الإلكتروني. ربما تم تسجيل حسابك ببريد إلكتروني آخر." +
                        "\n\n Somebody has entered this email address, asking the password be reset.  If this was " +
                        "you, please click the link below to complete your password reset.  If you DID NOT request " +
                        "this, please contact an administrator immediately to report this" +
                        "\n\n" + url;
            }

            emailUtil.sendMessageWithAttachment(toInternetAddress, subject, body);
        }
        catch (Exception e)
        {
            log.error("Unable to process email", e);
        }

    }
}
