package com.cau.issuemanagement.issuestation.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponseDto {
    private String token;
    private int exprTime; //token의 유효시간
}
