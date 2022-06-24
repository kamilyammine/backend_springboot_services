package com.sirenanalytics.worldbank_feedback.repository;

import com.sirenanalytics.worldbank_feedback.model.entity.Blob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlobRepository extends JpaRepository<Blob, Long>
{
    @Query(value = "select * from blob b where id in (select blob_id from feedback_blob where feedback_id = :feedbackId)", nativeQuery = true)
    List<Blob> findBlobsByFeedbackId(@Param("feedbackId") Long feedbackId);
}
