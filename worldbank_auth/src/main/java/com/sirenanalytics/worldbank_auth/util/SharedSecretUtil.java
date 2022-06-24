package com.sirenanalytics.worldbank_auth.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.sirenanalytics.worldbank_auth.config.AppProperties;
import com.sirenanalytics.worldbank_common.model.entity.XAccessKey;
import com.sirenanalytics.worldbank_common.model.entity.XSharedSecret;
import com.sirenanalytics.worldbank_common.service.CommonSecretsAndKeysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SharedSecretUtil
{
    private static final Logger log = LoggerFactory.getLogger(SharedSecretUtil.class);
    private static JWSSigner jwsSigner;
    @Resource
    AppProperties appProperties;

    @Resource
    private CommonSecretsAndKeysService commonSecretsAndKeysService;

    public static JWSSigner getJWSSigner()
    {
        return jwsSigner;
    }

    @PostConstruct
    private void setupJWSSignerAndTokenVars()
    {
        rotateSecretsAndKeysIfNecessary();
    }

    //Run once / day at 3am
    @Scheduled(cron = "0 0 3 ? * *")
    protected void rotateSecretsAndKeysIfNecessary()
    {
        rotateSharedSecretIfNecessary();
        rotateAccessKeyIfNecessary();
        log.info("==> rotateSecretsAndKeys complete");
    }

    private void rotateSharedSecretIfNecessary()
    {
        /*----------------------------------------------------------------------+
        |   Get a list of all xSharedSecret records.  If any are older than app |
        |   setting for rotation frequency (default 1 day), then delete it.  If |
        |   we have no keys, generate a new one and rebuild the JWS signer      |
        +----------------------------------------------------------------------*/
        List<XSharedSecret> persistentSharedSecretList = commonSecretsAndKeysService.findAllXSharedSecretOrderByCreateDateTimeDesc();
        XSharedSecret mostRecentSharedSecret = persistentSharedSecretList.size() > 0 ? persistentSharedSecretList.get(0) : null;
        List<XSharedSecret> freshSharedSecretList = new ArrayList<>();

        for (XSharedSecret xSharedSecret : persistentSharedSecretList)
        {
            if (xSharedSecret.getCreateDateTime().plusDays(appProperties
                    .getSharedSecretRotationFrequencyInDays()).isBefore(LocalDateTime.now()))
            {
                commonSecretsAndKeysService.delete(xSharedSecret);
            }
            else
            {
                freshSharedSecretList.add(xSharedSecret);
            }
        }

        if (freshSharedSecretList.size() < 1)
            mostRecentSharedSecret = generateAndSaveNewSharedSecret();

        rebuildJWSSigner(mostRecentSharedSecret);
    }

    private XSharedSecret generateAndSaveNewSharedSecret()
    {
        String sharedSecret = UUID.randomUUID().toString();
        XSharedSecret xSharedSecret = new XSharedSecret();
        xSharedSecret.setKey(sharedSecret);
        xSharedSecret.setCreateDateTime(LocalDateTime.now());

        return commonSecretsAndKeysService.save(xSharedSecret);
    }

    private void rebuildJWSSigner(XSharedSecret currentSharedSecret)
    {
        try
        {
            //Create or update the HMAC signer.  Every token will be signed with this.
            jwsSigner = new MACSigner(currentSharedSecret.getKey());
        }
        catch (JOSEException e)
        {
            log.error("JOSEException", e);
        }
    }

    private void rotateAccessKeyIfNecessary()
    {
        /*----------------------------------------------------------------------+
        |   Get a list of all xAccessKey records.  If any are older than app    |
        |   setting for rotation frequency (default 1 day), then delete it.  If |
        |   we have no keys, generate a new one.                                |
        +----------------------------------------------------------------------*/
        List<XAccessKey> accessKeyList = commonSecretsAndKeysService.findAllXAccessKeyOrderByCreateDateTimeDesc();
        accessKeyList.stream().filter(xAccessKey -> xAccessKey.getCreateDateTime().plusDays(appProperties
                .getSharedSecretRotationFrequencyInDays()).isBefore(LocalDateTime.now())).forEach(xAccessKey -> commonSecretsAndKeysService.delete(xAccessKey));

        if (accessKeyList.size() < 1)
        {
            generateAndSaveNewAccessKey();
        }

    }

    private void generateAndSaveNewAccessKey()
    {
        String secretKey = UUID.randomUUID().toString();
        XAccessKey xAccessKey = new XAccessKey();
        xAccessKey.setKey(secretKey);
        xAccessKey.setCreateDateTime(LocalDateTime.now());

        commonSecretsAndKeysService.save(xAccessKey);
    }
}
