import { Select } from "flowbite-react";
// import { useParams } from "react-router-dom";
import { IssueCard } from "../Components/IssueCard";
import { Tag } from "../Components/Tag";
import { Date } from "../Components/Date";
import { Link } from "react-router-dom";
import { IssueCreate } from "../Layout/IssueCreate";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { ProjectDetail, GetMember, MyRole } from "../apis/project";
import { SearchIssue } from "../apis/issue";
import isLogin from "../util/checklogin";

export const Project = () => {
  const params = useParams();
  const [detail, setDetail] = useState({});
  const [issuelist, setIssuelist] = useState([]);
  const [member, setMember] = useState([]);
  const [myRole, setMyRole] = useState("");
  const [search, setSearch] = useState("");
  const [status, setStatus] = useState("");
  const [searchType, setSearchType] = useState("name");

  useEffect(() => {
    getProjectDetail();
    getIssueList();
    getMember();
    if (isLogin()) {
      getRole();
    }
  }, []);

  useEffect(() => {
    getIssueList();
  }, [search, status]);

  const getProjectDetail = async () => {
    const response = await ProjectDetail(params.id);
    setDetail(response.data.result);
  };

  const getIssueList = async () => {
    const response = await SearchIssue(params.id, {
      name: search,
      status: status,
    });
    setIssuelist(response.data);
  };

  const getMember = async () => {
    const response = await GetMember(params.id);
    setMember(response.data);
  };

  const getRole = async () => {
    const response = await MyRole(params.id);
    setMyRole(response.data.result);
  };

  return (
    <div className="md:w-2/3 md:mx-auto">
      <div className="flex flex-col md:flex-row md:justify-between gap-2">
        {/* <div>Project{params.id}</div> */}
        <div className="flex flex-col gap-2">
          <div className="text-3xl font-bold">{detail.name}</div>
          <div className="font-bold">{detail.description}</div>
          <Date date={detail.initdate} />
          <div className="">
            <div className="inline font-bold">
              {detail.isPrivate ? "private" : "public"}
            </div>{" "}
            Project
          </div>
          <div className="border rounded-lg p-3 bg-slate-100">
            <div className="text-2xl font-bold mb-3 text-sky-600">
              Project Member
            </div>
            {member.map((member, i) => {
              return (
                <div className="flex gap-2 my-1" key={i}>
                  {member.nickname}: <Tag status={member.role} />
                </div>
              );
            })}
          </div>
        </div>
        {myRole === "TESTER" ? (
          <IssueCreate pid={params.id} role={myRole} />
        ) : null}
        {/* <IssueCreate pid={params.id} /> */}
      </div>
      <div
        id="default-popover"
        className="text-gray-900 border-gray-200  px-5 py-2 mt-5 font-bold"
      >
        이슈 검색하기
      </div>
      <div className="flex items-center mx-5 md:w-3/4  mb-5">
        <div className="w-3/5 flex items-center">
          {searchType === "name" ? (
            <input
              className="h-10 border-2 border-gray-200 focus:outline-none"
              onChange={(e) => {
                setStatus("");
                setSearch(e.target.value);
              }}
            />
          ) : (
            <Select
              id="status"
              required
              onChange={(e) => {
                setSearch("");
                setStatus(e.target.value);
              }}
              className="h-10 focus:outline-none"
            >
              <option>NEW</option>
              <option>ASSIGNED</option>
              <option>RESOLVED</option>
              <option>CLOSED</option>
              <option>REOPENED</option>
            </Select>
          )}
        </div>
        <div className="max-w-md">
          <Select
            id="search"
            required
            onChange={(e) => {
              setSearchType(e.target.value);
              setSearch("");
              setStatus("NEW");
            }}
          >
            <option>name</option>
            <option>status</option>
          </Select>
        </div>
      </div>
      <div className="flex flex-wrap">
        {issuelist.length ? (
          issuelist.map((issue) => {
            return <IssueCard issue={issue} role={myRole} key={issue.id} />;
          })
        ) : (
          <div className="text-center w-full">No Issue</div>
        )}
      </div>
    </div>
  );
};
