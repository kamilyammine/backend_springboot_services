package com.sirenanalytics.worldbank_feedback.controller;

import com.sirenanalytics.worldbank_feedback.mapper.FeedbackMapper;
import com.sirenanalytics.worldbank_feedback.model.dto.FeedbackDTO;
import com.sirenanalytics.worldbank_feedback.model.entity.Feedback;
import com.sirenanalytics.worldbank_feedback.service.FeedbackService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@ApiOperation(value = "Operations with WorldBank Feedback")
@RestController
@RequestMapping("/feedback")
public class FeedbackController
{
    private static final Logger log = LoggerFactory.getLogger(FeedbackController.class);
    static List<String> allowedFileExtensionList = Arrays.asList("jpg", "gif", "png", "mp3", "mp4", "wav", "avi", "mkv");

    @Resource
    FeedbackService feedbackService;

    @Resource
    FeedbackMapper feedbackMapper;

    @ApiOperation(value = "POST a new Feedback record.  This will also include a BlobBytes if an attachment is present")
    @PostMapping("")
    ResponseEntity<Object> newItem(@Valid FeedbackDTO dto)
    {
        log.info("\n\n>>> feedback POST begin\n\n");
        if (dto.getFileList() != null)
        {
            for (MultipartFile multipartFile : dto.getFileList())
            {
                String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                if (StringUtils.isNotBlank(extension) && !allowedFileExtensionList.contains(extension))
                    return new ResponseEntity<>("Only jpg, gif, png, mp3, mp4, wav, avi, mkv files allowed", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            }
        }

        //set primary key to null.  A POST is a new record.
        dto.setId(null);
        Feedback feedback = feedbackMapper.dtoToEntity(dto);
/* - DB 2021-12-29 - Carole asked that we temporarily disable proximity checking
        ErrorCode errorCode = feedbackService.validateUserOrPhotosAreInProximityToProject(feedback);
        if (!errorCode.equals(ErrorCode.NO_ERROR))
        {
            return new ResponseEntity<>(new ResponseMessage(errorCode), HttpStatus.NOT_ACCEPTABLE);
        }
*/
        feedback = feedbackService.save(feedback);

        dto = feedbackMapper.entityToDTO(feedback);
        log.info("\n\n>>> feedback POST complete\n\n");
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
