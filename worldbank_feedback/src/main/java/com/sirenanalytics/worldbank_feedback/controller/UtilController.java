package com.sirenanalytics.worldbank_feedback.controller;

import com.sirenanalytics.worldbank_common.model.entity.XAccessKey;
import com.sirenanalytics.worldbank_common.util.AccessKeyUtil;
import com.sirenanalytics.worldbank_feedback.model.dto.FeedbackDTO;
import com.sirenanalytics.worldbank_feedback.util.EmailUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@ApiOperation(value = "Utility Operations")
@RestController
@RequestMapping("/util")
public class UtilController
{
    @Resource
    EmailUtil emailUtil;

    @Resource
    AccessKeyUtil accessKeyUtil;

    @GetMapping("/process")
    ResponseEntity<ResponseEntity<Object>> processSubmittedFeedbacks()
    {
        emailUtil.processSubmittedFeedbacks();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/email/{email}")
    ResponseEntity<FeedbackDTO> testEmail(@PathVariable String email)
    {
        emailUtil.sendTestMessage(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/keys")
    ResponseEntity<List<XAccessKey>> getValidAccessKeys()
    {
        List<XAccessKey> accessKeyList = accessKeyUtil.getAccessKeyList();
        return new ResponseEntity<>(accessKeyList, HttpStatus.OK);
    }
}
