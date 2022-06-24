package com.sirenanalytics.worldbank_feedback.service;

import com.sirenanalytics.worldbank_feedback.model.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProjectService
{
    Page<Project> findAll(Pageable pageable, String kadaaKey);

    List<Project> findByPartialWord(String partialWord, String locale);

    Optional<Project> findById(Long id);

    Project save(Project project);

    List<Map<String, String>> findClosestProjectsToLatLong(double latitude, double longitude);
}
