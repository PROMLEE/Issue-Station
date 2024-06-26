package com.issuestation.Service.Temp;

import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Dto.ResponseDto;
import com.issuestation.Repository.ProjectRepository;
import com.issuestation.Entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired private ProjectRepository projectRepository;

    public ResponseDto<?> createProject(ProjectRequestDto projectCreateDto, String userId) {
        // 프로젝트 생성 로직 구현
        Project project = new Project(); //객체 하나 만들어서 여기에 담는거임
//        project.setPname(projectCreateDto.getPname());
//        project.setIsprivate(projectCreateDto.isIsprivate());
//        project.setDescription(projectCreateDto.getDescription());
//        project.setThumbnaillink(projectCreateDto.getThumbnaillink()); //입력받는것들은 다 Dto
//        project.setInitdate(new Date()); //Date 컬럼은 전달받는게 아니라서 여기서 만들어줘야함

        projectRepository.save(project); //그리고 담은 객체를 db에 저장하는거고 ㅇㅋ?
        return ResponseDto.setSuccess("Project created successfully", project);
    }
}
