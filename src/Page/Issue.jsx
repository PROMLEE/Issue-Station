// import { useParams } from "react-router-dom";
import issue from "../assets/issue_dump.json";
import { Tag } from "../Components/Tag";
import { Date } from "../Components/Date";
import { IssueCreate } from "../Layout/IssueCreate";
export const Issue = () => {
  // const params = useParams();
  return (
    <div className="flex flex-col gap-2 md:w-2/3 mx-auto">
      {/* <div>Issue{params.id}</div> */}
      <div className="font-bold text-2xl">{issue.name}</div>
      <div className="text-slate-500">{issue.description}</div>
      <div className="flex gap-2 my-1">
        <div>status: </div>
        <Tag status={issue.status} />
      </div>
      <div className="">reporter: {issue.reporter}</div>
      <div className="">assignee: {issue.assignee}</div>
      <Date date={issue.initdate} />
      {issue.comments.map((comment) => {
        return (
          <div
            className="border-t-2  border-slate-200 p-2 flex flex-col gap-2"
            key={comment.commentid}
          >
            <div>commenter: {comment.commenter}</div>
            <Date date={comment.initdate} />
            <div className="text-slate-500">{comment.comment}</div>
          </div>
        );
      })}
      <IssueCreate edit={true} />
    </div>
  );
};
