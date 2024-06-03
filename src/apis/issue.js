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
    const response = await authAPI.post(`issue/state/${id}`, request);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("이슈 상태 변경 에러");
  }
};

const CommentIssue = async (id, comment) => {
  try {
    const response = await authAPI.post(`issue/comment/create/${id}`, comment);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("댓글 작성 에러");
  }
};

const SearchIssue = async (id, params) => {
  try {
    const response = await authAPI.get(`issue/search/${id}`, {
      params: params,
    });
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("이슈 조회 에러");
  }
};

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

const CommentList = async (id) => {
  try {
    const response = await authAPI.get(`issue/comment/${id}`);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("댓글 조회 에러");
  }
};

const SetReporter = async (id, nickname) => {
  try {
    const response = await authAPI.post(`issue/reporter/${id}`, { nickname });
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("담당자 변경 에러");
  }
};
const SetAssignee = async (id, nickname) => {
  try {
    const response = await authAPI.post(`issue/assignee/${id}`, { nickname });
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("개발자 변경 에러");
  }
};

const SetFixer = async (id, nickname) => {
  try {
    const response = await authAPI.post(`issue/fixer/${id}`, { nickname });
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("수정자 변경 에러");
  }
};

const IssueDelete = async (id) => {
  try {
    const response = await authAPI.delete(`issue/delete/${id}`);
    console.log(response);
    return response;
  } catch (e) {
    console.log(e);
    alert("이슈 삭제 에러");
  }
};

export {
  CreateIssue,
  IssueDetail,
  DeleteIssue,
  SetIssueState,
  CommentIssue,
  SearchIssue,
  CommentList,
  SetReporter,
  SetAssignee,
  SetFixer,
  IssueDelete,
};
