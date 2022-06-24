package com.sirenanalytics.worldbank_feedback.service.impl;

import com.sirenanalytics.worldbank_feedback.model.entity.Project;
import com.sirenanalytics.worldbank_feedback.repository.ProjectRepository;
import com.sirenanalytics.worldbank_feedback.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService
{
    @Resource
    ProjectRepository projectRepository;

    @Override
    public Page<Project> findAll(Pageable pageable, String kadaaKey)
    {
        if (kadaaKey == null)
            return projectRepository.findAll(pageable);

        return projectRepository.findByKadaaKey(pageable, kadaaKey);
    }

    @Override
    public List<Project> findByPartialWord(String partialWord, String locale)
    {
        if (StringUtils.isBlank(partialWord))
            return projectRepository.findAll();

        if (StringUtils.isBlank(locale) || "EnUs".equalsIgnoreCase(locale))
            return projectRepository.findByDescriptionEnUsLikeIgnoreCase("%".concat(partialWord).concat("%"));

        return projectRepository.findByDescriptionArLbLikeIgnoreCase("%".concat(partialWord).concat("%"));
    }

    @Override
    public Optional<Project> findById(Long id)
    {
        return projectRepository.findById(id);
    }

    @Override
    public Project save(Project project)
    {
        return projectRepository.save(project);
    }

    @Override
    public List<Map<String, String>> findClosestProjectsToLatLong(double latitude, double longitude)
    {
        return projectRepository.findClosestProjectsToLatLong(latitude, longitude);
    }
}
