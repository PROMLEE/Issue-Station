import { useParams } from "react-router-dom";
import detail from "../assets/projectdetail_dump.json";
import issuelist from "../assets/issuelist_dump.json";
import { IssueCard } from "./IssueCard";
export const Project = () => {
  const params = useParams();
  return (
    <div>
      <div>Project{params.id}</div>
      <div>title: {detail.title}</div>
      <div>description: {detail.description}</div>
      <div>created_at: {detail.created_at}</div>
      <div>updated_at: {detail.updated_at}</div>
      {detail.participants.map((member) => {
        return (
          <div className="flex gap-2 my-1">
            <div>{member.name}</div>
            <div className="bg-slate-800 text-white text-sm rounded-md p-0.5">
              {member.Role}
            </div>
          </div>
        );
      })}
      {issuelist.issuelist.map((issue) => {
        return <IssueCard issue={issue} />;
      })}
    </div>
  );
};
