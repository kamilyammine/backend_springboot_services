package com.sirenanalytics.worldbank_feedback.service.impl;

import com.sirenanalytics.worldbank_feedback.model.simple.LookupItem;
import com.sirenanalytics.worldbank_feedback.model.simple.LookupItemPhoto;
import com.sirenanalytics.worldbank_feedback.repository.CategoryItemRepository;
import com.sirenanalytics.worldbank_feedback.repository.CategoryTypeRepository;
import com.sirenanalytics.worldbank_feedback.repository.KadaaRepository;
import com.sirenanalytics.worldbank_feedback.service.LookupService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LookupServiceImpl implements LookupService
{
    private static final List<LookupItem> categoryTypeList = new ArrayList<>();
    private static final Map<String, List<LookupItemPhoto>> categoryItemMapList = new HashMap<>();
    private static final List<LookupItem> kadaaList = new ArrayList<>();

    @Resource
    CategoryTypeRepository categoryTypeRepository;
    @Resource
    CategoryItemRepository categoryItemRepository;
    @Resource
    KadaaRepository kadaaRepository;


    @Override
    public List<LookupItem> getCategoryTypes()
    {
        return categoryTypeList;
    }

    @Override
    public Map<String, List<LookupItemPhoto>> getCategoryItems(List<String> keyList)
    {
        /*------------------------------------------------------+
        |   No filter has been specified.  Return everything    |
        +------------------------------------------------------*/
        if (keyList == null || keyList.size() < 1)
            return categoryItemMapList;

        /*------------------------------------------------------+
        |   Return only the keys specified                      |
        +------------------------------------------------------*/
        return keyList.stream().collect(Collectors.toMap(key -> key, categoryItemMapList::get, (a, b) -> b));
    }

    @Override
    public List<LookupItem> getKadaaList()
    {
        return kadaaList;
    }

    @EventListener(ApplicationReadyEvent.class)
    protected void initializeLookupsOnSystemStart()
    {
        categoryTypeRepository.findByActive(true).stream().map(categoryType ->
                new LookupItem(categoryType.getKey(), categoryType.getNameEnUs(), categoryType.getNameArLb(),
                        categoryType.getDescriptionEnUs(), categoryType.getDescriptionEnUs())).forEach(categoryTypeList::add);

        categoryItemRepository.findByActive(true).forEach(categoryItem ->
        {
            List<LookupItemPhoto> lookupList = categoryItemMapList.computeIfAbsent(categoryItem.getCategoryTypeKey(), k -> new ArrayList<>());
            lookupList.add(new LookupItemPhoto(categoryItem.getKey(), categoryItem.getNameEnUs(), categoryItem.getNameArLb(),
                    categoryItem.getDescriptionEnUs(), categoryItem.getDescriptionArLb(), categoryItem.getPhotoRequired()));
        });

        kadaaRepository.findByActiveOrderByNameEnUs(true).stream().map(kadaa ->
                new LookupItem(kadaa.getKey(), kadaa.getNameEnUs(), kadaa.getNameArLb(),
                        kadaa.getDescriptionEnUs(), kadaa.getDescriptionEnUs())).forEach(kadaaList::add);
    }
}
