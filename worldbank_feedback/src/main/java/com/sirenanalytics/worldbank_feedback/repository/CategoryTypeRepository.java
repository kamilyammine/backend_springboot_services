package com.sirenanalytics.worldbank_feedback.repository;

import com.sirenanalytics.worldbank_feedback.model.entity.LookupCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryTypeRepository extends JpaRepository<LookupCategoryType, Long>
{
    List<LookupCategoryType> findByActive(boolean active);
}
