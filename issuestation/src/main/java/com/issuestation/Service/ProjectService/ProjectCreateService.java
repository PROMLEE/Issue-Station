package com.issuestation.Service.ProjectService;

import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Entity.Project;

public interface ProjectCreateService {
    Project joinProject(ProjectRequestDto.JoinProjectCreateRequestDto requset);
}
