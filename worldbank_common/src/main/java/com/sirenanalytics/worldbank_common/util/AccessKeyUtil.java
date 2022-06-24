package com.sirenanalytics.worldbank_common.util;

import com.sirenanalytics.worldbank_common.model.entity.XAccessKey;
import com.sirenanalytics.worldbank_common.repository.CommonAccessKeyRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccessKeyUtil
{
    private List<XAccessKey> accessKeyList = new ArrayList<>();

    @Resource
    private CommonAccessKeyRepository commonAccessKeyRepository;

    public boolean isAccessKeyValid(String accessKey)
    {
        /*----------------------------------------------------------+
        |   Check if the accessKey passed in is in the valid list.  |
        |   If not, refresh and try again.  If that fails, then     |
        |   the access key is bad.                                  |
        +----------------------------------------------------------*/
        if (doesAccessKeyListContainKey(accessKey))
            return true;

        refreshKeys();
        return doesAccessKeyListContainKey(accessKey);
    }

    public List<XAccessKey> getAccessKeyList()
    {
        refreshKeys();
        return accessKeyList;
    }

    private boolean doesAccessKeyListContainKey(String accessKey)
    {
        return accessKeyList.stream().anyMatch(xAccessKey -> accessKey.equals(xAccessKey.getKey()));
    }

    private void refreshKeys()
    {
        accessKeyList = commonAccessKeyRepository.findAllOrderByCreateDateTimeDesc();
    }
}
