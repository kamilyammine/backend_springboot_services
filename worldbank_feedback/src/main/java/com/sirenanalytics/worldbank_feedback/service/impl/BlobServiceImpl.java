package com.sirenanalytics.worldbank_feedback.service.impl;

import com.sirenanalytics.worldbank_feedback.model.entity.Blob;
import com.sirenanalytics.worldbank_feedback.model.entity.BlobBytes;
import com.sirenanalytics.worldbank_feedback.repository.BlobBytesRepository;
import com.sirenanalytics.worldbank_feedback.repository.BlobRepository;
import com.sirenanalytics.worldbank_feedback.service.BlobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class BlobServiceImpl implements BlobService
{
    @Resource
    BlobRepository blobRepository;

    @Resource
    BlobBytesRepository blobBytesRepository;

    @Override
    public Optional<Blob> findById(Long id)
    {
        return blobRepository.findById(id);
    }

    @Override
    public Optional<BlobBytes> findBytesById(Long id)
    {
        return blobBytesRepository.findById(id);
    }
}
