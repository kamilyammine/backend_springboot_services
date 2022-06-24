package com.sirenanalytics.worldbank_common.service.impl;


import com.sirenanalytics.worldbank_common.model.entity.XAccessKey;
import com.sirenanalytics.worldbank_common.model.entity.XSharedSecret;
import com.sirenanalytics.worldbank_common.repository.CommonAccessKeyRepository;
import com.sirenanalytics.worldbank_common.repository.CommonSharedSecretRepository;
import com.sirenanalytics.worldbank_common.service.CommonSecretsAndKeysService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommonSecretsAndKeysServiceImpl implements CommonSecretsAndKeysService
{
    @Resource
    CommonSharedSecretRepository commonSharedSecretRepository;

    @Resource
    CommonAccessKeyRepository commonAccessKeyRepository;

    @Override
    public List<XSharedSecret> findAllXSharedSecretOrderByCreateDateTimeDesc()
    {
        return commonSharedSecretRepository.findAllOrderByCreateDateTimeDesc();
    }

    @Override
    public List<XAccessKey> findAllXAccessKeyOrderByCreateDateTimeDesc()
    {
        return commonAccessKeyRepository.findAllOrderByCreateDateTimeDesc();
    }

    @Override
    public XSharedSecret save(XSharedSecret xSharedSecret)
    {
        return commonSharedSecretRepository.save(xSharedSecret);
    }

    @Override
    public XAccessKey save(XAccessKey xAccessKey)
    {
        return commonAccessKeyRepository.save(xAccessKey);
    }

    @Override
    public void delete(XSharedSecret xSharedSecret)
    {
        commonSharedSecretRepository.delete(xSharedSecret);
    }

    @Override
    public void delete(XAccessKey xAccessKey)
    {
        commonAccessKeyRepository.delete(xAccessKey);
    }
}
