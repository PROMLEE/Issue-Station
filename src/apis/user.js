import { authAPI, baseAPI } from "./customApi";

const Login = async (request) => {
  try {
    const response = await baseAPI.post(`user/login`, request);
    // console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("로그인 에러");
  }
};

const Signup = async (request) => {
  try {
    const response = await baseAPI.post(`user/signup`, request);
    // console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("회원가입 에러");
  }
};

const CheckId = async (request) => {
  try {
    const response = await baseAPI.post(`user/id`, request);
    // console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("아이디 중복 확인 에러");
  }
};

const CheckNickname = async (request) => {
  try {
    const response = await baseAPI.post(`user/nickname`, request);
    // console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("닉네임 중복 확인 에러");
  }
};

const TokenCheck = async () => {
  try {
    const response = await authAPI.get(`user/check`);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("토큰 만료. 로그아웃");
  }
};
export { Login, Signup, CheckId, CheckNickname, TokenCheck };
