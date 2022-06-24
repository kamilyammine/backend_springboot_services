package com.sirenanalytics.worldbank_common.service.impl;


import com.sirenanalytics.worldbank_common.repository.CommonSessionRepository;
import com.sirenanalytics.worldbank_common.service.CommonSessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class CommonSessionServiceImpl implements CommonSessionService
{
    @Resource
    CommonSessionRepository commonSessionRepository;

    @Override
    @Transactional
    public void deleteStaleSessions()
    {
        commonSessionRepository.deleteStaleSessions();
    }
}
