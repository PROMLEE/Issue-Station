package com.issuestation.Controller;

import com.issuestation.Dto.*;
import com.issuestation.Dto.Project.ProjectResponseDto;
import com.issuestation.Dto.UserDto.Id.IdDto;
import com.issuestation.Dto.UserDto.Id.IdResponseDto;
import com.issuestation.Dto.UserDto.Login.LoginDto;
import com.issuestation.Dto.UserDto.Login.LoginResponseDto;
import com.issuestation.Dto.UserDto.Nickname.NicknameDto;
import com.issuestation.Dto.UserDto.Nickname.NicknameResponseDto;
import com.issuestation.Dto.UserDto.Signup.SignupDto;
import com.issuestation.Dto.UserDto.Token.TokenResponseDto;
import com.issuestation.Service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/check")
    public ResponseDto<TokenResponseDto> getUserProjects(HttpServletRequest token) {
        // 토큰에서 "Bearer " 부분 제거
        var getToken = token.getHeader("Authorization");
        String jwtToken = getToken.replace("Bearer ", "");
        return authService.token(jwtToken);
    }
}