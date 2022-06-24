package com.sirenanalytics.worldbank_feedback.service;

import com.sirenanalytics.worldbank_common.exception.ErrorCode;
import com.sirenanalytics.worldbank_feedback.model.entity.Feedback;
import com.sirenanalytics.worldbank_feedback.model.simple.ContactsNeedingEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FeedbackService
{
    Page<Feedback> findAll(Pageable pageable);

    Optional<Feedback> findById(Long id);

    Feedback save(Feedback feedback);

    List<ContactsNeedingEmail> findFeedbacksRequiringEmail();

    List<Long> findBlobIdsForFeedbackId(Long feedbackId);

    void insertFeedbackContact(Long feedbackId, Long contactId);

    ErrorCode validateUserOrPhotosAreInProximityToProject(Feedback feedback);
}
