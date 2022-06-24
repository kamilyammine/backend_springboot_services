package com.sirenanalytics.worldbank_feedback.service;

import com.sirenanalytics.worldbank_feedback.model.entity.Blob;
import com.sirenanalytics.worldbank_feedback.model.entity.BlobBytes;

import java.util.Optional;

public interface BlobService
{
    Optional<Blob> findById(Long id);

    Optional<BlobBytes> findBytesById(Long id);
}
