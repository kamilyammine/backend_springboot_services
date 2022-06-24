package com.sirenanalytics.worldbank_feedback.mapper;

import com.sirenanalytics.worldbank_feedback.model.dto.ProjectDTO;
import com.sirenanalytics.worldbank_feedback.model.entity.Project;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProjectMapper
{
    public abstract Project dtoToEntity(ProjectDTO projectDTO);

    public abstract ProjectDTO entityToDTO(Project project);

    public abstract List<ProjectDTO> entityToDTOList(List<Project> entityList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void dtoToEntity(ProjectDTO dto, @MappingTarget Project entity);
}
