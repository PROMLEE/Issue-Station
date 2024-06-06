package com.issuestation.converter;


import com.issuestation.Dto.UserDto.Signup.SignupDto;

import com.issuestation.Entity.User;

public class UserConverter {
//    public static LoginDto toLoginDto(User user) {
//        return ProjectResponseDto.JoinProjectCreateResponseDto.builder()
//                .pid(project.getPid())
//                .build();
//    }

    public static User toLoginEntity(SignupDto request) {
        return User.builder()
                .loginId(request.getLoginId())
                .loginPw(request.getLoginPw())
                .nickname(request.getNickname())
                .build();
    }
}