import { useParams } from "react-router-dom";
// import issue from "../assets/issue_dump.json";
import { Tag } from "../Components/Tag";
import { Date } from "../Components/Date";
// import { IssueCreate } from "../Layout/IssueCreate";
import islogin from "../util/checklogin";
import { useState, useEffect } from "react";
import { IssueDetail, CommentIssue, CommentList } from "../apis/issue";
import { Button, TextInput } from "flowbite-react";
import { loginstate } from "../recoil/user";
import { useRecoilValue } from "recoil";
export const Issue = () => {
  const isLogin = islogin();

  const params = useParams();

  const user = useRecoilValue(loginstate);

  const [issue, setIssue] = useState({});
  const [comment, setComment] = useState("");
  const [commenton, setCommenton] = useState(false);
  const [comments, setComments] = useState([]);

  useEffect(() => {
    getIssueDetail();
    getCommentList();
  }, [params]);

  const getIssueDetail = async () => {
    const response = await IssueDetail(params.id);
    setIssue(response.data.result);
  };

  const getCommentList = async () => {
    const response = await CommentList(params.id);
    setComments(response.data);
  };

  const handleComment = async () => {
    if (commenton && comment !== "") {
      const response = await CommentIssue(params.id, {
        comment: comment,
        tag: "OTHERS",
      });
      if (response.status === 200) {
        setComments([
          ...comments,
          {
            comment: comment,
            nickname: user.nickname,
            tag: "OTHERS",
            modDate: "now",
          },
        ]);
        getIssueDetail();
        setComment("");
      } else {
        alert("댓글 작성 실패");
      }
    }
    setCommenton(!commenton);
  };
  return (
    <div className="flex flex-col gap-2 md:w-2/3 mx-auto">
      <div className="font-bold text-2xl">{issue.name}</div>
      <div className="text-slate-500">{issue.description}</div>
      <div className="flex gap-2 my-1">
        <div>status: </div>
        <Tag status={issue.status} />
      </div>
      <div className="">
        reporter: <div className="inline font-bold">{issue.reporter}</div>
      </div>
      <div className="">
        assignee: <div className="inline font-bold">{issue.assignee}</div>
      </div>
      <Date date={issue.modDate} />
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
      })}
      {/* <IssueCreate edit={true} /> */}
      {commenton && (
        <TextInput
          value={comment}
          onChange={(e) => {
            setComment(e.target.value);
          }}
        />
      )}
      {isLogin && <Button onClick={handleComment}>Comment</Button>}
    </div>
  );
};
