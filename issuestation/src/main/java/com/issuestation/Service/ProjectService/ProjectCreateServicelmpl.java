package com.issuestation.Service.ProjectService;

import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Entity.Project;
import com.issuestation.Repository.ProjectRepository;
import com.issuestation.converter.ProjectConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectCreateServicelmpl implements ProjectCreateService {

    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public Project joinProject(ProjectRequestDto.JoinProjectCreateRequestDto requset) {
        Project newProject = ProjectConverter.toProjectEntity(requset);
        return projectRepository.save(newProject);
    }
}
