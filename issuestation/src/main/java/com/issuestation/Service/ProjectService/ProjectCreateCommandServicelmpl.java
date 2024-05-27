package com.issuestation.Service.ProjectService;

import com.issuestation.Dto.ProjectCreateRequestDto;
import com.issuestation.Entity.ProjectEntity;
import com.issuestation.Repository.ProjectRepository;
import com.issuestation.converter.ProjectCreateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectCreateCommandServicelmpl implements ProjectCreateCommandService{
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public ProjectEntity joinProject(ProjectCreateRequestDto.JoinProjectCreateRequestDto requset) {
        ProjectEntity newProject = ProjectCreateConverter.toProjectEntity(requset);

        return projectRepository.save(newProject);
    }
}
