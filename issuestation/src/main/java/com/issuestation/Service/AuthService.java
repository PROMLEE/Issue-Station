package com.issuestation.Service;

import com.issuestation.Dto.ResponseDto;
import com.issuestation.Repository.UserRepository;
import com.issuestation.Security.TokenProvider;
import com.issuestation.Dto.LoginDto;
import com.issuestation.Dto.LoginResponseDto;
import com.issuestation.Dto.SignupDto;
import com.issuestation.Entity.User;
import com.issuestation.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired private UserRepository userRepository;
    @Autowired private TokenProvider tokenProvider;

    public ResponseDto<String> signUp(SignupDto signupDto) {
        // ID 중복 확인
        Optional<User> existingUserById = userRepository.findByLoginId(signupDto.getLoginId());
        if (existingUserById.isPresent()) {
            return ResponseDto.setFailed("ID already exists", null);
        }

        // Nickname 중복 확인
        Optional<User> existingUserByNickname = userRepository.findByNickname(signupDto.getNickname());
        if (existingUserByNickname.isPresent()) {
            return ResponseDto.setFailed("Nickname already exists", null);
        }

        // 유저 생성 및 저장
        User userEntity = UserConverter.toLoginEntity(signupDto);
        userRepository.save(userEntity);
        return ResponseDto.setSuccess("User registered successfully", null);
    }

    public ResponseDto<LoginResponseDto> login(LoginDto dto) {
        String id = dto.getId();
        String password = dto.getPw();
        try {
            boolean existed = userRepository.existsByLoginIdAndLoginPw(id, password);

            if(!existed) {
                return ResponseDto.setFailed("Login Info is Wrong", null);
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("Database Error", null);
        }

        User userEntity = null;
        try {
            // 값이 존재하면
            userEntity = userRepository.findByLoginId(id).get(); // 사용자 id을 가져옴
        } catch(Exception e) {
            return ResponseDto.setFailed("Database Error", null);
        }

        //userEntity.setPw("");

        String token = tokenProvider.createJwt(id);
        int exprTime = 3600000; //한 시간

        LoginResponseDto loginResponseDto = new LoginResponseDto(token, exprTime, userEntity);
        return ResponseDto.setSuccess("Login Success", loginResponseDto);

    }
}
