package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Assignee;
import com.issuestation.Entity.Team;
import com.issuestation.Entity.enums.Role;
import com.issuestation.Repository.AssigneeRepository;
import com.issuestation.Repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssigneeAlgoService {
    private final AssigneeRepository assigneeRepository;
    private final TeamRepository teamRepository;

    public List<IssueResponseDto.AssigneeAlgoResponseDto> getDevelopersWithLeastAssignments(Long projectId) {
        // 프로젝트에 속한 Assignee 엔티티 가져오기
        List<Assignee> assignees = assigneeRepository.findByIssueProjectId(projectId);

        // 프로젝트에 속한 DEVELOPER 역할의 팀원 가져오기
        List<Team> developers = teamRepository.findByProjectIdAndRole(projectId, Role.DEVELOPER);

        // 각 개발자에게 할당된 프로젝트의 이슈 개수 계산
        Map<Long, Long> assignmentCount = assignees.stream()
                .filter(assignee -> assignee.getIssue().getProject().getId().equals(projectId)) // 프로젝트에 속한 이슈 필터링
                .collect(Collectors.groupingBy(assignee -> assignee.getUser().getId(), Collectors.counting()));

        // 할당된 이슈 개수 기준으로 개발자 정렬
        List<Team> sortedDevelopers = developers.stream()
                .sorted(Comparator.comparing(team -> assignmentCount.getOrDefault(team.getUser().getId(), 0L)))
                .collect(Collectors.toList());

        // DTO로 변환
        return sortedDevelopers.stream().map(team -> {
            IssueResponseDto.AssigneeAlgoResponseDto dto = new IssueResponseDto.AssigneeAlgoResponseDto();
            dto.setId(team.getId());
            dto.setNickname(team.getUser().getNickname()); // User 엔티티의 필드 사용
            dto.setRole(team.getRole());
            dto.setAssignedIssueCount(assignmentCount.getOrDefault(team.getUser().getId(), 0L));
            return dto;
        }).collect(Collectors.toList());
    }

}
