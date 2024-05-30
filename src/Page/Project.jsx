import { Select } from "flowbite-react";
// import { useParams } from "react-router-dom";
import { IssueCard } from "../Components/IssueCard";
import { Tag } from "../Components/Tag";
import { Date } from "../Components/Date";
import { Link } from "react-router-dom";
import { IssueCreate } from "../Layout/IssueCreate";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { ProjectDetail, GetMember } from "../apis/project";
import { SearchIssue } from "../apis/issue";

export const Project = () => {
  const params = useParams();
  const [detail, setDetail] = useState({});
  const [issuelist, setIssuelist] = useState([]);
  const [member, setMember] = useState([]);

  useEffect(() => {
    getProjectDetail();
    getIssueList();
    getMember();
  }, []);

  const getProjectDetail = async () => {
    const response = await ProjectDetail(params.id);
    setDetail(response.data.result);
  };

  const getIssueList = async () => {
    const response = await SearchIssue(params.id);
    setIssuelist(response.data);
  };

  const getMember = async () => {
    const response = await GetMember(params.id);
    setMember(response.data);
  };

  return (
    <div className="md:w-2/3 md:mx-auto">
      <div className="flex flex-col md:flex-row md:justify-between gap-2">
        {/* <div>Project{params.id}</div> */}
        <div className="flex flex-col gap-2">
          <div className="text-3xl font-bold">{detail.name}</div>
          <div className="font-bold">{detail.description}</div>
          <Date date={detail.initdate} />
          <div>{detail.isPrivate ? "private" : "public"} Project</div>
          <div className="text-2xl font-bold mt-3">Project Member</div>
          {member.map((member, i) => {
            return (
              <div className="flex gap-2 my-1" key={i}>
                {member.nickname}: <Tag status={member.role} />
              </div>
            );
          })}
        </div>
        <IssueCreate pid={params.id} />
      </div>
      <div
        id="default-popover"
        className="text-gray-900 border-gray-200  px-5 py-2 mt-5 font-bold"
      >
        이슈 검색하기
      </div>
      <div className="flex items-center mx-5 md:w-1/2">
        <div className="w-3/5 flex items-center">
          <input className="h-10 w-5/6 border-2 border-gray-200 focus:outline-none" />
          <Link to="#" className="w-1/6">
            <button className="h-10 text-sm w-full bg-gray-200">→</button>
          </Link>
        </div>
        <div className="max-w-md ml-3">
          <Select id="search" required>
            <option>name</option>
            <option>status</option>
            <option>reporter</option>
            <option>assignee</option>
          </Select>
        </div>
      </div>
      <div className="flex flex-wrap">
        {issuelist.length &&
          issuelist.map((issue) => {
            return <IssueCard issue={issue} key={issue.id} />;
          })}
      </div>
    </div>
  );
};
