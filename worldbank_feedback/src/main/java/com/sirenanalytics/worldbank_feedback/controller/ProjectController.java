package com.sirenanalytics.worldbank_feedback.controller;

import com.sirenanalytics.worldbank_common.exception.NotFoundException;
import com.sirenanalytics.worldbank_feedback.mapper.ProjectMapper;
import com.sirenanalytics.worldbank_feedback.model.dto.ProjectDTO;
import com.sirenanalytics.worldbank_feedback.model.entity.Project;
import com.sirenanalytics.worldbank_feedback.service.ProjectService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApiOperation(value = "Operations with WorldBank Projects")
@RestController
@RequestMapping("/project")
public class ProjectController
{
    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    @Resource
    ProjectService projectService;

    @Resource
    ProjectMapper projectMapper;

    @ApiOperation(value = "GET all ProjectDTO records in paged segments")
    @GetMapping({"", "/kadaa/{kadaaKey}"})
    ResponseEntity<Page<ProjectDTO>> findAllPagedFilteredByKadaa(
            @PathVariable(required = false) String kadaaKey,
            @PageableDefault(page = 0, size = 10) Pageable pageable)
    {
        Page<Project> entityPage = projectService.findAll(pageable, kadaaKey);
        return new ResponseEntity<>(entityPage.map(projectMapper::entityToDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "GET all ProjectDTO records that match a partial word")
    @GetMapping({"/find", "/find/{partialWord}"})
    ResponseEntity<List<ProjectDTO>> findByPartialWord(
            @PathVariable(required = false) String partialWord,
            @RequestParam(name = "locale", required = false, defaultValue = "EnUs") String locale)
    {
        List<Project> projectList = projectService.findByPartialWord(partialWord, locale);
        return new ResponseEntity<>(projectMapper.entityToDTOList(projectList), HttpStatus.OK);
    }

    @ApiOperation(value = "POST a new Project record")
    @PostMapping("")
    ResponseEntity<Object> newItem(@Valid @RequestBody ProjectDTO dto)
    {
        return new ResponseEntity<>("This is currently disabled", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ApiOperation(value = "GET a single Project record based on id")
    @GetMapping("/{id}")
    ResponseEntity<ProjectDTO> findOne(@PathVariable Long id)
    {
        Project entity = projectService.findById(id).orElseThrow(() -> new NotFoundException(id));
        return new ResponseEntity<>(projectMapper.entityToDTO(entity), HttpStatus.OK);
    }

    @ApiOperation(value = "PUT a Project record with a given id")
    @PutMapping("/{id}")
    ResponseEntity<ProjectDTO> saveOrUpdate(@Valid @RequestBody ProjectDTO dto, @PathVariable Long id)
    {
        Optional<Project> optionalProject = projectService.findById(id);
        if (optionalProject.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        dto.setId(id);
        Project entity = optionalProject.get();
        projectMapper.dtoToEntity(dto, entity);

        entity = projectService.save(entity);
        return new ResponseEntity<>(projectMapper.entityToDTO(entity), HttpStatus.OK);
    }

    @ApiOperation(value = "GET the closest projects to a given lat / long")
    @GetMapping("/closest")
    ResponseEntity<List<Map<String, String>>> getClosestProjects(@RequestParam("latitude") double latitude,
                                                                 @RequestParam("longitude") double longitude)
    {
        return new ResponseEntity<>(projectService.findClosestProjectsToLatLong(latitude, longitude), HttpStatus.OK);
    }
}
