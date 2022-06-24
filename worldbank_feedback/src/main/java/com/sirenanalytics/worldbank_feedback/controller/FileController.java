package com.sirenanalytics.worldbank_feedback.controller;

import com.sirenanalytics.worldbank_common.util.AccessKeyUtil;
import com.sirenanalytics.worldbank_feedback.model.entity.Blob;
import com.sirenanalytics.worldbank_feedback.model.entity.BlobBytes;
import com.sirenanalytics.worldbank_feedback.service.BlobService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

@ApiOperation(value = "Operations with WorldBank Feedback")
@RestController
@RequestMapping("/file")
public class FileController
{
    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    @Resource
    AccessKeyUtil accessKeyUtil;
    @Resource
    private BlobService blobService;

    @ApiOperation(value = "GET a single BlobBytes record.  This will only return the bytes.")
    @GetMapping("/{id}")
    ResponseEntity<byte[]> downloadFile(@PathVariable Long id)
    {
        Optional<Blob> optionalBlob = blobService.findById(id);
        if (optionalBlob.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<BlobBytes> optionalBlobBytes = blobService.findBytesById(id);
        if (optionalBlobBytes.isEmpty())
        {
            log.error("blob with id of {} found, but no matching blobBytes!", id);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Blob blob = optionalBlob.get();
        MediaType mediaType = getMediaType(blob);
        String fileName = blob.getFileName();

        return ResponseEntity.ok().contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(optionalBlobBytes.get().getBytes());
    }

    @ApiOperation(value = "GET a single BlobBytes record.  This will only return the bytes.")
    @GetMapping("/key/{idKey}")
    ResponseEntity<byte[]> downloadFile(@PathVariable String idKey)
    {
        /*--------------------------------------------------------------+
        |   This method expects a format of id|accessKey, with a pipe   |
        |   delimiter.  If this format fails, fail the request.         |
        +--------------------------------------------------------------*/
        IdKeyParts idKeyParts = new IdKeyParts(idKey);
        if (!idKeyParts.isValid)
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        if (!accessKeyUtil.isAccessKeyValid(idKeyParts.accessKey))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Long id = idKeyParts.id;

        Optional<Blob> optionalBlob = blobService.findById(id);
        if (optionalBlob.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Optional<BlobBytes> optionalBlobBytes = blobService.findBytesById(id);
        if (optionalBlobBytes.isEmpty())
        {
            log.error("blob with id of {} found, but no matching blobBytes!", id);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Blob blob = optionalBlob.get();
        MediaType mediaType = getMediaType(blob);
        String fileName = blob.getFileName();

        return ResponseEntity.ok().contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(optionalBlobBytes.get().getBytes());
    }

    private MediaType getMediaType(Blob blob)
    {
        try
        {
            String[] mimeTypeParts = blob.getMimeType().split("/");
            return new MediaType(mimeTypeParts[0], mimeTypeParts[1]);
        }
        catch (Exception e)
        {
            log.error("Unable to determine mediaType of blob with id of {} and mimeType of {}", blob.getId(), blob.getMimeType());
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    private class IdKeyParts
    {
        Long id;
        String accessKey;
        boolean isValid = false;

        private IdKeyParts(String idKey)
        {
            try
            {
                String[] parts = idKey.split("\\+");
                id = Long.parseLong(parts[0]);
                accessKey = parts[1];
                isValid = true;
            }
            catch (Exception e)
            {
                //do nothing.  isValid tells us if this parsed correctly
            }
        }
    }
}
