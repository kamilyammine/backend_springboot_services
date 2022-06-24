package com.sirenanalytics.worldbank_feedback.repository;

import com.sirenanalytics.worldbank_feedback.model.entity.BlobBytes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlobBytesRepository extends JpaRepository<BlobBytes, Long>
{
}
