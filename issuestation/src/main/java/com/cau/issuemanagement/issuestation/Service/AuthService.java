package com.cau.issuemanagement.issuestation.Service;


import com.cau.issuemanagement.issuestation.Dto.ResponseDto;
import com.cau.issuemanagement.issuestation.Dto.SignupDto;
import com.cau.issuemanagement.issuestation.Entity.UserEntity;
import com.cau.issuemanagement.issuestation.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired UserRepository userRepository;
    public ResponseDto<?> signUp(SignupDto dto) {
        String userNickname = dto.getNickname();
        String userId = dto.getId();
        String userPassword = dto.getPassword();
        String userPasswordCheck = dto.getPasswordCheck();

        try {
            if(userRepository.existsById(userNickname)) {
                return ResponseDto.setFailed("Existed Nickname gay ya!",  null);
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("Database Error", null);
        }

        try {
            if(userRepository.existsById(userId)) {
                return ResponseDto.setFailed("Existed Id gay ya!", null);
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("Database Error", null);
        }

        if(!userPassword.equals(userPasswordCheck)) {
            return ResponseDto.setFailed("Password is not Existed",null);
        }

        UserEntity userEntity = new UserEntity(dto);

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            return ResponseDto.setFailed("Database Error", null);
        }

        return ResponseDto.setSuccess("SignUp Success!", null);
    }
}
