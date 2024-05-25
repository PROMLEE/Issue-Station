package com.cau.issuemanagement.issuestation.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    //private int userid; 내부적으로만 사용하는 것이니까 DTO에서 제외
    private String id;
    private String nickname;
    private String password;
}
