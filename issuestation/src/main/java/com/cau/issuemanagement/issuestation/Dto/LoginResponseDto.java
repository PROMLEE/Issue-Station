package com.cau.issuemanagement.issuestation.Dto;


import com.cau.issuemanagement.issuestation.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String token;
    private int exprTime;
    private UserEntity user;
}
