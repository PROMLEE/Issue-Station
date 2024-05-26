package com.cau.issuemanagement.issuestation.Service;


import com.cau.issuemanagement.issuestation.Dto.ResponseDto;
import com.cau.issuemanagement.issuestation.Dto.SignupDto;
import com.cau.issuemanagement.issuestation.Entity.UserEntity;
import com.cau.issuemanagement.issuestation.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public ResponseDto<String> signUp(SignupDto signupDto) {
        // ID 중복 확인
        Optional<UserEntity> existingUserById = userRepository.findById(signupDto.getId());
        if (existingUserById.isPresent()) {
            return ResponseDto.setFailed("ID already exists", null);
        }

        // Nickname 중복 확인
        Optional<UserEntity> existingUserByNickname = userRepository.findByNickname(signupDto.getNickname());
        if (existingUserByNickname.isPresent()) {
            return ResponseDto.setFailed("Nickname already exists", null);
        }

        // 유저 생성 및 저장
        UserEntity userEntity = new UserEntity(signupDto);
        userRepository.save(userEntity);
        return ResponseDto.setSuccess("User registered successfully", null);
    }
}
