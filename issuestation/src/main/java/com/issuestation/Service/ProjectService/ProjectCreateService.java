package com.issuestation.Service.ProjectService;

import com.issuestation.Dto.ProjectRequestDto;
import com.issuestation.Entity.Project;

public interface ProjectCreateService {
    Project joinProject(ProjectRequestDto.JoinProjectCreateRequestDto requset);
}
