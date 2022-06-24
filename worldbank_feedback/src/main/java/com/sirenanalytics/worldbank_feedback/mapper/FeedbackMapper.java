package com.sirenanalytics.worldbank_feedback.mapper;

import com.sirenanalytics.worldbank_feedback.model.dto.BlobDTO;
import com.sirenanalytics.worldbank_feedback.model.dto.FeedbackDTO;
import com.sirenanalytics.worldbank_feedback.model.entity.Blob;
import com.sirenanalytics.worldbank_feedback.model.entity.BlobBytes;
import com.sirenanalytics.worldbank_feedback.model.entity.Feedback;
import com.sirenanalytics.worldbank_feedback.model.simple.ClientParsedImageLatLongData;
import com.sirenanalytics.worldbank_feedback.util.ImageUtil;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class FeedbackMapper
{
    private static final Logger log = LoggerFactory.getLogger(FeedbackMapper.class);

    @Resource
    ImageUtil imageUtil;

    public abstract Feedback dtoToEntity(FeedbackDTO feedbackDTO);

    @Mapping(source = "blobList", target = "blobDTOList")
    public abstract FeedbackDTO entityToDTO(Feedback feedback);

    public abstract BlobDTO entityToDTO(Blob blob);

    @AfterMapping
    protected void mapMultipartFilesToBlob(FeedbackDTO dto, @MappingTarget Feedback feedback)
    {
        List<Blob> blobList = new ArrayList<>();
        List<ClientParsedImageLatLongData> imageLatLongDataList = new ArrayList<>();

        if (dto.getImageLatLongData() != null)
        {
            for (String fileLatLongInfo : dto.getImageLatLongData())
                imageLatLongDataList.add(new ClientParsedImageLatLongData(fileLatLongInfo.split("\\+")));
        }

        if (dto.getFileList() != null)
        {
            try
            {
                for (MultipartFile mpFile : dto.getFileList())
                {
                    /*------------------------------------------------------+
                    |   A few categories don't require an image.  But if    |
                    |   the field is present, an mpFile of size 0 will      |
                    |   be there.  Ignore it.                               |
                    +------------------------------------------------------*/
                    if (mpFile.getSize() < 1)
                        continue;

                    BlobBytes blobBytes = new BlobBytes();
                    blobBytes.setBytes(mpFile.getBytes());

                    Blob blob = new Blob();
                    blob.setFileName(mpFile.getOriginalFilename());
                    blob.setMimeType(mpFile.getContentType());
                    blob.setBlobBytes(blobBytes);

                    Optional<ClientParsedImageLatLongData> optionalClientParsedImageLatLongData = imageLatLongDataList.stream()
                            .filter(item -> item.getFilename().equals(blob.getFileName()))
                            .findFirst();

                    imageUtil.populateLatLongFromImageMetadata(blob, optionalClientParsedImageLatLongData.orElse(null));

                    blobList.add(blob);
                }
            }
            catch (IOException e)
            {
                log.error("Unable to set Bytes in BlobBytesMapper", e);
            }
        }
        feedback.setBlobList(blobList);
    }
}
