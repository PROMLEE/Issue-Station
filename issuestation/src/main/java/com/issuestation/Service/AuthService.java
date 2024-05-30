package com.issuestation.Service;

import com.issuestation.Dto.ResponseDto;
import com.issuestation.Dto.UserDto.Id.IdDto;
import com.issuestation.Dto.UserDto.Id.IdResponseDto;
import com.issuestation.Dto.UserDto.Login.LoginDto;
import com.issuestation.Dto.UserDto.Login.LoginResponseDto;
import com.issuestation.Dto.UserDto.Nickname.NicknameDto;
import com.issuestation.Dto.UserDto.Nickname.NicknameResponseDto;
import com.issuestation.Dto.UserDto.Signup.SignupDto;
import com.issuestation.Dto.UserDto.Token.TokenResponseDto;
import com.issuestation.Repository.UserRepository;
import com.issuestation.Security.TokenProvider;
import com.issuestation.Entity.User;
import com.issuestation.apiPayload.code.status.ErrorStatus;
import com.issuestation.apiPayload.exception.handler.TempHandler;
import com.issuestation.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired private UserRepository userRepository;
    @Autowired private TokenProvider tokenProvider;

    public ResponseDto<String> signUp(SignupDto signupDto) {
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

        String token = tokenProvider.createJwt(userEntity.getId()); //Long Id로 JWT생성하도록 수정
        int exprTime = 3600000; //한 시간

        LoginResponseDto loginResponseDto = new LoginResponseDto(token, exprTime, userEntity);
        return ResponseDto.setSuccess("Login Success", loginResponseDto);

    }

    public ResponseDto<NicknameResponseDto> nickname(NicknameDto nicknameDto) {
        // Nickname 중복 확인
        Optional<User> existingUserByNickname = userRepository.findByNickname(nicknameDto.getNickname());
        boolean isDuplicate = existingUserByNickname.isPresent(); // 중복된 닉네임이 있으면 true, 없으면 false

        NicknameResponseDto response = new NicknameResponseDto(isDuplicate);
        if(isDuplicate) {
            // 중복된 닉네임이 있을 경우
            return ResponseDto.setFailed("Nickname already exists", response);
        } else {
            // 중복된 닉네임이 없을 경우
            return ResponseDto.setSuccess("Nickname not duplicate", response);
        }
    }
    public ResponseDto<IdResponseDto> id(IdDto idDto) {
        // Id 중복 확인
        Optional<User> existingUserById = userRepository.findByLoginId(idDto.getId());
        boolean isDuplicate = existingUserById.isPresent(); // 중복된 아이디가 있으면 true, 없으면 false

        IdResponseDto response = new IdResponseDto(isDuplicate);
        if(isDuplicate) {
            // 중복된 아이디가 있을 경우
            return ResponseDto.setFailed("Id already exists", response);
        } else {
            // 중복된 아이디가 없을 경우
            return ResponseDto.setSuccess("Id not duplicate", response);
        }
    }

    public ResponseDto<TokenResponseDto> token(String token) {
        long userId;
        try {
            userId = Long.parseLong(tokenProvider.validateJwt(token));
        } catch (Exception e) {
            throw new TempHandler(ErrorStatus.TEMP_EXCEPTION);
        }
        User userEntity = userRepository.findById(userId).get(); // 사용자 id을 가져옴
        TokenResponseDto tokenResponseDto = new TokenResponseDto(userId, userEntity.getLoginId(), userEntity.getNickname());
        return ResponseDto.setSuccess("Token is valid", tokenResponseDto);
    }
}
