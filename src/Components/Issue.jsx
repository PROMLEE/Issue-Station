import { useParams } from "react-router-dom";
import issue from "../assets/issue_dump.json";
import { Button } from "flowbite-react";
export const Issue = () => {
  const params = useParams();
  return (
    <div>
      <div>Issue{params.id}</div>
      <div>title: {issue.title}</div>
      <div>description: {issue.description}</div>
      <div className="flex gap-2 my-1">
        <div>status: </div>
        <div className="bg-slate-800 text-white text-sm rounded-md p-0.5">
          {issue.status}
        </div>
      </div>
      <div className="underline">reporter: {issue.reporter}</div>
      <div className="underline">assignee: {issue.assignee}</div>
      <div>created_at: {issue.created_at}</div>
      {issue.comments.map((comment) => {
        return (
          <div className="border-2 border-slate-200 my-2 p-2 rounded-md">
            <div>comment: {comment.comment}</div>
            <div>commenter: {comment.commenter}</div>
            <div>created_at: {comment.created_at}</div>
          </div>
        );
      })}
      <div className="flex gap-2 mt-4">
        <Button>Edit</Button>
        <Button>Set Assignee</Button>
        <Button>Set Fixed</Button>
        <Button>Set Closed</Button>
        <Button>Set Reopened</Button>
      </div>
    </div>
  );
};
