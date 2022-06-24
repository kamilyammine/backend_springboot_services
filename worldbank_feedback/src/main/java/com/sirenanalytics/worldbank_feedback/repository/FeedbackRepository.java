package com.sirenanalytics.worldbank_feedback.repository;

import com.sirenanalytics.worldbank_feedback.model.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>
{
    String contactsNeedingEmailQuery =
            "select " +
                    "f.id as feedback_id, f.comment, f.gender, f.age_range, f.user_type, f.user_type_other, " +
                    "f.first_name feedback_first_name, f.last_name feedback_last_name, f.address feedback_address, " +
                    "f.phone feedback_phone, f.email feedback_email, p.name_en_us as road_label_en_us, p.name_ar_lb " +
                    "as road_label_ar_lb, p.description_en_us as road_name_en_us, p.description_ar_lb as road_name_ar_lb, " +
                    "k.name_en_us as kadaa_name_en_us, k.name_ar_lb as kadaa_name_ar_lb, c.id as contact_id, " +
                    "c.first_name contact_first_name, c.last_name contact_last_name, c.email contact_email, " +
                    "ci.name_en_us category_item_name_en_us, ci.name_ar_lb category_item_name_ar_lb, " +
                    "ct.name_en_us category_type_name_en_us, ct.name_ar_lb category_type_name_ar_lb " +
                    "from feedback f " +
                    "left join feedback_contact fc on f.id = fc.feedback_id " +
                    "inner join project p on f.project_id = p.id " +
                    "inner join kadaa k on p.kadaa_key = k.key " +
                    "left join contact_kadaa ck on k.key = ck.kadaa_key " +
                    "inner join contact c on ck.contact_id = c.id " +
                    "inner join category_item ci on f.category_item_key = ci.key " +
                    "inner join category_type ct on ci.category_type_key = ct.key " +
                    "where fc.feedback_id is null;";

    @Modifying
    @Query(value = "insert into feedback_blob values(:feedbackId, :blobId, :createUserId, :createTimestamp)", nativeQuery = true)
    void insertFeedbackBlob(@Param("feedbackId") Long feedbackId, @Param("blobId") Long blobId,
                            @Param("createUserId") Long createUserId, @Param("createTimestamp") LocalDateTime createTimestamp);

    @Query(value = contactsNeedingEmailQuery, nativeQuery = true)
    List<Map<String, Object>> findFeedbacksRequiringEmail();

    @Query(value = "select blob_id from feedback_blob where feedback_id = :feedbackId", nativeQuery = true)
    List<Long> findBlobIdsForFeedbackId(@Param("feedbackId") Long feedbackId);

    @Modifying
    @Query(value = "insert into feedback_contact values(:feedbackId, :contactId, :createUserId, :createTimestamp)", nativeQuery = true)
    void insertFeedbackContact(@Param("feedbackId") Long feedbackId, @Param("contactId") Long contactId,
                               @Param("createUserId") Long createUserId, @Param("createTimestamp") LocalDateTime createTimestamp);
}
