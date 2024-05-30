import { authAPI } from "./customApi";

const CreateIssue = async (id, request) => {
  try {
    const response = await authAPI.post(`issue/create/${id}`, request);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("이슈 생성 에러");
  }
};

const IssueDetail = async (id) => {
  try {
    const response = await authAPI.get(`issue/info/${id}`);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("이슈 상세 조회 에러");
  }
};

const SetIssueState = async (id, request) => {
  try {
    const response = await authAPI.put(`issue/state/${id}`, request);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("이슈 상태 변경 에러");
  }
};

// const SearchIssue = async () => {
//   try {
//     const response = await authAPI.get(`issue`);
//     // console.log(response);
//     return response;
//   } catch (e) {
//     console.log(e);
//     alert("이슈 조회 에러");
//   }
// };

const DeleteIssue = async (id) => {
  try {
    const response = await authAPI.delete(`issue/delete/${id}`);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("이슈 삭제 에러");
  }
};

export { CreateIssue, IssueDetail, DeleteIssue, SetIssueState };
