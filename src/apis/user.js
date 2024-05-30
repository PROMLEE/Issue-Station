import { baseAPI } from "./customApi";

// const Handler = async (provider) => {
//   try {
//     const response = await baseAPI.get(`/oauth2/state/${provider}`);
//     return response;
//   } catch (e) {
//     console.log(e);
//     alert("연동 에러");
//   }
// };

const Login = async (request) => {
  try {
    const response = await baseAPI.post(`user/login`, request);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("로그인 에러");
  }
};

export { Login };
