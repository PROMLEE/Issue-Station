import { Card, Dropdown, DropdownItem } from "flowbite-react";
import { Link } from "react-router-dom";
import { Date } from "./Date";
import isLogin from "../util/checklogin";
import { useNavigate } from "react-router-dom";
export function ProjectCard({ project }) {
  const nav = useNavigate();
  const handleDetail = () => {
    if (!isLogin() && project.isPrivate) {
      alert("로그인이 필요합니다.");
      return;
    } else {
      nav(`/project/${project.id}`);
    }
  };
  return (
    <Card className="md:min-w-[20rem] m-5 flex-grow">
      <div className="flex justify-end px-4 pt-4">
        <Dropdown inline label="">
          <DropdownItem>
            <Link
              to="/"
              className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-600 dark:hover:text-white"
            >
              Edit
            </Link>
          </DropdownItem>
          <DropdownItem>
            <Link
              to="/"
              className="block px-4 py-2 text-sm text-red-600 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-600 dark:hover:text-white"
            >
              Delete
            </Link>
          </DropdownItem>
        </Dropdown>
      </div>
      <div className="flex flex-col items-center gap-3">
        <img
          alt="thumbnail"
          src={project.thumbnaillink || "/assets/logo.png"}
          height="96"
          width="96"
          className="mb-3 rounded-full shadow-lg"
        />
        <h5 className="mb-1 text-xl font-medium text-gray-900 dark:text-white">
          {project.name}
        </h5>
        <span className="text-sm text-gray-500 dark:text-gray-400">
          {project.description}
        </span>
        <span className="text-sm text-gray-500 dark:text-gray-400">
          {project.isPrivate ? "Private" : "Public"}
        </span>
        {/* <div className="text-xs text-slate-400 mt-1">
          Admin: {project.admin}
        </div> */}
        <Date date={project.initdate} style={`text-xs text-slate-400`} />
        <div className="mt-4 flex space-x-3 lg:mt-6">
          <button
            onClick={handleDetail}
            className="inline-flex items-center rounded-lg bg-cyan-700 px-4 py-2 text-center text-sm font-medium text-white hover:bg-cyan-800 focus:outline-none focus:ring-4 focus:ring-cyan-300 dark:bg-cyan-600 dark:hover:bg-cyan-700 dark:focus:ring-cyan-800"
          >
            View Detail
          </button>
        </div>
      </div>
    </Card>
  );
}
