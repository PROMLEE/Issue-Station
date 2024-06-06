package com.issuestation.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.issuestation.Dto.UserDto.Id.IdDto;
import com.issuestation.Dto.UserDto.Id.IdResponseDto;
import com.issuestation.Dto.UserDto.Login.LoginDto;
import com.issuestation.Dto.UserDto.Login.LoginResponseDto;
import com.issuestation.Dto.UserDto.Nickname.NicknameDto;
import com.issuestation.Dto.UserDto.Nickname.NicknameResponseDto;
import com.issuestation.Dto.UserDto.Token.TokenResponseDto;
import com.issuestation.Entity.User;
import com.issuestation.Security.TokenProvider;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.issuestation.apiPayload.code.status.ErrorStatus;
import com.issuestation.apiPayload.exception.handler.TempHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuestation.Dto.ResponseDto;
import com.issuestation.Dto.UserDto.Signup.SignupDto;
import com.issuestation.Service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.BDDMockito.given;


//junit5와 Mockito 연동
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    //테스트 대상인 AuthController에 가짜 객체 의존성 넣기
    @InjectMocks
    private AuthController authController;

    //AuthService에는 가짜 객체 생성을 위해 @Mock
    @Mock
    private AuthService authService;


    //컨트롤러를 테스트하기 위해 HTTP 호출
    //MockMVC 사용해서 호출이 가능하다
    private MockMvc mockMvc;

    //Json
    private ObjectMapper objectMapper = new ObjectMapper();

    //각 테스트를 실행하기 전 MockMvc 인스터스를 초기화
    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @DisplayName("회원 가입 성공")
    @Test
    void signUpSuccess() throws Exception{
        //given
        SignupDto signupDto = new SignupDto("test", "testN", "1004");
        ResponseDto<String> expectedResponse = ResponseDto.setSuccess("User registered successfully", null);

        //given을 통해 생성한 signUpDto가 받는다면, willReturn으로 expectedResponse가 되도록 하였습니다.
        given(authService.signUp(signupDto)).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));
    }

    @DisplayName("로그인 성공")
    @Test
    void loginSuccess() throws Exception {
        // given
        LoginDto loginDto = new LoginDto("testId", "testPw");
        User testUser = User.builder()
                .id(1L)
                .loginId("testId")
                .loginPw("testPw")
                .nickname("testNickname")
                .build();
        String token = "generatedJwtToken";
        LoginResponseDto loginResponseDto = new LoginResponseDto(token, 3600000, testUser);
        ResponseDto<LoginResponseDto> expectedResponse = ResponseDto.setSuccess("Login Success", loginResponseDto);

        given(authService.login(loginDto)).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));
    }

    @DisplayName("닉네임 중복")
    @Test
    public void whenNicknameDuplicate_thenReturns400() throws Exception {
        NicknameDto nicknameDto = new NicknameDto("사용중인닉네임");
        NicknameResponseDto nicknameResponseDto = new NicknameResponseDto(true);
        ResponseDto<NicknameResponseDto> responseDto = ResponseDto.setFailed("Nickname already exists", nicknameResponseDto);

        given(authService.nickname(nicknameDto)).willReturn(responseDto);

        mockMvc.perform(post("/user/nickname")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nicknameDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
    }
    @DisplayName("사용 가능한 닉네임")
    @Test
    public void whenNicknameNotDuplicate_thenReturns200() throws Exception {
        NicknameDto nicknameDto = new NicknameDto("사용가능한닉네임");
        NicknameResponseDto nicknameResponseDto = new NicknameResponseDto(false);
        ResponseDto<NicknameResponseDto> responseDto = ResponseDto.setSuccess("Nickname not duplicate", nicknameResponseDto);

        given(authService.nickname(nicknameDto)).willReturn(responseDto);

        mockMvc.perform(post("/user/nickname")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nicknameDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
    }


    @DisplayName("아이디 중복")
    @Test
    void whenIdDuplicate_thenReturns400() throws Exception {
        IdDto idDto = new IdDto("사용중인아이디");
        IdResponseDto idResponseDto = new IdResponseDto(true);
        ResponseDto<IdResponseDto> responseDto = ResponseDto.setFailed("Id already exists", idResponseDto);

        given(authService.id(idDto)).willReturn(responseDto);

        mockMvc.perform(post("/user/id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(idDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
    }

    @DisplayName("사용 가능한 아이디")
    @Test
    public void whenIdDuplicate_thenReturns200() throws Exception {
        IdDto idDto = new IdDto("사용가능한아이디");
        IdResponseDto idResponseDto = new IdResponseDto(false);
        ResponseDto<IdResponseDto> responseDto = ResponseDto.setSuccess("Id not duplicate", idResponseDto);

        given(authService.id(idDto)).willReturn(responseDto);

        mockMvc.perform(post("/user/id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(idDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
    }

    @DisplayName("유효한 토큰으로 유저 조회")
    @Test
    void getUserProjects_withValidToken() throws Exception {
        // given
        String validToken = "Bearer 유효한토큰"; // 실제 유효한 토큰으로 대체 필요
        TokenResponseDto tokenResponseDto = new TokenResponseDto(1L, "testLoginId", "testNickname");
        ResponseDto<TokenResponseDto> expectedResponse = ResponseDto.setSuccess("Token valid", tokenResponseDto);

        given(authService.token(anyString())).willReturn(expectedResponse);

        // when & then
        mockMvc.perform(get("/user/check")
                        .header("Authorization", validToken))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));
    }

    @DisplayName("유효하지 않은 토큰으로 유저 조회 시 오류 반환")
    @Test
    void getUserProjects_withInvalidToken() throws Exception {
        // given
        String invalidToken = "Bearer 유효하지않은토큰";
        given(authService.token(anyString())).willThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token"));

        // when & then
        mockMvc.perform(get("/user/check")
                        .header("Authorization", invalidToken))
                .andExpect(status().isUnauthorized());
    }
}