package com.issuestation.Controller;

import com.issuestation.Dto.*;
import com.issuestation.Dto.UserDto.Id.IdDto;
import com.issuestation.Dto.UserDto.Id.IdResponseDto;
import com.issuestation.Dto.UserDto.Login.LoginDto;
import com.issuestation.Dto.UserDto.Login.LoginResponseDto;
import com.issuestation.Dto.UserDto.Nickname.NicknameDto;
import com.issuestation.Dto.UserDto.Nickname.NicknameResponseDto;
import com.issuestation.Dto.UserDto.Signup.SignupDto;
import com.issuestation.Service.AuthService;
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

    @PostMapping("/nickname")
    public ResponseDto<NicknameResponseDto> nickname(@RequestBody NicknameDto requestBody) {
        ResponseDto<NicknameResponseDto> result = authService.nickname(requestBody);
        System.out.println(result);
        return result;
    }

    @PostMapping("/id")
    public ResponseDto<IdResponseDto> id(@RequestBody IdDto requestBody) {
        ResponseDto<IdResponseDto> result = authService.id(requestBody);
        System.out.println(result);
        return result;
    }

}