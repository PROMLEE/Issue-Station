import { useParams } from "react-router-dom";
// import issue from "../assets/issue_dump.json";
import { Tag } from "../Components/Tag";
import { Date } from "../Components/Date";
// import { IssueCreate } from "../Layout/IssueCreate";
import islogin from "../util/checklogin";
import { useState, useEffect } from "react";
import {
  IssueDetail,
  CommentIssue,
  CommentList,
  SetIssueState,
  SetAssignee,
  SetFixer,
  IssueDelete,
} from "../apis/issue";
import { Button, TextInput } from "flowbite-react";
import { loginstate } from "../recoil/user";
import { useRecoilValue } from "recoil";
import { GetMember } from "../apis/project";
import { CheckNickname } from "../apis/user";
import { useLocation } from "react-router-dom";

export const Issue = () => {
  const location = useLocation();
  const isLogin = islogin();

  const params = useParams();

  const user = useRecoilValue(loginstate);

  const [issue, setIssue] = useState({});
  const [comment, setComment] = useState("");
  const [commenton, setCommenton] = useState(false);
  const [comments, setComments] = useState([]);
  const [members, setMembers] = useState([]);
  const [setmember, setSetmember] = useState(false);
  const [nickname, setNickname] = useState("");
  const [assign, setAssign] = useState("");
  const [commentbutton, setCommentbutton] = useState(true);
  const [otherbutton, setOtherbutton] = useState(true);
  const [showmembers, setShowmembers] = useState(false);

  useEffect(() => {
    getIssueDetail();
    getCommentList();
  }, []);

  const handleCheckNickname = async (e) => {
    e.preventDefault();
    const response = await CheckNickname(nickname);
    if (response.data.result) {
      alert("존재하지 않는 닉네임입니다.");
      setAssign("");
    } else {
      setAssign(nickname);
    }
    // setNickname("");
  };

  const getIssueDetail = async () => {
    const response = await IssueDetail(params.id);
    setIssue(response.data.result);
    getMembers(response.data.result.projectId);
  };

  const DeleteIssue = async () => {
    if (window.confirm("정말 삭제하시겠습니까?")) {
      const response = await IssueDelete(params.id);
      if (response.status === 200) {
        alert("이슈 삭제 성공");
        window.history.back();
      } else {
        alert("이슈 삭제 실패");
      }
    }
  };

  const getCommentList = async () => {
    const response = await CommentList(params.id);
    setComments(response.data);
  };

  const getMembers = async (id) => {
    const response = await GetMember(id);
    setMembers(response.data);
  };

  const setAssignee = async (nickname) => {
    const response = await SetAssignee(params.id, nickname);
    if (response.status === 200) {
      getIssueDetail();
    } else {
      alert("담당자 변경 실패");
    }
  };

  const setFixer = async (nickname) => {
    const response = await SetFixer(params.id, nickname);
    if (response.status === 200) {
      getIssueDetail();
    } else {
      alert("개발자 변경 실패");
    }
  };

  const setState = async (status) => {
    const response = await SetIssueState(params.id, { status });
    if (response.status === 200) {
      getIssueDetail();
    } else {
      alert("상태 변경 실패");
    }
  };

  const handleComment = async (tag) => {
    if (commenton && comment !== "") {
      if (tag === "OTHERS") {
        setOtherbutton(!otherbutton);
      }
      const response = await CommentIssue(params.id, {
        comment: comment,
        tag: tag,
      });
      console.log(response);
      if (response.status === 200) {
        setComments([
          ...comments,
          {
            comment: comment,
            nickname: user.nickname,
            tag: tag,
            modDate: "now",
          },
        ]);
        getIssueDetail();
        setComment("");
      } else {
        alert("댓글 작성 실패");
      }
    } else {
      if (tag === "OTHERS") {
        setOtherbutton(!otherbutton);
      }
    }
    setCommenton(!commenton);
  };

  const handleState = async (state) => {
    if (commenton) {
      if (state === "ASSIGNED" && nickname !== "") {
        setAssignee(nickname);
        setShowmembers(!showmembers);
        setState(state);
      } else if (state === "FIXED") {
        setFixer(nickname);
        setState(state);
      } else if (state === "RESOLVED") {
        setState(state);
      } else if (state === "CLOSED") {
        setState(state);
      } else if (state === "REOPENED") {
        setState(state);
      }
      setSetmember(false);
      setCommenton(false);
      setCommentbutton(!commentbutton);
      setAssign("");
    } else {
      setCommentbutton(!commentbutton);
      if (state === "ASSIGNED") {
        setSetmember(true);
        setShowmembers(!showmembers);
      }
      setCommenton(!commenton);
    }
  };

  return (
    <div className="flex flex-col gap-2 md:w-2/3 mx-auto">
      <div className="font-bold text-2xl">{issue.name}</div>
      <div className="text-slate-500">{issue.description}</div>
      <div className="flex gap-2 my-1">
        <div>status: </div>
        <Tag status={issue.status} />
      </div>
      <div className="flex gap-2 my-1">
        <div>priority: </div>
        {issue.priority ? (
          <Tag status={issue.priority} />
        ) : (
          <Tag status={"MAJOR"} />
        )}
      </div>
      <div className="">
        reporter:{" "}
        <div
          className={`inline font-bold ${
            user.nickname === issue.reporter && "text-purple-500"
          }`}
        >
          {issue.reporter}
        </div>
      </div>
      <div className="">
        assignee:{" "}
        <div
          className={`inline font-bold ${
            user.nickname === issue.assignee && "text-purple-500"
          }`}
        >
          {issue.assignee}
        </div>
      </div>
      <div className="">
        fixer:{" "}
        <div
          className={`inline font-bold ${
            user.nickname === issue.fixer && "text-purple-500"
          }`}
        >
          {issue.fixer}
        </div>
      </div>
      <Date date={issue.initDate} />
      <Date date={issue.modDate} create={false} />
      {showmembers &&
        members.map((member, i) => {
          return (
            <div className="flex gap-2 my-1" key={i}>
              {member.nickname}: <Tag status={member.role} />
            </div>
          );
        })}
      {/* <IssueCreate edit={true} /> */}
      {setmember &&
        (assign === "" ? (
          <>
            <div className="text-2xl font-bold">팀 멤버 할당</div>
            <form
              className="flex gap-2"
              onSubmit={(e) => {
                handleCheckNickname(e);
              }}
            >
              <TextInput
                className="w-4/6"
                type="id"
                placeholder="닉네임"
                required
                shadow
                value={nickname}
                onChange={(e) => {
                  setNickname(e.target.value);
                }}
              />
              <Button className="w-2/6" type="submit">
                추가
              </Button>
            </form>
          </>
        ) : (
          <div>{assign}</div>
        ))}
      {commenton && (
        <TextInput
          value={comment}
          placeholder="comment"
          onChange={(e) => {
            setComment(e.target.value);
          }}
        />
      )}
      <div className="flex w-full gap-3">
        {isLogin && commentbutton && (
          <Button
            onClick={() => handleComment("OTHERS")}
            className="bg-blue-400 text-black"
          >
            Add Comment
          </Button>
        )}
        {otherbutton && (
          <>
            {location.state.role === "PL" &&
              (issue.status === "NEW" || issue.status === "REOPENED") && (
                <Button
                  onClick={() => {
                    handleComment("SET_ASSIGNEE");
                    handleState("ASSIGNED");
                  }}
                >
                  Set Assigned
                </Button>
              )}
            {location.state.role === "DEVELOPER" &&
              issue.status === "ASSIGNED" && (
                <Button
                  onClick={() => {
                    handleComment("SET_FIXED");
                    handleState("FIXED");
                  }}
                >
                  Set Fixed
                </Button>
              )}
            {location.state.role === "TESTER" && issue.status === "FIXED" && (
              <Button
                onClick={() => {
                  handleComment("SET_RESOLVED");
                  handleState("RESOLVED");
                }}
              >
                Set Resolved
              </Button>
            )}
            {location.state.role === "PL" && issue.status === "RESOLVED" && (
              <Button
                onClick={() => {
                  handleComment("SET_CLOSED");
                  handleState("CLOSED");
                }}
              >
                Set Colsed
              </Button>
            )}
            {location.state.role === "TESTER" && issue.status === "CLOSED" && (
              <Button
                onClick={() => {
                  handleComment("SET_REOPENED");
                  handleState("REOPENED");
                }}
              >
                Set Reopened
              </Button>
            )}
          </>
        )}
      </div>
      {comments.map((comment, index) => {
        return (
          <div
            className="border-t-2  border-slate-200 p-2 flex flex-col gap-2"
            key={index}
          >
            <div className="flex justify-between">
              <div>
                commenter:{" "}
                <div className="inline font-bold">{comment.nickname}</div>
              </div>
              <Date date={comment.modDate} />
            </div>
            <div className="text-yellow-700 font-bold">TAG: {comment.tag}</div>
            <div className="text-slate-500">{comment.comment}</div>
          </div>
        );
      })}{" "}
      <div className="flex w-full gap-3">
        {isLogin && location.state.role === "PL" && (
          <Button onClick={DeleteIssue} className="bg-red-500 text-black">
            Delete Issue
          </Button>
        )}
      </div>
    </div>
  );
};
