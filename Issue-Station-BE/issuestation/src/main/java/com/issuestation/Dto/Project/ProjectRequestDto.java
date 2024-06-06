package com.issuestation.Dto.Project;

import com.issuestation.Entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ProjectRequestDto {

    @Getter
    public static class JoinProjectCreateRequestDto {
        String name;
        String description;
        Boolean isPrivate;
//        String thumbnaillink;
    }

    @Getter
    public static class JoinTeamRequestDto {
        //        Long projectId;  요거는 파라미터로 받아야함
        String nickname;
        Boolean isAdmin;
        Role role;
    }

}
