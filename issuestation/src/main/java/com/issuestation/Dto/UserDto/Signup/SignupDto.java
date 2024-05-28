package com.issuestation.Dto.UserDto.Signup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    //private int userid;
    private String loginId;
    private String nickname;
    private String loginPw;
}
