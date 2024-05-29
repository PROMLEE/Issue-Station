package com.issuestation.Service.ProjectService;

import com.issuestation.Entity.Project;
import com.issuestation.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectSearchService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectSearchService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> searchProjectsByName(String name) {
        return projectRepository.findByNameContainingIgnoreCase(name);
    }
    public List<Project> searchAllProjects() {
        return projectRepository.findAll();
    }
}
