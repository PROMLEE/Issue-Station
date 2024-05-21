import { Select } from "flowbite-react";
// import { useParams } from "react-router-dom";
import detail from "../assets/projectdetail_dump.json";
import issuelist from "../assets/issuelist_dump.json";
import { IssueCard } from "../Components/IssueCard";
import { Tag } from "../Components/Tag";
import { Date } from "../Components/Date";
import { Link } from "react-router-dom";
import { IssueCreate } from "../Layout/IssueCreate";
export const Project = () => {
  // const params = useParams();
  return (
    <>
      <div className="flex flex-col gap-2">
        {/* <div>Project{params.id}</div> */}
        <div className="text-xl font-bold">제목: {detail.title}</div>
        <div className="font-bold">설명: {detail.description}</div>
        <Date date={detail.initdate} />
        <div>{detail.isprisvate ? "private" : "public"} Project</div>
        {detail.participants.map((member) => {
          return (
            <div className="flex gap-2 my-1" key={member.userid}>
              {member.name}: <Tag status={member.Role} />
            </div>
          );
        })}
        <IssueCreate />
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
        {issuelist.issuelist.map((issue) => {
          return <IssueCard issue={issue} key={issue.issuesid} />;
        })}
      </div>
    </>
  );
};
