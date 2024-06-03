import { authAPI, baseAPI } from "./customApi";

const JoinTeam = async (id, request) => {
  try {
    const response = await authAPI.post(`project/team/${id}`, request);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("팀 생성 에러");
  }
};

const JoinProject = async (request) => {
  try {
    const response = await authAPI.post(`project/create`, request);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("프로젝트 생성 에러");
  }
};

const SearchProject = async (name) => {
  try {
    const response = await baseAPI.get(`project/search`, {
      params: { name: name },
    });
    // console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("프로젝트 검색 에러");
  }
};

const MyProject = async () => {
  try {
    const response = await authAPI.get(`project/my`);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("내 프로젝트 조회 에러");
  }
};

const ProjectDetail = async (id) => {
  try {
    const response = await authAPI.get(`project/info/${id}`);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("프로젝트 조회 에러");
  }
};

const GetMember = async (id) => {
  try {
    const response = await baseAPI.get(`project/team/member/${id}`);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("프로젝트 멤버 조회 에러");
  }
};

const MyRole = async (id) => {
  try {
    const response = await authAPI.get(`project/role/${id}`);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("내 역할 조회 에러");
  }
};

export {
  JoinTeam,
  JoinProject,
  SearchProject,
  MyProject,
  ProjectDetail,
  GetMember,
  MyRole,
};
