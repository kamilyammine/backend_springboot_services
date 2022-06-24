package com.sirenanalytics.worldbank_feedback.mapper;

import com.sirenanalytics.worldbank_feedback.model.simple.ContactsNeedingEmail;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ContactsNeedingEmailMapper
{
    public List<ContactsNeedingEmail> mapToSimpleModelList(List<Map<String, Object>> mapList)
    {
        if (mapList == null)
            return null;

        List<ContactsNeedingEmail> list = new ArrayList<>(mapList.size());
        for (Map<String, Object> map : mapList)
            list.add(mapToSimpleModel(map));

        return list;
    }

    public ContactsNeedingEmail mapToSimpleModel(Map<String, Object> map)
    {
        if (map == null)
            return null;

        ContactsNeedingEmail contactsNeedingEmail = new ContactsNeedingEmail();

        contactsNeedingEmail.setFeedbackId(Long.valueOf((Integer) map.get("feedback_id")));
        contactsNeedingEmail.setContactId(Long.valueOf((Integer) map.get("contact_id")));
        contactsNeedingEmail.setComment(toStringSafely(map.get("comment")));
        contactsNeedingEmail.setGender(toStringSafely(map.get("gender")));
        contactsNeedingEmail.setAgeRange(toStringSafely(map.get("age_range")));
        contactsNeedingEmail.setUserType(toStringSafely(map.get("user_type")));
        contactsNeedingEmail.setUserTypeOther(toStringSafely(map.get("user_type_other")));
        contactsNeedingEmail.setFeedbackFirstName(toStringSafely(map.get("feedback_first_name")));
        contactsNeedingEmail.setFeedbackLastName(toStringSafely(map.get("feedback_last_name")));
        contactsNeedingEmail.setFeedbackAddress(toStringSafely(map.get("feedback_address")));
        contactsNeedingEmail.setFeedbackPhone(toStringSafely(map.get("feedback_phone")));
        contactsNeedingEmail.setFeedbackEmail(toStringSafely(map.get("feedback_email")));
        contactsNeedingEmail.setRoadLabelEnUs(toStringSafely(map.get("road_label_en_us")));
        contactsNeedingEmail.setRoadLabelArLb(toStringSafely(map.get("road_label_ar_lb")));
        contactsNeedingEmail.setRoadNameEnUs(toStringSafely(map.get("road_name_en_us")));
        contactsNeedingEmail.setRoadNameArLb(toStringSafely(map.get("road_name_ar_lb")));
        contactsNeedingEmail.setKadaaNameEnUs(toStringSafely(map.get("kadaa_name_en_us")));
        contactsNeedingEmail.setKadaaNameArLb(toStringSafely(map.get("kadaa_name_ar_lb")));
        contactsNeedingEmail.setContactFirstName(toStringSafely(map.get("contact_first_name")));
        contactsNeedingEmail.setContactLastName(toStringSafely(map.get("contact_last_name")));
        contactsNeedingEmail.setContactEmail(toStringSafely(map.get("contact_email")));
        contactsNeedingEmail.setCategoryTypeNameEnUs(toStringSafely(map.get("category_type_name_en_us")));
        contactsNeedingEmail.setCategoryTypeNameArlb(toStringSafely(map.get("category_type_name_ar_lb")));
        contactsNeedingEmail.setCategoryItemNameEnUs(toStringSafely(map.get("category_item_name_en_us")));
        contactsNeedingEmail.setCategoryItemNameArlb(toStringSafely(map.get("category_item_name_ar_lb")));

        return contactsNeedingEmail;
    }

    private String toStringSafely(Object obj)
    {
        return (obj == null) ? "Not Provided" : obj.toString();
    }
}
