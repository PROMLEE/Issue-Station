import axios from "axios";

const BASE_URL = process.env.REACT_APP_URI;

// 인터셉터 추가 함수
const Interceptors = (instance) => {
  instance.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem("accessToken");
      if (token) {
        config.headers["Authorization"] = token;
      }
      return config;
    },
    (error) => Promise.reject(error)
  );
};

const axiosInstance = (auth) => {
  const instance = axios.create({
    baseURL: BASE_URL,
    timeout: 15000,
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (auth) {
    Interceptors(instance);
  }
  return instance;
};

export const baseAPI = axiosInstance(false);
export const authAPI = axiosInstance(true);
