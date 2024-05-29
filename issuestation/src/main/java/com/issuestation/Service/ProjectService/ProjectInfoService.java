package com.issuestation.Service.ProjectService;

import com.issuestation.Entity.Project;
import com.issuestation.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectInfoService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectInfoService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project projectInfo(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + id));
    }
}
