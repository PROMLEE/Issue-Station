package com.cau.issuemanagement.issuestation.Service;


import com.cau.issuemanagement.issuestation.Dto.ResponseDto;
import com.cau.issuemanagement.issuestation.Dto.SignupDto;
import com.cau.issuemanagement.issuestation.Entity.UserEntity;
import com.cau.issuemanagement.issuestation.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public ResponseDto<String> signUp(SignupDto signupDto) {
        UserEntity userEntity = new UserEntity(signupDto);
        userRepository.save(userEntity);
        return ResponseDto.setSuccess("User registered successfully", null);
    }
}
