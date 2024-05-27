package com.issuestation.Service.ProjectService;

import com.issuestation.Dto.ProjectCreateRequestDto;
import com.issuestation.Entity.ProjectEntity;

public interface ProjectCreateCommandService {
    ProjectEntity joinProject(ProjectCreateRequestDto.JoinProjectCreateRequestDto requset);
}
