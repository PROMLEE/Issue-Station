import { Link } from "react-router-dom";

export const IssueCard = ({ issue }) => {
  return (
    <Link
      className="border-2 border-sky-400 m-3 p-2 rounded-md block hover:bg-sky-100"
      to={`/issue/${issue.id}`}
    >
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
    </Link>
  );
};
