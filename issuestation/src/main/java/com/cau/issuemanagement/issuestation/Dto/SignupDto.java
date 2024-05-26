package com.cau.issuemanagement.issuestation.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    private int userid;
    private String id;
    private String nickname;
    private String password;
    private String passwordCheck;
}
