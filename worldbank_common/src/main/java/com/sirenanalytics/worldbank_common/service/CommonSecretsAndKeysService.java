package com.sirenanalytics.worldbank_common.service;

import com.sirenanalytics.worldbank_common.model.entity.XAccessKey;
import com.sirenanalytics.worldbank_common.model.entity.XSharedSecret;

import java.util.List;

public interface CommonSecretsAndKeysService
{
    List<XSharedSecret> findAllXSharedSecretOrderByCreateDateTimeDesc();

    List<XAccessKey> findAllXAccessKeyOrderByCreateDateTimeDesc();

    XSharedSecret save(XSharedSecret xSharedSecret);

    XAccessKey save(XAccessKey xAccessKey);

    void delete(XSharedSecret xSharedSecret);

    void delete(XAccessKey xAccessKey);
}
