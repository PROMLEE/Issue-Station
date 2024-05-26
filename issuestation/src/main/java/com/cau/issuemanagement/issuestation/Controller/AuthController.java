package com.cau.issuemanagement.issuestation.Controller;

import com.cau.issuemanagement.issuestation.Dto.*;
import com.cau.issuemanagement.issuestation.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthController {
    @Autowired AuthService authService;
    @PostMapping("/signup")
    public ResponseDto<?> signUp(@RequestBody SignupDto requestBody) {
        ResponseDto<?> result = authService.signUp(requestBody);
        System.out.println(result.toString());
        return result;
    }
    @PostMapping("/login")
    public ResponseDto<LoginResponseDto> login(@RequestBody LoginDto requestBody) {
        ResponseDto<LoginResponseDto> result = authService.login(requestBody);
        System.out.println(result.toString());
        return result;
    }

}