package com.sirenanalytics.worldbank_feedback.service.impl;

import com.sirenanalytics.worldbank_common.exception.ErrorCode;
import com.sirenanalytics.worldbank_feedback.mapper.ContactsNeedingEmailMapper;
import com.sirenanalytics.worldbank_feedback.model.entity.BaseLookup;
import com.sirenanalytics.worldbank_feedback.model.entity.Blob;
import com.sirenanalytics.worldbank_feedback.model.entity.BlobBytes;
import com.sirenanalytics.worldbank_feedback.model.entity.Feedback;
import com.sirenanalytics.worldbank_feedback.model.entity.LookupCategoryItem;
import com.sirenanalytics.worldbank_feedback.model.simple.ContactsNeedingEmail;
import com.sirenanalytics.worldbank_feedback.model.simple.PhotoValidationResults;
import com.sirenanalytics.worldbank_feedback.repository.BlobBytesRepository;
import com.sirenanalytics.worldbank_feedback.repository.BlobRepository;
import com.sirenanalytics.worldbank_feedback.repository.CategoryItemRepository;
import com.sirenanalytics.worldbank_feedback.repository.CoordinateRepository;
import com.sirenanalytics.worldbank_feedback.repository.FeedbackRepository;
import com.sirenanalytics.worldbank_feedback.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService
{
    private static final List<String> categoryItemsNotRequiringPhotos = new ArrayList<>();
    private final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);
    @Resource
    FeedbackRepository feedbackRepository;

    @Resource
    BlobRepository blobRepository;

    @Resource
    BlobBytesRepository blobBytesRepository;

    @Resource
    CoordinateRepository coordinateRepository;

    @Resource
    CategoryItemRepository categoryItemRepository;
    @Resource
    ContactsNeedingEmailMapper contactsNeedingEmailMapper;

    @EventListener(ApplicationReadyEvent.class)
    void loadCategoryItemsNotRequiringPhotos()
    {
        List<LookupCategoryItem> lookupCategoryItemList = categoryItemRepository.findAll();
        lookupCategoryItemList.stream().filter(lookupCategoryItem -> !lookupCategoryItem.getPhotoRequired())
                .map(BaseLookup::getKey).forEach(categoryItemsNotRequiringPhotos::add);
    }

    @Override
    public Page<Feedback> findAll(Pageable pageable)
    {
        Page<Feedback> feedbackPage = feedbackRepository.findAll(pageable);
        feedbackPage.forEach(feedback -> feedback.setBlobList(blobRepository.findBlobsByFeedbackId(feedback.getId())));

        return feedbackPage;
    }

    @Override
    public Optional<Feedback> findById(Long id)
    {
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(id);

        //load blobs (no bytes)
        optionalFeedback.ifPresent(feedback -> feedback.setBlobList(blobRepository.findBlobsByFeedbackId(feedback.getId())));

        return optionalFeedback;
    }

    @Override
    @Transactional
    public Feedback save(Feedback feedback)
    {
        LocalDateTime now = LocalDateTime.now();
        log.info("\n\n>>> saving feedback\n\n");
        feedbackRepository.save(feedback);
        log.info("\n\n>>> feedback saved\n\n");

        /*------------------------------------------+
        |   There can be zero or more blobs.  Loop  |
        |   over and save them one by one.          |
        +------------------------------------------*/
        for (Blob blob : feedback.getBlobList())
        {
            log.info("\n\n>>> saving blob with filename {}\n\n", blob.getFileName());
            blob = blobRepository.save(blob);
            /*----------------------------------------------+
            |   This is a one-to-one.  I prefer to keep the |
            |   ids identical for simplicity.               |
            +----------------------------------------------*/
            BlobBytes blobBytes = blob.getBlobBytes();
            blobBytes.setId(blob.getId());
            log.info("\n\n>>> saving blobBytes for filename {}\n\n", blob.getFileName());
            blobBytesRepository.save(blobBytes);
            log.info("\n\n>>> blob and blobBytes for filename {} saved\n\n", blob.getFileName());

            /*--------------------------------------------------+
            |   Insert the feedback and blob ids                |
            |   into the junction table                         |
            +--------------------------------------------------*/
            feedbackRepository.insertFeedbackBlob(feedback.getId(), blob.getId(), 0L, now);


            /*--------------------------------------------------+
            |   We remove the bytes so that the response to the |
            |   caller doesn't include unnecessary data         |
            +--------------------------------------------------*/
            blob.setBlobBytes(null);
        }
        return feedback;
    }

    @Override
    public List<ContactsNeedingEmail> findFeedbacksRequiringEmail()
    {
        List<Map<String, Object>> mapList = feedbackRepository.findFeedbacksRequiringEmail();
        return contactsNeedingEmailMapper.mapToSimpleModelList(mapList);
    }

    @Override
    public List<Long> findBlobIdsForFeedbackId(Long feedbackId)
    {
        return feedbackRepository.findBlobIdsForFeedbackId(feedbackId);
    }

    @Override
    @Transactional
    public void insertFeedbackContact(Long feedbackId, Long contactId)
    {
        feedbackRepository.insertFeedbackContact(feedbackId, contactId, 0L, LocalDateTime.now());
    }

    @Override
    public ErrorCode validateUserOrPhotosAreInProximityToProject(Feedback feedback)
    {
        try
        {
            /*------------------------------------------------------------------+
            |   If the categoryItem does not require a photo, we do not need    |
            |   check any geo-coding logic.  This can be reported anywhere.     |
            +------------------------------------------------------------------*/
            if (categoryItemsNotRequiringPhotos.contains(feedback.getCategoryItemKey()))
                return ErrorCode.NO_ERROR;

            /*------------------------------------------------------------------+
            |   If the feedback itself or any of the photos are within 200m of  |
            |   the project, we return true.  It is true if the x and y are     |
            |   within 200m of any segment from the coordinates.  We'll do the  |
            |   math at a higher number to allow for a margin of error.         |
            +------------------------------------------------------------------*/
            List<Map<String, Object>> results;
            PhotoValidationResults photoValidationResults = new PhotoValidationResults();

            photoValidationResults.setProjectId(feedback.getProjectId());

            //first check the feedback to see if they are at that physical location
            if (feedback.getLatitude() != null && feedback.getLongitude() != null)
            {
                photoValidationResults.setAtLeastOneImageIsGeotagged(true);
                photoValidationResults.setFeedbackLatitude(feedback.getLatitude());
                photoValidationResults.setFeedbackLongitude(feedback.getLongitude());

                results = coordinateRepository.findClosestCoordinateFromLatLongAndProject(
                        feedback.getLatitude(), feedback.getLongitude(), feedback.getProjectId());

                photoValidationResults.setClosestCoordinateId((Integer) results.get(0).get("id"));
                photoValidationResults.setClosestCoordinateDistanceInKm((Double) results.get(0).get("distance_in_km"));

                if (photoValidationResults.getClosestCoordinateDistanceInKm() < .4)
                {
                    photoValidationResults.setErrorCode(ErrorCode.NO_ERROR.getCode());
                    logPhotoValidationResults(photoValidationResults);
                    return ErrorCode.NO_ERROR;
                }
            }

            //otherwise, check each image for geotags.  If any are in range, pass this.
            for (Blob blob : feedback.getBlobList())
            {
                PhotoValidationResults.Photo photo = new PhotoValidationResults.Photo();
                photo.setFileName(blob.getFileName());

                if (blob.getLatitude() != null && blob.getLongitude() != null)
                {
                    results = coordinateRepository.findClosestCoordinateFromLatLongAndProject(
                            blob.getLatitude(), blob.getLongitude(), feedback.getProjectId());

                    photo.setLatitude(blob.getLatitude());
                    photo.setLongitude(blob.getLongitude());
                    photo.setClosestCoordinateId((Integer) results.get(0).get("id"));
                    photo.setClosestCoordinateDistanceInKm((Double) results.get(0).get("distance_in_km"));

                    photoValidationResults.setAtLeastOneImageIsGeotagged(true);
                    photoValidationResults.addPhoto(photo);

                    if (photo.getClosestCoordinateDistanceInKm() < .4)
                    {
                        photoValidationResults.setErrorCode(ErrorCode.NO_ERROR.getCode());
                        logPhotoValidationResults(photoValidationResults);
                        return ErrorCode.NO_ERROR;
                    }
                }
            }

            if (!photoValidationResults.isAtLeastOneImageIsGeotagged())
            {
                photoValidationResults.setErrorCode(ErrorCode.PHOTO_NOT_GEOTAGGED.getCode());
                logPhotoValidationResults(photoValidationResults);
                return ErrorCode.PHOTO_NOT_GEOTAGGED;
            }

            photoValidationResults.setErrorCode(ErrorCode.PHOTO_NOT_IN_PROXIMITY.getCode());
            logPhotoValidationResults(photoValidationResults);
            return ErrorCode.PHOTO_NOT_IN_PROXIMITY;
        }
        catch (Exception e)
        {
            log.error("Error in validateUserOrPhotosAreInProximityToProject", e);
            return ErrorCode.INTERNAL_SERVER_ERROR;
        }
    }

    private void logPhotoValidationResults(PhotoValidationResults pvr)
    {
        log.info("--------------------  begin  --------------------");
        log.info("Project Id: " + pvr.getProjectId());
        log.info("Feedback Latitude: " + pvr.getFeedbackLatitude());
        log.info("Feedback Longitude: " + pvr.getFeedbackLongitude());
        log.info("Feedback Closest Coordinate Id: " + pvr.getClosestCoordinateId());
        log.info("Feedback Closest Coordinate Distance In Km: " + pvr.getClosestCoordinateDistanceInKm());
        log.info("Feedback At Least One Image Is Geotagged: " + pvr.isAtLeastOneImageIsGeotagged());
        log.info("Feedback ErrorCode: " + pvr.getErrorCode());

        for (PhotoValidationResults.Photo photo : pvr.getPhotoList())
        {
            log.info("- - - - - - - - - - - - - - - - - - - - - - - - - ");
            log.info("Photo Filename: " + photo.getFileName());
            log.info("Photo Latitude: " + photo.getLatitude());
            log.info("Photo Longitude: " + photo.getLongitude());
            log.info("Photo Closest Coordinate Id: " + photo.getClosestCoordinateId());
            log.info("Photo Closest Coordinate Distance In Km: " + photo.getClosestCoordinateDistanceInKm());
        }
        log.info("--------------------   end   --------------------");
    }
}
