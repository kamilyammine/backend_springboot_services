package com.sirenanalytics.worldbank_feedback.repository;

import com.sirenanalytics.worldbank_feedback.model.entity.LookupCategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemRepository extends JpaRepository<LookupCategoryItem, Long>
{
    List<LookupCategoryItem> findByActive(boolean active);
}
