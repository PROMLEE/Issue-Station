package com.cau.issuemanagement.issuestation.Controller;

import com.cau.issuemanagement.issuestation.Dto.ResponseDto;
import com.cau.issuemanagement.issuestation.Dto.SignupDto;
import com.cau.issuemanagement.issuestation.Dto.SignupResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/signUp")
    public ResponseDto<SignupResponseDto> signUp(@RequestBody SignupDto requestBody) {
        System.out.println(requestBody.toString());
        return null;
    }
}