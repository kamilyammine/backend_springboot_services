package com.sirenanalytics.worldbank_feedback.service;

import com.sirenanalytics.worldbank_feedback.model.simple.LookupItem;
import com.sirenanalytics.worldbank_feedback.model.simple.LookupItemPhoto;

import java.util.List;
import java.util.Map;

public interface LookupService
{
    List<LookupItem> getCategoryTypes();

    Map<String, List<LookupItemPhoto>> getCategoryItems(List<String> keyList);

    List<LookupItem> getKadaaList();
}
