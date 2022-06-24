package com.sirenanalytics.worldbank_feedback.controller;

import com.sirenanalytics.worldbank_common.exception.NotFoundException;
import com.sirenanalytics.worldbank_feedback.mapper.FeedbackMapper;
import com.sirenanalytics.worldbank_feedback.model.dto.FeedbackDTO;
import com.sirenanalytics.worldbank_feedback.model.entity.Feedback;
import com.sirenanalytics.worldbank_feedback.service.FeedbackService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@ApiOperation(value = "Operations with WorldBank Feedback")
@RestController
@RequestMapping("/secure/feedback")
public class FeedbackSecureController
{
    private static final Logger log = LoggerFactory.getLogger(FeedbackController.class);
    static List<String> allowedPhotoFileExtensionList = Arrays.asList("jpg", "gif", "png", "mp3", "mp4", "wav", "avi", "mkv");

    @Resource
    FeedbackService feedbackService;

    @Resource
    FeedbackMapper feedbackMapper;

    @ApiOperation(value = "GET all FeedbackDTO records in paged segments")
    @GetMapping("")
    ResponseEntity<Page<FeedbackDTO>> findAllPaged(@PageableDefault(page = 0, size = 10) Pageable pageable)
    {
        Page<Feedback> entityPage = feedbackService.findAll(pageable);
        return new ResponseEntity<>(entityPage.map(feedbackMapper::entityToDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "GET a single Feedback record based on id")
    @GetMapping("/{id}")
    ResponseEntity<FeedbackDTO> findOne(@PathVariable Long id)
    {
        Feedback entity = feedbackService.findById(id).orElseThrow(() -> new NotFoundException(id));
        FeedbackDTO dto = feedbackMapper.entityToDTO(entity);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
